/**
 * Copyright 2009 Jens Kapitza
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.sourceforge.ajaxtags.tags;

import javax.servlet.jsp.JspException;
import javax.xml.transform.TransformerException;

import net.sourceforge.ajaxtags.helpers.XMLUtils;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Wraps a DisplayTag (http://displaytag.org) table, enabling AJAX capabilities. In the process,
 * anchors in the navigation are rewritten on the fly so that the DisplayTag table refreshes within
 * the same region on the page without a full-page reload.
 * 
 * @author Darren Spurgeon
 * @version $Revision: 86 $ $Date: 2007/07/08 17:52:30 $ $Author: jenskapitza $
 */
public class AjaxDisplayTag extends AjaxAreaTag {

    private static final long serialVersionUID = -5945152631578965550L;

    private String pagelinksClass;

    private String columnClass;

    /**
     * @return Returns the pagelinksClass.
     */
    public String getPagelinksClass() {
        return this.pagelinksClass;
    }

    /**
     * @param pagelinksClass
     *            The pagelinksClass to set.
     */
    public void setPagelinksClass(String pagelinksClass) {
        this.pagelinksClass = pagelinksClass;
    }

    /**
     * @return Returns the columnClass.
     */
    public String getColumnClass() {
        return this.columnClass;
    }

    /**
     * @param columnClass
     *            The columnClass to set.
     */
    public void setColumnClass(String columnClass) {
        this.columnClass = columnClass;
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    @Override
    public void releaseTag() {
        super.releaseTag();
        this.pagelinksClass = "pagelinks";
        this.columnClass = "sortable";
    }

    private void rewriteAnchors0(Document document) {
        // String ypath = "//span[@class = \"" + getPagelinksClass() + "\"]//a";

        NodeList links = document.getElementsByTagName("a");
        for (int i = 0; i < links.getLength(); i++) {
            Node link = links.item(i);
            Node parent = link.getParentNode();
            boolean rewrite = false;
            Attr clazz = (Attr) parent.getAttributes().getNamedItem("class");
            if (parent.getNodeName().equals("span")) {
                rewrite = clazz != null && clazz.getNodeValue().contains(getPagelinksClass());
            }
            if (parent.getNodeName().equals("th")) {
                rewrite = clazz != null && clazz.getNodeValue().contains(getColumnClass());
            }

            if (rewrite) {
                rewriteLink(link, getId());
            }
        }
    }

    /**
     * @throws JspException
     * @see net.sourceforge.ajaxtags.tags.AjaxAreaTag#processContent(java.lang.String)
     */
    @Override
    protected String processContent(String content) throws JspException {
        try {
            Document doc = getDocument(content);
            rewriteAnchors0(doc);
            return XMLUtils.toString(doc);
        } catch (SAXException e) {
            throw new JspException(e);
        } catch (TransformerException e) {
            throw new JspException(e);
        }
    }
}

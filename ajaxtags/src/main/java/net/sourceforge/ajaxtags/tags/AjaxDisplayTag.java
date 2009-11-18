/*
 * Copyright 2009 AjaxTags-Team
 *
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

import static org.apache.commons.lang.StringUtils.trimToNull;

import javax.servlet.jsp.JspException;
import javax.xml.transform.TransformerException;

import net.sourceforge.ajaxtags.helpers.XMLUtils;

import org.apache.commons.lang.StringUtils;
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
     * Default constructor.
     */
    public AjaxDisplayTag() {
        super();
        init();
    }

    /**
     * Initialize properties to default values. Used in {@link #AjaxDisplayTag()} and in
     * {@link #releaseTag()}.
     */
    private void init() {
        this.pagelinksClass = "pagelinks";
        this.columnClass = "sortable";
    }

    /**
     * @return Returns the pagelinksClass.
     */
    public String getPagelinksClass() {
        return this.pagelinksClass;
    }

    /**
     * @param pagelinksClass
     *            The pagelinksClass to set. Null-safe.
     */
    public void setPagelinksClass(final String pagelinksClass) {
        // this.pagelinksClass = pagelinksClass == null ? StringUtils.EMPTY : pagelinksClass;
        this.pagelinksClass = trimToNull(pagelinksClass);
    }

    /**
     * @return Returns the columnClass.
     */
    public String getColumnClass() {
        return this.columnClass;
    }

    /**
     * @param columnClass
     *            The columnClass to set. Null-safe.
     */
    public void setColumnClass(final String columnClass) {
        // this.columnClass = columnClass == null ? StringUtils.EMPTY : columnClass;
        this.columnClass = trimToNull(columnClass);
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    @Override
    public void releaseTag() {
        super.releaseTag();
        init();
    }

    private void rewriteAnchors0(final Document document) {
        final NodeList links = document.getElementsByTagName("a");
        for (int i = 0; i < links.getLength(); i++) {
            final Node link = links.item(i);
            final Node parent = link.getParentNode();

            final Attr parentClass = (Attr) parent.getAttributes().getNamedItem("class");
            if (parentClass == null) {
                continue;
            }

            boolean rewrite = false;
            final String parentName = parent.getNodeName();
            final String parentClassValue = parentClass.getNodeValue();
            if ("span".equals(parentName)) {
                rewrite = StringUtils.contains(parentClassValue, getPagelinksClass());
            } else if ("th".equals(parentName)) {
                rewrite = StringUtils.contains(parentClassValue, getColumnClass());
            }

            if (rewrite) {
                rewriteLink(link, getId());
            }
        }
    }

    /**
     * Rewrite anchors in content.
     * 
     * Parse content to XHTML {@link org.w3c.dom.Document}, rewrite DisplayTag anchor elements and
     * return string representation of document.
     * 
     * @throws JspException
     * @see net.sourceforge.ajaxtags.tags.AjaxAreaTag#processContent(java.lang.String)
     */
    @Override
    protected String processContent(final String content) throws JspException {
        try {
            final Document doc = getDocument(content);
            rewriteAnchors0(doc);
            return XMLUtils.toString(doc);
        } catch (SAXException e) {
            throw new JspException(e);
        } catch (TransformerException e) {
            throw new JspException(e);
        }
    }
}

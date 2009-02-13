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
import javax.xml.xpath.XPathExpressionException;

import net.sourceforge.ajaxtags.helpers.XMLUtils;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



/**
 * Rewrites HTML anchor tags (<A>), replacing the href attribute with an onclick
 * event so that retrieved content is loaded inside a region on the page.
 * 
 * @author Darren Spurgeon
 * @author Jens Kapitza
 * @version $Revision: 86 $ $Date: 2007/07/22 16:29:16 $ $Author: jenskapitza $
 */
public class AjaxAnchorsTag extends BaseAjaxBodyTag {

    private static final long serialVersionUID = -1732745741282114289L;


    @Override
    public int doEndTag() throws JspException {
        out(ajaxAnchors());
        return EVAL_PAGE;
    }


    protected String ajaxAnchors() throws JspException {
        return ajaxAnchors(getBody(), getTarget());
    }


    public String ajaxAnchors(final String html, final String target) throws JspException {
        try {
            return ajaxAnchors0(html, target);
        }
        catch (Exception e) {
            throw new JspException("rewrite links faild < is the content xhtml? > \n" + html, e);
        }
    }


    private String ajaxAnchors0(final String html, final String target) throws Exception {
        Document document = getDocument(html);
        return XMLUtils.toString(rewriteAnchors(document, target));
    }


    public Document rewriteAnchors(Document document, String target)
                    throws XPathExpressionException {
        return rewriteAnchors(document, target, null);
    }


    public Document rewriteAnchors(Document document, String target, String className)
                    throws XPathExpressionException {
        String ypath = "//a";
        String xclass = "@class=\"" + className + "\"";

        if (className != null) {
            ypath = ypath + "[" + xclass + "]";
        }

        final NodeList links = XMLUtils.evaluateXPathExpression(ypath, document);// document.getElementsByTagName("a");
        for (int i = 0; i < links.getLength(); i++) {
            rewriteLink(links.item(i), target);
        }
        return document;
    }


    public void rewriteLink(Node link, String target) {
        NamedNodeMap map = link.getAttributes();
        Attr href = (Attr) map.getNamedItem("href");
        Attr onclick = (Attr) map.getNamedItem("onclick");
        if (onclick == null) {
            onclick = link.getOwnerDocument().createAttribute("onclick");
            map.setNamedItem(onclick);
        }
        onclick.setValue(getOnclickAjax(target, href.getValue(), getOptionsBuilder()));
        href.setValue("javascript://nop/");
    }


    public static Document getDocument(final String html) throws SAXException {
        String xhtml = trim2Null(html); // .replaceAll("<br(.*?)>", "<br$1/>");
        if (xhtml == null) {
            return null;
        }
        return XMLUtils.getXMLDocument(WARP0 + xhtml + WARP1);
    }


    private static final String WARP0 = "<div>";
    private static final String WARP1 = "</div>";
}

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

import static net.sourceforge.ajaxtags.helpers.StringUtils.trim2Null;

import javax.servlet.jsp.JspException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import net.sourceforge.ajaxtags.helpers.XMLUtils;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Rewrites HTML anchor tags (&lt;A&gt;), replacing the href attribute with an onclick event so that
 * retrieved content is loaded inside a region on the page.
 *
 * @author Darren Spurgeon
 * @author Jens Kapitza
 * @version $Revision: 86 $ $Date: 2007/07/22 16:29:16 $ $Author: jenskapitza $
 */
public class AjaxAnchorsTag extends BaseAjaxBodyTag {

    private static final long serialVersionUID = -1732745741282114289L;

    private static final String WARP0 = "<div>";
    private static final String WARP1 = "</div>";

    @Override
    public int doEndTag() throws JspException {
        out(ajaxAnchors(getBody(), getTarget(), getSourceClass()));
        return EVAL_PAGE;
    }

    /**
     * Rewrite anchors.
     *
     * @param html
     *            XHTML source
     * @param target
     *            target of request
     * @param clazz
     *            CSS class of anchors
     * @return rewritten and reformatted XHTML text
     * @throws JspException
     *             on errors
     */
    public String ajaxAnchors(final String html, final String target, final String clazz)
            throws JspException {
        try {
            return rewriteAnchors(getDocument(html), target, clazz);
        } catch (XPathExpressionException e) {
            throw new JspException("rewrite links failed (wrong XPath expression)\n" + html, e);
        } catch (TransformerException e) {
            throw new JspException(
                    "rewrite links failed (cannot transform XHTML to text)\n" + html, e);
        } catch (SAXException e) {
            throw new JspException("rewrite links failed (is the content xhtml?)\n" + html, e);
        }
    }

    private String rewriteAnchors(final Document document, final String target,
            final String className) throws XPathExpressionException, TransformerException {
        String xpath = "//a";
        if (className != null) {
            xpath = xpath + "[@class=\"" + className + "\"]";
        }

        final NodeList links = XMLUtils.evaluateXPathExpression(xpath, document);
        // document.getElementsByTagName("a");
        for (int i = 0; i < links.getLength(); i++) {
            rewriteLink(links.item(i), target);
        }
        return XMLUtils.toString(document);
    }

    /**
     * Rewrite link. Change (or create) "onclick" attribute, set "href" attribute to
     * "javascript://nop/".
     *
     * @param link
     *            node of document with link
     * @param target
     *            target of request
     */
    protected final void rewriteLink(final Node link, final String target) {
        final NamedNodeMap map = link.getAttributes();
        final Attr href = (Attr) map.getNamedItem("href");
        if (href != null) {
            Attr onclick = (Attr) map.getNamedItem("onclick");
            if (onclick == null) {
                onclick = link.getOwnerDocument().createAttribute("onclick");
                map.setNamedItem(onclick);
            }
            onclick.setValue(getOnclickAjax(target, href.getValue(), getOptionsBuilder()));
            href.setValue("javascript://nop/");
        }
    }

    /**
     * Parse XHTML document from given string.
     *
     * @param html
     *            string with XHTML content
     * @return parsed document or null
     * @throws SAXException
     *             if string cannot be parsed
     */
    protected static final Document getDocument(final String html) throws SAXException {
        final String xhtml = trim2Null(html); // .replaceAll("<br(.*?)>", "<br$1/>");
        if (xhtml == null) {
            return null;
        }
        return XMLUtils.getXMLDocument(WARP0 + xhtml + WARP1);
    }

}

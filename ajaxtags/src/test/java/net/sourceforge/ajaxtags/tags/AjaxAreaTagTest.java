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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTag;
import javax.xml.transform.TransformerException;

import net.sourceforge.ajaxtags.FakeBodyContent;
import net.sourceforge.ajaxtags.FakeHttpServletRequest;
import net.sourceforge.ajaxtags.FakePageContext;
import net.sourceforge.ajaxtags.helpers.XMLUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * Test for AjaxAreaTag.
 *
 * @author Victor Homyakov
 * @version $Revision$ $Date$ $Author$
 */
public class AjaxAreaTagTest {

    private static final String TAG_CLASS = "textArea";
    private static final String TAG_ID = "ajaxFrame";
    private AjaxAreaTag tag;

    /**
     * Set up.
     */
    @Before
    public void setUp() {
        tag = new AjaxAreaTag();
        tag.setBodyContent(new FakeBodyContent());
        tag.setPageContext(new FakePageContext());
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
        tag.release();
    }

    /**
     * Test method for tag content generation in response to usual HTTP request.
     *
     * @throws JspException
     *             on errors
     * @throws IOException
     *             on BodyContent errors
     * @throws SAXException
     *             if any parse errors occur
     * @throws TransformerException
     *             if it is not possible to transform document to string
     */
    @Test
    public void testTag() throws JspException, IOException, TransformerException, SAXException {
        final PageContext context = new FakePageContext();
        tag.setPageContext(context);

        tag.setId(TAG_ID);
        tag.setStyleClass(TAG_CLASS);
        tag.setAjaxAnchors(true);

        context.getOut().print("<div>before tag");

        assertEquals("doStartTag() must return BodyTag.EVAL_BODY_BUFFERED",
                BodyTag.EVAL_BODY_BUFFERED, tag.doStartTag());

        final String html = "<div>Text<br/>link to " + "<a href=\"pagearea.jsp\">itself</a>"
                + "<br/>Text</div>";
        final String expected = "<div>before tag"
                + "<div class=\"textArea\" id=\"ajaxFrame\"><div>"
                + "<div>Text<br/>link to "
                + "<a href=\"javascript://nop/\" onclick=\"new AjaxJspTag.OnClick({baseUrl: &quot;pagearea.jsp&quot;, eventBase: this, requestHeaders: ['x-request-target', '"
                + TAG_ID + "'], target: &quot;ajaxFrame&quot;}); return false;\">itself</a>"
                + "<br/>Text</div>" + "</div></div>" + "after tag</div>";

        tag.getBodyContent().print(html);

        assertEquals("doAfterBody() must return BodyTag.SKIP_BODY", BodyTag.SKIP_BODY, tag
                .doAfterBody());
        assertEquals("doEndTag() must return BodyTag.EVAL_PAGE", BodyTag.EVAL_PAGE, tag.doEndTag());

        context.getOut().print("after tag</div>");
        final String content = ((FakeBodyContent) context.getOut()).getString();

        // .replaceAll("[\\s|\n|\r\n]","") dirty hack, problem with WS remove all! cause
        // we just need to check the javascript here
        assertEquals("HTML after doEndTag()", XMLUtils.format(expected).replaceAll("[\\s|\n|\r\n]",
                ""), XMLUtils.format(content).replaceAll("[\\s|\n|\r\n]", ""));
    }

    /**
     * Test method for tag content generation in response to AJAX request.
     *
     * @throws JspException
     *             on errors
     * @throws IOException
     *             on BodyContent errors
     * @throws SAXException
     *             if any parse errors occur
     * @throws TransformerException
     *             if it is not possible to transform document to string
     */
    @Test
    public void testTagAjax() throws JspException, IOException, TransformerException, SAXException {
        final PageContext context = new FakePageContext();
        tag.setPageContext(context);
        ((FakeHttpServletRequest) context.getRequest())
                .setHeader(AjaxAreaTag.TARGET_HEADER, TAG_ID);
        ((FakeHttpServletRequest) context.getRequest()).setHeader(BaseAjaxBodyTag.HEADER_FLAG,
                BaseAjaxBodyTag.HEADER_FLAG_VALUE);

        tag.setId(TAG_ID);
        tag.setStyleClass(TAG_CLASS);
        tag.setAjaxAnchors(true);

        context.getOut().print("<div>before tag");

        assertEquals("doStartTag() must return BodyTag.EVAL_BODY_BUFFERED",
                BodyTag.EVAL_BODY_BUFFERED, tag.doStartTag());

        final String html = "<div>Text<br/>link to " + "<a href=\"pagearea.jsp\">itself</a>"
                + "<br/>Text</div>";
        final String expected = "<div>"
                + "<div>Text<br/>link to "
                + "<a href=\"javascript://nop/\" onclick=\"new AjaxJspTag.OnClick({baseUrl: &quot;pagearea.jsp&quot;, eventBase: this, requestHeaders: ['x-request-target', '"
                + TAG_ID + "'], target: &quot;ajaxFrame&quot;}); return false;\">itself</a>"
                + "<br/>Text</div>" + "</div>";
        tag.getBodyContent().print(html);

        assertEquals("doAfterBody() must return BodyTag.SKIP_BODY", BodyTag.SKIP_BODY, tag
                .doAfterBody());
        assertEquals("doEndTag() must return BodyTag.SKIP_PAGE", BodyTag.SKIP_PAGE, tag.doEndTag());

        // context.getOut().print("after tag</div>"); SKIP_PAGE
        final String content = ((FakeBodyContent) context.getOut()).getString();
        assertEquals("HTML after doEndTag()", XMLUtils.format(expected), XMLUtils.format(content));
    }

    /**
     * Test method for {@link AjaxAreaTag#isAjaxRequest()}.
     */
    @Test
    public void testIsAjaxRequest() {
        assertFalse("Request without headers", tag.isAjaxRequest());

        tag.setId(TAG_ID);
        final PageContext context = new FakePageContext();
        tag.setPageContext(context);

        ((FakeHttpServletRequest) context.getRequest())
                .setHeader(AjaxAreaTag.TARGET_HEADER, TAG_ID);
        ((FakeHttpServletRequest) context.getRequest()).setHeader(BaseAjaxBodyTag.HEADER_FLAG,
                BaseAjaxBodyTag.HEADER_FLAG_VALUE);
        assertTrue("Request with proper " + BaseAjaxBodyTag.HEADER_FLAG + " and "
                + AjaxAreaTag.TARGET_HEADER + " headers", tag.isAjaxRequest());

        ((FakeHttpServletRequest) context.getRequest()).setHeader(AjaxAreaTag.TARGET_HEADER, TAG_ID
                + "1");
        assertFalse("Request with proper " + BaseAjaxBodyTag.HEADER_FLAG + " header and invalid "
                + AjaxAreaTag.TARGET_HEADER + " header", tag.isAjaxRequest());
    }

    /**
     * Test method for {@link AjaxAreaTag#processContent(String)}.
     *
     * @throws JspException
     *             on errors
     * @throws SAXException
     *             if any parse errors occur
     * @throws TransformerException
     *             if it is not possible to transform document to string
     */
    @Test
    public void testProcessContent() throws JspException, TransformerException, SAXException {
        tag.setAjaxAnchors(false);
        String html = null, expected = null;
        assertEquals("null content", expected, tag.processContent(html));

        html = "";
        expected = "";
        assertEquals("empty content", expected, tag.processContent(html));

        tag.setAjaxAnchors(true);
        html = "content";
        expected = "<div>content</div>";
        assertEquals("simple content", XMLUtils.format(expected), tag.processContent(html));

        html = " content ";
        expected = "<div>content</div>";
        assertEquals("trimming whitespace", XMLUtils.format(expected), tag.processContent(html));

        tag.setId(TAG_ID);
        tag.setStyleClass(TAG_CLASS);
        tag.setAjaxAnchors(true);
        html = "<div>Text<br/>link to " + "<a href=\"pagearea.jsp\">itself</a>" + "<br/>Text</div>";
        expected = "<div>"
                + "<div>Text<br/>link to "
                + "<a href=\"javascript://nop/\" onclick=\"new AjaxJspTag.OnClick({baseUrl: &quot;pagearea.jsp&quot;, eventBase: this, requestHeaders: ['x-request-target', '"
                + TAG_ID + "'], target: &quot;ajaxFrame&quot;}); return false;\">itself</a>"
                + "<br/>Text</div>" + "</div>";
        assertEquals(XMLUtils.format(expected), tag.processContent(html));
    }

}

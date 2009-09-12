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
/**
 *
 */
package net.sourceforge.ajaxtags.tags;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTag;
import javax.xml.transform.TransformerException;

import net.sourceforge.ajaxtags.FakeBodyContent;
import net.sourceforge.ajaxtags.FakePageContext;
import net.sourceforge.ajaxtags.helpers.XMLUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * Test for AjaxAnchorsTag.
 *
 * @author В.Хомяков
 * @version $Revision$ $Date$ $Author$
 */
public class AjaxAnchorsTagTest {

    private static final String HEADER = "";

    private static final String BASE_URL = "http://localhost:8080/test/test.do";

    private AjaxAnchorsTag tag;

    /**
     * Set up.
     */
    @Before
    public void setUp() {
        tag = new AjaxAnchorsTag();
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
     * Test method for {@link AjaxAnchorsTag#doEndTag()}.
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
    public void testDoEndTag() throws JspException, IOException, TransformerException, SAXException {
        final OptionsBuilder options = OptionsBuilder.getOptionsBuilder();
        final PageContext context = new FakePageContext();
        tag.setPageContext(context);
        // tag.setVar("ajaxAnchors");
        tag.setTarget("target");

        assertEquals("doStartTag() must return BodyTag.EVAL_BODY_BUFFERED",
                BodyTag.EVAL_BODY_BUFFERED, tag.doStartTag());

        final String html = "<a href=\"" + BASE_URL + "\">testDoEndTag</a>";
        final String expected = HEADER + "<div>" + "<a href=\"javascript://nop/\" "
                + "onclick=\"new AjaxJspTag.OnClick({" + "baseUrl: &quot;" + BASE_URL + "&quot;"
                + options.getOptionsDelimiter() + "eventBase: this" + options.getOptionsDelimiter()
                + "requestHeaders: ['x-request-target', 'target']" + options.getOptionsDelimiter()
                + "target: &quot;target&quot;" + "});" + " return false;\">testDoEndTag</a>"
                + "</div>";

        tag.getBodyContent().print(html);

        assertEquals("doAfterBody() must return BodyTag.SKIP_BODY", BodyTag.SKIP_BODY, tag
                .doAfterBody());
        assertEquals("doEndTag() must return BodyTag.EVAL_PAGE", BodyTag.EVAL_PAGE, tag.doEndTag());
        final String content = ((FakeBodyContent) context.getOut()).getString();
        assertEquals("HTML after doEndTag()", XMLUtils.format(expected), content);
    }

    /**
     * Test method for {@link AjaxAnchorsTag#ajaxAnchors(String, String, String)}.
     *
     * @throws JspException
     *             on errors
     * @throws SAXException
     *             if any parse errors occur
     * @throws TransformerException
     *             if it is not possible to transform document to string
     */
    @Test
    public void testAjaxAnchors() throws JspException, TransformerException, SAXException {
        final OptionsBuilder options = OptionsBuilder.getOptionsBuilder();

        String html = "HTML content";
        String expected = HEADER + "<div>HTML content</div>";// + IOUtils.LINE_SEPARATOR;
        assertEquals("HTML w/o links", XMLUtils.format(expected), tag.ajaxAnchors(html, "target",
                null));

        html = "html <a>link</a>";
        expected = HEADER + "<div>html <a>link</a>" + "</div>";
        assertEquals("HTML with empty link", XMLUtils.format(expected), tag.ajaxAnchors(html,
                "target", null));

        html = "html <a href=\"" + BASE_URL + "\">testAjaxAnchors</a>";
        expected = HEADER + "<div>html <a href=\"javascript://nop/\" "
                + "onclick=\"new AjaxJspTag.OnClick({" + "baseUrl: &quot;" + BASE_URL + "&quot;"
                + options.getOptionsDelimiter() + "eventBase: this" + options.getOptionsDelimiter()
                + "requestHeaders: ['x-request-target', 'target']" + options.getOptionsDelimiter()
                + "target: &quot;target&quot;" + "});" + " return false;\">testAjaxAnchors</a>"
                + "</div>";
        assertEquals("HTML with link", XMLUtils.format(expected), tag.ajaxAnchors(html, "target",
                null));
    }
}

/*
 * Copyright 2009-2010 AjaxTags-Team
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

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.xml.transform.TransformerException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * Test for AjaxAnchorsTag.
 *
 * @author Victor Homyakov
 */
public class AjaxAnchorsTagTest extends AbstractTagTest<AjaxAnchorsTag> {

    private static final String HEADER = "";

    private static final String BASE_URL = "http://localhost:8080/test/test.do";

    /**
     * Set up.
     *
     * @throws Exception
     *             if setUp fails
     */
    @Before
    public void setUp() throws Exception {
        setUp(AjaxAnchorsTag.class);
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
        tag.setTarget("target");

        assertStartTagEvalBody();

        final String html = "<a href=\"" + BASE_URL + "\">testDoEndTag</a>";
        final String expected = HEADER + "<div>" + "<a href=\"javascript://nop/\" "
                + "onclick=\"new AjaxJspTag.OnClick({" + "baseUrl: &quot;" + BASE_URL + "&quot;"
                + options.getOptionsDelimiter() + "eventBase: this" + options.getOptionsDelimiter()
                + "requestHeaders: ['x-request-target', 'target']" + options.getOptionsDelimiter()
                + "target: &quot;target&quot;" + "});" + " return false;\">testDoEndTag</a>"
                + "</div>";

        tag.getBodyContent().print(html);

        assertAfterBody();
        assertEndTag();

        assertContent(expected);
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
        String expected = HEADER + "<div>HTML content</div>"; // + IOUtils.LINE_SEPARATOR;
        assertContent(expected, tag.ajaxAnchors(html, "target", null));

        html = "html <a>link</a>";
        expected = HEADER + "<div>html <a>link</a>" + "</div>";
        assertContent(expected, tag.ajaxAnchors(html, "target", null));

        html = "html <a href=\"" + BASE_URL + "\">testAjaxAnchors</a>";
        expected = HEADER + "<div>html <a href=\"javascript://nop/\" "
                + "onclick=\"new AjaxJspTag.OnClick({" + "baseUrl: &quot;" + BASE_URL + "&quot;"
                + options.getOptionsDelimiter() + "eventBase: this" + options.getOptionsDelimiter()
                + "requestHeaders: ['x-request-target', 'target']" + options.getOptionsDelimiter()
                + "target: &quot;target&quot;" + "});" + " return false;\">testAjaxAnchors</a>"
                + "</div>";
        assertContent(expected, tag.ajaxAnchors(html, "target", null));
    }
}

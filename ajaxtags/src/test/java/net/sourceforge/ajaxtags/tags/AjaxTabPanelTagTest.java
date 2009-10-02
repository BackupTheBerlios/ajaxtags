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
import static org.junit.Assert.fail;

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
 * Test for AjaxTabPanelTag.
 *
 * @author Victor Homyakov
 * @version $Revision$ $Date$ $Author$
 */
public class AjaxTabPanelTagTest {

    private AjaxTabPanelTag tag;

    /**
     * Set up.
     */
    @Before
    public void setUp() {
        tag = new AjaxTabPanelTag();
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

    @Test
    public void testGetPages() {
        assertEquals("No pages", "[]", tag.getPages());

        tag.addPage(page(1));
        assertEquals("One page", "[" + pageText(1) + "]", tag.getPages());

        tag.addPage(page(2));
        assertEquals("Two pages", "[" + pageText(1) + "," + pageText(2) + "]", tag.getPages());

        tag.addPage(page(3));
        assertEquals("Three pages",
                "[" + pageText(1) + "," + pageText(2) + "," + pageText(3) + "]", tag.getPages());
    }

    /**
     * Test method for tag content generation.
     *
     * @throws JspException
     *             on tag errors
     */
    @Test
    public void testDoEndTagEmpty() throws JspException {
        assertEquals("doStartTag() must return BodyTag.EVAL_BODY_BUFFERED",
                BodyTag.EVAL_BODY_BUFFERED, tag.doStartTag());
        assertEquals("doAfterBody() must return BodyTag.SKIP_BODY", BodyTag.SKIP_BODY, tag
                .doAfterBody());

        try {
            tag.doEndTag();
            fail("Empty tab panel should throw JspException");
        } catch (JspException e) {
            // annotation Test(expected) for exception doesn't work for me
        }
    }

    /**
     * Test method for tag content generation.
     *
     * @throws JspException
     *             on tag errors
     * @throws SAXException
     *             if any parse errors occur
     * @throws TransformerException
     *             if it is not possible to transform document to string
     */
    @Test
    public void testDoEndTag() throws JspException, TransformerException, SAXException {
        final PageContext context = new FakePageContext();
        tag.setPageContext(context);

        assertEquals("doStartTag() must return BodyTag.EVAL_BODY_BUFFERED",
                BodyTag.EVAL_BODY_BUFFERED, tag.doStartTag());

        tag.addPage(page(1));
        tag.addPage(page(2));

        assertEquals("doAfterBody() must return BodyTag.SKIP_BODY", BodyTag.SKIP_BODY, tag
                .doAfterBody());
        assertEquals("doEndTag() must return BodyTag.EVAL_PAGE", BodyTag.EVAL_PAGE, tag.doEndTag());

        final String content = ((FakeBodyContent) context.getOut()).getString();
        final String expected = "<div><script type=\"text/javascript\">"
                + "new AjaxJspTag.TabPanel({pages: [" + pageText(1) + "," + pageText(2) + "]});"
                + "</script></div>";

        assertEquals("HTML after doEndTag()", reformat(expected), reformat(content));
    }

    private String reformat(final String html) throws TransformerException, SAXException {
        return XMLUtils.format(html).replaceAll("[\\s\r\n]", "");
    }

    private AjaxTabPageTag page(final int n) {
        final AjaxTabPageTag page = new AjaxTabPageTag();
        page.setCaption("c" + n);
        page.setBaseUrl("b" + n);
        return page;
    }

    private String pageText(final int n) {
        return "{baseUrl: \"b" + n + "\", caption: \"c" + n + "\"}";
    }

}

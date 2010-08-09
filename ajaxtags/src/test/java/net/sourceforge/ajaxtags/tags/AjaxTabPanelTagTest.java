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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.servlet.jsp.JspException;
import javax.xml.transform.TransformerException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * Test for AjaxTabPanelTag.
 *
 * @author Victor Homyakov
 */
public class AjaxTabPanelTagTest extends AbstractTagTest<AjaxTabPanelTag> {

    private static final String SCRIPT_START = "<script type=\"text/javascript\">new AjaxJspTag.TabPanel({";
    private static final String SCRIPT_END = "});</script>";

    /**
     * Set up.
     *
     * @throws Exception
     *             if setUp fails
     */
    @Before
    public void setUp() throws Exception {
        setUp(AjaxTabPanelTag.class);
    }

    /**
     * Test list of tabs.
     */
    @Test
    public void testGetPages() {
        assertEquals("No pages", "[]", tag.getPages());

        tag.addPage(page(1));
        assertEquals("One page", '[' + pageText(1) + ']', tag.getPages());

        tag.addPage(page(2));
        assertEquals("Two pages", '[' + pageText(1) + ',' + pageText(2) + ']', tag.getPages());

        final int page3 = 3;
        tag.addPage(page(page3));
        assertEquals("Three pages", '[' + pageText(1) + ',' + pageText(2) + ',' + pageText(page3)
                + ']', tag.getPages());
    }

    /**
     * Test method for tag content generation.
     *
     * @throws JspException
     *             on tag errors
     */
    @Test(expected = JspException.class)
    public void testDoEndTagEmpty() throws JspException {
        assertStartTagEvalBody();
        assertAfterBody();

        tag.doEndTag();
        fail("Empty tab panel should throw JspException");
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
        assertStartTagEvalBody();

        tag.addPage(page(1));
        tag.addPage(page(2));

        assertAfterBody();
        assertEndTag();

        final String expected = "<div>" + SCRIPT_START + "pages: [" + pageText(1) + ","
                + pageText(2) + "]" + SCRIPT_END + "</div>";

        assertContent(expected);
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
    public void testDoEndTagIds() throws JspException, TransformerException, SAXException {
        assertStartTagEvalBody();

        tag.addPage(page(1));
        tag.addPage(page(2));
        tag.setId("idTabs");
        tag.setContentId("idContent");

        assertAfterBody();
        assertEndTag();

        final String expected = "<div id=\"idTabs\">" + SCRIPT_START
                + "contentId:\"idContent\",id:\"idTabs\",pages: [" + pageText(1) + ","
                + pageText(2) + "]" + SCRIPT_END + "</div>";

        assertContent(expected);
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
    public void testDoEndTagClasses() throws JspException, TransformerException, SAXException {
        assertStartTagEvalBody();

        tag.addPage(page(1));
        tag.addPage(page(2));
        tag.setStyleClass("customPanelClass");

        assertAfterBody();
        assertEndTag();

        final String expected = "<div class=\"customPanelClass\">" + SCRIPT_START + "pages: ["
                + pageText(1) + "," + pageText(2) + "]" + SCRIPT_END + "</div>";

        assertContent(expected);
    }

    private AjaxTabPageTag page(final int pageNo) {
        final AjaxTabPageTag page = new AjaxTabPageTag();
        page.setCaption("c" + pageNo);
        page.setBaseUrl("b" + pageNo);
        return page;
    }

    private String pageText(final int pageNo) {
        return "{baseUrl: \"b" + pageNo + "\", caption: \"c" + pageNo + "\"}";
    }

}

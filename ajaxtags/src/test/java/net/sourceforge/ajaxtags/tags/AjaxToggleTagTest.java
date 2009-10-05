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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.Tag;
import javax.xml.transform.TransformerException;

import net.sourceforge.ajaxtags.FakeBodyContent;
import net.sourceforge.ajaxtags.FakePageContext;
import net.sourceforge.ajaxtags.helpers.XMLUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * Test for AjaxToggleTag.
 *
 * @author Victor Homyakov
 * @version $Revision$ $Date$ $Author$
 */
public class AjaxToggleTagTest {

    private AjaxToggleTag tag;

    /**
     * Set up.
     */
    @Before
    public void setUp() {
        tag = new AjaxToggleTag();
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
     * Test method for tag content generation (star rating with empty value).
     *
     * @throws JspException
     *             on tag errors
     * @throws SAXException
     *             if any parse errors occur
     * @throws TransformerException
     *             if it is not possible to transform document to string
     */
    @Test
    public void testDoEndTag0() throws JspException, TransformerException, SAXException {
        final PageContext context = new FakePageContext();
        tag.setPageContext(context);
        tag.setContainerClass("star-rating");
        tag.setRatings(",Two,Three");

        assertEquals("doStartTag() must return Tag.SKIP_BODY", Tag.SKIP_BODY, tag.doStartTag());
        assertEquals("doAfterBody() must return BodyTag.SKIP_BODY", BodyTag.SKIP_BODY, tag
                .doAfterBody());
        assertEquals("doEndTag() must return BodyTag.EVAL_PAGE", BodyTag.EVAL_PAGE, tag.doEndTag());

        final String content = ((FakeBodyContent) context.getOut()).getString();
        final String expected = "<div class=\"star-rating\">"
                + "<a href=\"javascript://nop\" title=\"\"></a>"
                + "<a href=\"javascript://nop\" title=\"Two\"></a>"
                + "<a href=\"javascript://nop\" title=\"Three\"></a>"
                + "<script type=\"text/javascript\">"
                + "new AjaxJspTag.Toggle({containerClass: \"star-rating\", ratings: \",Two,Three\"});"
                + "</script></div>";

        assertEquals("HTML after doEndTag()", reformat(expected), reformat(content));
    }

    /**
     * Test method for tag content generation (star rating).
     *
     * @throws JspException
     *             on tag errors
     * @throws SAXException
     *             if any parse errors occur
     * @throws TransformerException
     *             if it is not possible to transform document to string
     */
    @Test
    public void testDoEndTag1() throws JspException, TransformerException, SAXException {
        final PageContext context = new FakePageContext();
        tag.setPageContext(context);
        tag.setContainerClass("star-rating");
        tag.setRatings("One,Two,Three");

        assertEquals("doStartTag() must return Tag.SKIP_BODY", Tag.SKIP_BODY, tag.doStartTag());
        assertEquals("doAfterBody() must return BodyTag.SKIP_BODY", BodyTag.SKIP_BODY, tag
                .doAfterBody());
        assertEquals("doEndTag() must return BodyTag.EVAL_PAGE", BodyTag.EVAL_PAGE, tag.doEndTag());

        final String content = ((FakeBodyContent) context.getOut()).getString();
        final String expected = "<div class=\"star-rating\">"
                + "<a href=\"javascript://nop\" title=\"One\"></a>"
                + "<a href=\"javascript://nop\" title=\"Two\"></a>"
                + "<a href=\"javascript://nop\" title=\"Three\"></a>"
                + "<script type=\"text/javascript\">"
                + "new AjaxJspTag.Toggle({containerClass: \"star-rating\", ratings: \"One,Two,Three\"});"
                + "</script></div>";

        assertEquals("HTML after doEndTag()", reformat(expected), reformat(content));
    }

    /**
     * Test method for tag content generation (star rating with selected element).
     *
     * @throws JspException
     *             on tag errors
     * @throws SAXException
     *             if any parse errors occur
     * @throws TransformerException
     *             if it is not possible to transform document to string
     */
    @Test
    public void testDoEndTag2() throws JspException, TransformerException, SAXException {
        final PageContext context = new FakePageContext();
        tag.setPageContext(context);
        tag.setContainerClass("star-rating");
        tag.setRatings("One,Two,Three");
        tag.setDefaultRating("Two");
        tag.setSelectedClass("selected");

        assertEquals("doStartTag() must return Tag.SKIP_BODY", Tag.SKIP_BODY, tag.doStartTag());
        assertEquals("doAfterBody() must return BodyTag.SKIP_BODY", BodyTag.SKIP_BODY, tag
                .doAfterBody());
        assertEquals("doEndTag() must return BodyTag.EVAL_PAGE", BodyTag.EVAL_PAGE, tag.doEndTag());

        final String content = ((FakeBodyContent) context.getOut()).getString();
        final String expected = "<div class=\"star-rating\">"
                + "<a href=\"javascript://nop\" title=\"One\"></a>"
                + "<a href=\"javascript://nop\" class=\"selected\" title=\"Two\"></a>"
                + "<a href=\"javascript://nop\" title=\"Three\"></a>"
                + "<script type=\"text/javascript\">"
                + "new AjaxJspTag.Toggle({containerClass: \"star-rating\", defaultRating: \"Two\", ratings: \"One,Two,Three\", selectedClass: \"selected\"});"
                + "</script></div>";

        assertEquals("HTML after doEndTag()", reformat(expected), reformat(content));
    }

    /**
     * Test method for tag content generation (on-off toggle).
     *
     * @throws JspException
     *             on tag errors
     * @throws SAXException
     *             if any parse errors occur
     * @throws TransformerException
     *             if it is not possible to transform document to string
     */
    @Test
    public void testDoEndTagOnOff() throws JspException, TransformerException, SAXException {
        final PageContext context = new FakePageContext();
        tag.setPageContext(context);
        tag.setContainerClass("power-rating");
        tag.setRatings("true,false");
        tag.setOnOff("true");

        assertEquals("doStartTag() must return Tag.SKIP_BODY", Tag.SKIP_BODY, tag.doStartTag());
        assertEquals("doAfterBody() must return BodyTag.SKIP_BODY", BodyTag.SKIP_BODY, tag
                .doAfterBody());
        assertEquals("doEndTag() must return BodyTag.EVAL_PAGE", BodyTag.EVAL_PAGE, tag.doEndTag());

        final String content = ((FakeBodyContent) context.getOut()).getString();
        final String expected = "<div class=\"power-rating onoff\">"
                + "<a href=\"javascript://nop\" title=\"false\"></a>"
                + "<script type=\"text/javascript\">"
                + "new AjaxJspTag.Toggle({containerClass: \"power-rating\", ratings: \"true,false\"});"
                + "</script></div>";

        assertEquals("HTML after doEndTag()", reformat(expected), reformat(content));
    }

    private String reformat(final String html) throws TransformerException, SAXException {
        return XMLUtils.format(html).replaceAll("[\\s\r\n]", "");
    }

}

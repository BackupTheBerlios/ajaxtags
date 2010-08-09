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

import javax.servlet.jsp.JspException;
import javax.xml.transform.TransformerException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * Test for AjaxToggleTag.
 *
 * @author Victor Homyakov
 */
public class AjaxToggleTagTest extends AbstractTagTest<AjaxToggleTag> {

    private static final String SCRIPT_START = "<script type=\"text/javascript\">new AjaxJspTag.Toggle({";
    private static final String SCRIPT_END = "});</script>";

    /**
     * Set up.
     *
     * @throws Exception
     *             if setUp fails
     */
    @Before
    public void setUp() throws Exception {
        setUp(AjaxToggleTag.class);
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
        tag.setContainerClass("star-rating");
        tag.setRatings(",Two,Three");

        assertStartTagSkipBody();
        assertAfterBody();
        assertEndTag();

        final String expected = "<div class=\"star-rating\">" + toggle("") + toggle("Two")
                + toggle("Three") + SCRIPT_START
                + "containerClass: \"star-rating\", ratings: \",Two,Three\"" + SCRIPT_END
                + "</div>";
        assertContent(expected);
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
        tag.setContainerClass("star-rating");
        tag.setRatings("One,Two,Three");

        assertStartTagSkipBody();
        assertAfterBody();
        assertEndTag();

        final String expected = "<div class=\"star-rating\">" + toggle("One") + toggle("Two")
                + toggle("Three") + SCRIPT_START
                + "containerClass: \"star-rating\", ratings: \"One,Two,Three\"" + SCRIPT_END
                + "</div>";
        assertContent(expected);
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
        tag.setContainerClass("star-rating");
        tag.setRatings("One,Two,Three");
        tag.setDefaultRating("Two");
        tag.setSelectedClass("selected");

        assertStartTagSkipBody();
        assertAfterBody();
        assertEndTag();

        final String expected = "<div class=\"star-rating\">" + toggle("One")
                + "<a href=\"javascript://nop\" class=\"selected\" title=\"Two\"></a>"
                + toggle("Three") + SCRIPT_START
                + "containerClass: \"star-rating\", defaultRating: \"Two\", "
                + "ratings: \"One,Two,Three\", selectedClass: \"selected\"" + SCRIPT_END + "</div>";
        assertContent(expected);
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
        tag.setContainerClass("power-rating");
        tag.setRatings("true,false");
        tag.setOnOff("true");

        assertStartTagSkipBody();
        assertAfterBody();
        assertEndTag();

        final String expected = "<div class=\"power-rating onoff\">" + toggle("false")
                + SCRIPT_START + "containerClass: \"power-rating\", ratings: \"true,false\""
                + SCRIPT_END + "</div>";
        assertContent(expected);
    }

    private String toggle(final String rating) {
        return "<a href=\"javascript://nop\" title=\"" + rating + "\"></a>";
    }

}

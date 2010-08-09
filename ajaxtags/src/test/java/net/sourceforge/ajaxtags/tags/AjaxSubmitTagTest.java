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
 * Test for AjaxSubmitTag.
 *
 * @author Victor Homyakov
 */
public class AjaxSubmitTagTest extends AbstractTagTest<AjaxSubmitTag> {

    private static final String SCRIPT_START = "<script type=\"text/javascript\">new AjaxJspTag.Submit({";
    private static final String SCRIPT_END = "});</script>";

    /**
     * Set up.
     *
     * @throws Exception
     *             if setUp fails
     */
    @Before
    public void setUp() throws Exception {
        setUp(AjaxSubmitTag.class);
        tag.setSource("formId");
        tag.setTarget("targetId");
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
        assertStartTagSkipBody();
        assertAfterBody();
        assertEndTag();

        final String expected = SCRIPT_START + "source: \"formId\", target: \"targetId\""
                + SCRIPT_END;
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
    public void testCallbacks() throws JspException, TransformerException, SAXException {
        tag.setPreFunction("preFunction");
        tag.setPostFunction("postFunction");

        assertStartTagSkipBody();
        assertAfterBody();
        assertEndTag();

        final String expected = SCRIPT_START
                + "onComplete:postFunction, onCreate:preFunction, source: \"formId\", target: \"targetId\""
                + SCRIPT_END;
        assertContent(expected);
    }

}

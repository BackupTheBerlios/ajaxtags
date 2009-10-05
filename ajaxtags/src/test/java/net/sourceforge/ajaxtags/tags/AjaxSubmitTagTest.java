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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.xml.transform.TransformerException;

import net.sourceforge.ajaxtags.FakeBodyContent;
import net.sourceforge.ajaxtags.FakePageContext;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * Test for AjaxSubmitTag.
 *
 * @author Victor Homyakov
 */
public class AjaxSubmitTagTest extends AbstractTagTest<AjaxSubmitTag> {

    /**
     * Set up.
     *
     * @throws Exception
     *             if setUp fails
     */
    @Before
    public void setUp() throws Exception {
        setUp(AjaxSubmitTag.class);
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
        tag.setSource("formId");
        tag.setTarget("targetId");

        assertStartTagSkipBody();
        assertAfterBody();
        assertEndTag();

        final String content = ((FakeBodyContent) context.getOut()).getString();
        final String expected = "<script type=\"text/javascript\">"
                + "new AjaxJspTag.Submit({source: \"formId\", target: \"targetId\"});"
                + "</script>";
        assertContent(expected, content);
    }

}

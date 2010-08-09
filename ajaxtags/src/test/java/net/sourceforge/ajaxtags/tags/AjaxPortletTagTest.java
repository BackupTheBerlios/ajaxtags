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
 * Test for AjaxPortletTag.
 *
 * @author Victor Homyakov
 */
public class AjaxPortletTagTest extends AbstractTagTest<AjaxPortletTag> {

    private static final String TAG_ID = "ajaxFrame";

    /**
     * Set up.
     *
     * @throws Exception
     *             if setUp fails
     */
    @Before
    public void setUp() throws Exception {
        setUp(AjaxPortletTag.class);
    }

    /**
     * Test method for tag content generation in response to usual HTTP request.
     *
     * @throws JspException
     *             on tag errors
     * @throws IOException
     *             on BodyContent errors
     * @throws SAXException
     *             if any parse errors occur
     * @throws TransformerException
     *             if it is not possible to transform document to string
     */
    @Test
    public void testTag() throws JspException, IOException, TransformerException, SAXException {
        tag.setId(TAG_ID);

        context.getOut().print("<div>before tag");

        assertStartTagSkipBody();
        assertAfterBody();
        assertEndTag();

        context.getOut().print("after tag</div>");

        final String expected = "<div>before tag<div>" + "<script type=\"text/javascript\">"
                + "newAjaxJspTag.Portlet({executeOnLoad:false,startMinimize:false});" + "</script>"
                + "</div>after tag</div>";
        assertContent(expected);
    }

}

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

import java.io.IOException;

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
 * Test for AjaxPortletTag.
 * 
 * @author Victor Homyakov
 * @version $Revision$ $Date$ $Author$
 */
public class AjaxPortletTagTest {

    private static final String TAG_ID = "ajaxFrame";
    private AjaxPortletTag tag;

    /**
     * Set up.
     */
    @Before
    public void setUp() {
        tag = new AjaxPortletTag();
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
        final PageContext context = new FakePageContext();
        tag.setPageContext(context);

        tag.setId(TAG_ID);

        context.getOut().print("<div>before tag");

        assertEquals("doStartTag() must return Tag.SKIP_BODY", Tag.SKIP_BODY, tag.doStartTag());

        assertEquals("doAfterBody() must return BodyTag.SKIP_BODY", BodyTag.SKIP_BODY, tag
                .doAfterBody());

        assertEquals("doEndTag() must return BodyTag.EVAL_PAGE", BodyTag.EVAL_PAGE, tag.doEndTag());

        context.getOut().print("after tag</div>");
        final String content = ((FakeBodyContent) context.getOut()).getString();

        final String expected = "<div>before tag<div>" + "<script type=\"text/javascript\">"
                + "newAjaxJspTag.Portlet({executeOnLoad:false,startMinimize:false});" + "</script>"
                + "</div>after tag</div>";

        // .replaceAll("[\\s|\n|\r\n]","") dirty hack, problem with WS remove all! cause
        // we just need to check the javascript here
        assertEquals("HTML after doEndTag()", XMLUtils.format(expected).replaceAll("[\\s|\n|\r\n]",
                ""), XMLUtils.format(content).replaceAll("[\\s|\n|\r\n]", ""));
    }

}

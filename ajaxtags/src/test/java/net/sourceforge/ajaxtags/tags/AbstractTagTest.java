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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.Tag;
import javax.xml.transform.TransformerException;

import net.sourceforge.ajaxtags.FakeBodyContent;
import net.sourceforge.ajaxtags.FakePageContext;
import net.sourceforge.ajaxtags.helpers.XMLUtils;

import org.junit.After;
import org.xml.sax.SAXException;

/**
 * Base for tag tests.
 *
 * @param <T>
 *            tag class
 *
 * @author Victor Homyakov
 */
public abstract class AbstractTagTest<T extends BaseAjaxBodyTag> {

    protected T tag;

    /**
     * Set up.
     *
     * @param clazz
     *            tag class
     *
     * @throws Exception
     *             if the instantiation fails
     */
    public void setUp(final Class<T> clazz) throws Exception {
        tag = clazz.newInstance();
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
     * Test start of tag without body.
     *
     * @throws JspException
     *             if an error occurred while processing this tag
     */
    public void assertStartTagSkipBody() throws JspException {
        assertEquals("doStartTag() must return Tag.SKIP_BODY", Tag.SKIP_BODY, tag.doStartTag());
    }

    /**
     * Test start of tag with body.
     *
     * @throws JspException
     *             if an error occurred while processing this tag
     */
    public void assertStartTagEvalBody() throws JspException {
        assertEquals("doStartTag() must return BodyTag.EVAL_BODY_BUFFERED",
                BodyTag.EVAL_BODY_BUFFERED, tag.doStartTag());
    }

    /**
     * Test body evaluation of tag.
     *
     * @throws JspException
     *             if an error occurred while processing this tag
     */
    public void assertAfterBody() throws JspException {
        assertEquals("doAfterBody() must return BodyTag.SKIP_BODY", BodyTag.SKIP_BODY, tag
                .doAfterBody());
    }

    /**
     * Test end of tag.
     *
     * @throws JspException
     *             if an error occurred while processing this tag
     */
    public void assertEndTag() throws JspException {
        assertEquals("doEndTag() must return BodyTag.EVAL_PAGE", BodyTag.EVAL_PAGE, tag.doEndTag());
    }

    public void assertContent(final String expected, final String actual)
            throws TransformerException, SAXException {
        assertEquals("HTML content", reformat(expected), reformat(actual));
    }

    protected String reformat(final String html) throws TransformerException, SAXException {
        // .replaceAll("[\\s|\n|\r\n]","") dirty hack, problem with WS remove all! cause
        // we just need to check the javascript here
        return XMLUtils.format(html).replaceAll("[\\s\r\n]", "");
    }

}

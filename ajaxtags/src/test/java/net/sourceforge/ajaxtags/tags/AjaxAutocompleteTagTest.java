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
import javax.xml.transform.TransformerException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * Test for AjaxAutocompleteTag.
 *
 * @author Victor Homyakov
 */
public class AjaxAutocompleteTagTest extends AbstractTagTest<AjaxAutocompleteTag> {

    private static final String SCRIPT_START = "<script type=\"text/javascript\">new AjaxJspTag.Autocomplete({";
    private static final String SCRIPT_END = "});</script>";

    /**
     * Set up.
     *
     * @throws Exception
     *             if setUp fails
     */
    @Before
    public void setUp() throws Exception {
        setUp(AjaxAutocompleteTag.class);
    }

    /**
     * Test default options.
     */
    @Test
    public void testDefaultOptions() {
        assertEquals("Default options", "className: \"autocomplete\"", tag.getOptions().toString());
    }

    /**
     * Test options.
     */
    @Test
    public void testOptions() {
        tag.setAfterUpdate("Object.method");
        tag.setClassName("");
        assertEquals("Options 1", "afterUpdate: Object.method", tag.getOptions().toString());

        tag.setAfterUpdate("function(value){alert('value: '+value);}");
        assertEquals("Options 2", "afterUpdate: function(value){alert('value: '+value);}", tag
                .getOptions().toString());
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
        tag.setBaseUrl("address.do");
        tag.setSource("addressId");
        tag.setAfterUpdate("AddressAutocompleter.fillFields");

        assertStartTagSkipBody();
        assertAfterBody();
        assertEndTag();
        assertContent(SCRIPT_START + "afterUpdate:AddressAutocompleter.fillFields, "
                + "baseUrl:\"address.do\", className:\"autocomplete\", source:\"addressId\""
                + SCRIPT_END);
    }
}

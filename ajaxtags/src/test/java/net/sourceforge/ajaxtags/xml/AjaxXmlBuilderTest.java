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
/**
 *
 */
package net.sourceforge.ajaxtags.xml;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for AjaxXmlBuilder.
 *
 * @author Victor Homyakov
 * @version $Revision$ $Date$ $Author$
 */
public class AjaxXmlBuilderTest {

    private transient AjaxXmlBuilder xml;

    /**
     * Set up.
     */
    @Before
    public void setUp() {
        xml = new AjaxXmlBuilder();
    }

    /**
     * Test method for {@link AjaxXmlBuilder#addItem(String, String)}.
     */
    @Test
    public void testAddItemStringString() {
        xml.addItem("name1", "value1");
        assertEquals("Added item with name and value", AjaxXmlBuilder.RESPONSE_START
                + "<item><name>name1</name><value>value1</value></item>"
                + AjaxXmlBuilder.RESPONSE_END, xml.getXMLString());
    }

    /**
     * Test method for {@link AjaxXmlBuilder#addItemAsCData(String, String)}.
     */
    @Test
    public void testAddItemAsCData() {
        xml.addItemAsCData("name2", "value2");
        assertEquals("Added item with CDATA name and CDATA value", AjaxXmlBuilder.RESPONSE_START
                + "<item><name><![CDATA[name2]]></name><value><![CDATA[value2]]></value></item>"
                + AjaxXmlBuilder.RESPONSE_END, xml.getXMLString());
    }

    /**
     * Test method for
     * {@link AjaxXmlBuilder#addItems(java.util.Collection, AjaxXmlBuilder.PropertyProvider)}.
     */
    @Test
    public void testAddItemsProvider() {
        final List<String> collection = Arrays.asList("string1", "string2");
        xml.addItems(collection, new AjaxXmlBuilder.AbstractPropertyProvider<String>() {
            public String getName(final String item) {
                return item + "-name";
            }

            public String getValue(final String item) {
                return item + "-value";
            }
        });
        assertEquals("Added collection of two items", AjaxXmlBuilder.RESPONSE_START
                + "<item><name>string1-name</name><value>string1-value</value></item>"
                + "<item><name>string2-name</name><value>string2-value</value></item>"
                + AjaxXmlBuilder.RESPONSE_END, xml.getXMLString());
    }
}

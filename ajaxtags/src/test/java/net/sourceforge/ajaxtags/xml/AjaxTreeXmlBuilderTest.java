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

import org.junit.Before;
import org.junit.Test;

/**
 * Test for AjaxTreeXmlBuilder.
 *
 * @author В.Хомяков
 */
public class AjaxTreeXmlBuilderTest {

    private AjaxTreeXmlBuilder xml;

    /**
     * Set up.
     */
    @Before
    public void setUp() {
        xml = new AjaxTreeXmlBuilder();
    }

    /**
     * Test method for {@link AjaxTreeXmlBuilder#getXMLString()}.
     */
    @Test
    public void testGetXMLString() {
        assertEquals(AjaxTreeXmlBuilder.RESPONSE_START + AjaxTreeXmlBuilder.RESPONSE_END, xml
                .getXMLString());
    }

    /**
     * Test method for {@link AjaxTreeXmlBuilder#addItem(String, String)} .
     */
    @Test
    public void testAddItemStringString() {
        xml.addItem("name1", "value");
        assertEquals(AjaxTreeXmlBuilder.RESPONSE_START
                + "<item><name>name1</name><value>value</value></item>"
                + AjaxTreeXmlBuilder.RESPONSE_END, xml.getXMLString());
        xml.addItem("name2", "");
        assertEquals(AjaxTreeXmlBuilder.RESPONSE_START
                + "<item><name>name1</name><value>value</value></item>"
                + "<item><name>name2</name><value></value></item>"
                + AjaxTreeXmlBuilder.RESPONSE_END, xml.getXMLString());
        xml.addItem("name3", null);
        assertEquals(AjaxTreeXmlBuilder.RESPONSE_START
                + "<item><name>name1</name><value>value</value></item>"
                + "<item><name>name2</name><value></value></item>"
                + "<item><name>name3</name><value>null</value></item>"
                + AjaxTreeXmlBuilder.RESPONSE_END, xml.getXMLString());
    }

    /**
     * Test method for {@link AjaxTreeXmlBuilder#addItemAsCData(String, String)} .
     */
    @Test
    public void testAddItemAsCDataStringString() {
        xml.addItemAsCData("name 4", "Value contains <name> and <value> tags");
        assertEquals(AjaxTreeXmlBuilder.RESPONSE_START + "<item><name><![CDATA[name 4]]></name>"
                + "<value><![CDATA[Value contains <name> and <value> tags]]></value></item>"
                + AjaxTreeXmlBuilder.RESPONSE_END, xml.getXMLString());
    }

    /**
     * Test method for {@link AjaxTreeXmlBuilder#addItem(String, String, String, boolean)} .
     */
    @Test
    public void testAddItemStringStringStringBoolean() {
        xml.addItem("name5", "value5", "url5", false);
        assertEquals(AjaxTreeXmlBuilder.RESPONSE_START + "<item><name>name5</name>"
                + "<value>value5</value><collapsed>false</collapsed><url>url5</url></item>"
                + AjaxTreeXmlBuilder.RESPONSE_END, xml.getXMLString());
    }

    /**
     * Test method for {@link AjaxTreeXmlBuilder#addItem(String, String, boolean, String)} .
     */
    @Test
    public void testAddItemStringStringBooleanString() {
        xml.addItem("name6", "value6", true, "url6");
        assertEquals(AjaxTreeXmlBuilder.RESPONSE_START + "<item><name>name6</name>"
                + "<value>value6</value><collapsed>true</collapsed><url>url6</url></item>"
                + AjaxTreeXmlBuilder.RESPONSE_END, xml.getXMLString());
    }

    /**
     * Test method for {@link AjaxTreeXmlBuilder#addItem(String, String, boolean, String, boolean)}
     * .
     */
    @Test
    public void testAddItemStringStringBooleanStringBoolean() {
        xml.addItem("name7", "value7", true, "url7", true);
        assertEquals(
                AjaxTreeXmlBuilder.RESPONSE_START
                        + "<item><name><![CDATA[name7]]></name>"
                        + "<value><![CDATA[value7]]></value><collapsed>true</collapsed><url>url7</url></item>"
                        + AjaxTreeXmlBuilder.RESPONSE_END, xml.getXMLString());
    }

    /**
     * Test method for {@link AjaxTreeXmlBuilder#addItemAsCData(String, String, boolean, String)} .
     */
    @Test
    public void testAddItemAsCDataStringStringBooleanString() {
        xml.addItemAsCData("name8", "value8", true, "url8");
        assertEquals(
                AjaxTreeXmlBuilder.RESPONSE_START
                        + "<item><name><![CDATA[name8]]></name>"
                        + "<value><![CDATA[value8]]></value><collapsed>true</collapsed><url>url8</url></item>"
                        + AjaxTreeXmlBuilder.RESPONSE_END, xml.getXMLString());
    }

}

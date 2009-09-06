/**
 *
 */
package net.sourceforge.ajaxtags.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for AjaxXmlBuilder.
 *
 * @author В.Хомяков
 * @version $Revision$ $Date$ $Author$
 */
public class AjaxXmlBuilderTest {

    private AjaxXmlBuilder xml;

    /**
     * Set up.
     */
    @Before
    public void setUp() {
        xml = new AjaxXmlBuilder();
    }

    /**
     * Test method for {@link AjaxXmlBuilder#addItem(String, String)} .
     */
    @Test
    public void testAddItemStringString() {
        xml.addItem("name1", "value1");
        assertEquals(AjaxXmlBuilder.RESPONSE_START
                + "<item><name>name1</name><value>value1</value></item>"
                + AjaxXmlBuilder.RESPONSE_END, xml.getXMLString());
    }

    /**
     * Test method for {@link AjaxXmlBuilder#addItemAsCData(String, String)} .
     */
    @Test
    public void testAddItemAsCData() {
        xml.addItemAsCData("name2", "value2");
        assertEquals(AjaxXmlBuilder.RESPONSE_START
                + "<item><name><![CDATA[name2]]></name><value><![CDATA[value2]]></value></item>"
                + AjaxXmlBuilder.RESPONSE_END, xml.getXMLString());
    }

}

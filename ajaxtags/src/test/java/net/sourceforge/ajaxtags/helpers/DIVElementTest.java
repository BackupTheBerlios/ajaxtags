/**
 *
 */
package net.sourceforge.ajaxtags.helpers;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author В.Хомяков
 *
 */
public class DIVElementTest {

    private DIVElement div;

    /**
     * Set up.
     */
    @Before
    public void setUp() {
        div = new DIVElement(null);
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
        div = null;
    }

    /**
     * Test method for
     * {@link net.sourceforge.ajaxtags.helpers.DIVElement#DIVElement(java.lang.String)}.
     */
    @Test
    public void testDIVElement() {
        assertEquals("Empty div", "<div></div>", div.toString());

        div = new DIVElement("idDiv1");
        assertEquals("Empty div with id", "<div id=\"idDiv1\"></div>", div.toString());
    }

    /**
     * Test method for
     * {@link net.sourceforge.ajaxtags.helpers.HTMLElementFactory#setClassName(java.lang.String)}.
     */
    @Test
    public void testSetClassName() {
        div.setClassName("class1");
        assertEquals("Empty div with class", "<div class=\"class1\"></div>", div.toString());
    }

    /**
     * Test method for
     * {@link net.sourceforge.ajaxtags.helpers.HTMLElementFactory#setId(java.lang.String)}.
     */
    @Test
    public void testSetId() {
        div.setId("idDiv2");
        assertEquals("Empty div with id", "<div id=\"idDiv2\"></div>", div.toString());
    }

}

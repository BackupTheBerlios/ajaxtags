/**
 *
 */
package net.sourceforge.ajaxtags.tags;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author В.Хомяков
 * @version $Revision$ $Date$ $Author$
 */
public class OptionsBuilderTest {

    private OptionsBuilder options;

    /**
     * Set up.
     */
    @Before
    public void setUp() {
        options = OptionsBuilder.getOptionsBuilder();
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
        options = null;
    }

    /**
     * Test method for {@link OptionsBuilder#getOptionsBuilder(OptionsBuilder)} .
     */
    @Test
    public void testGetOptionsBuilder() {
        assertNotNull("OptionsBuilder must be created successfully", options);
        assertEquals("Empty OptionsBuilder 1", "", options.toString());
        final OptionsBuilder ob1 = OptionsBuilder.getOptionsBuilder(options);
        assertEquals("Empty OptionsBuilder 2", "", ob1.toString());
    }

    /**
     * Test method for {@link OptionsBuilder#add(String, boolean)}.
     */
    @Test
    public void testAddStringBoolean() {
        options.add("parameter", true);
        options.add("parameter with space", false);
        assertEquals("toString", "parameter with space: false,\nparameter: true", options
                .toString());
    }

    /**
     * Test method for {@link OptionsBuilder#add(String, int)}.
     */
    @Test
    public void testAddStringInt() {
        options.add("p1", -1);
        assertEquals("int option", "p1: -1", options.toString());
        options.add("p1", 0); // immutable option, should not change
        assertEquals("int immutable option", "p1: -1", options.toString());

        options.add("p2", 0).add("p3", 1);
        assertEquals("int options", "p3: 1,\np1: -1,\np2: 0", options.toString());
    }

    /**
     * Test method for {@link OptionsBuilder#add(String, String, boolean)} .
     */
    @Test
    public void testAddStringStringBoolean() {
        options.add("opt1", "", false).add("opt2", "", true);
        options.add("opt3", "1", false).add("opt4", "1", true);
        assertEquals("string options", "opt4: \"1\",\nopt2: \"\",\nopt1: ,\nopt3: 1", options
                .toString());
    }

    /**
     * Test method for {@link OptionsBuilder#remove(String)} .
     */
    @Test
    public void testRemove() {
        options.remove("opt123");
        assertEquals("Empty OptionsBuilder 3", "", options.toString());
        options.add("opt1", true).remove("opt1");
        assertEquals("Empty OptionsBuilder 4", "", options.toString());
        options.add("opt1", true).add("opt2", true).remove("opt1");
        assertEquals("OptionsBuilder", "opt2: true", options.toString());
    }

}

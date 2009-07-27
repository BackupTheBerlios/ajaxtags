/**
 *
 */
package net.sourceforge.ajaxtags.tags;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
        assertEquals("toString", "parameter with space: false" + options.getOptionsDelimiter()
                + "parameter: true", options.toString());
    }

    /**
     * Test method for {@link OptionsBuilder#add(String, int)}.
     */
    @Test
    public void testAddStringInt() {
        final String param1 = "test1-param1";
        final String expected1 = "test1-param1: -1";

        options.add(param1, -1);
        assertEquals("int option", expected1, options.toString());
        options.add(param1, 0); // immutable option, should not change
        assertEquals("int immutable option", expected1, options.toString());

        options.add("test1-param2", 0).add("test1-param3", 1);
        assertEquals("int options", "test1-param2: 0" + options.getOptionsDelimiter() + expected1
                + options.getOptionsDelimiter() + "test1-param3: 1", options.toString());
    }

    /**
     * Test method for {@link OptionsBuilder#add(String, String, boolean)} .
     */
    @Test
    public void testAddStringStringBoolean() {
        final String param1 = "test2-param1";
        final String expected1 = "test2-param1: ";

        options.add(param1, "", false);
        options.add("test2-param2", "", true);
        options.add("test2-param3", "string3", false);
        options.add("test2-param4", "string4", true);
        assertEquals("string options", "test2-param3: string3" + options.getOptionsDelimiter()
                + "test2-param2: \"\"" + options.getOptionsDelimiter()
                + "test2-param4: \"string4\"" + options.getOptionsDelimiter() + expected1, options
                .toString());
    }

    /**
     * Test method for {@link OptionsBuilder#remove(String)} .
     */
    @Test
    public void testRemove() {
        final String param1 = "test3-param1";

        options.remove("test3-param123");
        assertEquals("Empty OptionsBuilder 3", "", options.toString());
        options.add(param1, true).remove(param1);
        assertEquals("Empty OptionsBuilder 4", "", options.toString());
        options.add(param1, true).add("test3-param2", true).remove(param1);
        assertEquals("OptionsBuilder", "test3-param2: true", options.toString());
    }

}

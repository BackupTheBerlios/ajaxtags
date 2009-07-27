/**
 *
 */
package net.sourceforge.ajaxtags.helpers;

import static net.sourceforge.ajaxtags.helpers.StringUtils.trim2Null;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * @author В.Хомяков
 * @version $Revision$ $Date$ $Author$
 */
public class StringUtilsTest {

    /**
     * Test method for
     * {@link net.sourceforge.ajaxtags.helpers.StringUtils#trim2Null(java.lang.String)}.
     */
    @Test
    public void testTrim2Null() {
        assertNull("trim2Null(null) must be null", trim2Null(null));
        assertNull("trim2Null(\"\") must be null", trim2Null(""));
        assertNull("trim2Null(\" \") must be null", trim2Null(" "));
        assertNull("trim2Null(\"\t\") must be null", trim2Null("\t"));
        assertNull("trim2Null(\"\r\n\") must be null", trim2Null("\r\n"));
        String str = "a";
        assertEquals("Without whitespace", str, trim2Null(str));
        str = " a ";
        assertEquals("With whitespace", str, trim2Null(str));
    }

}

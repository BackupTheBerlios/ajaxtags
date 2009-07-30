/**
 *
 */
package net.sourceforge.ajaxtags.helpers;

import static org.apache.commons.lang.StringUtils.trimToNull;
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
     * {@link net.sourceforge.ajaxtags.helpers.StringUtils#trimToNull(java.lang.String)}.
     */
    @Test
    public void testTrim2Null() {
        assertNull("trim2Null(null) must be null", trimToNull(null));
        assertNull("trim2Null(\"\") must be null", trimToNull(""));
        assertNull("trim2Null(\" \") must be null", trimToNull(" "));
        assertNull("trim2Null(\"\t\") must be null", trimToNull("\t"));
        assertNull("trim2Null(\"\r\n\") must be null", trimToNull("\r\n"));
        String str = "a";
        assertEquals("Without whitespace", str, trimToNull(str));
        str = " a ";
        // this test fail if we use trimToNULL
        // assertEquals("With whitespace", str, trim2Null(str));
        assertEquals("With whitespace", str.trim(), trimToNull(str));
    }

}

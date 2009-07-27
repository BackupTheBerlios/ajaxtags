/**
 * Copyright 2009 Jens Kapitza
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
package net.sourceforge.ajaxtags.helpers;

import static org.apache.commons.lang.StringUtils.isBlank;

/**
 *
 * @author Jens Kapitza
 * @version $Revision$ $Date$ $Author$
 */
public final class StringUtils {

    private StringUtils() {
    }

    /**
     * Return null if argument string is null, empty or blank (contains only whitespace). Otherwise
     * return unchanged string.
     *
     * @param str
     *            string
     * @return string or null
     */
    public static String trim2Null(final String str) {
        if (str != null && !isBlank(str)) {
            return str;
        }
        return null;
    }

}

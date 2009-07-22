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
package net.sourceforge.ajaxtags.tags;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Helper class to assist in building options passed to JavaScript method.
 *
 * @author Darren Spurgeon
 * @author Jens Kapitza
 * @version $Revision: 86 $ $Date: 2007/06/20 20:55:56 $ $Author: jenskapitza $
 */
public final class OptionsBuilder {

    private Map<String, String> parameters = new HashMap<String, String>();
    private Map<String, Boolean> parameterQuotes = new HashMap<String, Boolean>();

    private OptionsBuilder() {
    }

    private OptionsBuilder(OptionsBuilder opt) {
        if (opt != null) {
            this.parameterQuotes.putAll(opt.parameterQuotes);
            this.parameters.putAll(opt.parameters);
        }
    }

    public static OptionsBuilder getOptionsBuilder() {
        return getOptionsBuilder(null);
    }

    public OptionsBuilder add(String parameter, boolean value) {
        return add(parameter, String.valueOf(value), false);
    }

    public OptionsBuilder add(String parameter, int value) {
        return add(parameter, String.valueOf(value), false);
    }

    public OptionsBuilder add(String parameter, String value, boolean quoted) {
        if (value != null && !this.parameters.containsKey(parameter)) {
            this.parameters.put(parameter, value);
            this.parameterQuotes.put(parameter, Boolean.valueOf(quoted));
        }
        return this;
    }

    public OptionsBuilder add(String parameter, String value, boolean quoted, boolean forceWrite) {
        if (this.parameters.containsKey(parameter)) {
            this.parameters.remove(parameter);
            this.parameterQuotes.remove(parameter);
        }

        return add(parameter, value, quoted);
    }

    public OptionsBuilder remove(String parameter) {
        this.parameters.remove(parameter);
        this.parameterQuotes.remove(parameter);
        return this;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Iterator<String> iter = this.parameters.keySet().iterator(); iter.hasNext();) {
            String key = iter.next();
            String value = this.parameters.get(key);
            boolean quoted = this.parameterQuotes.get(key).booleanValue();
            sb.append(key).append(": ");
            if (quoted) {
                sb.append("\"").append(value).append("\"");
            } else {
                sb.append(value);
            }
            if (iter.hasNext()) {
                sb.append(",");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static OptionsBuilder getOptionsBuilder(OptionsBuilder opt) {
        return new OptionsBuilder(opt);
    }
}

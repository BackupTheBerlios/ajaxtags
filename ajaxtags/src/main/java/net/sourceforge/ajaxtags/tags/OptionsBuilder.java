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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * Helper class to assist in building options passed to JavaScript method. This class makes no
 * guarantees as to the order of the options; in particular, it does not guarantee that the order
 * will remain constant over time.
 *
 * TODO create class for single option, merge two maps into one, in toString use option.toString
 *
 * TODO or add to single map already rendered strings "parameter: value"
 *
 * @author Darren Spurgeon
 * @author Jens Kapitza
 * @version $Revision: 86 $ $Date: 2007/06/20 20:55:56 $ $Author: jenskapitza $
 */
public final class OptionsBuilder {

    private final Map<String, String> parameters = new HashMap<String, String>();
    private final Map<String, Boolean> parameterQuotes = new HashMap<String, Boolean>();

    private OptionsBuilder() {
    }

    private OptionsBuilder(final OptionsBuilder opt) {
        if (opt != null) {
            this.parameterQuotes.putAll(opt.parameterQuotes);
            this.parameters.putAll(opt.parameters);
        }
    }

    /**
     * Get new OptionsBuilder with initial options.
     *
     * @param opt
     *            initial options
     * @return new empty OptionsBuilder
     */
    public static OptionsBuilder getOptionsBuilder(final OptionsBuilder opt) {
        return new OptionsBuilder(opt);
    }

    /**
     * Get new empty OptionsBuilder.
     *
     * @return new empty OptionsBuilder
     */
    public static OptionsBuilder getOptionsBuilder() {
        return getOptionsBuilder(null);
    }

    /**
     * Add boolean option. Option with given name is added only once.
     *
     * @param parameter
     *            name of option
     * @param value
     *            value of option
     * @return updated OptionsBuilder
     */
    public OptionsBuilder add(final String parameter, final boolean value) {
        return add(parameter, String.valueOf(value), false);
    }

    /**
     * Add integer option. Option with given name is added only once.
     *
     * @param parameter
     *            name of option
     * @param value
     *            value of option
     * @return updated OptionsBuilder
     */
    public OptionsBuilder add(final String parameter, final int value) {
        return add(parameter, String.valueOf(value), false);
    }

    /**
     * Add option. Option is added only once. If {@link OptionsBuilder} already contains option with
     * given name, it will stay unchanged.
     *
     * @param parameter
     *            name of option
     * @param value
     *            value of option
     * @param quoted
     *            true if value must be surrounded with quotes
     * @return updated OptionsBuilder
     */
    public OptionsBuilder add(final String parameter, final String value, final boolean quoted) {
        if (value != null && !this.parameters.containsKey(parameter)) {
            this.parameters.put(parameter, value);
            this.parameterQuotes.put(parameter, Boolean.valueOf(quoted));
        }
        return this;
    }

    /**
     * Add option. Overwrite previous value if it exists.
     *
     * @param parameter
     *            name of option
     * @param value
     *            value of option
     * @param quoted
     *            true if value must be surrounded with quotes
     * @return updated OptionsBuilder
     */
    public OptionsBuilder add(final String parameter, final String value, final boolean quoted,
            final boolean forceWrite) {
        if (this.parameters.containsKey(parameter)) {
            this.parameters.remove(parameter);
            this.parameterQuotes.remove(parameter);
        }

        return add(parameter, value, quoted);
    }

    /**
     * Remove option.
     *
     * @param parameter
     *            name of option
     * @return updated OptionsBuilder
     */
    public OptionsBuilder remove(final String parameter) {
        this.parameters.remove(parameter);
        this.parameterQuotes.remove(parameter);
        return this;
    }

    /**
     * Create string representation of options (in JSON format).
     *
     * @return options as string
     */
    @Override
    public String toString() {
        final List<String> options = new ArrayList<String>();
        for (String key : parameters.keySet()) {
            final String value = parameters.get(key);
            final String quote = parameterQuotes.get(key) ? "\"" : "";
            options.add(key + ": " + quote + value + quote);
        }
        return StringUtils.join(options, ",\n");
    }
}

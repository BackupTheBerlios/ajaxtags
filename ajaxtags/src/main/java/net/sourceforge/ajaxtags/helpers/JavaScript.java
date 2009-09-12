/*
 * Copyright 2009 AjaxTags-Team
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
package net.sourceforge.ajaxtags.helpers;

import net.sourceforge.ajaxtags.tags.BaseAjaxBodyTag;
import net.sourceforge.ajaxtags.tags.OptionsBuilder;

/**
 * HTML JavaScript element.
 *
 * @author Jens Kapitza
 * @version $Revision$ $Date$ $Author$
 */
public final class JavaScript extends HTMLElementFactory {

    private BaseAjaxBodyTag tag;

    /**
     * Create a script element.
     */
    public JavaScript() {
        super("script");
    }

    /**
     * Constructor.
     *
     * @param tag
     *            the tag, which uses this JavaScript
     */
    public JavaScript(final BaseAjaxBodyTag tag) {
        this();
        this.tag = tag;
    }

    /**
     * Create new AjaxJspTag.TabPanel.
     *
     * @param options
     *            options for TabPanel
     * @return updated HTML element
     */
    public HTMLElementFactory newTabPanel(final OptionsBuilder options) {
        return append(tag.getJSVariable()).append(newTag("TabPanel", options));
    }

    /**
     * Create new AjaxJspTag.Select.
     *
     * @param options
     *            options for Select
     * @return updated HTML element
     */
    public HTMLElementFactory newSelect(final OptionsBuilder options) {
        return append(tag.getJSVariable()).append(newTag("Select", options));
    }

    /**
     * Create new AjaxJspTag.Toggle.
     *
     * @param options
     *            options for Toggle
     * @return updated HTML element
     */
    public HTMLElementFactory newToggle(final OptionsBuilder options) {
        return append(tag.getJSVariable()).append(newTag("Toggle", options));
    }

    /**
     * Create new AjaxJspTag.Autocomplete.
     *
     * @param options
     *            options for Autocomplete
     * @return updated HTML element
     */
    public HTMLElementFactory newAutocomplete(final OptionsBuilder options) {
        return append(tag.getJSVariable()).append(newTag("Autocomplete", options));
    }

    /**
     * Create "new AjaxJspTag.tag({options});" tag expression string.
     *
     * @param tag
     *            tag name
     * @param options
     *            options
     * @return string with expression
     */
    private String newTag(final String tag, final OptionsBuilder options) {
        return "new AjaxJspTag." + tag + "({" + options + "});";
    }

    /**
     * Just allow type attribute. All others are dropped.
     */
    @Override
    protected void cleanAttributes() {
        getAttributes().clear();
        getAttributes().put("type", "text/javascript");
    }
}

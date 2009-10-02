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
public final class JavaScript extends AbstractHTMLElement {

    /**
     * Create a script element.
     */
    public JavaScript() {
        super("script");
    }

    /**
     * Constructor.
     *
     * @param script
     *            initial script content
     */
    public JavaScript(final String script) {
        this();
        append(script);
    }

    /**
     * Create new AjaxJspTag.Autocomplete JavaScript.
     *
     * TODO generalize all newXxx methods and place in BaseAjaxBodyTag.
     *
     * @param tag
     *            the tag, whose parameters will be used
     * @param options
     *            options for Autocomplete
     * @return updated HTML element
     */
    public static JavaScript newAutocomplete(final BaseAjaxBodyTag tag, final OptionsBuilder options) {
        return new JavaScript(newTag("Autocomplete", tag.getJSVariable(), options));
    }

    /**
     * Create new AjaxJspTag.Callout JavaScript.
     *
     * @param tag
     *            the tag, whose parameters will be used
     * @param options
     *            options for Callout
     * @return updated HTML element
     */
    public static JavaScript newCallout(final BaseAjaxBodyTag tag, final OptionsBuilder options) {
        return new JavaScript(newTag("Callout", tag.getJSVariable(), options));
    }

    /**
     * Create new AjaxJspTag.HtmlContent JavaScript.
     *
     * @param tag
     *            the tag, whose parameters will be used
     * @param options
     *            options for HtmlContent
     * @return updated HTML element
     */
    public static JavaScript newHtmlContent(final BaseAjaxBodyTag tag, final OptionsBuilder options) {
        return new JavaScript(newTag("HtmlContent", tag.getJSVariable(), options));
    }

    /**
     * Create new AjaxJspTag.Portlet JavaScript.
     *
     * @param tag
     *            the tag, whose parameters will be used
     * @param options
     *            options for Portlet
     * @return updated HTML element
     */
    public static JavaScript newPortlet(final BaseAjaxBodyTag tag, final OptionsBuilder options) {
        return new JavaScript(newTag("Portlet", tag.getJSVariable(), options));
    }

    /**
     * Create new AjaxJspTag.Select JavaScript.
     *
     * @param tag
     *            the tag, whose parameters will be used
     * @param options
     *            options for Select
     * @return updated HTML element
     */
    public static JavaScript newSelect(final BaseAjaxBodyTag tag, final OptionsBuilder options) {
        return new JavaScript(newTag("Select", tag.getJSVariable(), options));
    }

    /**
     * Create new AjaxJspTag.Submit JavaScript.
     *
     * @param tag
     *            the tag, whose parameters will be used
     * @param options
     *            options for Submit
     * @return updated HTML element
     */
    public static JavaScript newSubmit(final BaseAjaxBodyTag tag, final OptionsBuilder options) {
        return new JavaScript(newTag("Submit", tag.getJSVariable(), options));
    }

    /**
     * Create new AjaxJspTag.TabPanel JavaScript.
     *
     * @param tag
     *            the tag, whose parameters will be used
     * @param options
     *            options for TabPanel
     * @return updated HTML element
     */
    public static JavaScript newTabPanel(final BaseAjaxBodyTag tag, final OptionsBuilder options) {
        return new JavaScript(newTag("TabPanel", tag.getJSVariable(), options));
    }

    /**
     * Create new AjaxJspTag.Toggle JavaScript.
     *
     * @param tag
     *            the tag, whose parameters will be used
     * @param options
     *            options for Toggle
     * @return updated HTML element
     */
    public static JavaScript newToggle(final BaseAjaxBodyTag tag, final OptionsBuilder options) {
        return new JavaScript(newTag("Toggle", tag.getJSVariable(), options));
    }

    /**
     * Create new AjaxJspTag.Tree JavaScript.
     *
     * @param tag
     *            the tag, whose parameters will be used
     * @param options
     *            options for Tree
     * @return updated HTML element
     */
    public static JavaScript newTree(final BaseAjaxBodyTag tag, final OptionsBuilder options) {
        return new JavaScript(newTag("Tree", tag.getJSVariable(), options));
    }

    /**
     * Create new AjaxJspTag.UpdateField JavaScript.
     *
     * @param tag
     *            the tag, whose parameters will be used
     * @param options
     *            options for UpdateField
     * @return updated HTML element
     */
    public static JavaScript newUpdateField(final BaseAjaxBodyTag tag, final OptionsBuilder options) {
        return new JavaScript(newTag("UpdateField", tag.getJSVariable(), options));
    }

    /**
     * Create "var = new AjaxJspTag.tagName({options});" tag expression string.
     *
     * @param tagName
     *            tag name
     * @param var
     *            name of variable or field to attach to
     * @param options
     *            options
     * @return string with expression
     */
    private static String newTag(final String tagName, final String var,
            final OptionsBuilder options) {
        return var + newTag(tagName, options);
    }

    /**
     * Create "new AjaxJspTag.tagName({options});" tag expression string.
     *
     * @param tagName
     *            tag name
     * @param options
     *            options
     * @return string with expression
     */
    private static String newTag(final String tagName, final OptionsBuilder options) {
        return "new AjaxJspTag." + tagName + "({" + options + "});";
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

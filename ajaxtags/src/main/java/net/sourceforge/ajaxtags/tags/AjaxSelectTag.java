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
package net.sourceforge.ajaxtags.tags;

import javax.servlet.jsp.JspException;

import net.sourceforge.ajaxtags.helpers.JavaScript;

/**
 * Tag handler for the select AJAX tag.
 *
 * @author Darren Spurgeon
 * @author Jens Kapitza
 * @version $Revision: 86 $ $Date: 2007/07/08 17:52:30 $ $Author: jenskapitza $
 */
public class AjaxSelectTag extends BaseAjaxTag {

    private static final long serialVersionUID = -686763601190277341L;

    private String emptyOptionValue;

    private String emptyOptionName;

    private boolean executeOnLoad;

    private String defaultOptions;

    public void setEmptyOptionName(final String emptyOptionName) {
        this.emptyOptionName = emptyOptionName;
    }

    public String getEmptyOptionName() {
        return emptyOptionName;
    }

    public void setEmptyOptionValue(final String emptyOptionValue) {
        this.emptyOptionValue = emptyOptionValue;
    }

    public String getEmptyOptionValue() {
        return emptyOptionValue;
    }

    public boolean getExecuteOnLoad() {
        return executeOnLoad;
    }

    public void setExecuteOnLoad(final boolean executeOnLoad) {
        this.executeOnLoad = executeOnLoad;
    }

    public String getDefaultOptions() {
        return defaultOptions;
    }

    public void setDefaultOptions(final String defaultOptions) {
        this.defaultOptions = defaultOptions;
    }

    private OptionsBuilder getOptions() {
        final OptionsBuilder options = getOptionsBuilder();
        options.add("executeOnLoad", this.executeOnLoad);
        options.add("defaultOptions", this.defaultOptions, true);
        options.add("emptyOptionValue", emptyOptionValue, true);
        options.add("emptyOptionName", emptyOptionName, true);
        return options;
    }

    @Override
    public int doEndTag() throws JspException {
        out(JavaScript.newSelect(this, getOptions()));
        return EVAL_PAGE;
    }

    @Override
    public void releaseTag() {
        this.executeOnLoad = false;
        this.defaultOptions = null; // NOPMD
        this.emptyOptionName = null; // NOPMD
        this.emptyOptionValue = null; // NOPMD
    }
}

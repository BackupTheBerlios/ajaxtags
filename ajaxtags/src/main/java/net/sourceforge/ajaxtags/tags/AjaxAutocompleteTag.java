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

import javax.servlet.jsp.JspException;

import net.sourceforge.ajaxtags.helpers.JavaScript;

/**
 * Tag handler for the autocomplete AJAX tag.
 *
 * @author Darren Spurgeon
 * @version $Revision: 86 $ $Date: 2007/06/20 20:55:56 $ $Author: jenskapitza $
 */
public class AjaxAutocompleteTag extends BaseAjaxTag {

    private static final long serialVersionUID = -6332721548834673822L;

    private String minimumCharacters;

    private String appendSeparator;

    private String className;

    private String indicator;

    public String getAppendSeparator() {
        return appendSeparator;
    }

    public void setAppendSeparator(String appendSeparator) {
        this.appendSeparator = appendSeparator;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public String getMinimumCharacters() {
        return minimumCharacters;
    }

    public void setMinimumCharacters(String minimumCharacters) {
        this.minimumCharacters = minimumCharacters;
    }

    @Override
    public int doEndTag() throws JspException {
        OptionsBuilder options = getOptionsBuilder();
        options.add("className", this.className, true);
        options.add("indicator", this.indicator, true);
        options.add("minChars", this.minimumCharacters, true);
        options.add("appendSeparator", this.appendSeparator, true);

        JavaScript script = new JavaScript();
        script.append(getJSVariable());
        script.append("new AjaxJspTag.Autocomplete(\n").append("{\n").append(options.toString())
                .append("});\n");
        out(script);
        return EVAL_PAGE;
    }

    @Override
    public void releaseTag() {
        this.className = null;
        this.minimumCharacters = null;
        this.appendSeparator = null;
        this.indicator = null;
    }
}

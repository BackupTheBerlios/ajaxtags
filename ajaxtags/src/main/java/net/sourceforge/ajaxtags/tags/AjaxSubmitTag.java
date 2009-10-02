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
 * Builds the JavaScript required to submit form and retrieve response via AJAX.
 *
 * @author Victor Homyakov
 */
public class AjaxSubmitTag extends BaseAjaxTag {

    private static final long serialVersionUID = -8804246033367573302L;

    private OptionsBuilder getOptions() {
        final OptionsBuilder options = getOptionsBuilder(true);
        options.add("source", getSource(), true);
        options.add("target", getTarget(), true);
        return options;
    }

    @Override
    public int doEndTag() throws JspException {
        out(JavaScript.newSubmit(this, getOptions()));
        return EVAL_PAGE;
    }
}

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

/**
 * Tag handler for the form field AJAX tag.
 * 
 * @author Darren Spurgeon
 * @author Jens Kapitza
 * @version $Revision: 86 $ $Date: 2007/06/20 20:55:56 $ $Author: jenskapitza $
 */
public class AjaxFormFieldTag extends BaseAjaxTag {

    private static final long serialVersionUID = -7774526024294932262L;

    private String action;

    private boolean valueUpdateByName;

    public String getAction() {
        return action;
    }

    public void setAction(final String action) {
        this.action = action;
    }

    @Override
    protected String getJsClass() {
        return JSCLASS_BASE + "UpdateField";
    }

    @Override
    protected OptionsBuilder getOptions() {
        final OptionsBuilder options = getOptionsBuilder();
        options.add("action", this.action, true);
        options.add("valueUpdateByName", String.valueOf(this.valueUpdateByName), false);
        return options;
    }

    @Override
    public int doEndTag() throws JspException {
        out(buildScript());
        return EVAL_PAGE;
    }

    @Override
    public void releaseTag() {
        this.valueUpdateByName = false;
        this.action = null; // NOPMD
    }

    public boolean getValueUpdateByName() {
        return valueUpdateByName;
    }

    public void setValueUpdateByName(final boolean valueUpdateByName) {
        this.valueUpdateByName = valueUpdateByName;
    }
}

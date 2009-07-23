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

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public int doEndTag() throws JspException {
        OptionsBuilder options = getOptionsBuilder();
        options.add("action", this.action, true);
        options.add("valueUpdateByName", String.valueOf(this.valueUpdateByName), false);

        JavaScript script = new JavaScript();
        script.append(getJSVariable());
        script.append(" new AjaxJspTag.UpdateField(\n").append("{\n").append(options.toString())
                .append("});\n");

        out(script);
        return EVAL_PAGE;
    }

    @Override
    public void releaseTag() {
        this.valueUpdateByName = false;
        this.action = null;
    }

    public boolean getValueUpdateByName() {
        return valueUpdateByName;
    }

    public void setValueUpdateByName(boolean valueUpdateByName) {
        this.valueUpdateByName = valueUpdateByName;
    }
}

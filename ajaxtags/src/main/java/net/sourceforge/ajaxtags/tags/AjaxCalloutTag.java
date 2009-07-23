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
import javax.servlet.jsp.tagext.Tag;

import net.sourceforge.ajaxtags.helpers.JavaScript;

/**
 * Tag handler for the callout AJAX tag.
 *
 * @author Darren Spurgeon
 * @version $Revision: 86 $ $Date: 2007/08/05 14:11:00 $ $Author: jenskapitza $
 */
public class AjaxCalloutTag extends BaseAjaxTag {

    private static final long serialVersionUID = -2356224207093958968L;

    private String title;

    private String overlib;

    private String emptyFunction;

    private String openEventType;

    private String closeEventType;

    public String getCloseEventType() {
        return this.closeEventType;
    }

    public void setCloseEventType(String closeEvent) {
        this.closeEventType = closeEvent;
    }

    public String getOpenEventType() {
        return this.openEventType;
    }

    public void setOpenEventType(String openEvent) {
        this.openEventType = openEvent;
    }

    public String getEmptyFunction() {
        return this.emptyFunction;
    }

    public void setEmptyFunction(String emptyFunction) {
        this.emptyFunction = emptyFunction;
    }

    public String getOverlib() {
        return this.overlib;
    }

    public void setOverlib(String overlib) {
        this.overlib = overlib;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int doEndTag() throws JspException {
        OptionsBuilder options = getOptionsBuilder();
        options.add("title", this.title, true);
        options.add("overlib", this.overlib, true);
        options.add("emptyFunction", this.emptyFunction, false);
        options.add("openEvent", this.openEventType, true);
        options.add("closeEvent", this.closeEventType, true);

        JavaScript script = new JavaScript();
        script.append(getJSVariable());
        script.append("new AjaxJspTag.Callout(\n").append("{\n").append(options.toString()).append(
                "});\n");

        out(script);
        return Tag.EVAL_PAGE;
    }

    @Override
    public void releaseTag() {
        this.title = null;
        this.overlib = null;
        this.emptyFunction = null;
        this.openEventType = null;
        this.closeEventType = null;
    }
}

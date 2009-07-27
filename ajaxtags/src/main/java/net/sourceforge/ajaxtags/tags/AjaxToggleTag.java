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

import static net.sourceforge.ajaxtags.helpers.StringUtils.trim2Null;

import javax.servlet.jsp.JspException;

import net.sourceforge.ajaxtags.helpers.DIVElement;
import net.sourceforge.ajaxtags.helpers.HTMLElementFactory;
import net.sourceforge.ajaxtags.helpers.JavaScript;

/**
 * Tag handler for the toggle (on/off, true/false) AJAX tag.
 *
 * @author Darren Spurgeon
 * @author Jens Kapitza
 * @version $Revision: 86 $ $Date: 2007/06/20 20:55:56 $ $Author: jenskapitza $
 */
public class AjaxToggleTag extends BaseAjaxTag {

    private static final long serialVersionUID = 6877730352175914711L;

    private String ratings;

    private String defaultRating;

    private String state;

    private String onOff;

    private String containerClass;

    private String messageClass;

    private String selectedClass;

    private String selectedOverClass;

    private String selectedLessClass;

    private String overClass;

    private String updateFunction;

    public String getUpdateFunction() {
        return updateFunction;
    }

    public void setUpdateFunction(String updateFunction) {
        this.updateFunction = updateFunction;
    }

    public String getContainerClass() {
        return containerClass;
    }

    public void setContainerClass(String containerClass) {
        this.containerClass = containerClass;
    }

    public String getDefaultRating() {
        return defaultRating;
    }

    public void setDefaultRating(String defaultRating) {
        this.defaultRating = trim2Null(defaultRating);
    }

    public String getMessageClass() {
        return messageClass;
    }

    public void setMessageClass(String messageClass) {
        this.messageClass = messageClass;
    }

    public String getOnOff() {
        return onOff;
    }

    public void setOnOff(String onOff) {
        this.onOff = onOff;
    }

    public String getOverClass() {
        return overClass;
    }

    public void setOverClass(String overClass) {
        this.overClass = overClass;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = trim2Null(ratings);
    }

    public String getSelectedClass() {
        return selectedClass;
    }

    public void setSelectedClass(String selectedClass) {
        this.selectedClass = selectedClass;
    }

    public String getSelectedLessClass() {
        return selectedLessClass;
    }

    public void setSelectedLessClass(String selectedLessClass) {
        this.selectedLessClass = selectedLessClass;
    }

    public String getSelectedOverClass() {
        return selectedOverClass;
    }

    public void setSelectedOverClass(String selectedOverClass) {
        this.selectedOverClass = selectedOverClass;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private OptionsBuilder getOptions() {
        OptionsBuilder options = getOptionsBuilder();
        options.add("ratings", this.ratings, true);
        options.add("containerClass", this.containerClass, true);
        options.add("selectedClass", this.selectedClass, true);
        options.add("selectedOverClass", this.selectedOverClass, true);
        options.add("selectedLessClass", this.selectedLessClass, true);
        options.add("overClass", this.overClass, true);
        options.add("messageClass", this.messageClass, true);
        options.add("state", this.state, true);
        options.add("onOff", this.onOff, true);
        options.add("defaultRating", this.defaultRating, true);
        options.add("updateFunction", this.updateFunction, false);

        return options;
    }

    @Override
    public int doEndTag() throws JspException {
        final OptionsBuilder options = getOptions();

        final boolean xOnOff = Boolean.parseBoolean(this.onOff);
        final String AVOID_URL_START = "<a href=\"" + AJAX_VOID_URL + "\" title=\"";
        final String AVOID_URL_END = "\"></a>";
        // write opening div
        final HTMLElementFactory div = new DIVElement(getSource());
        div.setClassName(xOnOff ? getContainerClass() + " onoff" : getContainerClass());

        String[] ratingValues = this.ratings == null ? null : this.ratings.split(",");
        // / TODO write this in javascript
        // / XXX write this in javascript!!!!
        // write links
        if (xOnOff) {
            div.append(AVOID_URL_START);
            if (ratingValues != null) {
                if (defaultRating != null && this.defaultRating.equalsIgnoreCase(ratingValues[0])) {
                    div.append(ratingValues[0]).append("\" class=\"").append(this.selectedClass);
                } else {
                    div.append(ratingValues[1]);
                }
            }
            div.append(AVOID_URL_END);
        } else {
            boolean ratingMatch = false;
            for (String val : ratingValues) {
                div.append(AVOID_URL_START);
                if (defaultRating == null || ratingMatch) {
                    div.append(val);
                } else if (!ratingMatch || this.defaultRating.equalsIgnoreCase(val)) {
                    div.append(val).append("\" class=\"").append(this.selectedClass);
                    if (this.defaultRating.equalsIgnoreCase(val)) {
                        ratingMatch = true;
                    }
                }
                div.append(AVOID_URL_END);
            }
        }

        // write script
        JavaScript script = new JavaScript(this);
        script.newToggle(options);
        div.append(script);
        out(div);
        return EVAL_PAGE;
    }

    @Override
    public void releaseTag() {
        this.ratings = null;
        this.defaultRating = null;
        this.state = null;
        this.onOff = null;
        this.containerClass = null;
        this.messageClass = null;
        this.selectedClass = null;
        this.selectedOverClass = null;
        this.selectedLessClass = null;
        this.overClass = null;
    }
}

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
import javax.servlet.jsp.tagext.TagSupport;



/**
 * Tag handler for individual page within a AJAX tabbed panel.
 * 
 * @author Darren Spurgeon
 * @version $Revision: 86 $ $Date: 2007/06/20 20:55:56 $ $Author: jenskapitza $
 */
public class AjaxTabPageTag extends BaseAjaxTag {

    private static final long serialVersionUID = 1L;
    private String caption;
    private String defaultTab;


    public String getCaption() {
        return caption;
    }


    public void setCaption(String caption) {
        this.caption = caption;
    }


    public String getDefaultTab() {
        return defaultTab;
    }


    public void setDefaultTab(Object defaultTab) {
        setDefaultTab(String.valueOf(defaultTab));
    }


    public void setDefaultTab(String defaultTab) {
        this.defaultTab = String.valueOf(defaultTab);
    }

    
    @Override
    public String toString() {
        OptionsBuilder o = OptionsBuilder.getOptionsBuilder(); // clean one
        o.add("caption", getCaption(), true);
        o.add("baseUrl", getBaseUrl(), true);
        o.add("parameters", getParameters(), true);
        o.add("defaultTab", getDefaultTab(), false);
        return "{" + o.toString() + "}";
    }


    @Override
    public int doEndTag() throws JspException {
        AjaxTabPanelTag parent = (AjaxTabPanelTag) TagSupport.findAncestorWithClass(this,
                        AjaxTabPanelTag.class);
        parent.addPage(this);
        return EVAL_PAGE;
    }


    @Override
    public void releaseTag() {
        this.caption = null;
        this.defaultTab = null;
    }
}

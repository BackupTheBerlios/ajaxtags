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
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Tag handler for individual page within a AJAX tabbed panel.
 *
 * @author Darren Spurgeon
 * @version $Revision: 86 $ $Date: 2007/06/20 20:55:56 $ $Author: jenskapitza $
 */
public class AjaxTabPageTag extends BaseAjaxTag {

    private static final long serialVersionUID = 2438025605821348018L;

    private String caption;

    private String defaultTab;

    public final String getCaption() {
        return caption;
    }

    public final void setCaption(final String caption) {
        this.caption = caption;
    }

    public final String getDefaultTab() {
        return defaultTab;
    }

    public final void setDefaultTab(final String defaultTab) {
        this.defaultTab = String.valueOf(defaultTab);
    }

    @Override
    public String toString() {
        final OptionsBuilder options = OptionsBuilder.getOptionsBuilder(); // clean one
        options.add("caption", getCaption(), true);
        options.add("baseUrl", getBaseUrl(), true);
        options.add("parameters", getParameters(), true);
        options.add("defaultTab", getDefaultTab(), false);
        return "{" + options.toString() + "}";
    }

    @Override
    public int doEndTag() throws JspException {
        final AjaxTabPanelTag parent = (AjaxTabPanelTag) TagSupport.findAncestorWithClass(this,
                AjaxTabPanelTag.class);
        parent.addPage(this);
        return EVAL_PAGE;
    }

    @Override
    public void releaseTag() {
        this.caption = null; // NOPMD
        this.defaultTab = null; // NOPMD
    }
}

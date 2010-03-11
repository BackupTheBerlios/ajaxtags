/*
 * Copyright 2009-2010 AjaxTags-Team
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

import net.sourceforge.ajaxtags.helpers.DIVElement;

/**
 * Tag handler for AJAX tabbed panel.
 * 
 * @author Jens Kapitza
 * @version $Revision: 86 $ $Date: 2007/06/20 20:55:56 $ $Author: jenskapitza $
 */
public class AjaxTabPanelTag extends BaseAjaxBodyTag {

    private static final long serialVersionUID = 4008240512963947567L;

    private static final char PAGES_DELIMITER = ',';

    // TODO refactor with List?
    private StringBuilder pages = new StringBuilder();

    @Override
    protected void initParameters() throws JspException {
        pages = new StringBuilder();
    }

    @Override
    protected String getJsClass() {
        return JSCLASS_BASE + "TabPanel";
    }

    @Override
    protected OptionsBuilder getOptions() {
        final OptionsBuilder options = getOptionsBuilder();
        options.add("id", getId(), true);
        options.add("pages", getPages(), false);
        return options;
    }

    @Override
    public int doEndTag() throws JspException {
        // tabs
        if (pages.length() == 0) {
            throw new JspException("No tabs added to tab panel.");
        }

        final DIVElement div = new DIVElement(getId());
        div.append(buildScript());
        out(div);
        return EVAL_PAGE;
    }

    @Override
    public void releaseTag() {
        this.pages = null; // NOPMD
    }

    /**
     * Add one tab to panel.
     * 
     * @param ajaxTabPageTag
     *            tab
     */
    public final void addPage(final AjaxTabPageTag ajaxTabPageTag) {
        if (pages.length() > 0) {
            // append delimiter after previous tabs
            pages.append(PAGES_DELIMITER);
        }
        pages.append(ajaxTabPageTag.toString());
    }

    /**
     * Get list of tabs as JavaScript array (JSON).
     * 
     * @return JSON string with array of tabs
     */
    protected String getPages() {
        /*if (pages.length() > 0 && pages.charAt(pages.length() - 1) == PAGES_DELIMITER) {
            pages.deleteCharAt(pages.length() - 1);
        }*/
        return "[" + pages.toString() + "]";
    }
}

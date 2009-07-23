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

import net.sourceforge.ajaxtags.helpers.DIVElement;
import net.sourceforge.ajaxtags.helpers.HTMLElementFactory;
import net.sourceforge.ajaxtags.helpers.JavaScript;

/**
 * Tag handler for AJAX tabbed panel.
 * 
 * @author Jens Kapitza
 * @version $Revision: 86 $ $Date: 2007/06/20 20:55:56 $ $Author: jenskapitza $
 */
public class AjaxTabPanelTag extends BaseAjaxBodyTag {

    private static final long serialVersionUID = 4008240512963947567L;
    private StringBuilder pages = new StringBuilder();

    @Override
    protected void initParameters() throws JspException {
        pages = new StringBuilder();
    }

    @Override
    public int doEndTag() throws JspException {
        // tabs
        if (pages.length() > 0) {
            pages.deleteCharAt(pages.length() - 1);
        } else {
            throw new JspException("no pages");
        }
        OptionsBuilder op = getOptionsBuilder();
        op.add("id", getId(), true);
        op.add("pages", "[" + pages.toString() + " ]", false);

        HTMLElementFactory div = new DIVElement(getId());
        JavaScript script = new JavaScript(this);
        script.newTabPanel(op);
        div.append(script);
        out(div);
        return EVAL_PAGE;
    }

    @Override
    public void releaseTag() {
        this.pages = null;
    }

    public final void addPage(AjaxTabPageTag ajaxTabPageTag) {
        pages.append(ajaxTabPageTag.toString()).append(",");
    }
}

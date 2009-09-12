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
 * Wraps the scriptaculous' in-place editor
 * (http://github.com/madrobby/scriptaculous/wikis/ajax-inplaceeditor).
 * 
 * @author Musachy Barroso
 * @author Jens Kapitza
 * @version $Revision: 86 $ $Date: 2007/06/20 20:55:56 $ $Author: jenskapitza $
 */
public class AjaxEditorTag extends BaseAjaxTag {

    private static final long serialVersionUID = -1129699932287300119L;

    private String showAcceptButton;

    private String acceptText;

    private String showCancelLink;

    private String cancelText;

    private String savingText;

    private String mouseOverText;

    private String formId;

    private String rows;

    private String columns;

    private String highlightColor;

    @Override
    protected void releaseTag() {
        showAcceptButton = null;
        acceptText = null;
        showCancelLink = null;
        cancelText = null;
        savingText = null;
        mouseOverText = null;
        formId = null;
        rows = null;
        columns = null;
        highlightColor = null;

    }

    @Override
    public int doEndTag() throws JspException {
        OptionsBuilder options = getOptionsBuilder();

        options.add("okControl", this.showAcceptButton, false);
        options.add("okText", this.acceptText, true);
        options.add("cancelControl", this.showCancelLink, false);
        options.add("cancelText", this.cancelText, true);
        options.add("savingText", this.savingText, true);
        options.add("clickToEditText", this.mouseOverText, true);

        // -- is wrong!
        // options.add("callback", getPreFunction(), false);

        // externalControl, externalControlOnly, submitOnBlur, ajaxOptions

        options.add("formId", this.formId, true);
        options.add("rows", this.rows, true);
        options.add("cols", this.columns, true);
        options.add("highlightcolor", this.highlightColor, true);
        JavaScript script = new JavaScript();
        setVar("$editor_" + getTarget());
        script.append(getJSVariable());
        script.append("new Ajax.InPlaceEditor(\"");
        script.append(getTarget());
        script.append("\", \"");
        script.append(getBaseUrl());
        script.append("\", {\n");
        script.append(options.toString());
        script.append("});\n");
        out(script);
        return EVAL_PAGE;
    }

    public String getAcceptText() {
        return acceptText;
    }

    public void setAcceptText(String acceptText) {
        this.acceptText = acceptText;
    }

    public String getCancelText() {
        return cancelText;
    }

    public void setCancelText(String cancelText) {
        this.cancelText = cancelText;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public String getHighlightColor() {
        return highlightColor;
    }

    public void setHighlightColor(String highlightColor) {
        this.highlightColor = highlightColor;
    }

    public String getMouseOverText() {
        return mouseOverText;
    }

    public void setMouseOverText(String mouseOverText) {
        this.mouseOverText = mouseOverText;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getSavingText() {
        return savingText;
    }

    public void setSavingText(String savingText) {
        this.savingText = savingText;
    }

    public String getShowAcceptButton() {
        return showAcceptButton;
    }

    public void setShowAcceptButton(String showAcceptButton) {
        this.showAcceptButton = showAcceptButton;
    }

    public String getShowCancelLink() {
        return showCancelLink;
    }

    public void setShowCancelLink(String showCancelLink) {
        this.showCancelLink = showCancelLink;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }
}

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
        showAcceptButton = null; // NOPMD
        acceptText = null; // NOPMD
        showCancelLink = null; // NOPMD
        cancelText = null; // NOPMD
        savingText = null; // NOPMD
        mouseOverText = null; // NOPMD
        formId = null; // NOPMD
        rows = null; // NOPMD
        columns = null; // NOPMD
        highlightColor = null; // NOPMD
    }

    @Override
    public int doEndTag() throws JspException {
        final OptionsBuilder options = getOptionsBuilder();

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

        final JavaScript script = new JavaScript(this);
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

    public void setAcceptText(final String acceptText) {
        this.acceptText = acceptText;
    }

    public String getCancelText() {
        return cancelText;
    }

    public void setCancelText(final String cancelText) {
        this.cancelText = cancelText;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(final String columns) {
        this.columns = columns;
    }

    public String getHighlightColor() {
        return highlightColor;
    }

    public void setHighlightColor(final String highlightColor) {
        this.highlightColor = highlightColor;
    }

    public String getMouseOverText() {
        return mouseOverText;
    }

    public void setMouseOverText(final String mouseOverText) {
        this.mouseOverText = mouseOverText;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(final String rows) {
        this.rows = rows;
    }

    public String getSavingText() {
        return savingText;
    }

    public void setSavingText(final String savingText) {
        this.savingText = savingText;
    }

    public String getShowAcceptButton() {
        return showAcceptButton;
    }

    public void setShowAcceptButton(final String showAcceptButton) {
        this.showAcceptButton = showAcceptButton;
    }

    public String getShowCancelLink() {
        return showCancelLink;
    }

    public void setShowCancelLink(final String showCancelLink) {
        this.showCancelLink = showCancelLink;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(final String formId) {
        this.formId = formId;
    }
}

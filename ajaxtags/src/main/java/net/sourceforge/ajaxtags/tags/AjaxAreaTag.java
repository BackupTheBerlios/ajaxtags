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

import static org.apache.commons.lang.StringUtils.trimToNull;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import net.sourceforge.ajaxtags.helpers.DIVElement;

/**
 * Wraps any area on the page (with a DIV element) so that actions within that area refresh/load
 * inside the defined DIV region rather than inside the whole browser window.
 *
 * @author Darren Spurgeon
 * @author Jens Kapitza
 * @version $Revision: 86 $ $Date: 2007/06/20 20:55:56 $ $Author: jenskapitza $
 */
public class AjaxAreaTag extends AjaxAnchorsTag {

    public static final String TARGET_HEADER = "x-request-target";

    private static final long serialVersionUID = -7940387487602588115L;

    private String styleClass;

    private boolean ajaxAnchors;

    /**
     * Returns true if we answering to AJAX request: request has proper "X-Requested-With" and
     * "x-request-target" headers.
     */
    @Override
    public final boolean isAjaxRequest() {
        return super.isAjaxRequest() && isHttpRequestHeader(TARGET_HEADER, getId());
    }

    /**
     * @return Returns the styleClass.
     */
    public final String getStyleClass() {
        return this.styleClass;
    }

    /**
     * @param styleClass
     *            The styleClass to set.
     */
    public final void setStyleClass(final String styleClass) {
        this.styleClass = trimToNull(styleClass);
    }

    /*
     * @return Returns the ajaxAnchors.
     */
    /*public final boolean getAjaxAnchors() {
        return isAjaxAnchors();
    } // we don't need it!*/

    public final boolean isAjaxAnchors() {
        return this.ajaxAnchors;
    }

    /**
     * @param ajaxAnchors
     *            The ajaxAnchors to set.
     */
    public final void setAjaxAnchors(final boolean ajaxAnchors) {
        this.ajaxAnchors = ajaxAnchors;
    }

    /**
     * Clear page content before start of tag if we are processing ajax request.
     *
     * @throws JspException
     *             when HTTP response cannot be reset (has already had its status code and headers
     *             written)
     */
    @Override
    public void initParameters() throws JspException {
        if (isAjaxRequest()) {
            try {
                pageContext.getOut().clearBuffer();
            } catch (IOException e) {
                throw new JspException(e);
            }
        }
    }

    /**
     * Write body. Skip the rest of the page if we are processing ajax request.
     *
     * @return SKIP_PAGE for ajax request, EVAL_PAGE for usual request
     * @throws JspException
     *             on errors
     */
    @Override
    public int doEndTag() throws JspException {
        final DIVElement div = new DIVElement(getId());
        div.append(processContent(getBody()));
        if (getStyleClass() != null) {
            div.setClassName(getStyleClass());
        }
        out(isAjaxRequest() ? div.getBody() : div);
        return isAjaxRequest() ? SKIP_PAGE : EVAL_PAGE;
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    @Override
    public void releaseTag() {
        this.ajaxAnchors = false;
        this.styleClass = null;
    }

    /**
     * Process content.
     *
     * @param content
     * @return processed content
     * @throws JspException
     * @throws Exception
     */
    protected String processContent(final String content) throws JspException {
        return isAjaxAnchors() ? ajaxAnchors(content, getId(), getSourceClass()) : content;
    }

}

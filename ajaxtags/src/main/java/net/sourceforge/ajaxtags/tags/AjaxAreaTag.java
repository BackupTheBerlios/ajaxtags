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



/**
 * Wraps any area on the page (with a DIV element) so that actions within that
 * area refresh/load inside the defined DIV region rather than inside the whole
 * browser window.
 * 
 * @author Darren Spurgeon
 * @author Jens Kapitza
 * @version $Revision: 86 $ $Date: 2007/06/20 20:55:56 $ $Author: jenskapitza $
 */
public class AjaxAreaTag extends AjaxAnchorsTag {

    public final static String TARGET_HEADER = "x-request-target";


    @Override
    public boolean isAjaxRequest() {
        // this is only a ajaxrequest if the target is right!
        return super.isAjaxRequest() && isHttpRequestHeader(TARGET_HEADER, getId());
    }


    private static final long serialVersionUID = -7940387487602588115L;
    private String styleClass;
    private boolean ajaxAnchors;


    /**
     * @return Returns the styleClass.
     */
    public String getStyleClass() {
        return this.styleClass;
    }


    /**
     * @param styleClass
     *            The styleClass to set.
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }


    /**
     * @return Returns the ajaxAnchors.
     */
    public boolean getAjaxAnchors() {
        return this.ajaxAnchors;
    } // we don't need it!


    private boolean isAjaxAnchors() {
        return this.ajaxAnchors;
    }


    /**
     * @param ajaxAnchors
     *            The ajaxAnchors to set.
     */
    public void setAjaxAnchors(boolean ajaxAnchors) {
        this.ajaxAnchors = ajaxAnchors;
    }


    @Override
    public int doEndTag() throws JspException {
        DIVElement div = new DIVElement(getId());
        div.append(processContent(getBody()));
        div.setClassName(getStyleClass());
        out(isAjaxRequest() ? div.getBody() : div);

        if (isAjaxRequest() && getHttpServletResponse().isCommitted()){
            throw new JspException("the request was flushed before");
        }

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
     * Set initial parameters.
     * 
     * @throws JspException
     */
    @Override
    public void initParameters() throws JspException {
        if (isAjaxRequest() && getHttpServletResponse().isCommitted()) {
           throw new JspException("try to avoid flush befor");
        } else {
            getHttpServletResponse().reset();
       }
    }


    /**
     * Process content.
     * 
     * @param content
     * @return
     * @throws JspException
     * @throws Exception
     */
    protected String processContent(String content) throws JspException {
        return isAjaxAnchors() ? ajaxAnchors(content, getId()) : content;
    }

}

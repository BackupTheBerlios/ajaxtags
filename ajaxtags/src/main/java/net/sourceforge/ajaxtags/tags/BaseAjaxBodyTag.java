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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;



/**
 * 
 * @author Jens Kapitza
 * @version $Revision: 86 $ $Date: 2007/06/20 20:55:56 $ $Author: jenskapitza $
 */
public abstract class BaseAjaxBodyTag extends BodyTagSupport {

    private static final long serialVersionUID = 1L;
    public static final String HEADER_FLAG = "X-Requested-With";
    public static final String HEADER_FLAG_VALUE = "XMLHttpRequest";


    protected static String trim2Null(String str) {
        if (str != null && str.trim().length() > 0) {
            return str;
        }
        return null;
    }


    protected HttpServletRequest getHttpServletRequest() {
        return (HttpServletRequest) pageContext.getRequest();
    }


    protected boolean isHttpRequestHeader(final String headerName, final String headerValue) {
        return headerValue.equalsIgnoreCase(getHttpRequestHeader(headerName));
    }


    protected String getHttpRequestHeader(final String headerName) {
        return getHttpServletRequest().getHeader(headerName);
    }


    public BaseAjaxBodyTag() {
        super();
        release();
    }


    /**
     * detect if the client does a ajax call or not
     * 
     * @return true only if the client send the header with with XMLHttpRequest
     */
    protected boolean isAjaxRequest() {
        return isHttpRequestHeader(HEADER_FLAG, HEADER_FLAG_VALUE);
    }


    protected void out(CharSequence csec) throws JspException {
        try {
            pageContext.getOut().append(csec);
        }
        catch (IOException e) {
            throw new JspException(e);
        }

    }


    private boolean skipBody;


    /**
     * 
     * @return true if the body should be ignored
     */
    final protected void skipBody() {
        skipBody = true;
    }


    @Override
    final public int doStartTag() throws JspException {
        initParameters();
        return skipBody ? SKIP_BODY : EVAL_BODY_BUFFERED; // XXX area tag and
        // displaytag have
        // problems
        // with this!
    }


    @Override
    final public void release() {
        setId(null);

        this.target = null;
        this.baseUrl = null;
        this.parser = null;

        this.preFunction = null;
        this.postFunction = null;
        this.errorFunction = null;
        this.parameters = null;

        this.var = null;
        this.attachTo = null;

        this.source = null;
        this.sourceClass = null;
        this.eventType = null;
        releaseTag();
    }


    private String source;
    private String target;
    private String baseUrl;
    private String parser;
    private String parameters;
    private String preFunction;
    private String postFunction;
    private String errorFunction;
    private String var;
    private String attachTo;
    private String sourceClass;
    private String eventType;


    final public String getEventType() {
        return eventType;
    }


    final public void setEventType(String eventType) {
        this.eventType = trim2Null(eventType);
    }


    final public String getSourceClass() {
        return sourceClass;
    }


    final public void setSourceClass(String sourceClass) {
        this.sourceClass = trim2Null(sourceClass);
    }


    final public String getSource() {
        return source;
    }


    final public void setSource(String source) {
        this.source = trim2Null(source);
    }


    final public String getVar() {
        return var;
    }


    final public void setVar(String var) {
        this.var = trim2Null(var);
    }


    final public void setAttachTo(String attachTo) {
        this.attachTo = trim2Null(attachTo);
    }


    final public String getAttachTo() {
        return attachTo;
    }


    final protected String getJSVariable() {

        StringBuilder script = new StringBuilder();

        if (this.var != null) {
            if (this.attachTo != null) {
                script.append(this.attachTo).append(".").append(this.var);
            }
            else {
                script.append("var ").append(this.var);
            }
            script.append(" = "); // needed
        }
        return script.toString();
    }


    final public String getParameters() {
        return parameters;
    }


    final public void setParameters(String parameters) {
        this.parameters = trim2Null(parameters);
    }


    final public String getErrorFunction() {
        return errorFunction;
    }


    final public void setErrorFunction(String errorFunction) {
        this.errorFunction = trim2Null(errorFunction);
    }


    final public String getPostFunction() {
        return postFunction;
    }


    final public void setPostFunction(String postFunction) {
        this.postFunction = trim2Null(postFunction);
    }


    final public String getPreFunction() {
        return preFunction;
    }


    final public void setPreFunction(String preFunction) {
        this.preFunction = trim2Null(preFunction);
    }


    final public String getParser() {
        return parser;
    }


    final public void setParser(String parser) {
        this.parser = trim2Null(parser);
    }


    final public String getBaseUrl() {
        return baseUrl;
    }


    final public void setBaseUrl(String baseUrl) {
        this.baseUrl = trim2Null(baseUrl);

    }


    /**
     * @return Returns the target.
     */
    final public String getTarget() {
        return this.target;
    }


    /**
     * @param target
     *            The target to set.
     */
    final public void setTarget(String target) {
        this.target = trim2Null(target);
    }


    protected void initParameters() throws JspException {
    }


    /**
     * never call release -> ends in loop
     */
    protected void releaseTag() {
    }


    /**
     * 
     * @return the Optionsbuilder with default options
     */
    OptionsBuilder getOptionsBuilder() {
        return getOptionsBuilder(false);
    }


    OptionsBuilder getOptionsBuilder(boolean empty) {

        OptionsBuilder builder = OptionsBuilder.getOptionsBuilder();
        if (empty) {
            return builder;
        }
        builder.add("baseUrl", getBaseUrl(), true);
        builder.add("parser", getParser(), false);

        builder.add("target", getTarget(), true);
        builder.add("source", getSource(), true);
        builder.add("sourceClass", getSourceClass(), true);

        builder.add("eventType", getEventType(), true);

        builder.add("parameters", getParameters(), true);

        builder.add("onCreate", this.preFunction, false);
        builder.add("onComplete", this.postFunction, false);
        builder.add("onFailure", this.errorFunction, false);

        return builder;

    }


    public static final String AJAX_VOID_URL = "javascript://nop";


    /**
     * Helper to define new AJAX updater for onclick attribute.
     * 
     * @param target
     *            the target to request
     * @param href
     *            the url
     * @param opt
     *            options for javascript lib
     * @return the javascript code to do ajax update
     */
    final String getOnclickAjax(final String target, final String href, final OptionsBuilder opt) {
        OptionsBuilder options = OptionsBuilder.getOptionsBuilder(opt); // copy
        // all
        // options

        options.add("target", target, true);
        options.add("baseUrl", href, true);

        options.add("eventBase", "this", false);
        options.add("requestHeaders", "['" + AjaxAreaTag.TARGET_HEADER + "' , '" + target + "']",
                        false);
        // this.selfTargetOnClick = this.selfTargetOnClick ||
        StringBuffer onclick = new StringBuffer("new AjaxJspTag.OnClick({");
        onclick.append(options.toString());
        onclick.append("}); return false;");
        return onclick.toString();
    }


    protected String getBody() {
        BodyContent bc = this.getBodyContent();
        if (bc == null) {
            return null;
        }
        return bc.getString();
    }
}

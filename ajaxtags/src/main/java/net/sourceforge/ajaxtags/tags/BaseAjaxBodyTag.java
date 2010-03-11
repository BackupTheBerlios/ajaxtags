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

import static org.apache.commons.lang.StringUtils.trimToNull;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import net.sourceforge.ajaxtags.helpers.JavaScript;
import net.sourceforge.ajaxtags.servlets.AjaxActionHelper.HMTLAjaxHeader;

/**
 * 
 * @author Jens Kapitza
 * @version $Revision: 86 $ $Date: 2007/06/20 20:55:56 $ $Author: jenskapitza $
 */
public abstract class BaseAjaxBodyTag extends BodyTagSupport {

    public static final String HEADER_FLAG = "X-Requested-With";
    public static final String HEADER_FLAG_VALUE = "XMLHttpRequest";
    public static final String AJAX_VOID_URL = "javascript://nop";

    /**
     * Common prefix for all JavaScript class names.
     */
    public static final String JSCLASS_BASE = "AjaxJspTag.";

    private static final long serialVersionUID = 2128368408391947139L;

    /**
     * True if the body should be ignored.
     */
    private boolean skipBody;

    private String styleClass;

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

    protected HttpServletRequest getHttpServletRequest() {
        return (HttpServletRequest) pageContext.getRequest();
    }

    protected HttpServletResponse getHttpServletResponse() {
        return (HttpServletResponse) pageContext.getResponse();
    }

    protected boolean isHttpRequestHeader(final String headerName, final String headerValue) {
        return headerValue.equalsIgnoreCase(getHttpRequestHeader(headerName));
    }

    protected String getHttpRequestHeader(final String headerName) {
        return getHttpServletRequest().getHeader(headerName);
    }

    /**
     * Detect if the client does an AJAX call or not.
     * 
     * @return true only if the client send the header with XMLHttpRequest
     */
    protected boolean isAjaxRequest() {
        return isHttpRequestHeader(HEADER_FLAG, HEADER_FLAG_VALUE);
    }

    protected void out(final CharSequence csec) throws JspException {
        try {
            pageContext.getOut().append(csec);
        } catch (IOException e) {
            throw new JspException(e);
        }
    }

    /**
     * Ignore the body of the tag.
     */
    protected final void skipBody() {
        skipBody = true;
    }

    @Override
    public final int doStartTag() throws JspException {
        initParameters(); // EVAL_BODY need to be flushed if it is nested!
        // we should set the no cache headers!

        // enable the ajaxheaders
        for (HMTLAjaxHeader header : HMTLAjaxHeader.values()) {
            header.enable(getHttpServletResponse());
        }

        return skipBody ? SKIP_BODY : EVAL_BODY_BUFFERED;
    }

    @Override
    public final void release() {
        setId(null);

        this.target = null; // NOPMD
        this.baseUrl = null; // NOPMD
        this.parser = null; // NOPMD

        this.preFunction = null; // NOPMD
        this.postFunction = null; // NOPMD
        this.errorFunction = null; // NOPMD
        this.parameters = null; // NOPMD

        this.var = null; // NOPMD
        this.attachTo = null; // NOPMD

        this.source = null; // NOPMD
        this.sourceClass = null; // NOPMD
        this.eventType = null; // NOPMD
        
        this.styleClass = null;
        
        releaseTag();
    }

    public final String getEventType() {
        return eventType;
    }

    public final void setEventType(final String eventType) {
        this.eventType = trimToNull(eventType);
    }

    public final String getSourceClass() {
        return sourceClass;
    }

    public final void setSourceClass(final String sourceClass) {
        this.sourceClass = trimToNull(sourceClass);
    }

    public final String getSource() {
        return source;
    }

    public final void setSource(final String source) {
        this.source = trimToNull(source);
    }

    public final String getVar() {
        return var;
    }

    public final void setVar(final String var) {
        this.var = trimToNull(var);
    }

    public final void setAttachTo(final String attachTo) {
        this.attachTo = trimToNull(attachTo);
    }

    public final String getAttachTo() {
        return attachTo;
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

    /**
     * Build JavaScript assignment string.
     * 
     * @return String with assignment to variable "var x = " or field "object.field = "
     */
    public final String getJSVariable() {
        final StringBuilder script = new StringBuilder();
        if (this.var != null) {
            if (this.attachTo == null) {
                script.append("var ");
            } else {
                script.append(this.attachTo).append(".");
            }
            script.append(this.var).append(" = ");
        }
        return script.toString();
    }

    /**
     * Return JavaScript class for JavaScript class corresponding to this tag (e.g.
     * "AjaxJspTag.Submit" for AjaxSubmitTag Java tag).
     * 
     * @return String with JavaScript class suffix
     */
    protected String getJsClass() {
        throw new UnsupportedOperationException(
                "You must implement getJsClass() in your tag class to use buildScript().");
    }

    /**
     * Options for JavaScript generation.
     * 
     * @return default options
     */
    protected OptionsBuilder getOptions() {
        return getOptionsBuilder();
    }

    /**
     * Generate JavaScript for tag.
     * 
     * @return JavaScript
     */
    public JavaScript buildScript() {
        return new JavaScript(getJSVariable() + "new " + getJsClass() + "({" + getOptions() + "});");
    }

    public final String getParameters() {
        return parameters;
    }

    public final void setParameters(final String parameters) {
        this.parameters = trimToNull(parameters);
    }

    public final String getErrorFunction() {
        return errorFunction;
    }

    public final void setErrorFunction(final String errorFunction) {
        this.errorFunction = trimToNull(errorFunction);
    }

    public final String getPostFunction() {
        return postFunction;
    }

    public final void setPostFunction(final String postFunction) {
        this.postFunction = trimToNull(postFunction);
    }

    public final String getPreFunction() {
        return preFunction;
    }

    public final void setPreFunction(final String preFunction) {
        this.preFunction = trimToNull(preFunction);
    }

    public final String getParser() {
        return parser;
    }

    public final void setParser(final String parser) {
        this.parser = trimToNull(parser);
    }

    public final String getBaseUrl() {
        return baseUrl;
    }

    public final void setBaseUrl(final String baseUrl) {
        this.baseUrl = trimToNull(baseUrl);
    }

    /**
     * @return Returns the target.
     */
    public final String getTarget() {
        return this.target;
    }

    /**
     * @param target
     *            The target to set.
     */
    public final void setTarget(final String target) {
        this.target = trimToNull(target);
    }

    protected void initParameters() throws JspException {
    }

    /**
     * Never call release() from releaseTag() -> ends in loop.
     */
    protected void releaseTag() {
    }

    /**
     * @return the OptionsBuilder with default options
     */
    protected OptionsBuilder getOptionsBuilder() {
        return getOptionsBuilder(false);
    }

    protected OptionsBuilder getOptionsBuilder(final boolean empty) {
        final OptionsBuilder builder = OptionsBuilder.getOptionsBuilder();
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

    /**
     * Helper to define new AJAX updater for onclick attribute.
     * 
     * @param target
     *            the target to request
     * @param href
     *            the URL
     * @param opt
     *            options for javascript library
     * @return the javascript code to do AJAX update
     */
    protected final String getOnclickAjax(final String target, final String href,
            final OptionsBuilder opt) {
        final OptionsBuilder options = OptionsBuilder.getOptionsBuilder(opt);
        // copy all options
        options.add("target", target, true);
        options.add("baseUrl", href, true);

        options.add("eventBase", "this", false);
        options.add("requestHeaders", "['" + AjaxAreaTag.TARGET_HEADER + "', '" + target + "']",
                false);

        // TODO with JavaScript class
        final StringBuilder onclick = new StringBuilder("new AjaxJspTag.OnClick({");
        onclick.append(options.toString());
        onclick.append("}); return false;");
        return onclick.toString();
    }

    protected String getBody() {
        final BodyContent body = this.getBodyContent();
        if (body == null) {
            return null;
        }
        return body.getString();
    }
}

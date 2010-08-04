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
package net.sourceforge.ajaxtags;

import java.io.IOException;
import java.util.Enumeration;

import javax.el.ELContext;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.VariableResolver;
import javax.servlet.jsp.tagext.BodyContent;

/**
 * Fake PageContext to test tags.
 *
 * @author В.Хомяков
 * @version $Revision$ $Date$ $Author$
 */
public class FakePageContext extends PageContext {

    private final BodyContent content = new FakeBodyContent();
    private final ServletRequest request = new FakeHttpServletRequest();
    private final ServletResponse response = new FakeHttpServletResponse();

    @Override
    public void forward(final String relativeUrlPath) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public Exception getException() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getPage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ServletRequest getRequest() {
        return request;
    }

    @Override
    public ServletResponse getResponse() {
        return response;
    }

    @Override
    public ServletConfig getServletConfig() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ServletContext getServletContext() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HttpSession getSession() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void handlePageException(final Exception e) throws ServletException, IOException {
        // empty method
    }

    @Override
    public void handlePageException(final Throwable t) throws ServletException, IOException {
        // empty method
    }

    @Override
    public void include(final String relativeUrlPath) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void include(final String relativeUrlPath, final boolean flush) throws ServletException,
            IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void initialize(final Servlet servlet, final ServletRequest request,
            final ServletResponse response, final String errorPageURL, final boolean needsSession,
            final int bufferSize, final boolean autoFlush) throws IOException,
            IllegalStateException, IllegalArgumentException {
        // TODO Auto-generated method stub
    }

    @Override
    public void release() {
        // empty method
    }

    @Override
    public Object findAttribute(final String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getAttribute(final String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getAttribute(final String name, final int scope) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Enumeration<String> getAttributeNamesInScope(final int scope) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getAttributesScope(final String name) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public JspWriter getOut() {
        return content;
    }

    @Override
    public void removeAttribute(final String name) {
        // TODO Auto-generated method stub
    }

    @Override
    public void removeAttribute(final String name, final int scope) {
        // TODO Auto-generated method stub
    }

    @Override
    public void setAttribute(final String name, final Object value) {
        // TODO Auto-generated method stub
    }

    @Override
    public void setAttribute(final String name, final Object value, final int scope) {
        // TODO Auto-generated method stub
    }

    @Override
    public ELContext getELContext() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ExpressionEvaluator getExpressionEvaluator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public VariableResolver getVariableResolver() {
        // TODO Auto-generated method stub
        return null;
    }
}

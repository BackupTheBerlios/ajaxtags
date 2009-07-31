/**
 * $HeadURL$
 * $Revision$
 * $Date$
 */
package net.sourceforge.ajaxtags;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Locale;

import javax.el.ELContext;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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

    private BodyContent content = new FakeBodyContent();

    @Override
    public void forward(String relativeUrlPath) throws ServletException, IOException {
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ServletResponse getResponse() {
        return new HttpServletResponse() {

            @Override
            public void setLocale(Locale loc) {
                // TODO Auto-generated method stub

            }

            @Override
            public void setContentType(String type) {
                // TODO Auto-generated method stub

            }

            @Override
            public void setContentLength(int len) {
                // TODO Auto-generated method stub

            }

            @Override
            public void setCharacterEncoding(String charset) {
                // TODO Auto-generated method stub

            }

            @Override
            public void setBufferSize(int size) {
                // TODO Auto-generated method stub

            }

            @Override
            public void resetBuffer() {
                // TODO Auto-generated method stub

            }

            @Override
            public void reset() {
                // TODO Auto-generated method stub

            }

            @Override
            public boolean isCommitted() {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public PrintWriter getWriter() throws IOException {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public ServletOutputStream getOutputStream() throws IOException {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Locale getLocale() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String getContentType() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String getCharacterEncoding() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public int getBufferSize() {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public void flushBuffer() throws IOException {
                // TODO Auto-generated method stub

            }

            @Override
            public void setStatus(int sc, String sm) {
                // TODO Auto-generated method stub

            }

            @Override
            public void setStatus(int sc) {
                // TODO Auto-generated method stub

            }

            @Override
            public void setIntHeader(String name, int value) {
                // TODO Auto-generated method stub

            }

            @Override
            public void setHeader(String name, String value) {
                // TODO Auto-generated method stub

            }

            @Override
            public void setDateHeader(String name, long date) {
                // TODO Auto-generated method stub

            }

            @Override
            public void sendRedirect(String location) throws IOException {
                // TODO Auto-generated method stub

            }

            @Override
            public void sendError(int sc, String msg) throws IOException {
                // TODO Auto-generated method stub

            }

            @Override
            public void sendError(int sc) throws IOException {
                // TODO Auto-generated method stub

            }

            @Override
            public String encodeUrl(String url) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String encodeURL(String url) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String encodeRedirectUrl(String url) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String encodeRedirectURL(String url) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public boolean containsHeader(String name) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void addIntHeader(String name, int value) {
                // TODO Auto-generated method stub

            }

            @Override
            public void addHeader(String name, String value) {
                // TODO Auto-generated method stub

            }

            @Override
            public void addDateHeader(String name, long date) {
                // TODO Auto-generated method stub

            }

            @Override
            public void addCookie(Cookie cookie) {
                // TODO Auto-generated method stub

            }
        };
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
    public void handlePageException(Exception e) throws ServletException, IOException {
    }

    @Override
    public void handlePageException(Throwable t) throws ServletException, IOException {
    }

    @Override
    public void include(String relativeUrlPath) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void include(String relativeUrlPath, boolean flush) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void initialize(Servlet servlet, ServletRequest request, ServletResponse response,
            String errorPageURL, boolean needsSession, int bufferSize, boolean autoFlush)
            throws IOException, IllegalStateException, IllegalArgumentException {
        // TODO Auto-generated method stub
    }

    @Override
    public void release() {
    }

    @Override
    public Object findAttribute(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getAttribute(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getAttribute(String name, int scope) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Enumeration<String> getAttributeNamesInScope(int scope) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getAttributesScope(String name) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public JspWriter getOut() {
        return content;
    }

    @Override
    public void removeAttribute(String name) {
        // TODO Auto-generated method stub
    }

    @Override
    public void removeAttribute(String name, int scope) {
        // TODO Auto-generated method stub
    }

    @Override
    public void setAttribute(String name, Object value) {
        // TODO Auto-generated method stub
    }

    @Override
    public void setAttribute(String name, Object value, int scope) {
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

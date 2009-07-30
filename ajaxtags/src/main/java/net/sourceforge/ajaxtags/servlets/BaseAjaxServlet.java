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
package net.sourceforge.ajaxtags.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * An abstract class from which each servlet extends. This class wraps the XML creation (delegated
 * to the child servlet class) and submission back through the HTTP response.
 *
 * @author Jens Kapitza
 * @version $Revision$ $Date$ $Author$
 */
public abstract class BaseAjaxServlet extends GenericServlet implements BaseAjaxXmlAction {

    private static final long serialVersionUID = -1772422788542156185L;

    /**
     * Get information about servlet.
     *
     * @return information about servlet
     */
    @Override
    public String getServletInfo() {
        return "Ajax Servlet (AjaxTags)";
    }

    /**
     * Invoke the {@link #getXmlContent(HttpServletRequest, HttpServletResponse)} method.
     *
     * @param request
     *            the {@link ServletRequest}
     * @param response
     *            the {@link ServletResponse}
     * @throws ServletException
     *             any errors
     * @throws IOException
     *             any IO error
     */
    @Override
    public final void service(final ServletRequest request, final ServletResponse response)
            throws ServletException, IOException {
        final String xml = AjaxActionHelper.invoke(this, (HttpServletRequest) request,
                (HttpServletResponse) response);

        final PrintWriter pw = response.getWriter();
        pw.write(xml);
        pw.flush(); // alles senden!
    }
    
    /**
     * @return the encoding default to UTF-8
     */
    @Override
    public String getXMLEncoding() {
        return "UTF-8";
    }
}

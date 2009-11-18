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
package net.sourceforge.ajaxtags.servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Jens Kapitza
 * @version $Revision$ $Date$ $Author$
 */
public final class AjaxActionHelper {

    private static final String HDR_PRAGMA = "Pragma";
    private static final String HDR_CACHE_CONTROL = "Cache-Control";

    // do not create an object
    private AjaxActionHelper() {
    }

    /**
     * Invoke the AJAX action and setup the request and response.
     * 
     * @param action
     *            the BaseAjaxXmlAction implementation
     * @param request
     *            the request
     * @param response
     *            the response
     * @return the XML content from action
     * @throws ServletException
     *             for any errors
     */
    public static String invoke(final BaseAjaxXmlAction action, final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException {
        // prepare CALL
        try {
            request.setCharacterEncoding(action.getXMLEncoding());
        } // we will use UTF-8
        catch (UnsupportedEncodingException e) {
            throw new ServletException(e);
        }
        // Set content to XML
        response.setContentType("text/xml; charset=" + action.getXMLEncoding());
        addNoCacheHeaders(response);
        try {
            return action.getXmlContent(request, response);
        } catch (IOException e) {
            throw new ServletException(e);
        }
    }

    /**
     * Add Cache-Control header to Servlet Response.
     * 
     * @param response
     *            Servlet response to be modified
     */
    public static void addNoCacheHeaders(final HttpServletResponse response) {
        // Set HTTP/1.1 no-cache headers.
        response.setHeader(HDR_CACHE_CONTROL, "no-store, max-age=0, no-cache");
        response.addHeader(HDR_CACHE_CONTROL, "must-revalidate, post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader(HDR_PRAGMA, "no-cache");
    }
}

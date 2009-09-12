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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Simple Action which can be invoked by AjaxActionHelper.
 * 
 * @author Jens Kapitza
 * @version $Revision$ $Date$ $Author$
 */
public interface BaseAjaxXmlAction {

    /**
     * Each child class should override this method to generate the specific XML content necessary
     * for each AJAX action.
     * 
     * @param request
     *            the HttpServletRequest object
     * @param response
     *            the HttpServletResponse object
     * @return a String representation of the XML response/content
     * @throws ServletException
     *             any errors
     * @throws IOException
     *             any IO error
     */
    String getXmlContent(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException;

    /**
     * Each child class should override this method to set the specific XML encoding.
     * 
     * @return the XML encoding for the AJAX action
     */
    String getXMLEncoding();
}

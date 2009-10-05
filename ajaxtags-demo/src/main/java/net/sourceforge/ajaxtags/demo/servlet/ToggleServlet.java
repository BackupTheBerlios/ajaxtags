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
package net.sourceforge.ajaxtags.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * An example servlet that responds to an ajax:toggle tag action. This servlet would be referenced
 * by the baseUrl attribute of the JSP tag.
 *
 * @author Darren L. Spurgeon
 * @version $Revision: 28 $ $Date: 2008-11-09 23:12:33 +0100 (So, 09. Nov 2008) $
 */
public class ToggleServlet extends HttpServlet {

    private static final long serialVersionUID = -394141738706308222L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String rating = request.getParameter("rating");
        if (rating != null && rating.length() > 0) {
            if ("2".equals(request.getParameter("type"))) {
                // request from second toggle sample - on-off
                request.getSession().setAttribute("toggleRatingOnOff", rating);
            } else {
                // request from first toggle sample - star-rating
                request.getSession().setAttribute("toggleRating", rating);
            }
        } else {
            rating = "";
        }

        PrintWriter pw = response.getWriter();
        pw.write(rating);
        pw.close();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}

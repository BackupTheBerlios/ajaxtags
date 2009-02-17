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
package net.sourceforge.ajaxtags.demo.servlet;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.ajaxtags.demo.Car;
import net.sourceforge.ajaxtags.demo.CarService;
import net.sourceforge.ajaxtags.servlets.BaseAjaxServlet;



/**
 * An example servlet that responds to an ajax:htmlContent tag action.
 * 
 * @author Darren L. Spurgeon
 * @version $Revision: 28 $ $Date: 2008-11-09 23:12:33 +0100 (So, 09. Nov 2008)
 *          $
 */
public class HtmlContentServlet extends BaseAjaxServlet {

    private static final long serialVersionUID = 1L;


    /**
     * @see org.ajaxtags.demo.servlet.BaseAjaxServlet#getXmlContent(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public String getXmlContent(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException  {
        String make = request.getParameter("make");
        if (make == null) {
            make = "";
        }
        CarService service = new CarService();
        List<Car> list = service.getModelsByMake(make);

        StringBuffer html = new StringBuffer();
        html.append("<h2>").append(make.toUpperCase()).append("</h2><p>Models</p><ul>");
        for (Car car : list) {
            html.append("<li>").append(car.getModel()).append("</li>");
        }
        html.append("</ul>");
        html.append("<br>");
        html.append("<code>Last Updated: ");
        html.append(new Date());
        html.append("</code>");

        return html.toString();
    }

}

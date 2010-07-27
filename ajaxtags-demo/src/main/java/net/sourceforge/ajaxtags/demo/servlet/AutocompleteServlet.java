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
package net.sourceforge.ajaxtags.demo.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.ajaxtags.demo.Car;
import net.sourceforge.ajaxtags.demo.CarService;
import net.sourceforge.ajaxtags.servlets.BaseAjaxServlet;
import net.sourceforge.ajaxtags.xml.AjaxXmlBuilder;

/**
 * An example servlet that responds to an ajax:autocomplete tag action. This servlet would be
 * referenced by the baseUrl attribute of the JSP tag.
 * <p>
 * This servlet should generate XML in the following format:
 * </p>
 * <pre>
 * &lt;![CDATA[&lt;?xml version=&quot;1.0&quot;?&gt;
 * &lt;list&gt;
 *   &lt;item value=&quot;Item1&quot;&gt;First Item&lt;/item&gt;
 *   &lt;item value=&quot;Item2&quot;&gt;Second Item&lt;/item&gt;
 *   &lt;item value=&quot;Item3&quot;&gt;Third Item&lt;/item&gt;
 * &lt;/list&gt;]]&gt;
 * </pre>
 *
 * @author Darren L. Spurgeon
 * @version $Revision: 28 $ $Date: 2008-11-09 23:12:33 +0100 (So, 09. Nov 2008) $
 */
public class AutocompleteServlet extends BaseAjaxServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see BaseAjaxServlet#getXmlContent(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public String getXmlContent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String model = request.getParameter("model");
        CarService service = new CarService();
        List<Car> list = service.getModelsByName(model);
        // Thread.sleep(3000); // sleep 3 seconds
        // Create xml schema
        try {
            return new AjaxXmlBuilder().addItems(list).toString();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

}

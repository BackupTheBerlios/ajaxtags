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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.ajaxtags.servlets.BaseAjaxServlet;
import net.sourceforge.ajaxtags.xml.AjaxXmlBuilder;



/**
 * An example servlet that responds to an ajax:updateField tag action. This
 * servlet would be referenced by the baseUrl attribute of the JSP tag.
 * 
 * @author Darren L. Spurgeon
 * @version $Revision: 28 $ $Date: 2008-11-09 23:12:33 +0100 (So, 09. Nov 2008)
 *          $
 */
public class FormUpdateServlet extends BaseAjaxServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final double MPH_TO_KPH = 1.609344;

    private static final double MPH_TO_MPS = 0.44704;


    /**
     * @see BaseAjaxServlet#getXmlContent(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public String getXmlContent(HttpServletRequest request, HttpServletResponse response) {
        double mph = 0, kph = 0, mps = 0;
        try {
            mph = Double.parseDouble(request.getParameter("mph"));
        }
        catch (Exception e) {
        }
        kph = mph * FormUpdateServlet.MPH_TO_KPH;
        mps = mph * FormUpdateServlet.MPH_TO_MPS;

        return new AjaxXmlBuilder().addItem("kph", Double.toString(kph)).addItem("mps",
                        Double.toString(mps)).toString();
    }

}

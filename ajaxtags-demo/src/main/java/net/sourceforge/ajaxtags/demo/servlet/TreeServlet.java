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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.ajaxtags.helpers.TreeItem;
import net.sourceforge.ajaxtags.servlets.BaseAjaxServlet;
import net.sourceforge.ajaxtags.xml.AjaxTreeXmlBuilder;



/**
 * An example servlet that responds to an ajax:tree tag action.
 * 
 * @author Musachy Barroso
 * @version $Revision: 28 $ $Date: 2008-11-25 21:27:48 +0100 (Di, 25. Nov 2008)
 *          $
 */
public class TreeServlet extends BaseAjaxServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;


    /**
     * @see BaseAjaxServlet#getXmlContent(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public String getXmlContent(HttpServletRequest request, HttpServletResponse response)
                    throws Exception {
        String action = request.getParameter("action");
        String node = request.getParameter("node");
        if ((action != null) && action.equalsIgnoreCase("info")) {
            return "<b>" + node + "</b> at " + new Date(); // nur den namen
            // liefern
        }

        if (node == null) {
            return "<b>node is null</b>";
        }

        AjaxTreeXmlBuilder treeBuilder = new AjaxTreeXmlBuilder();
        if ("cars".equals(node)) {
            treeBuilder.addItem("American", "American", true, "#");
            treeBuilder.addItem("Japanese", "Japanese", false, "#");
        }
        else if (node.equals("American")) {
            treeBuilder.addItem("Ford", "Ford", true, "#");
        }
        else if (node.equals("Ford")) {
            TreeItem item = new TreeItem("Mustang", "Mustang", false);
            item.setLeaf(true);
            treeBuilder.add(item);
            item = new TreeItem("FordGT", "FordGT", false);
            item.setLeaf(true);
            treeBuilder.add(item);
        }
        else if (node.equals("Ford")) {
            treeBuilder.addItem("Mustang", "Mustang", false, "#");
            treeBuilder.addItem("FordGT", "FordGT", false, "#");
        }
        else if (node.equals("Japanese")) {
            treeBuilder.addItem("Honda", "Honda", true, "#");
        }
        else if (node.equals("Honda")) {
            treeBuilder.addItem("Civic", "Civic", true, "#");
            treeBuilder.addItem("Accord", "Accord", true, "#");
        }
        return treeBuilder.toString();
    }

}

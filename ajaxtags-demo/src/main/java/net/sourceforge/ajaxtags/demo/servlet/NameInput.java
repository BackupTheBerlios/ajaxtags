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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.ajaxtags.servlets.BaseAjaxServlet;
import net.sourceforge.ajaxtags.xml.AjaxXmlBuilder;

public class NameInput extends BaseAjaxServlet {

    private static final long serialVersionUID = 8397661247446643963L;

    public String getXmlContent(HttpServletRequest arg0, HttpServletResponse arg1)
            throws ServletException, IOException {
        AjaxXmlBuilder xml = new AjaxXmlBuilder();
        xml.addItem("name", arg0.getParameter("name"));
        int age = -1;
        try {
            age = Integer.parseInt(arg0.getParameter("age"));
        } catch (NumberFormatException e) {
            age = -1;
        }

        xml.addItem("age", age == -1 ? "$not a number (" + arg0.getParameter("age") + ") ;)"
                : String.valueOf(age));
        return xml.toString();
    }
}

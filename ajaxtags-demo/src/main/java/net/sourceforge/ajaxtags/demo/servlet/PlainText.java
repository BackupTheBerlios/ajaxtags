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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public final class PlainText extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
                    IOException {

        // load the frameworks and the javascript things
        String res = req.getRequestURI();
        // finde the needed resource
        // js/...
        // or css/...
        String name = res.substring(req.getContextPath().length());
        InputStream in = null;
        if (name.endsWith(".txt")) {
            name = name.substring(9, name.length() - 4);
            System.out.println(name);
            in = new FileInputStream(getServletContext().getRealPath(name));
        }

        BufferedReader r = new BufferedReader(new InputStreamReader(in));
        String line = null;
        BufferedWriter w = new BufferedWriter(resp.getWriter());
        while ((line = r.readLine()) != null) {
            w.append(line);
            w.newLine();
        }
        w.flush();

    }
}

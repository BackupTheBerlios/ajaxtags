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
package net.sourceforge.ajaxtags.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.apache.commons.lang.StringUtils.trimToNull;

/**
 * This class loads the frameworks, javascript files and css files from the jar file. Changes to the
 * javascript or css files can only be done by repacking the ajaxtags-resources.jar file.
 *
 * @author Jens Kapitza
 * @since 1.5
 */
public final class SourceLoader extends GenericServlet {

    /**
     * This is the base where we can find the source which should be loaded.
     */
    public static final String BASE = "/net/sourceforge/ajaxtags";

    private static final int BUFFER = 1024;

    private static final long serialVersionUID = 4621190060172885624L;

    private String prefix;

    /**
     * Write the content from the jarfile to the client stream. Use bufferedwriter to handle
     * newline. The filename is found in the requestURI, the contextpath is excluded and replaced
     * with the base package name.
     *
     * @param req
     *            the request
     * @param resp
     *            the response
     * @throws ServletException
     *             any errors
     * @throws IOException
     *             any io errors
     */
    public void service(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        final String res = req.getRequestURI();
        final OutputStream stream = resp.getOutputStream();
        final byte[] buffer = new byte[BUFFER];

        String loadPath = res.substring(req.getContextPath().length());

        if (prefix != null && loadPath.startsWith(prefix)) {
            loadPath = loadPath.substring(prefix.length());
        }

        InputStream in = null;
        try {
            in = getClass().getResourceAsStream(SourceLoader.BASE + loadPath);
            if (in == null) {
                throw new IOException("resource not found");
            }
            int read = -1;
            while ((read = in.read(buffer)) != -1) {
                stream.write(buffer, 0, read);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            stream.flush();
            stream.close();
        }
    }

    /**
     * Warp to http request and response.
     *
     * @param req0
     *            the request
     * @param resp0
     *            the response
     * @throws ServletException
     *             any errors
     * @throws IOException
     *             any io errors
     */
    @Override
    public void service(final ServletRequest req0, final ServletResponse resp0)
            throws ServletException, IOException {
        service((HttpServletRequest) req0, (HttpServletResponse) resp0);
    }

    /**
     * Check the configuration if we do have a prefix.
     *
     */
    @Override
    public void init() throws ServletException {
        prefix = trimToNull(getInitParameter("prefix"));
    }
}

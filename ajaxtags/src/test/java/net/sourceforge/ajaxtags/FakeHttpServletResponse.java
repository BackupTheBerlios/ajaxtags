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
package net.sourceforge.ajaxtags;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Fake HttpServletResponse to test tags.
 *
 * @author В.Хомяков
 * @version $Revision$ $Date$ $Author$
 */
public class FakeHttpServletResponse implements HttpServletResponse {

    public void addCookie(final Cookie cookie) {
        // TODO Auto-generated method stub
    }

    public void addDateHeader(final String name, final long date) {
        // TODO Auto-generated method stub
    }

    public void addHeader(final String name, final String value) {
        // TODO Auto-generated method stub
    }

    public void addIntHeader(final String name, final int value) {
        // TODO Auto-generated method stub
    }

    public boolean containsHeader(final String name) {
        // TODO Auto-generated method stub
        return false;
    }

    public String encodeRedirectURL(final String url) {
        // TODO Auto-generated method stub
        return null;
    }

    public String encodeRedirectUrl(final String url) {
        // TODO Auto-generated method stub
        return null;
    }

    public String encodeURL(final String url) {
        // TODO Auto-generated method stub
        return null;
    }

    public String encodeUrl(final String url) {
        // TODO Auto-generated method stub
        return null;
    }

    public void sendError(final int sc) throws IOException {
        // TODO Auto-generated method stub
    }

    public void sendError(final int sc, final String msg) throws IOException {
        // TODO Auto-generated method stub
    }

    public void sendRedirect(final String location) throws IOException {
        // TODO Auto-generated method stub
    }

    public void setDateHeader(final String name, final long date) {
        // TODO Auto-generated method stub
    }

    public void setHeader(final String name, final String value) {
        // TODO Auto-generated method stub
    }

    public void setIntHeader(final String name, final int value) {
        // TODO Auto-generated method stub
    }

    public void setStatus(final int sc) {
        // TODO Auto-generated method stub
    }

    public void setStatus(final int sc, final String sm) {
        // TODO Auto-generated method stub
    }

    public void flushBuffer() throws IOException {
        // TODO Auto-generated method stub
    }

    public int getBufferSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    public String getCharacterEncoding() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getContentType() {
        // TODO Auto-generated method stub
        return null;
    }

    public Locale getLocale() {
        // TODO Auto-generated method stub
        return null;
    }

    public ServletOutputStream getOutputStream() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    public PrintWriter getWriter() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isCommitted() {
        // TODO Auto-generated method stub
        return false;
    }

    public void reset() {
        // TODO Auto-generated method stub
    }

    public void resetBuffer() {
        // TODO Auto-generated method stub
    }

    public void setBufferSize(final int size) {
        // TODO Auto-generated method stub
    }

    public void setCharacterEncoding(final String charset) {
        // TODO Auto-generated method stub
    }

    public void setContentLength(final int len) {
        // TODO Auto-generated method stub
    }

    public void setContentType(final String type) {
        // TODO Auto-generated method stub
    }

    public void setLocale(final Locale loc) {
        // TODO Auto-generated method stub
    }
}

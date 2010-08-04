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
import java.io.Reader;
import java.io.Writer;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;

/**
 * Fake BodyContent to test tags.
 *
 * @author В.Хомяков
 * @version $Revision$ $Date$ $Author$
 */
public class FakeBodyContent extends BodyContent {

    private static final String NEW_LINE = "\n";

    private String content = "";

    protected FakeBodyContent(final JspWriter writer) {
        super(writer);
    }

    public FakeBodyContent() {
        super(null);
    }

    @Override
    public Reader getReader() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getString() {
        return content;
    }

    @Override
    public void writeOut(final Writer out) throws IOException {
        out.write(content);
    }

    @Override
    public void clear() throws IOException {
        content = "";
    }

    @Override
    public void clearBuffer() throws IOException {
        content = "";
    }

    @Override
    public void close() throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public int getRemaining() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void newLine() throws IOException {
        this.content += NEW_LINE;
    }

    @Override
    public Writer append(final CharSequence csq) throws IOException {
        this.content += csq;
        return this;
    }

    @Override
    public void print(final boolean b) throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void print(final char c) throws IOException {
        this.content += c;
    }

    @Override
    public void print(final int i) throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void print(final long l) throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void print(final float f) throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void print(final double d) throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void print(final char[] s) throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void print(final String s) throws IOException {
        this.content += s;
    }

    @Override
    public void print(final Object obj) throws IOException {
        this.content += obj;
    }

    @Override
    public void println() throws IOException {
        this.content += NEW_LINE;
    }

    @Override
    public void println(final boolean x) throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void println(final char x) throws IOException {
        this.content += x + NEW_LINE;
    }

    @Override
    public void println(final int x) throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void println(final long x) throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void println(final float x) throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void println(final double x) throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void println(final char[] x) throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void println(final String x) throws IOException {
        this.content += x + NEW_LINE;
    }

    @Override
    public void println(final Object x) throws IOException {
        this.content += x + NEW_LINE;
    }

    @Override
    public void write(final char[] cbuf, final int off, final int len) throws IOException {
        // TODO Auto-generated method stub
    }

}

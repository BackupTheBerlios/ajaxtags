/**
 * $HeadURL$
 * $Revision$
 * $Date$
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

    protected FakeBodyContent(JspWriter writer) {
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
    public void writeOut(Writer out) throws IOException {
        out.write(content);
    }

    @Override
    public void clear() throws IOException {
        content = "";
    }

    @Override
    public void clearBuffer() throws IOException {
        // TODO Auto-generated method stub
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
    public Writer append(CharSequence csq) throws IOException {
        this.content += csq;
        return this;
    }

    @Override
    public void print(boolean b) throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void print(char c) throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void print(int i) throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void print(long l) throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void print(float f) throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void print(double d) throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void print(char[] s) throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void print(String s) throws IOException {
        this.content += s;
    }

    @Override
    public void print(Object obj) throws IOException {
        this.content += obj;
    }

    @Override
    public void println() throws IOException {
        this.content += NEW_LINE;
    }

    @Override
    public void println(boolean x) throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void println(char x) throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void println(int x) throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void println(long x) throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void println(float x) throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void println(double x) throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void println(char[] x) throws IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void println(String x) throws IOException {
        this.content += x + NEW_LINE;
    }

    @Override
    public void println(Object x) throws IOException {
        this.content += x + NEW_LINE;
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        // TODO Auto-generated method stub
    }

}

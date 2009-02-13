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
package net.sourceforge.ajaxtags.helpers;

import java.util.HashMap;
import java.util.Map;



/**
 * diese klasse stellt eine möglichkeit um html elemente zu schreiben
 * 
 * @author jenskapitza
 */
public abstract class HTMLElementFactory implements CharSequence, Appendable {

    /**
     * der name ist das schl&uuml;sselelement f&uuml;r start bzw. endtag
     */
    private String name;
    /**
     * der body
     */
    private CharSequence body;
    /**
     * store the attributes of an html element
     */
    private Map<String, String> attributes;


    /**
     * erstelle ein html element
     * 
     * @param name
     *            der name des start bzw. endtag
     * @param id
     *            die id des elements
     * @param body
     *            der inhalt
     */
    protected HTMLElementFactory(final String name, final String id, final String body) {
        this.name = name;
        this.body = body;
        attributes = new HashMap<String, String>() {

            private static final long serialVersionUID = 1L;


            @Override
            public synchronized String put(final String key, final String value) {
                if (value != null) {
                    return super.put(key, value);
                }
                return null;
            }
        };
        if (id != null) {
            setId(id);
        }
    }


    /**
     * create a html element
     * 
     * @param name
     *            the tag name
     */
    protected HTMLElementFactory(final String name) {
        this(name, null);
    }


    /**
     * create a html element
     * 
     * @param name
     *            the tag name
     * @param id
     *            the id attribute
     */
    protected HTMLElementFactory(final String name, final String id) {
        this(name, null, null);
    }


    /**
     * 
     * @return the map of attributes
     */
    protected final Map<String, String> getAttributes() {
        return attributes;
    }


    /**
     * 
     * @return the tag name
     */
    protected final String getName() {
        return name;
    }


    /**
     * 
     * @return the body content or null if body is null
     */
    public final String getBody() {
        if (body != null) {
            return body.toString();
        }
        return null;
    }


    /**
     * set the body content
     * 
     * @param body
     *            the body content
     */
    public final void setBody(final CharSequence body) {
        this.body = body;
    }


    /**
     * 
     * @return the class name attribute
     */
    public final String getClassName() {
        return attributes.get("class");
    }


    /**
     * set the class name attribute
     * 
     * @param className
     *            the class attribute value
     */
    public final void setClassName(final String className) {
        attributes.put("class", className);
    }


    /**
     * 
     * @return the id attribute
     */
    public final String getId() {
        return attributes.get("id");
    }


    /**
     * set the id attribute
     * 
     * @param id
     *            the id attribute value
     */
    public final void setId(final String id) {
        attributes.put("id", id);
    }


    /**
     * @return the length
     */
    public final int length() {
        return toString().length();
    }


    /**
     * @see CharSequence#charAt(int)
     * @param index
     *            the index
     * @return the char at pos index
     */
    public final char charAt(final int index) {
        return toString().charAt(index);
    }


    /**
     * @see CharSequence#subSequence(int, int)
     * @param start
     *            the start index
     * @param end
     *            the end index
     * @return the substring from start to end
     */
    public final CharSequence subSequence(final int start, final int end) {
        return toString().subSequence(start, end);
    }


    protected void cleanAttributes() {
    }


    /**
     * @return the string value of this element
     */
    @Override
    public final String toString() {
        final StringBuilder s = new StringBuilder("<");
        s.append(getName());
        cleanAttributes();
        for (Map.Entry<String, String> e : getAttributes().entrySet()) {
            s.append(" ").append(e.getKey()).append("=\"");
            s.append(e.getValue().replaceAll("\"", "\\\"")).append("\"");
        }

        s.append(">");
        s.append(getBody());
        s.append("</").append(getName()).append(">");
        return s.toString();
    }


    /**
     * append {@link CharSequence} to this object
     * 
     * @param string
     *            the data to append
     * @return self
     */
    public final HTMLElementFactory append(final CharSequence string) {
        if (getBody() == null) {
            setBody(string);
        }
        else {
            setBody(getBody() + string);
        }
        return this;
    }


    /**
     * append a char
     * 
     * @param c
     *            the char to append
     * @return self
     */
    public final HTMLElementFactory append(final char c) {
        return append(String.valueOf(c));
    }


    /**
     * @param csq
     *            the char sequence
     * @param start
     *            the start index
     * @param end
     *            the end index
     * @return self
     */

    public final HTMLElementFactory append(final CharSequence csq, final int start, final int end) {
        return append(csq.subSequence(start, end));
    }
}

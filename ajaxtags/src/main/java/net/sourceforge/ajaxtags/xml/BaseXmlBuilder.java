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
package net.sourceforge.ajaxtags.xml;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to build valid XML as a base for all XML builders.
 *
 * @author Jens Kapitza
 * @version $Revision: 86 $ $Date: 2007/07/22 16:58:28 $ $Author: jenskapitza $
 * @param <V>
 *            type of list elements (Item, TreeItem)
 */
public abstract class BaseXmlBuilder<V> {

    /**
     * AJAX response opening tags.
     */
    static final String RESPONSE_START = "<ajax-response><response>";

    /**
     * AJAX response closing tags.
     */
    static final String RESPONSE_END = "</response></ajax-response>";

    /**
     * Starting CDATA sequence.
     */
    static final String CDATA_START = "<![CDATA[";

    /**
     * Ending CDATA sequence.
     */
    static final String CDATA_END = "]]>";

    private List<V> list = new ArrayList<V>();

    /**
     * Default encoding is UTF-8.
     */
    private String encoding = "UTF-8";

    protected List<V> getList() {
        return this.list;
    }

    protected void setList(final List<V> list) {
        this.list = list;
    }

    /**
     * @return the XML encoding
     */
    public String getEncoding() {
        return this.encoding;
    }

    /**
     * Set the XML encoding.
     *
     * @param encoding
     *            the XML encoding
     */
    public void setEncoding(final String encoding) {
        this.encoding = encoding;
    }

    /**
     *
     * @return the item list
     */
    protected List<V> getItems() {
        return getList();
    }

    /**
     * @return the XML body, XML encoding is added by {@link #toString()}
     */
    protected abstract String getXMLString();

    /**
     * @return the full XML document
     */
    @Override
    public String toString() {
        final StringBuilder xml = new StringBuilder("<?xml version=\"1.0\"");
        if (getEncoding() != null) {
            xml.append(" encoding=\"").append(getEncoding()).append("\"");
        }
        xml.append(" ?>");
        xml.append(getXMLString());
        return xml.toString();
    }

    /**
     * Add item to list.
     *
     * @param item
     *            the item to add
     * @return BaseXmlBuilder
     * @see ArrayList#add(Object)
     */
    public BaseXmlBuilder<V> add(final V item) {
        this.list.add(item);
        return this;
    }

    /**
     * Delete all items.
     */
    public void clear() {
        this.list.clear();
    }

    /**
     * Return the item at index.
     *
     * @param index
     *            the index
     * @return the item at index
     */
    public V get(final int index) {
        return this.list.get(index);
    }

    /**
     * Check if list contains no elements (is empty).
     *
     * @return true if it is empty else false
     */
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    /**
     * @return the item count
     */
    public int size() {
        return this.list.size();
    }

}

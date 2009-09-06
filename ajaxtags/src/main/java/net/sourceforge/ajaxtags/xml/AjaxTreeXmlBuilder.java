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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sourceforge.ajaxtags.helpers.TreeItem;

/**
 * Helper class to build valid XML, for the AjaxTreeTag, typically returned in a response to the
 * client.
 *
 * @author Musachy Barroso
 * @author Jens Kapitza
 * @version $Revision: 86 $ $Date: 2007/07/24 12:21:13 $ $Author: jenskapitza $
 */
public final class AjaxTreeXmlBuilder extends BaseXmlBuilder<TreeItem> {

    /**
     * Add tree item to XML builder.
     *
     * @param name
     *            name
     * @param value
     *            value
     * @return AjaxTreeXmlBuilder XML builder
     */
    public AjaxTreeXmlBuilder addItem(final String name, final String value) {
        return addItem(name, value, null);
    }

    /**
     * Add tree item to XML builder.
     *
     * @param name
     *            name
     * @param value
     *            value
     * @return AjaxTreeXmlBuilder XML builder
     */
    public AjaxTreeXmlBuilder addItemAsCData(final String name, final String value) {
        return addItemAsCData(name, value, null);
    }

    /**
     * Add tree item to XML builder.
     *
     * @param name
     *            name
     * @param value
     *            value
     * @param attributes
     *            attributes
     * @return AjaxTreeXmlBuilder XML builder
     */
    public AjaxTreeXmlBuilder addItemAsCData(final String name, final String value,
            final Map<String, String> attributes) {
        return addItem(name, value, true, attributes);
    }

    /**
     * Add tree item to XML builder.
     *
     * @param name
     *            name
     * @param value
     *            value
     * @param attributes
     *            attributes
     * @return AjaxTreeXmlBuilder XML builder
     */
    public AjaxTreeXmlBuilder addItem(final String name, final String value,
            final Map<String, String> attributes) {
        return addItem(name, value, false, attributes);
    }

    /**
     * Add tree item to XML builder.
     *
     * @param name
     *            name
     * @param value
     *            value
     * @param asCData
     *            true if item must be present as CDATA
     * @param attributes
     *            item attributes
     * @return AjaxTreeXmlBuilder XML builder
     */
    public AjaxTreeXmlBuilder addItem(final String name, final String value, final boolean asCData,
            final Map<String, String> attributes) {
        getList().add(new TreeItem(name, value, asCData, attributes));
        return this;
    }

    /**
     * Add tree item to XML builder.
     *
     * @param name
     *            name
     * @param value
     *            value
     * @param url
     *            URL
     * @param asCData
     *            true if item must be present as CDATA
     * @return AjaxTreeXmlBuilder XML builder
     */
    public AjaxTreeXmlBuilder addItem(final String name, final String value, final String url,
            final boolean asCData) {
        return addItem(name, value, false, url, asCData);
    }

    /**
     * Add tree item to XML builder.
     *
     * @param name
     *            name
     * @param value
     *            value
     * @param collapsed
     *            true if subtree is initially collapsed
     * @param url
     *            URL
     * @return AjaxTreeXmlBuilder XML builder
     */
    public AjaxTreeXmlBuilder addItem(final String name, final String value,
            final boolean collapsed, final String url) {
        return addItem(name, value, collapsed, url, false);
    }

    /**
     * Add tree item to XML builder.
     *
     * @param name
     *            name
     * @param value
     *            value
     * @param collapsed
     *            true if subtree is initially collapsed
     * @param url
     *            URL
     * @param asCData
     *            true if item must be present as CDATA
     * @return AjaxTreeXmlBuilder XML builder
     */
    public AjaxTreeXmlBuilder addItem(final String name, final String value,
            final boolean collapsed, final String url, final boolean asCData) {
        final Map<String, String> data = new HashMap<String, String>();
        data.put(TreeItem.URL, url);
        data.put(TreeItem.COLLAPSED, String.valueOf(collapsed));
        return addItem(name, value, asCData, data);
    }

    /**
     * Add tree item to XML builder.
     *
     * @param name
     *            name
     * @param value
     *            value
     * @param collapsed
     *            true if subtree is initially collapsed
     * @param url
     *            URL
     * @return AjaxTreeXmlBuilder XML builder
     */
    public AjaxTreeXmlBuilder addItemAsCData(final String name, final String value,
            final boolean collapsed, final String url) {
        final Map<String, String> data = new HashMap<String, String>();
        data.put(TreeItem.URL, url);
        data.put(TreeItem.COLLAPSED, String.valueOf(collapsed));
        return addItem(name, value, true, data);
    }

    public interface PropertyReader {

        String getName();

        boolean isCollapsed();

        String getURL();

        boolean isLeaf();

        String getValue();

        boolean isCData();
    }

    public AjaxTreeXmlBuilder addItems(final Collection<PropertyReader> collection) {
        for (PropertyReader element : collection) {
            addItem(element);
        }
        return this;
    }

    public AjaxTreeXmlBuilder addItem(final PropertyReader element) {
        final Map<String, String> data = new HashMap<String, String>();
        data.put(TreeItem.URL, element.getURL());
        data.put(TreeItem.COLLAPSED, String.valueOf(element.isCollapsed()));
        data.put(TreeItem.LEAF, String.valueOf(element.isLeaf()));
        addItem(element.getName(), element.getValue(), element.isCData(), data);
        return this;
    }

    /**
     * Build an XML body to describe TreeItem.
     *
     * @see BaseXmlBuilder#getXMLString()
     */
    @Override
    protected String getXMLString() {
        final StringBuilder xml = new StringBuilder(RESPONSE_START);
        for (TreeItem item : getItems()) {
            xml.append("<item><name>");
            if (item.isAsCData()) {
                xml.append(CDATA_START);
            }
            xml.append(item.getName());
            if (item.isAsCData()) {
                xml.append(CDATA_END);
            }
            xml.append("</name><value>");
            if (item.isAsCData()) {
                xml.append(CDATA_START);
            }
            xml.append(item.getValue());
            if (item.isAsCData()) {
                xml.append(CDATA_END);
            }
            xml.append("</value>");

            for (String attr : item.getAttributeKeySet()) {
                xml.append('<').append(attr).append('>');
                xml.append(item.getAttributeValue(attr));
                xml.append("</").append(attr).append('>');
            }
            xml.append("</item>");
        }
        xml.append(RESPONSE_END);
        return xml.toString();
    }

}

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

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sourceforge.ajaxtags.helpers.TreeItem;



/**
 * Helper class to build valid XML, for the AjaxTreeTag, typically returned in a
 * response to the client.
 * 
 * @author Musachy Barroso
 * @author Jens Kapitza
 * @version $Revision: 86 $ $Date: 2007/07/24 12:21:13 $ $Author: jenskapitza $
 */
public final class AjaxTreeXmlBuilder extends BaseXmlBuilder<TreeItem> {

    /**
     * add tree item to xml builder
     * 
     * @param name
     * @param value
     * @return AjaxTreeXmlBuilder xml builder
     */
    public AjaxTreeXmlBuilder addItem(String name, String value) {
        return addItem(name, value, null);
    }


    /**
     * 
     * add tree item to xml builder
     * 
     * @param name
     * @param value
     * @return AjaxTreeXmlBuilder xml builder
     */
    public AjaxTreeXmlBuilder addItemAsCData(String name, String value) {
        return addItemAsCData(name, value, null);
    }


    /**
     * 
     * add tree item to xml builder
     * 
     * @param name
     * @param value
     * @param attributes
     * @return AjaxTreeXmlBuilder xml builder
     */
    public AjaxTreeXmlBuilder addItemAsCData(String name, String value,
                    Map<String, String> attributes) {
        return addItem(name, value, true, attributes);
    }


    /**
     * 
     * add tree item to xml builder
     * 
     * @param name
     * @param value
     * @param attributes
     * @return AjaxTreeXmlBuilder xml builder
     */
    public AjaxTreeXmlBuilder addItem(String name, String value, Map<String, String> attributes) {
        return addItem(name, value, false, attributes);
    }


    /**
     * 
     * add tree item to xml builder
     * 
     * @param name
     * @param value
     * @param asCData
     * @param attributes
     * @return AjaxTreeXmlBuilder xml builder
     */
    public AjaxTreeXmlBuilder addItem(String name, String value, boolean asCData,
                    Map<String, String> attributes) {
        TreeItem treeitem = new TreeItem(name, value, asCData, attributes);
        getListe().add(treeitem);
        return this;
    }


    /**
     * 
     * add tree item to xml builder
     * 
     * @param name
     * @param value
     * @param url
     * @param asCData
     * @return AjaxTreeXmlBuilder xml builder
     */
    public AjaxTreeXmlBuilder addItem(String name, String value, String url, boolean asCData) {
        return addItem(name, value, false, url, asCData);
    }


    /**
     * add tree item
     * 
     * @param name
     * @param value
     * @param collapsed
     * @param url
     * @return AjaxTreeXmlBuilder xml builder
     */
    public AjaxTreeXmlBuilder addItem(String name, String value, boolean collapsed, String url) {
        return addItem(name, value, collapsed, url, false);
    }


    /**
     * 
     * add tree item
     * 
     * @param name
     * @param value
     * @param collapsed
     * @param url
     * @param asCData
     * @return AjaxTreeXmlBuilder xml builder
     */
    public AjaxTreeXmlBuilder addItem(String name, String value, boolean collapsed, String url,
                    boolean asCData) {
        Map<String, String> data = new HashMap<String, String>();
        data.put(TreeItem.URL, url);
        data.put(TreeItem.COLLAPSED, String.valueOf(collapsed));
        return addItem(name, value, asCData, data);
    }


    /**
     * 
     * add tree item to xml builder
     * 
     * @param name
     * @param value
     * @param collapsed
     * @param url
     * @return AjaxTreeXmlBuilder xml builder
     */
    public AjaxTreeXmlBuilder addItemAsCData(String name, String value, boolean collapsed,
                    String url) {
        Map<String, String> data = new HashMap<String, String>();
        data.put(TreeItem.URL, url);
        data.put(TreeItem.COLLAPSED, String.valueOf(collapsed));
        return addItem(name, value, true, data);
    }


    /**
     * 
     * add tree items to xml builder
     * 
     * @param collection
     * @param nameProperty
     * @param valueProperty
     * @param collapsedProperty
     * @param urlProperty
     * @return AjaxTreeXmlBuilder xml builder
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public AjaxTreeXmlBuilder addItems(Collection<?> collection, String nameProperty,
                    String valueProperty, String collapsedProperty, String urlProperty)
                    throws Exception {
        return addItems(collection, nameProperty, valueProperty, collapsedProperty, urlProperty,
                        false);
    }


    /**
     * 
     * add tree items to xml builder
     * 
     * @param collection
     * @param nameProperty
     * @param valueProperty
     * @param collapsedProperty
     * @param urlProperty
     * @param asCData
     * @return AjaxTreeXmlBuilder xml builder
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public AjaxTreeXmlBuilder addItems(Collection<?> collection, String nameProperty,
                    String valueProperty, String collapsedProperty, String urlProperty,
                    boolean asCData) throws Exception, NoSuchMethodException {
        return addItems(collection, nameProperty, valueProperty, collapsedProperty, urlProperty,
                        null, asCData);
    }


    public interface PropertyReader {

        String getName();


        boolean isCollapsed();


        String getURL();


        boolean isLeaf();


        String getValue();


        boolean isCData();
    }


    public AjaxTreeXmlBuilder addItems(Collection<PropertyReader> collection) {
        for (PropertyReader element : collection) {
            addItem(element);
        }
        return this;
    }


    public AjaxTreeXmlBuilder addItem(PropertyReader element) {
        String name = element.getName();
        String value = element.getValue();
        Map<String, String> data = new HashMap<String, String>();
        data.put(TreeItem.URL, element.getURL());
        data.put(TreeItem.COLLAPSED, String.valueOf(element.isCollapsed()));
        data.put(TreeItem.LEAF, String.valueOf(element.isLeaf()));
        addItem(name, value, element.isCData(), data);
        return this;
    }


    /**
     * 
     * add tree items to xml builder
     * 
     * @param collection
     * @param nameProperty
     * @param valueProperty
     * @param collapsedProperty
     * @param urlProperty
     * @param leafProperty
     * @param asCData
     * @return AjaxTreeXmlBuilder xml builder
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public AjaxTreeXmlBuilder addItems(Collection<?> collection, String nameProperty,
                    String valueProperty, String collapsedProperty, String urlProperty,
                    String leafProperty, boolean asCData) throws Exception {

        for (Object element : collection) {
            String name = getProperty(element, nameProperty);
            String value = getProperty(element, valueProperty);
            Map<String, String> data = new HashMap<String, String>();
            if (urlProperty != null && urlProperty.length() != 0) {
                data.put(TreeItem.URL, (String) getProperty(element, urlProperty));
            }
            if (collapsedProperty != null && collapsedProperty.length() != 0) {
                data.put(TreeItem.COLLAPSED, (String) getProperty(element, collapsedProperty));
            }
            if (leafProperty != null && leafProperty.length() != 0) {
                data.put(TreeItem.LEAF, (String) getProperty(element, leafProperty));
            }
            addItem(name, value, asCData, data);
        }
        return this;
    }


    /**
     * 
     * add tree items to xml builder
     * 
     * @param collection
     * @param nameProperty
     * @param valueProperty
     * @param collapsedProperty
     * @param urlProperty
     * @return AjaxTreeXmlBuilder xml builder
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public AjaxTreeXmlBuilder addItemsAsCData(Collection<?> collection, String nameProperty,
                    String valueProperty, String collapsedProperty, String urlProperty)
                    throws Exception {
        return addItemsAsCData(collection, nameProperty, valueProperty, collapsedProperty,
                        urlProperty, null);
    }


    /**
     * 
     * add tree items to xml builder
     * 
     * @param collection
     * @param nameProperty
     * @param valueProperty
     * @param collapsedProperty
     * @param urlProperty
     * @param leafProperty
     * @return AjaxTreeXmlBuilder xml builder
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public AjaxTreeXmlBuilder addItemsAsCData(Collection<?> collection, String nameProperty,
                    String valueProperty, String collapsedProperty, String urlProperty,
                    String leafProperty) throws Exception {
        return addItems(collection, nameProperty, valueProperty, collapsedProperty, urlProperty,
                        leafProperty, true);
    }


    /**
     * build an xml body to describe TreeItem
     * 
     * @see BaseXmlBuilder#getXMLString()
     * 
     */
    @Override
    protected String getXMLString() {
        StringBuffer xml = new StringBuffer();

        xml.append("<ajax-response>");
        xml.append("<response>");
        for (TreeItem item : getItems()) {
            xml.append("<item>");
            xml.append("<name>");
            if (item.isAsCData()) {
                xml.append("<![CDATA[");
            }
            xml.append(item.getName());
            if (item.isAsCData()) {
                xml.append("]]>");
            }
            xml.append("</name>");
            xml.append("<value>");
            if (item.isAsCData()) {
                xml.append("<![CDATA[");
            }
            xml.append(item.getValue());
            if (item.isAsCData()) {
                xml.append("]]>");
            }
            xml.append("</value>");

            for (String attr : item.getAttributeKeySet()) {

                xml.append("<").append(attr).append(">");
                xml.append(item.getAttributeValue(attr));
                xml.append("</").append(attr).append(">");
            }

            xml.append("</item>");
        }
        xml.append("</response>");
        xml.append("</ajax-response>");

        return xml.toString();

    }
}

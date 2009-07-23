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

import net.sourceforge.ajaxtags.helpers.ValueItem;

/**
 * Helper class to build valid XML for ajax with more than one value.
 * 
 * @author Jens Kapitza
 * @version $Revision: 86 $ $Date: 2007/07/22 16:29:16 $ $Author: jenskapitza $
 */
public abstract class AjaxValueListXmlBuilder extends BaseXmlBuilder<ValueItem> {

    /**
     * Add an Item.
     * 
     * @param name
     *            the name
     * @param asCdata
     *            true if so else false
     * @param value
     *            a list of values
     * @return the xmlbuilder
     * 
     */
    public AjaxValueListXmlBuilder addItem(String name, boolean asCdata, String... value) {
        getListe().add(new ValueItem(name, asCdata, value));
        return this;
    }

    /**
     * Add an Item with asCdata = false.
     * 
     * @param name
     *            the name
     * @param value
     *            a list of values
     * @return the xmlbuilder
     */
    public AjaxValueListXmlBuilder addItem(String name, String... value) {
        return addItem(name, false, value);
    }

    /**
     * Build the node.
     * 
     * @param item
     *            the item
     * @return xml string for this item
     */
    private static String valueToString(ValueItem item) {
        StringBuffer xml = new StringBuffer();
        xml.append("<name>");
        if (item.isAsCData()) {
            xml.append("<![CDATA[");
        }
        xml.append(item.getName());
        if (item.isAsCData()) {
            xml.append("]]>");
        }
        xml.append("</name>");
        for (String value : item.getValue()) {
            xml.append("<value>");
            if (item.isAsCData()) {
                xml.append("<![CDATA[");
            }
            xml.append(value);
            if (item.isAsCData()) {
                xml.append("]]>");
            }
            xml.append("</value>");
        }

        return xml.toString();
    }

    /**
     * Build the xml string.
     * 
     * @see BaseXmlBuilder#getXMLString()
     */
    @Override
    protected String getXMLString() {
        StringBuffer xml = new StringBuffer();
        xml.append("<ajax-response><response>");
        for (ValueItem item : getItems()) {
            xml.append("<item>");
            xml.append(valueToString(item));
            xml.append("</item>");
        }
        xml.append("</response></ajax-response>");
        return xml.toString();
    }

}

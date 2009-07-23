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

/**
 * Helper class to build valid XML typically returned in a response to the client.
 *
 * @author Darren Spurgeon
 * @author Jens Kapitza
 * @version $Revision: 86 $ $Date: 2007/07/22 16:29:16 $ $Author: jenskapitza $
 */
public final class AjaxXmlBuilder extends AjaxValueListXmlBuilder {

    /**
     * Add item to XML.
     *
     * @param key
     *            The name of the item
     * @param value
     *            The value of the item
     * @return the xmlbuilder
     */
    public AjaxXmlBuilder addItem(String key, String value) {
        return addItem(key, value, false);
    }

    /**
     * Add item wrapped with inside a CDATA element.
     *
     * @param key
     *            The name of the item
     * @param value
     *            The value of the item
     * @return the xmlbuilder
     */
    public AjaxXmlBuilder addItemAsCData(String key, String value) {
        return addItem(key, value, true);
    }

    /**
     * Add item to XML.
     *
     * @param name
     *            The name of the item
     * @param value
     *            The value of the item
     * @param asCData
     *            add as CData
     * @return the xmlbuilder
     */
    public AjaxXmlBuilder addItem(String name, String value, boolean asCData) {
        super.addItem(name, asCData, value);
        return this;
    }

    public interface PropertyReader {

        String getName();

        String getValue();

        boolean isCData();
    }

    public AjaxXmlBuilder addItems(Collection<? extends PropertyReader> collection) {
        for (PropertyReader element : collection) {
            addItem(element);
        }
        return this;
    }

    public AjaxXmlBuilder addItem(PropertyReader element) {
        String name = element.getName();
        String value = element.getValue();
        addItem(name, value, element.isCData());
        return this;
    }

}

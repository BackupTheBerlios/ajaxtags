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
     * @param name
     *            The name of the item
     * @param value
     *            The value of the item
     * @return the XML builder
     */
    public AjaxXmlBuilder addItem(final String name, final String value) {
        return addItem(name, value, false);
    }

    /**
     * Add item wrapped with inside a CDATA element.
     *
     * @param name
     *            The name of the item
     * @param value
     *            The value of the item
     * @return the XML builder
     */
    public AjaxXmlBuilder addItemAsCData(final String name, final String value) {
        return addItem(name, value, true);
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
     * @return the XML builder
     */
    public AjaxXmlBuilder addItem(final String name, final String value, final boolean asCData) {
        super.addItem(name, asCData, value);
        return this;
    }

    /**
     * Add collection of items to XML.
     *
     * @param collection
     *            collection of items
     * @return the XML builder
     */
    public AjaxXmlBuilder addItems(final Collection<? extends PropertyReader> collection) {
        for (PropertyReader element : collection) {
            addItem(element);
        }
        return this;
    }

    /**
     * Add item to XML.
     *
     * @param element
     *            item
     * @return the XML builder
     */
    public AjaxXmlBuilder addItem(final PropertyReader element) {
        return addItem(element.getName(), element.getValue(), element.isCData());
    }

    /**
     * Add collection of items to XML.
     *
     * @param <T>
     *            class of items
     * @param collection
     *            collection of items
     * @param provider
     *            provider to access properties of items
     * @return the XML builder
     */
    public <T> AjaxXmlBuilder addItems(final Collection<? extends T> collection,
            final PropertyProvider<T> provider) {
        for (T item : collection) {
            addItem(provider.getName(item), provider.getValue(item), provider.isCData(item));
        }
        return this;
    }

    /**
     * Interface for objects with name/value/cData properties.
     */
    public interface PropertyReader {

        /**
         * @return the name of the item
         */
        String getName();

        /**
         * @return the value of the item
         */
        String getValue();

        /**
         * @return true if item should be added as CData
         */
        boolean isCData();
    }

    /**
     * Interface for property provider. Provides access to name/value/CData properties for object of
     * any class.
     *
     * @param <T>
     *            class of items
     */
    public interface PropertyProvider<T> {

        /**
         * @param item
         *            item
         * @return the name of the item
         */
        String getName(T item);

        /**
         * @param item
         *            item
         * @return the value of the item
         */
        String getValue(T item);

        /**
         * @param item
         *            item
         * @return true if item should be added as CData
         */
        boolean isCData(T item);
    }

    /**
     * Template for text property provider (all items should be added as text, not as CData).
     *
     * @param <T>
     *            class of items
     */
    public abstract static class AbstractPropertyProvider<T> implements PropertyProvider<T> {

        /**
         * @param item
         *            item
         * @return always false
         */
        public boolean isCData(final T item) { // NOPMD
            return false;
        };
    }

    /**
     * Template for CData property provider (all items should be added as CData).
     *
     * @param <T>
     *            class of items
     */
    public abstract static class AbstractCDataPropertyProvider<T> implements PropertyProvider<T> {

        /**
         * @param item
         *            item
         * @return always true
         */
        public boolean isCData(final T item) { // NOPMD
            return true;
        };
    }
}

/*
 * Copyright 2009 AjaxTags-Team
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
package net.sourceforge.ajaxtags.helpers;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * A generic item class, basically representing a name-value pair.
 * 
 * @author Darren L. Spurgeon
 * @author Jens Kapitza
 * @param <T>
 *            the item type
 * @version $Revision: 86 $ $Date: 2007/07/22 18:04:50 $ $Author: jenskapitza $
 */
public abstract class AbstractItem<T> {

    /**
     * The name.
     */
    private String name;

    /**
     * The value.
     */
    private T value;

    /**
     * CDATA ?
     */
    private boolean asCData;

    /**
     * All other attributes. Using a TreeMap cause of Junit tests.
     */
    private Map<String, String> attributes = new TreeMap<String, String>();

    /**
     * Constructor for Item.
     * 
     * @param name
     *            the name for the item
     * @param value
     *            the value
     * @param asCData
     *            response as CDATA
     */
    protected AbstractItem(final String name, final T value, final boolean asCData) {
        this();
        this.name = name;
        this.value = value;
        this.asCData = asCData;
    }

    /**
     * Constructor for Item.
     */
    protected AbstractItem() {
        super();
    }

    /**
     * Set all attributes.
     * 
     * @param attributes
     *            the attributes to set
     */
    public final void setAllAttributes(final Map<String, String> attributes) {
        if (attributes != null) {
            for (Entry<String, String> e : attributes.entrySet()) {
                setAttributes(e.getKey(), e.getValue());
            }
        }
    }

    /**
     * 
     * @return the key set of the attributes
     */
    public final Set<String> getAttributeKeySet() {
        return this.attributes.keySet();
    }

    /**
     * Removes an attribute.
     * 
     * @param name
     *            the name of attribute
     */
    public final void removeAttribute(final String name) {
        this.attributes.remove(name);
    }

    /**
     * Clear the attributes.
     */
    public final void clearAttribute() {
        this.attributes.clear();
    }

    /**
     * Set an attribute to extend the item.
     * 
     * @param name
     *            the name for the attribute
     * @param value
     *            the value for the attribute
     */
    public final void setAttributes(final String name, final String value) {
        setAttributes(name, value, false);
    }

    /**
     * Set an attribute to extend the item.
     * 
     * @param name
     *            the name for the attribute
     * @param value
     *            the value for the attribute
     * @param evenIfNull
     *            set attribute even if it is null
     */
    public final void setAttributes(final String name, final String value, final boolean evenIfNull) {
        if (value != null || evenIfNull) {
            // FindBug say we should use
            // String.toLowerCase( Locale l )
            this.attributes.put(name.toLowerCase(Locale.getDefault()), value);
        }
    }

    /**
     * Read the attribute value.
     * 
     * @param name
     *            the attribute name
     * @return the value of attribute <code>name</code>
     */
    public final String getAttributeValue(final String name) {
        return this.attributes.get(name);
    }

    /**
     * @return Returns the name.
     */
    public final String getName() {
        return this.name;
    }

    /**
     * @param name
     *            The name to set.
     */
    public final void setName(final String name) {
        this.name = name;
    }

    /**
     * @return Returns the value.
     */
    public final T getValue() {
        return this.value;
    }

    /**
     * @param value
     *            The value to set.
     */
    public final void setValue(final T value) {
        this.value = value;
    }

    /**
     * @return Returns the asCData.
     */
    public final boolean isAsCData() {
        return this.asCData;
    }

    /**
     * @param asCData
     *            The asCData to set.
     */
    public final void setAsCData(final boolean asCData) {
        this.asCData = asCData;
    }
}

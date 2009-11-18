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

import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * A generic item class, basically representing a name-value pair.
 * 
 * @author Darren L. Spurgeon
 * @author Jens Kapitza
 */
public abstract class AbstractItem {

    /**
     * The name.
     */
    private String name;

    /**
     * The value.
     */
    private Object value;

    /**
     * CDATA is used to flag the value and force the writer to nest the value in an CDATA TAG.
     */
    private boolean asCData;

    /**
     * All other attributes. Using a TreeMap cause of JUnit tests.
     */
    private final SortedMap<Object, String> attributes = new TreeMap<Object, String>();

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
    protected AbstractItem(final String name, final Object value, final boolean asCData) {
        this();
        setName(name);
        setValue(value);
        setAsCData(asCData);
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
     * @param evenIfNull
     *            set the attribute even if value is null
     */
    public final void setAllAttributes(final Map<?, String> attributes, final boolean evenIfNull) {
        if (attributes != null) {
            for (Entry<?, String> e : attributes.entrySet()) {
                setAttributes(e.getKey(), e.getValue(), evenIfNull);
            }
        }
    }

    /**
     * Set all attributes.
     * 
     * @param attributes
     *            the attributes to set
     */
    public final void setAllAttributes(final Map<?, String> attributes) {
        setAllAttributes(attributes, false);
    }

    /**
     * List all attribute-names.
     * 
     * @return the key set of the attributes
     */
    public Set<Object> getAttributeKeySet() {
        return attributes.keySet();
    }

    /**
     * Removes an attribute.
     * 
     * @param name
     *            the name of attribute
     */
    public final void removeAttribute(final String name) {
        attributes.remove(name);
    }

    /**
     * Clear the attributes.
     */
    public final void clearAttribute() {
        attributes.clear();
    }

    /**
     * Set an attribute to extend the item. This just invoke
     * {@link #setAttributes(String, String, boolean)} with false as third parameter.
     * 
     * @param name
     *            the name for the attribute
     * @param value
     *            the value for the attribute
     * @see #setAttributes(String, String, boolean)
     */
    public final void setAttributes(final Object name, final String value) {
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
    public final void setAttributes(final Object name, final String value, final boolean evenIfNull) {
        if (value != null || evenIfNull) {
            // don't change the name here, we should do this
            // in the Map implementation or never!
            attributes.put(name, value);
        }
    }

    /**
     * Read the attribute value.
     * 
     * @param name
     *            the attribute name
     * @return the value of attribute <code>name</code>
     */
    public String getAttributeValue(final Object name) {
        return attributes.get(name);
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the Name.
     * 
     * @param name
     *            The name to set.
     */
    public final void setName(final String name) {
        this.name = name;
    }

    /**
     * The type depends on the child-class.
     * 
     * @return Returns the value.
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * @param value
     *            The value to set.
     */
    public final void setValue(final Object value) {
        this.value = value;
    }

    /**
     * @return Returns the asCData.
     */
    public boolean isAsCData() {
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

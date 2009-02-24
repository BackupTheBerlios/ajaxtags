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
import java.util.Set;



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
     * the name
     */
    private String name;
    /**
     * the value
     */
    private T value;
    /**
     * CDATA ?
     */
    private boolean asData;
    /**
     * all other attributes
     */
    private Map<String, String> attributes = new HashMap<String, String>();


    /**
     * Constructor for Item.
     * 
     * @param name
     *            the name for the item
     * @param value
     *            the value
     * @param asData
     *            response as CDATA
     */
    protected AbstractItem(final String name, final T value, final boolean asData) {
        this();
        this.name = name;
        this.value = value;
        this.asData = asData;
    }


    /**
     * Constructor for Item.
     */
    protected AbstractItem() {
        super();
    }


    /**
     * set all attributes
     * 
     * @param attributes
     *            the attributes to set
     */
    public final void setAllAttributes(final Map<String, String> attributes) {
        if (attributes != null) {
            for (String key : attributes.keySet()) {
                setAttributes(key, attributes.get(key));
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
     * removes an attribute
     * 
     * @param name
     *            the name of attribute
     */
    public final void removeAttribute(final String name) {
        this.attributes.remove(name);
    }


    /**
     * clear the attributes
     */
    public final void clearAttribute() {
        this.attributes.clear();
    }


    /**
     * set an attribute to extend the item
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
     * set an attribute to extend the item
     * 
     * @param name
     *            the name for the attribute
     * @param value
     *            the value for the attribute
     * @param evenIfNull
     *            set attribute even if it is null
     */
    public final void setAttributes(final String name, final String value, boolean evenIfNull) {
        if (value != null || evenIfNull) {
            this.attributes.put(name.toLowerCase(), value);
        }
    }


    /**
     * read the attribute value
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
        return this.asData;
    }


    /**
     * @param asData
     *            The asData to set.
     */
    public final void setAsData(final boolean asData) {
        this.asData = asData;
    }
}

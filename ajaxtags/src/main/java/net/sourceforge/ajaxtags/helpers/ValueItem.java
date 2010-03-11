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
package net.sourceforge.ajaxtags.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Holding the data for ValueListXmlBuilder.
 * 
 * @author Jens Kapitza
 */
public class ValueItem extends AbstractItem {

    private static final long serialVersionUID = -5641651833544439174L;

    /**
     * Build a SimpleNode.
     * 
     * @param tagName
     *            name
     * @param asCData
     *            true if is cdate or false otherwise
     */
    public ValueItem(final String tagName, final boolean asCData) {
        this(tagName, null, asCData);
    }

    /**
     * Build a SimpleNode.
     * 
     * @param tagName
     *            name
     * @param value
     *            add one value
     * @param asCData
     *            true if is CDATA or false otherwise
     */
    public ValueItem(final String tagName, final String value, final boolean asCData) {
        this(tagName, asCData, value);
    }

    /**
     * Build a SimpleNode.
     * 
     * @param tagName
     *            name
     * @param asCData
     *            true if is CDATA or false otherwise
     * @param value
     *            a list of values
     */
    public ValueItem(final String tagName, final boolean asCData, String... value) {
        super(tagName, new ArrayList<String>(), asCData);
        getValue().addAll(Arrays.asList(value));
    }

    /**
     * Try to find the index of value.
     * 
     * @param value
     *            the value to find
     * @return the index of this value
     * @see ArrayList#indexOf(Object)
     */
    public int indexOfValue(final String value) {
        return getValue().indexOf(value);
    }

    /**
     * Add values to this valueitem.
     * 
     * @param values
     *            the value
     */
    public void addValue(String... values) {
        getValue().addAll(Arrays.asList(values));
    }

    /**
     * Add values to this valueitem.
     * 
     * @param values
     *            the value
     */
    public void addValue(final List<String> values) {
        getValue().addAll(values);
    }

    /**
     * Add a value to this valueitem.
     * 
     * @param value
     *            the value
     */
    public void addValue(final String value) {
        getValue().add(String.valueOf(value));
    }

    /**
     * Removes an item at specified index.
     * 
     * @param index
     *            the index to remove
     * @return the removed item
     */
    public String remove(final int index) {
        return getValue().remove(index);
    }

    /**
     * The list of values.
     * 
     * @return the list of values
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<String> getValue() {
        return (List<String>) super.getValue();
    }
}

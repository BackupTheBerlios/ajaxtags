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

/**
 * Extend Item to easily have a tree item with more options.
 * 
 * @author Musachy Barroso
 * @author Jens Kapitza
 */
public class TreeItem extends AbstractItem {

    /**
     * All known tree-elements for the parser. <br/>
     * This is just a subset of what we can do.
     * 
     * @author Jens Kapitza
     */
    public static enum TreeAttribute {

        /**
         * Key to set node or a leaf flag.
         */
        LEAF,
        /**
         * Key to set collapsed flag.
         */
        COLLAPSED,
        /**
         * Key to set URL flag.
         */
        URL;
    }

    /**
     * Constructor for TreeItem.
     */
    public TreeItem() {
        this(null, null, false, null);
    }

    /**
     * Create an item.
     * 
     * @param name
     *            name
     * @param value
     *            value
     * @param asData
     *            true if value must be set as CDATA
     */
    public TreeItem(final String name, final String value, final boolean asData) {
        this(name, value, false, null, asData);
    }

    /**
     * Create an item.
     * 
     * @param name
     *            name
     * @param value
     *            value
     */
    public TreeItem(final String name, final String value) {
        this(name, value, false, null, false);
    }

    /**
     * Create an item.
     * 
     * @param name
     *            name
     * @param value
     *            value
     * @param collapsed
     *            true if item is collapsed
     * @param url
     *            URL
     * @param asData
     *            true if value must be set as CDATA
     */
    public TreeItem(final String name, final String value, final boolean collapsed,
            final String url, final boolean asData) {
        this(name, value, asData, null);
        setCollapsed(collapsed);
        setUrl(url);
    }

    /**
     * Create an item.
     * 
     * @param name
     *            name
     * @param value
     *            value
     * @param asData
     *            true if value must be set as CDATA
     * @param attributes
     *            additional attributes
     */
    public TreeItem(final String name, final String value, final boolean asData,
            final Map<?, String> attributes) {
        super(name, value, asData);
        setAllAttributes(attributes);
    }

    /**
     * Check if this item is a leaf or not.
     * 
     * @return true if this is a leaf else false
     */
    public final boolean isLeaf() {
        return Boolean.parseBoolean(getAttributeValue(TreeAttribute.LEAF));
    }

    /**
     * Set node to leaf or not.
     * 
     * @param leaf
     *            true if it is leaf else false
     */
    public final void setLeaf(final boolean leaf) {
        setAttributes(TreeAttribute.LEAF, String.valueOf(leaf));
    }

    /**
     * @return Returns the collapsed value
     */
    public final boolean isCollapsed() {
        return Boolean.parseBoolean(getAttributeValue(TreeAttribute.COLLAPSED));
    }

    /**
     * @param collapsed
     *            The collapsed value to be set
     */
    public final void setCollapsed(final boolean collapsed) {
        setAttributes(TreeAttribute.COLLAPSED, String.valueOf(collapsed));
    }

    /**
     * @return Return the URL
     */
    public final String getUrl() {
        return getAttributeValue(TreeAttribute.URL);
    }

    /**
     * @param url
     *            The URL to be set
     */
    public final void setUrl(final String url) {
        setAttributes(TreeAttribute.URL, url);
    }
}

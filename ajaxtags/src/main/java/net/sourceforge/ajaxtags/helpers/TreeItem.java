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

import java.util.Map;

/**
 * Extend Item to easily have a tree item with more options.
 *
 * @author Musachy Barroso
 * @author Jens Kapitza
 * @version $Revision: 86 $ $Date: 2007/07/22 18:04:50 $ $Author: jenskapitza $
 */
public class TreeItem extends AbstractItem<String> {

    /**
     * Key to set node or a leaf flag.
     */
    public static final String LEAF = "leaf";

    /**
     * Key to set collapsed flag.
     */
    public static final String COLLAPSED = "collapsed";

    /**
     * Key to set url flag.
     */
    public static final String URL = "url";

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
     * @param value
     * @param asData
     */
    public TreeItem(final String name, final String value, final boolean asData) {
        this(name, value, false, null, asData);
    }

    /**
     *
     * @param name
     * @param value
     */
    public TreeItem(final String name, final String value) {
        this(name, value, false, null, false);
    }

    /**
     * @param name
     * @param value
     * @param collapsed
     * @param url
     * @param asData
     */
    public TreeItem(final String name, final String value, final boolean collapsed,
            final String url, final boolean asData) {
        this(name, value, asData, null);
        setCollapsed(collapsed);
        setUrl(url);
    }

    /**
     * @param name
     * @param value
     * @param asData
     * @param attributes
     */
    public TreeItem(String name, String value, boolean asData, Map<String, String> attributes) {
        super(name, value, asData);
        setAllAttributes(attributes);
    }

    /**
     * Check if this treeitem is a leaf or not.
     *
     * @return true if this is a leaf else false
     */
    public final boolean isLeaf() {
        return Boolean.parseBoolean(getAttributeValue(LEAF));
    }

    /**
     * Set node to leaf or not.
     *
     * @param l
     *            true if it is leaf else false
     */
    public void setLeaf(boolean l) {
        setAttributes(LEAF, String.valueOf(l));
    }

    /**
     * @return Returns the collapsed value
     */
    public boolean isCollapsed() {
        return Boolean.parseBoolean(getAttributeValue(COLLAPSED));
    }

    /**
     * @param collapsed
     *            The collapsed value to be set
     */
    public void setCollapsed(boolean collapsed) {
        setAttributes(COLLAPSED, String.valueOf(collapsed));
    }

    /**
     * @return Return the URL
     */
    public String getUrl() {
        return getAttributeValue(URL);
    }

    /**
     * @param url
     *            The url to be set
     */
    public void setUrl(String url) {
        setAttributes(URL, url);
    }
}

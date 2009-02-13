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
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;



/**
 * Helper class to build valid XML as a base for all xmlbuilder
 * 
 * @author Jens Kapitza
 * @version $Revision: 86 $ $Date: 2007/07/22 16:58:28 $ $Author: jenskapitza $
 * @param <V>
 *            Listtype (Item, TreeItem)
 */
public abstract class BaseXmlBuilder<V> {

    private List<V> liste = new ArrayList<V>();


    protected void setListe(List<V> liste) {
        this.liste = liste;
    }


    /**
     * default encoding is utf-8
     */
    private String encoding = "UTF-8";


    /**
     * @return the xml encoding
     */
    public String getEncoding() {
        return this.encoding;
    }


    /**
     * set the xml encoding
     * 
     * @param encoding
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }


    protected List<V> getListe() {
        return this.liste;
    }


    /**
     * 
     * @return the item list
     */
    protected List<V> getItems() {
        return getListe();
    }


    /**
     * 
     * @return the xml body, xml encoding is added by {@link #toString()}
     */
    protected abstract String getXMLString();


    /**
     * return the full XML ducument
     */
    @Override
    public String toString() {
        StringBuffer xml = new StringBuffer().append("<?xml version=\"1.0\"");
        if (getEncoding() != null) {
            xml.append(" encoding=\"");
            xml.append(getEncoding());
            xml.append("\"");
        }
        xml.append(" ?>");
        xml.append(getXMLString());

        return xml.toString();
    }


    /**
     * add item to list
     * 
     * @param o
     *            the item to add
     * @return BaseXmlBuilder
     * @see ArrayList#add(Object)
     */
    public BaseXmlBuilder<V> add(V o) {
        this.liste.add(o);
        return this;
    }


    /**
     * delete all items
     */
    public void clear() {
        this.liste.clear();
    }


    /**
     * return the item at index
     * 
     * @param index
     *            the index
     * @return the item at index
     */
    public V get(int index) {
        return this.liste.get(index);
    }


    /**
     * check if itemlist is empty
     * 
     * @return true if it is empty else false
     */
    public boolean isEmpty() {
        return this.liste.isEmpty();
    }


    /**
     * 
     * @return the item count
     */
    public int size() {
        return this.liste.size();
    }


    /**
     * use getNAME from object!
     * 
     * @param obj
     * @param name
     * @return
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    @SuppressWarnings("unchecked")
    protected static <T> T getProperty(Object obj, String name) throws Exception {
        String xname = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        Method m = obj.getClass().getMethod("get" + xname, (Class<?>[]) null);
        m.setAccessible(true);
        return (T) m.invoke(obj, (Object[]) null);
    }
}

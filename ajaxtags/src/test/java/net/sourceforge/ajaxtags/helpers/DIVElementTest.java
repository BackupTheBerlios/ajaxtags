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
/**
 *
 */
package net.sourceforge.ajaxtags.helpers;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author В.Хомяков
 * @version $Revision$ $Date$ $Author$
 */
public class DIVElementTest {

    private DIVElement div;

    /**
     * Set up.
     */
    @Before
    public void setUp() {
        div = new DIVElement(null);
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
        div = null;
    }

    /**
     * Test method for
     * {@link net.sourceforge.ajaxtags.helpers.DIVElement#DIVElement(java.lang.String)}.
     */
    @Test
    public void testDIVElement() {
        assertEquals("Empty div", "<div></div>", div.toString());

        div = new DIVElement("idDiv1");
        assertEquals("Empty div with id", "<div id=\"idDiv1\"></div>", div.toString());
    }

    /**
     * Test method for
     * {@link net.sourceforge.ajaxtags.helpers.HTMLElementFactory#setClassName(java.lang.String)}.
     */
    @Test
    public void testSetClassName() {
        div.setClassName("class1");
        assertEquals("Empty div with class", "<div class=\"class1\"></div>", div.toString());
    }

    /**
     * Test method for
     * {@link net.sourceforge.ajaxtags.helpers.HTMLElementFactory#setId(java.lang.String)}.
     */
    @Test
    public void testSetId() {
        div.setId("idDiv2");
        assertEquals("Empty div with id", "<div id=\"idDiv2\"></div>", div.toString());
    }

}

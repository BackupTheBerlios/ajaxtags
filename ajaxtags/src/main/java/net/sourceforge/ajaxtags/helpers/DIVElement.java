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

/**
 * Ein Div Element in html.
 * 
 * @author Jens Kapitza
 * @version $Revision$ $Date$ $Author$
 */
public final class DIVElement extends HTMLElementFactory {

    /**
     * Create a div element.
     * 
     * @param id
     *            the id for the div element
     */
    public DIVElement(final String id) {
        super("div", id, null);
    }
}

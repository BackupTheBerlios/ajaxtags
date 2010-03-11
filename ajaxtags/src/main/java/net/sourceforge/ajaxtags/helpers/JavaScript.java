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

/**
 * HTML JavaScript element. This will help building javascript.
 * 
 * @author Jens Kapitza
 */
public final class JavaScript extends AbstractHTMLElement {

    /**
     * Create a script element.
     */
    public JavaScript() {
        super("script");
    }

    /**
     * Constructor.
     * 
     * @param script
     *            initial script content
     */
    public JavaScript(final String script) {
        this();
        append(script);
    }

    /**
     * Just allow type attribute. All others are dropped.
     */
    @Override
    protected void cleanAttributes() {
        getAttributes().clear();
        getAttributes().put("type", "text/javascript");
    }
}

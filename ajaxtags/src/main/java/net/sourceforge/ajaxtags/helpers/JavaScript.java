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

import net.sourceforge.ajaxtags.tags.BaseAjaxBodyTag;
import net.sourceforge.ajaxtags.tags.OptionsBuilder;

/**
 * 
 * @author Jens Kapitza
 * @version $Revision$ $Date$ $Author$
 */
public final class JavaScript extends HTMLElementFactory {

    private BaseAjaxBodyTag tag;

    /**
     * Create a script element.
     */
    public JavaScript() {
        super("script");
    }

    public JavaScript(BaseAjaxBodyTag tag) {
        this();
        this.tag = tag;
    }

    public HTMLElementFactory newTabPanel(OptionsBuilder options) {
        return append(tag.getJSVariable()).append("new AjaxJspTag.TabPanel({" + options + "});");
    }

    public HTMLElementFactory newSelect(OptionsBuilder options) {
        return append(tag.getJSVariable()).append("new AjaxJspTag.Select({" + options + "});");
    }
    

    public HTMLElementFactory newToggle(OptionsBuilder options) {
        return append(tag.getJSVariable()).append("new AjaxJspTag.Toggle({" + options + "});");
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

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
package net.sourceforge.ajaxtags.tags;

import javax.servlet.jsp.JspException;

/**
 * This class does not support body content.
 * 
 * @author Jens Kapitza
 * @version $Revision$ $Date$ $Author$
 */
public abstract class BaseAjaxTag extends BaseAjaxBodyTag {

    private static final long serialVersionUID = -6954908083925539558L;

    @Override
    protected void initParameters() throws JspException {
        super.initParameters();
        skipBody();
    }
}

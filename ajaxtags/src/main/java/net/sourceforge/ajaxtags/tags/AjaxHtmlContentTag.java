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

import net.sourceforge.ajaxtags.helpers.JavaScript;

/**
 * Tag handler for the HTML Content AJAX tag.
 *
 * @author Darren Spurgeon
 * @version $Revision: 86 $ $Date: 2007/07/08 18:18:52 $ $Author: jenskapitza $
 */
public class AjaxHtmlContentTag extends BaseAjaxTag {

    private static final long serialVersionUID = -2633087429107457075L;

    @Override
    public int doEndTag() throws JspException {
        OptionsBuilder options = getOptionsBuilder();
        JavaScript script = new JavaScript();
        script.append(getJSVariable());
        script.append("new AjaxJspTag.HtmlContent(").append("{\n").append(options.toString())
                .append("});\n");

        out(script);
        return EVAL_PAGE;
    }
}

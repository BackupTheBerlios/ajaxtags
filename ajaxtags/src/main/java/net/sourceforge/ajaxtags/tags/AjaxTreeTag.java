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
package net.sourceforge.ajaxtags.tags;

import javax.servlet.jsp.JspException;

import net.sourceforge.ajaxtags.helpers.DIVElement;

/**
 * Tag handler for the tree AJAX tag.
 *
 * @author Musachy Barroso
 * @author Jens Kapitza
 * @version $Revision: 86 $ $Date: 2007/06/20 20:55:56 $ $Author: jenskapitza $
 */
public class AjaxTreeTag extends BaseAjaxTag {

    private static final long serialVersionUID = -1607090849231461287L;

    private String collapsedClass;

    private String expandedClass;

    private String nodeClass;

    private String treeClass;

    public String getTreeClass() {
        return treeClass;
    }

    public void setTreeClass(final String treeClass) {
        this.treeClass = treeClass;
    }

    public String getNodeClass() {
        return nodeClass;
    }

    public void setNodeClass(final String nodeClass) {
        this.nodeClass = nodeClass;
    }

    public String getCollapsedClass() {
        return collapsedClass;
    }

    public void setCollapsedClass(final String collapsedClass) {
        this.collapsedClass = collapsedClass;
    }

    public String getExpandedClass() {
        return expandedClass;
    }

    public void setExpandedClass(final String expandedClass) {
        this.expandedClass = expandedClass;
    }

    @Override
    protected String getJsClass() {
        return JSCLASS_BASE + "Tree";
    }

    @Override
    protected OptionsBuilder getOptions() {
        final OptionsBuilder options = getOptionsBuilder();
        options.add("target", getId(), true);
        options.add("collapsedClass", this.collapsedClass, true);
        options.add("expandedClass", this.expandedClass, true);
        options.add("treeClass", this.treeClass, true);
        options.add("nodeClass", this.nodeClass, true);
        return options;
    }

    @Override
    public int doEndTag() throws JspException {
        final DIVElement div = new DIVElement(getId());
        div.setBody(buildScript());
        out(div);
        return EVAL_PAGE;
    }

    @Override
    protected void releaseTag() {
        collapsedClass = null; // NOPMD
        expandedClass = null; // NOPMD
        nodeClass = null; // NOPMD
        treeClass = null; // NOPMD
    }
}

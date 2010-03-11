<%--

    Copyright 2009-2010 AjaxTags-Team


    Licensed under the Apache License, Version 2.0 (the "License"); you may not
    use this file except in compliance with the License. You may obtain a copy of
    the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
    License for the specific language governing permissions and limitations under
    the License.

--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://ajaxtags.sourceforge.net/tags/ajaxtags"
	prefix="ajax"%>


<div>You have clicked <span id="clicker"></span></div>

<ajax:tree baseUrl="tree.view" id="cars"
	parameters="node={ajaxParameter}" nodeClass="nodeClass"
	expandedClass="expandedNode" collapsedClass="collapsedNode"
	treeClass="tree" />


<ajax:htmlContent target="clicker" sourceClass="nodeClass"
	baseUrl="tree.view" parameters="node={ajaxParameter},action=info"></ajax:htmlContent>

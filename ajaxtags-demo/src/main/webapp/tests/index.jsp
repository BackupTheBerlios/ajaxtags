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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://ajaxtags.sourceforge.net/tags/ajaxtags"
	prefix="ajax"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"></meta>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/ajaxtags.css"></link>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/displaytag.css"></link>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css0/unittest.css"></link>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css0/site.css"></link>
<title>AJAX JSP Tag Library unit tests *</title>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/ajaxtags/js/prototype.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/ajaxtags/js/scriptaculous/effects.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/ajaxtags/js/scriptaculous/controls.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/ajaxtags/js/scriptaculous/unittest.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/ajaxtags/js/ajaxtags.js"></script>
</head>

<body>
<div id="header">AJAX JSP Tag Library unit tests</div>
<div id="subheader"><a href="${pageContext.request.contextPath}/">Project
Documentation</a> | <a href="http://ajaxtags.sourceforge.net/"
	class="external">Project website</a> | <a
	href="http://sourceforge.net/projects/ajaxtags/" class="external">AjaxTags@SourceForge</a></div>
<div id="buildVersion">Using Project Version 1.5</div>
<div id="content">

<h1>Unit tests</h1>

<div style="width: 750px; font-size: 90%">
<p>Here are unit tests for JavaScript code</p>
</div>

<ajax:tabPanel id="indexOfTests">
	<ajax:tab caption="AjaxJspTag" baseUrl="testAjaxJspTag.html" defaultTab="true" />
	<ajax:tab caption="AjaxJspTag.Base" baseUrl="testBase.html" />
	<ajax:tab caption="AjaxJspTag.UpdateField" baseUrl="testUpdateField.html" />
	<ajax:tab caption="AjaxJspTag.Select" baseUrl="testSelect.html" />
	<ajax:tab caption="AjaxJspTag.Portlet" baseUrl="testPortlet.html" />
	<ajax:tab caption="AjaxJspTag.Callout" baseUrl="testCallout.html" />
	<ajax:tab caption="AjaxJspTag.TabPanel" baseUrl="testTabPanel.html" />
</ajax:tabPanel></div>
</body>
</html>

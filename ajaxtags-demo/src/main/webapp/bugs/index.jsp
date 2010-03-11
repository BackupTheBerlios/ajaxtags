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
<%--
 * Copyright 2008 Jens Kapitza
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
--%>
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
	href="${pageContext.request.contextPath}/css0/site.css"></link>
<title>AJAX JSP Tag Library Bugs</title>

<!-- der ie kann das dynamische laden nicht, es fehlen die informationen & es ist die falsche reihenfolge -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/ajaxtags/js/prototype.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/ajaxtags/js/scriptaculous/scriptaculous.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/ajaxtags/js/overlibmws/overlibmws.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/ajaxtags/js/ajaxtags.js"></script>

<script type="text/javascript">
  /*
   * USER DEFINED FUNCTIONS
   */

  window.initProgress=function(){
    $('progressMsg').show();
  };

  window.resetProgress=function(request){
    Effect.Fade('progressMsg');
  };

  window.reportError=function(){
    $('errorMsg').update("AjaxTag busted!").show();
    //$('errorMsg').innerHTML = "AjaxTag busted!";
    //$('errorMsg').show();
    setTimeout("Effect.DropOut('errorMsg')",2500);
  };
</script>
</head>

<body>

<div id="header">AJAX JSP Tag Library BUGS</div>
<div id="subheader"><a href="${pageContext.request.contextPath}/">Project
Documentation</a> | <a href="http://ajaxtags.sourceforge.net/"
	class="external">Project website</a> | <a
	href="http://sourceforge.net/projects/ajaxtags/" class="external">AjaxTags@SourceForge</a></div>
<div id="buildVersion">Using Project Version 1.5</div>
<div id="content">

<h1>BUGS</h1>

<div style="width: 650px; font-size: 90%">
<p>Here are some solutions for some reported bugs</p>
</div>

<ajax:tabPanel id="panelIndexBugs">
	<ajax:tab caption="1857214 DisplayTag" baseUrl="1857214.jsp"
		defaultTab="true" />
	<ajax:tab caption="1844205 Select default selection"
		baseUrl="1844205.jsp" />
	<ajax:tab caption="016027 buildParameterString" baseUrl="016027.jsp" />
</ajax:tabPanel></div>
</body>
</html>

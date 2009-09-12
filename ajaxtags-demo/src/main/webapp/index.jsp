<%--

    Copyright 2009 AjaxTags-Team


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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%--
 * Copyright 2005 Darren L. Spurgeon
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
<%@page import="java.util.Date"%>
<jsp:useBean id="now" class="java.util.Date" />
	
<html  xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" ></meta>
<link type="text/css" rel="stylesheet" href="css/ajaxtags.css" ></link>
<link type="text/css" rel="stylesheet" href="css/displaytag.css" ></link>
<link rel="stylesheet" type="text/css" href="css0/site.css" ></link>
<title>AJAX JSP Tag Library Examples</title>
 
<!-- der ie kann das dynamische laden nicht, es fehlen die informationen & es ist die falsche reihenfolge --> 
<script type="text/javascript" src="ajaxtags/js/prototype.js"></script>
<script type="text/javascript" src="ajaxtags/js/scriptaculous/scriptaculous.js"></script>
<script type="text/javascript" src="ajaxtags/js/overlibmws/overlibmws.js"></script>
<script type="text/javascript" src="ajaxtags/js/ajaxtags.js"></script>

<script type="text/javascript">
    /*
     * USER DEFINED FUNCTIONS
     */
     
    window.initProgress = function() {
        Element.show('progressMsg');
    };

    window.resetProgress = function(request) {
        Effect.Fade('progressMsg');
    };

    window.reportError = function() {
        $('errorMsg').innerHTML = "AjaxTag busted!";
        Element.show('errorMsg');
        setTimeout("Effect.DropOut('errorMsg')", 2500);
    };
</script>

</head>

<body>

<div id="header">AJAX JSP Tag Library Examples</div>
<div id="subheader"><a href="bugs/" >BUGS solutions</a> | <a href="http://ajaxtags.sourceforge.net/"
    class="external">Project website</a> | <a
    href="http://sourceforge.net/projects/ajaxtags/" class="external">AjaxTags@SourceForge</a></div>
<div id="buildVersion">Using Project Version 1.5</div>
<div id="content">

<h1>Demos</h1>

<ajax:tabPanel id="panelIndex">
	<ajax:tab caption="Autocomplete" baseUrl="autocomplete.jsp" />
	<ajax:tab caption="Callout" baseUrl="callout.jsp"  />
	<ajax:tab caption="Form Update" baseUrl="formupdate.jsp" />
	<ajax:tab caption="HtmlContent" baseUrl="htmlcontent.jsp"/>
	<ajax:tab caption="Portlet" baseUrl="portlet.jsp"  defaultTab="true" />
	<ajax:tab caption="Select/Dropdown" baseUrl="dropdown.jsp"/>
	<ajax:tab caption="Tab Panel" baseUrl="tabpanel.jsp"/>
	<ajax:tab caption="Toggle" baseUrl="toggle.jsp"/>
	<ajax:tab caption="Area and Anchor" baseUrl="pagearea.jsp"/>
	<ajax:tab caption="Ajax DisplayTag" baseUrl="displaytag.jsp"/>
	<ajax:tab caption="Tree" baseUrl="tree.jsp"/>
	<ajax:tab caption="In Place Editor" baseUrl="inplaceeditor.jsp"/>
</ajax:tabPanel>

<p>Page loaded at: <span id="datum"> ${now}</span></p>
</div>
</body>
</html>



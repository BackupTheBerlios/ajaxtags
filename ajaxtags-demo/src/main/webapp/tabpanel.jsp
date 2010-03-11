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

<h1>Tab Panel Tag Demo</h1>

<div style="font-size: 90%; width: 650px;">
<p>The <code>ajax:tabPanel</code> tag provides a tabbed page view of
content from different resources, typically other JSP or HTML pages. It
expects the resource to return a valid HTML fragment, but not a complete
page.</p>
<p>Each panel is defined in a <code>ajax:tab</code> child tag with
its own unique URL. The tab panel relies heavily on CSS to structure the
panels themselves. The output is generated as an unordered list
(&lt;ul&gt;) which works very nicely with the styles cataloged at <a
	href="http://css.maxdesign.com.au/listamatic/">List-a-Matic</a>.</p>
</div>

<h3>Tab Panel in Action</h3>

<%@page import="java.util.Date"%>
<jsp:useBean id="now" class="java.util.Date" />

<div id="tabPanelWrapper"><ajax:tabPanel 
	id="tabPanel${now}">
	<ajax:tab caption="Ford" baseUrl="htmlcontent.view?make=ford" />
	<ajax:tab caption="Honda" baseUrl="htmlcontent.view"
		parameters="make=honda" />
	<ajax:tab caption="Mazda" baseUrl="htmlcontent.view" defaultTab="true"
		parameters="make=mazda" />
	<ajax:tab caption="chain" baseUrl="tabpanel.jsp" />
</ajax:tabPanel></div>


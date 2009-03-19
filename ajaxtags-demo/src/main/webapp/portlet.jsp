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

<h1>Portlet Tag Demo</h1>

<div style="font-size: 90%; width: 650px;">
<p>The <code>ajax:portlet</code> tag mimics a true portlet (in the <a
	href="http://www.jcp.org/en/jsr/detail?id=168">JSR-168</a> sense) by
allowing you to define a portion of the page that pulls content from
another location using Ajax with or without a periodic refresh. It
expects the resource to return a valid HTML fragment, but not a complete
page.</p>
<p>In the example below, the portlet is set to refresh automatically
every 5 seconds. In addition, each of the optional toolbar elements
(refresh, sizing, and close) is defined.</p>
</div>


<h3>Portlet in Action</h3>

<ajax:portlet source="portlet_3"
	baseUrl="htmlcontent.view?make=ford" classNamePrefix="portlet"
    title="Ford Portlet" imageMaximize="img/maximize.gif" imageMinimize="img/minimize.gif"
	imageRefresh="img/refresh.gif" refreshPeriod="5" startMinimize="true" />

<br>
<ajax:portlet source="portlet_1"  executeOnLoad="true"
	baseUrl="htmlcontent.view?make=ford" classNamePrefix="portlet"
	title="Ford Portlet" imageClose="img/close.gif"
	imageRefresh="img/refresh.gif" refreshPeriod="5" />
<br>

<ajax:portlet source="portlet_2" executeOnLoad="true"
	baseUrl="htmlcontent.view?make=ford" classNamePrefix="portlet"
	title="Ford Portlet" imageClose="img/close.gif"  
	imageMaximize="img/maximize.gif" imageMinimize="img/minimize.gif"
	imageRefresh="img/refresh.gif" refreshPeriod="5" />

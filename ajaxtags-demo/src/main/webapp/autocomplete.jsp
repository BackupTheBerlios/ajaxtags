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
<%@page import="net.sourceforge.ajaxtags.demo.CarService"%>

<%@page import="net.sourceforge.ajaxtags.demo.Car"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%><jsp:useBean id="service" class="net.sourceforge.ajaxtags.demo.CarService" />

<%@ taglib uri="http://ajaxtags.sourceforge.net/tags/ajaxtags"
	prefix="ajax"%>

<h1>Autocomplete Demo</h1>

<div style="font-size: 90%; width: 650px;">
<p>The <code>ajax:autocomplete</code> tag allows one to retrieve a
list of probable values from a backend servlet (or other server-side
control) and display them in a dropdown beneath an HTML text input
field.</p>

<form action="." class="basicForm">
<fieldset><legend>Enter Car Model</legend>
<p>Available values start with letters: 
<%

for (Iterator<Character> it = service.getModelsFirstChar().iterator(); it.hasNext();) {
	Character firstChar   = it.next();
	out.append('\'') .append(firstChar).append('\'') ;
	if (it.hasNext()) {
		out.append(',');
	}
}
%>
</p>

<label for="model">Name:</label> <input id="model" name="model"
	type="text" size="30" /> <span id="indicator" class="indicator"
	style="display: none;"></span> <label for="make">Make:</label> <input
	id="make" name="make" type="text" size="30" /></fieldset>
</form>
<div id="errorMsg"
	style="display: none; border: 1px solid #e00; background-color: #fee; padding: 2px; margin-top: 8px; width: 300px; font: normal 12px Arial; color: #900"></div>

<ajax:autocomplete source="model" target="make"  
	baseUrl="autocomplete.view" className="autocomplete"
	indicator="indicator" minimumCharacters="1" />

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
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://ajaxtags.sourceforge.net/tags/ajaxtags"
	prefix="ajax"%>

<h1>Toggle Tag Demo</h1>

<div style="font-size: 90%; width: 650px;">
<p>The <code>ajax:toggle</code> tag is the perfect way to implement
a rating system or single on/off switch. In addition to the visual, you
can also elect to store the value in a hidden form field.</p>
<p>This tag is fairly simple and could be powerful all at the same
time. We envision one could use this in a variety of cases such as
checking/unchecking recordsets.</p>
</div>


<h3>Toggle Using Stars</h3>
<form id="toggleForm" action="."><input type="hidden"
	id="raterField" /></form>

<h4>AJAX Rating System <span style="font: normal 8pt Verdana;">[${toggleRating}]</span></h4>
<ajax:toggle baseUrl="toggle.view" source="rater1" 
	ratings="One,Two,Three,Four,Five" defaultRating="${toggleRating}"
	containerClass="star-rating" messageClass="star-rating-message"
	selectedClass="selected" selectedOverClass="selectedover"
	selectedLessClass="selectedless" overClass="over" onOff="false"
	state="raterField"  
	parameters="rating={ajaxParameter}" /> 
	
	<script type="text/javascript">
	window.on_off = function(value) {
		$("onoff").innerHTML = value;
	}
</script> 



<h4>On/Off Toggle <span style="font: normal 8pt Verdana;"
	id="onoff">[${toggleRatingOnOff}]</span></h4>
<ajax:toggle baseUrl="toggle.view?type=2" source="rater2" ratings="true,false"
	defaultRating="${toggleRatingOnOff}" containerClass="power-rating"
	selectedClass="selected" selectedOverClass="selectedover"
	selectedLessClass="selectedless" overClass="over" onOff="true"
	updateFunction="on_off" state="raterField" 
	parameters="rating={ajaxParameter}" />


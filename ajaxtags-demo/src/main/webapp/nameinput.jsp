<%-- 
 * Copyright 2007 Jens Kapitza
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

<h1>Update Form Field Tag Demo 2</h1>
<div style="font-size: 90%; width: 650px;">
<p>The <code>ajax:updateField</code> tag allows you to update one or
more form fields based on the value of another single field.</p>
<p>The example below uses this concept to implement a simple
conversion tool.</p>
</div>

<script type="text/javascript">
	/*
	 * USER DEFINED FUNCTIONS
	 */

	 window.isNumber = function(n) {
		    return  (/^\d+$/g).test(n);
		 };

	 window.addAgeToParameters = function() {
		var name = $('name');
		name.value = prompt("enter your name", "");
		var age = $('age');

		if (age.value.length > 1 && age.value.charAt(0) == "$") {
			var c = 0;
			var n = age.value;
			
			while (! window.isNumber(n) && c < 5) {
				var text = c > 0 ? "enter your age, have to be a number try count = "
						+ c
						: "enter your age";
				if (c == 4)
					alert("last one now i'll send it");
				n = prompt(text, "");
				c++;
			}
			age.value = n;
		}
		this.parameters = "";
		var i;
		var eles = document.forms["updateForm"].elements;
		for (i = 0; i < eles.length; i++) {
			if (eles[i].id && eles[i].type) {
				if (this.parameters != "") {
					this.parameters += ",";
				}
				this.parameters += eles[i].id + "={" + eles[i].id + "}";
			}
		}
	};
</script>

<div style="width: 400px;">
<form id="updateForm">
<fieldset><legend>age and name submit</legend>
<p>Enter your age</p>

<label for="age">Your age</label> <input type="text" id="age" /> <input
	id="action" type="button" value="Say Hello" /> <label for="name">Your
Name is</label> <input type="text" id="name" /></fieldset>
</form>
</div>
<ajax:updateField baseUrl="nameinput.view" source="age"
	valueUpdateByName="true" target="name,age" action="action"
	parser="new DefaultResponseParser('xml')"
	preFunction="addAgeToParameters" />

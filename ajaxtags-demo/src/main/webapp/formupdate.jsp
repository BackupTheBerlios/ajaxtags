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

<h1>Update Form Field Tag Demo</h1>

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
		var eles = document.forms["updateForm2"].elements;
		for (i = 0; i < eles.length; i++) {
			if (eles[i].id && eles[i].type) {
				if (this.parameters != "") {
					this.parameters += ",";
				}
				this.parameters += eles[i].id + "={" + eles[i].id + "}";
			}
		}
	};
	
	 /// use window. XXX to make XXX visible!
	 // eval function error?
	window.initProgress2 = function () {
		Element.addClassName('mph', 'progressMeterLoading');
		$('kph').value = "";
		$('mps').value = "";
	};

	window.resetProgress2 = function () {
		Element.removeClassName('mph', 'progressMeterLoading');

		if ($F('kph') != "") {
			// clear error box
			$('errorMsg').innerHTML = "";

			// do cool effect
			new Effect.Highlight('kph');
			new Effect.Highlight('mps');

			// display success message
			Element.show('successMsg');
			setTimeout("Effect.DropOut('successMsg');", 2000);
		}
	}; 
</script>

<div style="width: 400px;">
<form id="updateForm">
<fieldset><legend>Velocity Conversion</legend>
<p>Enter miles per hour and click Calculate</p>

<label for="mph">Miles/Hour (mph)</label> <input type="text" id="mph" />
<input id="action" type="button" value="Calculate" /> <label for="kph">Kilometers/Hour
(kph)</label> <input type="text" id="kph" /> <label for="mps">Meters/Second
(m/s)</label> <input type="text" id="mps" /></fieldset>
</form>
</div>
<div id="successMsg"
	style="display: none; border: 1px solid #0e0; background-color: #efe; padding: 2px; margin-top: 8px; width: 300px; font: normal 12px Arial; color: #090">Calculation
complete</div>
<div id="errorMsg"
	style="display: none; border: 1px solid #e00; background-color: #fee; padding: 2px; margin-top: 8px; width: 300px; font: normal 12px Arial; color: #900"></div>

<ajax:updateField baseUrl="formupdate.view" source="mph"
	valueUpdateByName="true" target="mps,kph" action="action"
	parameters="mph={mph}" parser="new DefaultResponseParser('xml')"
	preFunction="initProgress2" postFunction="resetProgress2" />



<div style="width: 400px;">
<form action="." id="updateForm2">
<fieldset><legend>age and name submit</legend>
<p>Enter your age</p>

<label for="age">Your age</label> <input type="text" id="age" /> <input
	id="action2" type="button" value="Say Hello" /> <label for="name">Your
Name is</label> <input type="text" id="name" /></fieldset>
</form>
</div>
<ajax:updateField baseUrl="nameinput.view" source="age"
	valueUpdateByName="true" target="name,age" action="action2"
	parser="new DefaultResponseParser('xml')"
	preFunction="addAgeToParameters" />

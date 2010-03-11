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

<h1>Submit Form Tag</h1>

<div style="font-size: 90%; width: 650px;">
<p>The <code>ajax:submit</code> tag allows you to submit
form asynchronously and update page with retrieved answer.</p>
</div>

<div style="width: 400px;">
<form id="form1" action="formupdate.view">
<fieldset><legend>Velocity Conversion</legend>
<p>Enter miles per hour and click Calculate</p>

<label for="mph">Miles/Hour (mph)</label> <input type="text" id="mph" name="mph" />
<input type="submit" value="Calculate" />
</fieldset>
</form>
</div>

<div id="successMsg" style="border: 1px solid #0e0; background-color: #efe; padding: 2px; margin-top: 8px; font: normal 12px Arial; color: #090">Calculation results</div>

<ajax:submit source="form1" target="successMsg" />

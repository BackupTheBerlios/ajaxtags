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
<%--
BUG https://developer.berlios.de/bugs/?func=detailbug&bug_id=16027&group_id=10618
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://ajaxtags.sourceforge.net/tags/ajaxtags"
	prefix="ajax"%>

<h1>AjaxJspTag.Base#buildParameterString</h1>
<div>AjaxJspTag.Base#buildParameterString</div>

<div id="errorMsg"
	style="border: 1px solid #00e; background-color: #eef; padding: 2px; margin-top: 8px; font: normal 12px Arial; color: #009"></div>

<script type="text/javascript">
runTest=function(){
  var params="checkbox1={checkbox1},checkbox2={checkbox2},checkbox3={checkbox3},select1={select1}";
  var aj=new AjaxJspTag.Base;
  aj.options={parameters:params};
  var bps=aj.buildParameterString(params),fs=Form.serialize("form1"),passed=(bps===fs);
  $("errorMsg").update("AjaxJspTag.Base#buildParameterString(): "+bps+"<br/>Form#serialize(): "+fs+"<br/>"+(passed?"Test passed":"Test failed"));
  return passed;
};
</script>

<form id="form1" method="post" onsubmit="return false;">Checkbox
with default value <input type="checkbox" id="checkbox1"
	name="checkbox1" /><br />
Checkbox "on" <input type="checkbox" id="checkbox2" name="checkbox2"
	value="on" checked="checked" /><br />
Checkbox "true" <input type="checkbox" id="checkbox3" name="checkbox3"
	value="true" /><br />

<select multiple="multiple" id="select1" name="select1">
	<option value="1">1</option>
	<option value="2" selected="selected">2</option>
	<option value="3">3</option>
	<option value="4" selected="selected">4</option>
	<option value="5">5</option>
</select><br />

<button onclick="runTest(); return false;">Test</button>
</form>

<script type="text/javascript">
runTest();
</script>

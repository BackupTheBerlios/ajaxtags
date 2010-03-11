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
<%--
BUG http://sourceforge.net/tracker/index.php?func=detail&aid=1857214&group_id=140499&atid=746679
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://ajaxtags.sourceforge.net/tags/ajaxtags"
	prefix="ajax"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<%@page import="net.sourceforge.ajaxtags.demo.CarService"%>
<jsp:useBean id="service" class="net.sourceforge.ajaxtags.demo.CarService" />

<ajax:displayTag id="BUG:1857214" parameters="selected={ajaxParameter}">
<display:table name="pageScope.service.allCars"
	class="displaytag" pagesize="10" defaultsort="1"
	defaultorder="descending" export="false" id="row"  excludedParams="selected" >
	<display:column property="make" title="Make" sortable="true"
		headerClass="sortable" />
	<display:column title="Model" sortable="true"
		headerClass="sortable" > ${row.model} </display:column>
	<display:column title="Link" media="html">
		<a href="http://www.${row.make}.com" >${row.make} Web Page</a>
	</display:column>
	<display:column title="Link" media="xml">www.${row.make}.com
    </display:column>
</display:table>
</ajax:displayTag>



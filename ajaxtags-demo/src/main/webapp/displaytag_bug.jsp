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
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://ajaxtags.sourceforge.net/tags/ajaxtags"
    prefix="ajax"%>
    
<%@page import="java.util.Date"%>
<%@page import="net.sourceforge.ajaxtags.demo.CarService"%>

<jsp:useBean id="now" class="java.util.Date" />
<jsp:useBean id="service" class="net.sourceforge.ajaxtags.demo.CarService" />


Last Time Table Refreshed: ${now}
<display:table uid="${now.time}${param.num}" name="pageScope.service.allCars"
	class="displaytag" pagesize="10" defaultsort="1"
	defaultorder="descending" export="false" id="row" excludedParams="ajax">
	<display:column property="make" title="Make" sortable="true"
		headerClass="sortable" />
	<display:column title="Model" sortable="true"
		headerClass="sortable" >
		
     <a href="javascript://nop/" class="definition">${row.model}</a>
		
		</display:column>
	<display:column title="Link" media="html">
		<a href="http://www.${row.make}.com" >${row.make} Web Page</a>
	</display:column>
	<display:column title="Link" media="xml">www.${row.make}.com
    </display:column>
</display:table>


<%--
We just used this for testing.

<ajax:callout baseUrl="callout.view" sourceClass="definition"
    parameters="q={ajaxParameter}" title="Definition" />
--%>
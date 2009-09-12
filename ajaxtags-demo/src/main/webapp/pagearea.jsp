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
<%@page language="java" import="java.util.Date"%>
<%@ taglib uri="http://ajaxtags.sourceforge.net/tags/ajaxtags" prefix="ajax"%>
<jsp:useBean id="now" class="java.util.Date" />

<h1>Area Tag Demo</h1>

This area should not refresh: Date: ${now}
<br />
<ajax:anchors target="ajaxFrame">
  <!-- Ajax this link -->
  Refresh first area <a href="pagearea.jsp" class="contentLink">&gt;&gt;</a>
</ajax:anchors>

<br />
<br />
<ajax:area id="ajaxFrame" styleClass="textArea" ajaxAnchors="true">
  This is the first area and should be refreshed only when the first link or
  the link inside itself is clicked:
  <br />
  It include a link to <a href="pagearea.jsp">itself</a>
  <br />
  Date: ${now}
</ajax:area>

<br />
<br />
This area should not refresh: Date: ${now}
<br />
<ajax:anchors target="ajaxFrame2">
  <!-- Ajax this link -->
  Refresh second area <a href="pagearea.jsp" class="contentLink">&gt;&gt;</a>
</ajax:anchors>

<br />
<br />
<ajax:area id="ajaxFrame2" styleClass="textArea" ajaxAnchors="true">
  This is the second area and should be refreshed only when the second link or
  the link inside itself is clicked:
  <br />
  Click this link to refresh <a href="pagearea.jsp">itself</a>
  <br />
  Date: ${now}
</ajax:area>

<br />
<br />
This bottom area should not refresh: Date: ${now}
<br />

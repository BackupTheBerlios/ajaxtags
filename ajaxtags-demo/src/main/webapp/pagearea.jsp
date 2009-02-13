<%@ taglib uri="http://ajaxtags.sourceforge.net/tags/ajaxtags"
	prefix="ajax"%>


<%@page import="java.util.Date"%>
<jsp:useBean id="now" class="java.util.Date" />

<h1>Area Tag Demo</h1>

This area should not refresh: Date: ${now}
<br />
<ajax:anchors target="ajaxFrame" >
	<!-- Ajax this link -->
  Refresh first area <a href="pagearea.jsp" class="contentLink">
	&gt;&gt;</a>
</ajax:anchors>

<br />
<br />
<ajax:area id="ajaxFrame"
	styleClass="textArea" ajaxAnchors="true">
  This is the first area and should be refreshed only when the first link or
  the link inside itself is clicked:
  <br />
  It include a link to <a href="pagearea.jsp"> itself </a>
	<br />
  Date: ${now}
</ajax:area>

<br />
<br />
This area should not refresh: Date: ${now}
<br />
<ajax:anchors target="ajaxFrame2">
	<!-- Ajax this link -->
  Refresh second area <a href="pagearea.jsp" class="contentLink">
	&gt;&gt;</a>
</ajax:anchors>

<br />
<br />
<ajax:area id="ajaxFrame2"
	styleClass="textArea" ajaxAnchors="true">
  This is the second area and should be refreshed only when the second link or
  the link inside itself is clicked:
  <br />
  Click this link to refresh <a href="pagearea.jsp"> itself </a>
	<br />
  Date: ${now}
</ajax:area>

<br />
<br />
This bottom area should not refresh: Date: ${now}
<br>


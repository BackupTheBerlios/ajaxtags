<%@ taglib uri="http://ajaxtags.sourceforge.net/tags/ajaxtags"
	prefix="ajax"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<%@page import="java.util.Date"%>
<jsp:useBean id="now" class="java.util.Date" />
<jsp:useBean id="service"
	class="net.sourceforge.ajaxtags.demo.CarService" />

<h1>DisplayTag Wrapper Demo</h1>

This top area should not refresh:
<br />
Date: ${now}
<br />
<br />
<ajax:displayTag id="displayTagFrame">
	<jsp:include page="displaytag_bug.jsp" flush="false"></jsp:include>
</ajax:displayTag>
<br />

<%--
  Created by IntelliJ IDEA.
  User: kerem
  Date: 8.10.2020
  Time: 09:39
  To change this template use File | Settings | File Templates.
--%>
<jsp:include page="/includes/header.jsp"/>
<jsp:include page="/includes/column_left_all.jsp"/>

<%@ page isErrorPage="true" %>

<section>
    <h1>404 Error</h1>
    <p>The server was not able to find the file  you requested.</p>
    <p>To continue, click the back button or select a link from this page.</p>
    <h2>Details</h2>
    <p>Requested URI: ${pageContext.errorData.requestURI}</p>
</section>

<jsp:include page="/includes/footer.jsp"/>

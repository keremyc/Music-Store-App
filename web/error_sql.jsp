<%--
  Created by IntelliJ IDEA.
  User: kerem
  Date: 8.10.2020
  Time: 09:51
  To change this template use File | Settings | File Templates.
--%>
<jsp:include page="/includes/header.jsp"/>
<jsp:include page="/includes/column_left_all.jsp"/>

<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section>
    <h1>Database Error</h1>
    <p>You have encountered an error with the Music Store database.
        You may need to install this database as described in appendix A.</p>
    <p>To continue, click the back button or a link from this page.</p>

    <h2>Details</h2>
    <code>
        ${pageContext.errorData.servletName} threw a <br>
        ${pageContext.exception}<br>
            <c:forEach var="line" items="${pageContext.errorData.throwable.stackTrace}">
                &nbsp;&nbsp;&nbsp;&nbsp;at ${line}<br>
            </c:forEach>
    </code>
</section>

<jsp:include page="/includes/footer.jsp"/>

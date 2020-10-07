<%--
  Created by IntelliJ IDEA.
  User: kerem
  Date: 7.10.2020
  Time: 17:38
  To change this template use File | Settings | File Templates.
--%>
<jsp:include page="/includes/header.jsp"/>
<jsp:include page="/includes/column_left_admin.jsp"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section id="admin">
    <h1>Report List</h1>
    <p>This page contains a list of the reports  that are
        available form this site</p>

    <form action="<c:url value='/adminPage/displayReport'/>" method="post">
        <input type="hidden" name="reportName" value="userEmail">
        <input type="hidden" name="reportTitle" value="The User Email Report"/>
        <input type="submit" value="User Email Report" class="left_margin">
    </form>

    <form action="parameters.jsp" method="post">
        <input type="hidden" name="reportName" value="downloadDetail">
        <input type="hidden" name="reportTitle" value="The Downloads report">
        <input type="submit" value="Downloads Report" class="left_margin">
    </form>

</section>

<jsp:include page="/includes/footer.jsp"/>
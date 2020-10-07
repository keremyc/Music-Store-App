<%--
  Created by IntelliJ IDEA.
  User: kerem
  Date: 7.10.2020
  Time: 17:29
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/includes/header.jsp"/>
<jsp:include page="/includes/column_left_admin.jsp"/>

<section id="admin">
    <h1>Admin Menu</h1>
    <form method="get" action="<c:url value='/adminPage/displayInvoices'/>">
        <input type="submit" value="Process Invoices" class="left_margin">
    </form>
    <form method="get" action="reports.jsp">
        <input type="submit" value="Display Reports" class="left_margin">
    </form>
</section>

<jsp:include page="/includes/footer.jsp"/>

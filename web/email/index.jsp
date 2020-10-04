<%--
  Created by IntelliJ IDEA.
  User: kerem
  Date: 4.10.2020
  Time: 22:11
  To change this template use File | Settings | File Templates.
--%>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_email.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section>
    <p>Join Our Email List!</p>
    <p>If you do, we'll send you announcements about new releases and special offers.</p>

    <p>${message}</p>

    <form method="post" action="<c:url value='/user/subscribeToEmail'/>">
        <label>Email:</label>
        <input type="email" name="email" required><br>
        <label>First Name:</label>
        <input type="text" name="firstName" required><br>
        <label>Last Name:</label>
        <input type="text" name="lastName" required><br>
        <input type="submit" value="JOIN NOW" id="submit">
    </form>
</section>

<jsp:include page="/includes/column_right_news.jsp"/>
<jsp:include page="/includes/footer.jsp"/>
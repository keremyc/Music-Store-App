<%--
  Created by IntelliJ IDEA.
  User: kerem
  Date: 5.10.2020
  Time: 21:38
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/includes/header.jsp"/>
<jsp:include page="/includes/column_left_all.jsp"/>

<section>
    <h1>Download registration</h1>
    <p>Before you can download and listen to these sound files,
        you must register with us by entering your name and email
        address below.</p>

    <form action="<c:url value='/catalog/product/${product.code}/listen/register'/>"
          method="post">
        <label>Email:</label>
        <input type="email" name="email" required><br>
        <label>First Name:</label>
        <input type="text" name="firstName" required><br>
        <label>Last Name:</label>
        <input type="text" name="lastName" required><br>
        <input type="submit" value="REGISTER" id="submit">
    </form>
</section>

<jsp:include page="/includes/column_left_buttons.jsp"/>
<jsp:include page="/includes/footer.jsp"/>
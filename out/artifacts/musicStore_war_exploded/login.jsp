<%--
  Created by IntelliJ IDEA.
  User: kerem
  Date: 8.10.2020
  Time: 09:55
  To change this template use File | Settings | File Templates.
--%>
<jsp:include page="/includes/header.jsp"/>
<jsp:include page="/includes/column_left_all.jsp"/>

<section>
    <h1>Login Form</h1>
    <p>Please enter a username and password to continue</p>
    <form action="j_security_check" method="post">
        <label>User Name</label>
        <input type="text" name="j_username" required><br>
        <label>Password</label>
        <input type="password" name="j_password"><br>
        <label>&nbsp;</label>
        <input type="submit" value="Login">
    </form>
</section>

<jsp:include page="/includes/column_right_news.jsp"/>
<jsp:include page="/includes/footer.jsp"/>
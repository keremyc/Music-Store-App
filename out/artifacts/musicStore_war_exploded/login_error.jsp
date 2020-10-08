<%--
  Created by IntelliJ IDEA.
  User: kerem
  Date: 8.10.2020
  Time: 10:01
  To change this template use File | Settings | File Templates.
--%>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_all.jsp" />

<section>

    <h1>Login Form - Error</h1>
    <p>You did not log in successfully.</p>
    <p>Please check your username and password and try again.</p>

    <form action="j_security_check" method="get">
        <label>Username</label>
        <input type="text" name="j_username"><br>
        <label>Password</label>
        <input type="password" name="j_password"><br>
        <label>&nbsp;</label>
        <input type="submit" value="Login">
    </form>

</section>

<jsp:include page="/includes/column_right_news.jsp" />
<jsp:include page="/includes/footer.jsp" />

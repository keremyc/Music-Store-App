<%--
  Created by IntelliJ IDEA.
  User: kerem
  Date: 4.10.2020
  Time: 11:41
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Music Store</title>
    <meta charset="utf-8">
    <link rel="shortcut icon" href="<c:url value='/images/favicon.ico'/>" >
    <link rel="stylesheet" href="<c:url value='/styles/main.css' />" type="text/css">
    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
</head>

<body>
<header>
    <img src="<c:url value='/images/logo.jpg'/>" alt = "Fresh Corn Records Logo" width="58">
    <h1>Fresh Corn Records</h1>
    <h2>Quality Sounds Served Up Fresh!</h2>
</header>
<nav id="nav_bar">
    <ul>
        <li><a href="<c:url value='#Admin'/>">Admin</a></li>
        <li><a href="<c:url value='/user/deleteCookies'/>">Delete Cookies</a></li>
        <li><a href="<c:url value='/order/showCart'/>">Show Cart</a></li>
    </ul>
</nav>
</body>
</html>

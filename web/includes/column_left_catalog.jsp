<%--
  Created by IntelliJ IDEA.
  User: kerem
  Date: 4.10.2020
  Time: 21:46
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<aside id="sidebarA">
    <nav>
        <ul>
            <li><a href="<c:url value='/' />">
                Home</a></li>
            <li><a class="current" href="<c:url value='/catalog' />">
                Browse Catalog</a></li>
            <li><a href="<c:url value='/email' />">
                Join Email List</a></li>
            <li><a href="<c:url value='/customer_service' />">
                Customer Service</a></li>
        </ul>
    </nav>
</aside>

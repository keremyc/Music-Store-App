<%--
  Created by IntelliJ IDEA.
  User: kerem
  Date: 6.10.2020
  Time: 23:46
  To change this template use File | Settings | File Templates.
--%>
<jsp:include page="/includes/header.jsp"/>
<jsp:include page="/includes/column_left_all.jsp"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="cart">
    <h1>Your Invoice</h1>

    <table>
        <tr>
            <th>Date</th>
            <td>${invoice.invoiceDateDefaultFormat}</td>
            <td></td>
        </tr>
        <tr>
            <th class="top">Ship To</th>
            <td>${user.addressHTMLFormat}</td>
            <td></td>
        </tr>
        <tr>
            <td colspan="3"><hr></td>
        </tr>
        <tr>
            <th>Qty</th>
            <th>Description</th>
            <th>Price</th>
        </tr>
        <c:forEach var="item" items="${invoice.lineItems}">
            <tr>
                <td>${item.quantity}</td>
                <td>${item.product.description}</td>
                <td>${item.totalCurrencyFormat}</td>
            </tr>
        </c:forEach>

        <tr>
            <th>Total:</th>
            <td></td>
            <td>${invoice.invoiceTotalCurrencyFormat}</td>
        </tr>
    </table>

    <form action="<c:url value='/order/displayUser'/>" method="post" id="float_left">
        <input type="submit" value="Edit Address">
    </form>

    <form action="<c:url value='/order/displayCreditCard' />" method="post">
        <input type="submit" value="Continue">
    </form>
</form>
</section>

<jsp:include page="/includes/footer.jsp"/>

<%--
  Created by IntelliJ IDEA.
  User: kerem
  Date: 6.10.2020
  Time: 12:17
  To change this template use File | Settings | File Templates.
--%>
<jsp:include page="/includes/header.jsp"/>
<jsp:include page="/includes/column_left_all.jsp"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="cart">
    <h1>Your cart</h1>

    <c:choose>
        <c:when test="${emptyCart != null}">
            <p>Your cart is empty.</p>
        </c:when>
        <c:otherwise>
            <table>
                <tr>
                    <th>Quantity</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Amount</th>
                    <th>&nbsp;</th>
                </tr>
                <c:forEach var="item" items="${cart.items}">
                    <tr class="cart_row">
                        <td>
                            <form action="<c:url value='/order/updateItem'/>" method="post">
                                <input type="hidden" name="productCode"
                                       value="<c:out value='${item.product.code}'/>">
                                <input type="text" name="quantity"
                                       value="<c:out value='${item.quantity}'/>">
                                <input type="submit" value="Update">
                            </form>
                        </td>
                        <td>${item.product.description}</td>
                        <td>${item.product.priceCurrencyFormat}</td>
                        <td>${item.totalCurrencyFormat}</td>
                        <td>
                            <form method="post" action="<c:url value='/order/removeItem'/>">
                                <input type="hidden" name="productCode"
                                       value="<c:out value='${item.product.code}'/>">
                                <input type="submit" value="Remove">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                <tr>
                    <td colspan="2">
                        <p><b>To change the quantity for an item</b>, enter the new quantity
                            and click on the Update button.</p>
                        <p><b>To remove an item</b>, click on the Remove button.</p>
                    </td>
                    <td colspan="3">&nbsp;</td>
                </tr>
            </table>
        </c:otherwise>
    </c:choose>

    <form action="<c:url value='/catalog'/>" method="get" id="float_left">
        <input type="submit" value="Continue Shopping">
    </form>

    <c:if test="${emptyCart == null}">
        <form method="post" action="<c:url value='/order/checkUser'/>" >
            <input type="submit" value="Checkout">
        </form>
    </c:if>

</section>

<jsp:include page="/includes/footer.jsp"/>
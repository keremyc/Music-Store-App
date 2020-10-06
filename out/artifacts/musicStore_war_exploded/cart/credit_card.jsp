<%--
  Created by IntelliJ IDEA.
  User: kerem
  Date: 7.10.2020
  Time: 00:47
  To change this template use File | Settings | File Templates.
--%>
<jsp:include page="/includes/header.jsp"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="cart">
    <h1>Enter your credit card information</h1>

    <form action="<c:url value='/order/completeOrder'/>" method="post">
        <table>
            <tr>
                <td><b>Credit Card Type</b></td>
                <td>
                    <select name="creditCardType" size="1">
                        <option value="Visa">Visa</option>
                        <option value="Mastercard">Mastercard</option>
                        <option value="AmEx">American Express</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><b>Card Number</b></td>
                <td><input type="text" size="20" name="creditCardNumber"
                        maxlength="25" required></td>
            </tr>
            <tr>
                <td><b>Expiration date (mm/yyyy)</b></td>
                <td>
                    <select name="selectCardExpirationMonth">
                        <option value="01">01</option>
                        <option value="02">02</option>
                        <option value="03">03</option>
                        <option value="04">04</option>
                        <option value="05">05</option>
                        <option value="06">06</option>
                        <option value="07">07</option>
                        <option value="08">08</option>
                        <option value="09">09</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                    </select>
                    /
                    <select name="creditCardExpirationYear">
                        <c:forEach var="year" items="${creditCardExpirationYears}">
                            <option value="${year}">${year}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" value="Submit Order"></td>
            </tr>
        </table>
    </form>
</section>

<jsp:include page="/includes/footer.jsp"/>

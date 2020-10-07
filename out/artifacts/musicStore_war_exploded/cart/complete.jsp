<%--
  Created by IntelliJ IDEA.
  User: kerem
  Date: 7.10.2020
  Time: 01:44
  To change this template use File | Settings | File Templates.
--%>
<jsp:include page="/includes/header.jsp"/>
<jsp:include page="/includes/column_left_all.jsp"/>

<section class="cart">
    <h1>Thank you, ${user.firstName}</h1>

    <p>
        Your order has been submitted. We'll begin processing your
        order right away. If you have any questions about your order,
        please feel free to contact us at
        <a href="mailto:${custServEmail}">${custServEmail}</a>
    </p>
</section>

<jsp:include page="/includes/column_right_news.jsp" />
<jsp:include page="/includes/footer.jsp" />

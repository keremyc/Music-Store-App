<%--
  Created by IntelliJ IDEA.
  User: kerem
  Date: 4.10.2020
  Time: 22:42
  To change this template use File | Settings | File Templates.
--%>
<jsp:include page="/includes/header.jsp"/>
<jsp:include page="/includes/column_left_all.jsp"/>

<section>
    <h1>Thanks For Joining Our Email List</h1>
    <p>Here is the information you just entered:</p>
    <label class="no_pad_top">Email:</label>
    <span>${user.email}</span><br>
    <label class="no_pad_top">First Name</label>
    <span>${user.firstName}</span><br>
    <label class="no_pad_top">Last Name</label>
    <span>${user.lastName}</span><br>
</section>

<jsp:include page="/includes/column_right_news.jsp" />
<jsp:include page="/includes/footer.jsp"/>

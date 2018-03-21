<%@ page import="com.ooad.web.model.Item" %><%--
  Created by IntelliJ IDEA.
  User: chakri
  Date: 17-Mar-18
  Time: 11:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <script src="../js/jquery.min.js"></script>
    <script src="../js/jquery.cookie.js"></script>
    <script src="../js/items.js"></script>
    <title>Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs &amp; more</title></title>
    <% Item item= (Item)request.getAttribute("items");%>
</head>
<body>
<%@include file="header.jsp" %>
<div id="output" class="container-fluid">
    <div id="sortColumn" class="col-lg-3" onchange="checkAndSubmit()">
        <span class="dropdown">
            <label>Sort By:</label>
            <select id="sort">
                <option value="1">Price</option>
                <%--<option value="2">Weight</option>--%>
            </select>
        </span>
        <br>
        <br>
        <span id="min">
            <label>Minimum Price:</label>
            <input class="form-control" type="number" name="minimum" id="minimum">
        </span>
        <br>
        <span id="max">
            <label>Maximum Price:</label>
            <input class="form-control" type="number" name="maximum" id="maximum">
        </span>
        <br>
        <div>
            <input id="clearFilter" type="button" class="btn1" value="Clear Filter" onclick="clearFilter()">
        </div>
    </div>
    <div class="col-lg-9">
        <div class="row" id="items">
            <%--<div class="col-lg-4">--%>
                <%--<div id="itemImage">--%>
                    <%--<img id = "prodImage" width="150"  src="/images/watch.jpg" height="150" class = "img-responsive" alt="watch">--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="col-lg-8">--%>
                <%--<div id="itemDetails">--%>
                    <%--<a href="#"><span id="productTitle" class="btn-link">Harsha</span></a> by <a href="#" id="sellerName">Sandeep</a>--%>
                    <%--<div id="Price">&#2352;<span id="currentPrice">100</span></div>--%>
                    <%--<div id="availability"><span id="avail">In stock</span></div>--%>
                <%--</div>--%>
            <%--</div>--%>
        </div>
    </div>
</div>
<%--<%=item.toString()%>--%>
<%@include file="footer.jsp" %>
</body>
</html>

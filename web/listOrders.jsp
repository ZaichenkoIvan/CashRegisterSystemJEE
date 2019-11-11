<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Orders</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css">
</head>

<body class="m-3">

<div class="row col-md-6">
    <table class="table table-striped table-bordered table-sm">
        <tr>
            <th>Id</th>
            <th>Number</th>
            <th>Product id</th>
            <th>Invoice id</th>
        </tr>

        <c:forEach items="${orders}" var="order">
            <tr>
                <td>${order.getId()}</td>
                <td>${order.getNumber()}</td>
                <td>${order.getProduct().getId()}</td>
                <td>${order.getInvoice().getId()}</td>
            </tr>
        </c:forEach>
    </table>
</div>

<nav aria-label="Navigation for countries">
    <ul class="pagination">
        <c:if test="${currentPage != 1}">
            <li class="page-item"><a class="page-link"
                                     href="cash?recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}&command=${showOrders}">Previous</a>
            </li>
        </c:if>

        <c:forEach begin="1" end="${noOfPages}" var="i">
            <c:choose>
                <c:when test="${currentPage eq i}">
                    <li class="page-item active"><a class="page-link">
                            ${i} <span class="sr-only">(current)</span></a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item"><a class="page-link"
                                             href="cash?recordsPerPage=${recordsPerPage}&currentPage=${i}&command=${showOrders}">${i}</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${currentPage lt noOfPages}">
            <li class="page-item"><a class="page-link"
                                     href="cash?recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}&command=${showOrders}">Next</a>
            </li>
        </c:if>
    </ul>
</nav>

<a href=admin.jsp>
    <button type="submit" class="btn btn-primary">Back</button>
</a>

<script src="https://code.jquery.com/jquery-3.1.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js"></script>

</body>
</html>


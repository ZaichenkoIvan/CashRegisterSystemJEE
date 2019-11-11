<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Users</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css">
</head>

<body>


<div class="banner">
    <div class="container">
        <nav class="navbar navbar-expand-lg d-flex flex-row justify-content-between">
            <a class="navbar-brand" href="#">Cash register service</a>
            <div>
                <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                    <li class="nav-item active">
                        <form action = "user" method="post">
                            <fmt:message key="user.button.logout" var="login" />
                            <input type="submit" class="btn btn-primary" name="command" value="logout">
                        </form>
                    </li>
                    <li class="nav-item">
                        <form>
                            <select id="language" name="language" onchange="submit()">
                                <option value="ua" ${language == 'ua' ? 'selected' : ''}>Ukrainian</option>
                                <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
                            </select>
                        </form>
                    </li>
                </ul>
            </div>
        </nav>
    </div>


    <div class="container">
        <div class="title-wrap">


            <div class="row col-md-6">
                <table class="table table-striped table-bordered table-sm">
                    <tr>
                        <th>Id</th>
                        <th>Name</th>
                        <th>Surname</th>
                        <th>Email</th>
                        <th>Nick</th>
                        <th>Role</th>
                    </tr>

                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td>${user.getId()}</td>
                            <td>${user.getName()}</td>
                            <td>${user.getSurname()}</td>
                            <td>${user.getEmail()}</td>
                            <td>${user.getPassword()}</td>
                            <td>${user.getRole()}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <nav aria-label="Navigation for countries">
                <ul class="pagination">
                    <c:if test="${currentPage != 1}">
                        <li class="page-item"><a class="page-link"
                                                 href="user?recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}&command=${showUsers}">Previous</a>
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
                                                         href="user?recordsPerPage=${recordsPerPage}&currentPage=${i}&command=${showUsers}">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${currentPage lt noOfPages}">
                        <li class="page-item"><a class="page-link"
                                                 href="user?recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}&command=${showUsers}">Next</a>
                        </li>
                    </c:if>
                </ul>
            </nav>
            <a href=index.jsp>
                <button type="submit" class="btn btn-primary">Back</button>
            </a>


        </div>
    </div>
</div>

<style>
    .nav-item {
        margin-left: 20px;
    }
    .navbar-brand {
        text-decoration: none;
        color: #fff;
    }
    .banner {
        width: 100vw;
        min-height: 100vh;
        background: url("../img/lemon.jpg") center center /cover;
        background-repeat: no-repeat;
    }
    .btn-primary, .page-item.active .page-link {
        background-color: #66FF00!important;
        border-color: #66FF00!important;
    }
    .page-item.active .page-link {
        color: #fff!important;
    }
    .page-link {
        color: #e29400!important;
    }
    .navbar {
        background-color: rgba(0,0,0,.4)!important;
    }
    .title-wrap {
        margin-top: 30px;
        padding: 20px 0;
        background-color: rgba(0,0,0,.4);
    }
    .navbar-nav {
        display: flex;
        flex-direction: row;
        justify-content: center;
        align-items: center;
    }

    .table tr, .table td {
        color: #fff;
    }

</style>

<script src="https://code.jquery.com/jquery-3.1.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js"></script>

</body>
</html>

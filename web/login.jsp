
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="localization" />
<!DOCTYPE html>
<html lang="${language}">
<head>
    <title>Login</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>



<div class="banner">
    <div class="container">
        <nav class="navbar navbar-expand-lg d-flex flex-row justify-content-between">
            <a class="navbar-brand" href="#">Cash register service</a>
            <div>
                <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                    <li class="nav-item active">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#loginModal">
                            <fmt:message key="login.button.login"/>
                        </button>
                    </li>
                    <li class="nav-item">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#regModal">
                            <fmt:message key="login.button.register"/>
                        </button>
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
            <h1 class="title">This is my cash register service</h1>
        </div>
    </div>
</div>



<!-- Login Modal -->
<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <form class="modal-dialog" role="document" action="cash" method="post">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">
                    <fmt:message key="login.button.login"/>
                </h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <label for="login-email"><fmt:message key="login.label.email" />:</label>
                <input type="text" id="login-email" name="email">
                <label for="login-password"><fmt:message key="login.label.password" />:</label>
                <input type="password" id="login-password" name="password">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <fmt:message key="login.button.login" var="login" />
                <input type="submit"  class="btn btn-primary" name="command" value="login">
            </div>
        </div>
    </form>
</div>

<!-- Registration Modal -->
<div class="modal fade" id="regModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <form class="modal-dialog" role="document" action="cash" method="post">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">
                    <fmt:message key="login.button.register"/>
                </h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <label for="name"><fmt:message key="login.label.name" />:</label>
                <input type="text" id="name" name="name">
                <label for="surname"><fmt:message key="login.label.surname" />:</label>
                <input type="text" id="surname" name="surname">
                <label for="email"><fmt:message key="login.label.email" />:</label>
                <input type="text" id="email" name="email">
                <label for="password"><fmt:message key="login.label.password" />:</label>
                <input type="password" id="password" name="password">
                <label for="confirmPassword"><fmt:message key="login.label.passwordConfirm" />:</label>
                <input type="password" id="confirmPassword" name="confirmPassword">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <fmt:message key="login.button.login" var="register" />
                <input type="submit" class="btn btn-primary" name="command" value="register" >
            </div>
        </div>
    </form>
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
        height: 100vh;
        background: url("img/lemon.jpg") center center /cover;
        background-repeat: no-repeat;
    }
    .btn-primary {
        background-color: #66FF00!important;
        border-color: #66FF00!important;
    }
    .navbar {
        background-color: rgba(0,0,0,.4)!important;
    }
    .title {
        text-align: center;
        color: #fff;
    }
    .title-wrap {
        margin-top: 30px;
        padding: 20px 0;
        background-color: rgba(0,0,0,.4);
    }
    .modal-dialog {
        max-width: 400px;
    }
    .modal-body {
        display: flex;
        flex-direction: column;
    }
    .modal-body input {
        margin-bottom: 10px;
    }
</style>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>

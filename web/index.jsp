<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Home page</title>
  <meta charset="UTF-8">
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
      <h2 class="title">Show Info</h2>

      <form action="user" class="col-md-4">
        <input type="hidden" name="currentPage" value="1">
        <input type="hidden" name="recordsPerPage" value="5">

        <div class="form-group">
          <label for="records" class="select-label">Select records per page:</label>
          <select class="form-control" id="records" name="command">
            <option value="showUsers">Users</option>
            <option value="showOrders" selected>Orders</option>
            <option value="showInvoices">Invoices</option>
            <option value="showPayments">Payments</option>
            <option value="showProducts">Products</option>
          </select>
        </div>
        <button type="submit" class="btn btn-primary selector-submit">Submit</button>
      </form>
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
  .navbar-nav {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
  }
  .select-label {
    color: #fff;
  }
  .selector-submit {
    width: 100%;
  }
</style>


<script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" ></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" ></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" ></script>

</body>
</html>
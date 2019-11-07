<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Home page</title>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css">
</head>

<body class="m-3">

<h1>Show users</h1>

<form action="user">

  <input type="hidden" name="currentPage" value="1">
  <input type="hidden" name="recordsPerPage" value="5">

  <div class="form-group col-md-4">

    <label for="records">Select records per page:</label>

    <select class="form-control" id="records" name="commandShow">
      <option value="showUsers">Users</option>
      <option value="showOrders" selected>Orders</option>
    </select>

  </div>

  <button type="submit" class="btn btn-primary">Submit</button>

</form>

<script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" ></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" ></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" ></script>

</body>
</html>

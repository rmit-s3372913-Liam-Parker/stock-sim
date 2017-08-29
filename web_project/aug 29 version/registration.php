<?php
session_start();

?>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>ASX Simulator</title>
  <link rel="stylesheet" type="text/css" href="bootstrap-3.3.6-dist/css/bootstrap.css">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="index.css">
  <link rel="stylesheet" type="text/css" href="user.js">

</head>
<body>

<div class="container">
  <div class="col-lg-4">
    
  </div>
  <div class="col-lg-4">
    <div class="jumbotron" style="margin-top: 150px">
    
      <h2>ASX Simulator</h2>
      <form action="validate.php" method="POST">
        <div class="form-group">
          <input type="text" class="form-control" name="username" placeholder="Username" required>
        </div>

        <div class="form-group">
          <input type="email" class="form-control" name="email" placeholder="Email" required>
        </div>

        <div class="form-group">
          <input type="password" class="form-control" name="password" placeholder="Password" required>
        </div>

        <div class="form-group">
          <input type="password" class="form-control" name="con-password" placeholder="Confirm Password" required>
        </div>        

        <button type="submit" class="btn btn-primary form-control" name="submit">Register</button>
        <a href="login.php">Login</a>
      </form>
    </div>
  </div>
  <div class="col-lg-4">
    
  </div>
</div>

</body>
</html>
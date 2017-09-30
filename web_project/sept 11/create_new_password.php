<?php
session_start();
include 'connection.php';

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

  <script type="text/javascript" src="scripts.js"></script>

</head>
<body>

<div class="container">
  <div class="col-lg-3">
    
  </div>
  <div class="col-xs-6">
    <div class="jumbotron" style="margin-top: 150px">
    
      <h2>Create A New Password</h2>
      <!-- form for creating a new password 
      input for password & confirm password -->
      <form action="new_password_validation.php" method="POST">
        <div class="form-group">
          <input type="password" class="form-control" name="password" placeholder="Password" required>
        </div>

        <div class="form-group">
          <input type="password" class="form-control" name="con-password" placeholder="Confirm Password" required>
        </div>        

        <div class="container">
          <div class="row">
              <div class="col-xs-12">
                  <div  class="text-center">
                      <button type="submit" class="btn btn-success" name="change">Change Password</button>
                      <button type="submit" class="btn btn-danger" onClick="location.href='login.php'">Cancel</button>
                  </div>
              </div>
          </div>
        </div>



      </form>
    </div>
  </div>
  <div class="col-lg-4">
    
  </div>
</div>
 
</body>
</html>
<?php
session_start();
?>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>ASX Simulator</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" media="none" onload="if(media!='all')media='all'">
  <noscript><link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"></noscript>
</head>
<body style="margin: 0; font-size: 14px; font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif">
<div class="container" style="display: table; margin: auto">
  <div style="float: left; width: 33.33333333%; height: 1px">
  </div>
  <div class="col-lg-4">
    <div style="margin: 150px auto 30px auto; color: inherit; background-color: #eee; border-radius: 6px; padding: 30px">
      <h2>ASX Simulator</h2>
      <?php 
      //display a message using a session variable
      if (isset($_SESSION['error'])) 
      { 
      echo $_SESSION['error']; unset($_SESSION['error']); 
      }
      ?>
      <!-- Form for user registration -->
      <form action="registration_validation.php" method="POST">
        <input type="text" class="form-control" name="username" placeholder="Username" required style="margin-bottom: 15px; width: 100%">
        <input type="email" class="form-control" name="email" placeholder="Email" required style="margin-bottom: 15px; width: 100%">
        <input type="password" class="form-control" name="password" placeholder="Password" required style="margin-bottom: 15px; width: 100%">
        <input type="password" class="form-control" name="con-password" placeholder="Confirm Password" required style="margin-bottom: 15px; width: 100%">
        <button type="submit" class="btn form-control" name="submit" style="color: #fff; background-color: #337ab7; border-color:#2e6da4">Register</button>
        <a href="../index.php">Login</a>
      </form>
    </div>
  </div>
</div>
 
</body>
</html>
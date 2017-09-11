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

  
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>
<body>
<div class="container">
  <div class="col-lg-3">
    
  </div>
  <div class="col-xs-6">
    <div class="jumbotron" style="margin-top: 150px">
    
      <h2>Enter You Confirmation Code</h2>
      <p style="font-size:16px">We have sent an email confirmation code to 
      <strong>
      <?php 
      if (isset($_SESSION['address'])) 
        { 
        echo $_SESSION['address']; 
        }
      ?>.
      </strong>
      <br>
      Please enter the code below:
      </p>
      <?php 
      if (isset($_SESSION['code_error'])) 
        { 
        echo $_SESSION['code_error']; unset($_SESSION['code_error']); 
        }
      ?>
      
      <form action="code_for_password.php" method="POST">
        <div class="form-group">
          <input type="text" class="form-control" name="pass-reset-code" placeholder="Confimation Code" required>
        </div>

        

        <input type="submit" class="btn btn-primary form-control" name="reset-confirm" value="Confirm Email">
        
      
      </form>
    </div>
  </div>



</div>



</body>
</html>
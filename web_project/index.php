<?php
session_start();
unset($_SESSION['address']);
?>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>ASX Simulator</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" media="none" onload="if(media!='all')media='all'">
  <noscript><link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"></noscript>

  <script async src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script async src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>
<body>
<div class="container">
  <div style="float: left; width: 33.33333333%; height: 1px">
  </div>
  <div class="col-lg-4">
    <div style="margin: 150px auto 30px auto; color: inherit; background-color: #eee; border-radius: 6px; padding: 30px">
      <h2>ASX Simulator</h2>
      <!-- display error message or change password with session -->
      <?php
      if (isset($_SESSION['error_login'])) {
        echo $_SESSION['error_login']; unset($_SESSION['error_login']); 
      } elseif (isset($_SESSION['new-password'])) {
        echo $_SESSION['new-password']; unset($_SESSION['new-password']); 
      }
      ?>
      <!-- div container form login -->
      <form action="login/login_validation.php" method="POST">
        <div style="margin-bottom: 15px">
          <input type="text" class="form-control" name="username" placeholder="Username" 
          value="<?php if(isset($_COOKIE["member_user"])) { echo $_COOKIE["member_user"]; }?>" required
          style="display: block; width: 100%">
        </div>
        <div style="margin-bottom: 15px">
          <input type="password" class="form-control" name="password" placeholder="Password" 
          value="<?php if(isset($_COOKIE["member_password"])) { echo $_COOKIE["member_password"]; }?>" required
          style="display: block; width: 100%">
        </div>
        <div class="checkbox">
          <label><input type="checkbox" name="remember" <?php if(isset($_COOKIE["member_user"])) { ?> checked <?php } ?>>Remember Me</label>
        </div>
        <input type="submit" class="btn btn-primary form-control" name="login" value="Login">
        <a href="registration/registration.php">Register</a>&nbsp; &nbsp;<a href="#forgot-password-modal" data-toggle="modal">Forgot Password</a>
      </form>
    </div>
  </div>

  <!-- Modal for Forgot password -->
  <div class="modal fade" id="forgot-password-modal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content" style="position: relative">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h2 class="modal-title">Forgot Password</h2>
        </div>
        <div class="modal-body">
          <div class="text-center">
            <p>Enter email address to receive an email with a link to reset password.</p>
            
            <div class="panel-body">
              <form action="modal_email.php" method="POST">
                <div class="form-group">
                  <input type="email" class="form-control" name="email" placeholder="Email" required>
                </div>
                <button type="submit" class="btn btn-primary form-control" name="reset">Reset Password</button>
              </form>
            </div>
          </div>
        </div>

        <div class="modal-footer">
          <div class="col-md-12">
            <button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
          </div>
        </div>
      </div>
      
    </div>
  </div>
  <!-- End of Modal Forgot Password-->
</div>
</body>
</html>
 <?php  
 // if login is successful start a session  
 session_start();  
 if(isset($_SESSION["username"]))  
 {  
      echo '<h3>Login Success, Welcome - '.$_SESSION["username"].'</h3>';  
      echo '<br /><br /><a href="logout.php">Logout</a>';  
 }  
 else  
 {  
      header("location:pdo_login.php");  
 }  
 ?>  
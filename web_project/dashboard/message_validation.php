 <?php  
 //insert.php  
session_start();
include '../config/connection.php';

 if(isset($_POST["name"]))  
 {  
      $name = mysqli_real_escape_string($connect, $_POST["name"]);  
      $message = mysqli_real_escape_string($connect, $_POST["message"]);  
      $uname = $_SESSION['username'];
      $date = date('Y-m-d H:i:s');

      $sql = "INSERT INTO message(username, sender, `time`, message, read) VALUES ('".$username."', '".$name."', '$date', '".$message."', '1')";  
      if(mysqli_query($connect, $sql))  
      {  
           echo "Message Sent";  
      }  
 }  
 ?>
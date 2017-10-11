 <?php  
 //insert.php  
session_start();
include '../config/connection.php';

 if(isset($_POST["submit_message"]))  
 {  
    // set variables
    $name = mysqli_real_escape_string($conn, $_POST["name"]);  
    $message = mysqli_real_escape_string($conn, $_POST["message"]);  
    $uname = $_SESSION['username'];
    $date = date('Y-m-d H:i:s');

    // print_r($name);
    // print_r($message);
    // query for getting current user ID
    $sql = "select * FROM player WHERE username = '" . $_SESSION['username'] . "' ";
	$result = mysqli_query($conn, $sql);
	$row = mysqli_fetch_assoc($result);
	$currentUserId = $row['userId'];
	$currentUsername = $row['username'];
	// print_r($currentUserId);
	// print_r($currentUsername);

	$sql = "select * FROM player WHERE username = '" . $name . "' ";
	$result = mysqli_query($conn, $sql);
	$row = mysqli_fetch_assoc($result);
	$receiverUserId = $row['userId'];
	$receiverUsername = $row['username'];
	// print_r($receiverUserId);
	// print_r($receiverUsername);

	$sql = "INSERT INTO message(senderUserId, senderUsername, receiverUserId, receiverUsername, message) VALUES('$currentUserId', '$currentUsername', '$receiverUserId', '$receiverUsername', '$message')";

    //$sql = "INSERT INTO message(senderUserId, senderUsername, receiverUserId, receiverUsername, message, `time`, read) VALUES('$currentUserId', '$currentUsername', '$receiverUserId', '$receiverUsername', '$message', '$date', 'no')";
      //$sql = "INSERT INTO message(username, sender, `t, message, read) VALUES ('".$username."', '".$name."', '$date', '".$message."', '1')"; 
      // $record = mysqli_query($conn, $sql);
      // echo "Message Sent"; 
      // mysqli_close($conn);
      if(mysqli_query($conn, $sql))  
      {  
           echo "Message Sent";  
      }  
 }  
 ?>
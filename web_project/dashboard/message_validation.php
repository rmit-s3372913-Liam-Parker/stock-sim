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

  // query for getting current user ID
  $sql = "select * FROM player WHERE username = '" . $_SESSION['username'] . "' ";
	$result = mysqli_query($conn, $sql);
	$row = mysqli_fetch_assoc($result);
	$currentUserId = $row['userId'];
	$currentUsername = $row['username'];

	$sql = "select * FROM player WHERE username = '" . $name . "' ";
	$result = mysqli_query($conn, $sql);
	$row = mysqli_fetch_assoc($result);
	$receiverUserId = $row['userId'];
	$receiverUsername = $row['username'];

  
  $sql = "select count(*)as total from friend where (userId = '" . $currentUserId . "' AND username = '" . $_SESSION['username'] . "') OR (friendUserId = '". $receiverUserId ."' AND friendUsername = '". $receiverUsername ."') ";
  $result = mysqli_query($conn, $sql);
  $row = mysqli_fetch_assoc($result);
  print_r($row);

  if($row['total'] == 2)
  {
    $sql = "INSERT INTO message(senderUserId, senderUsername, receiverUserId, receiverUsername, message) VALUES('$currentUserId', '$currentUsername', '$receiverUserId', '$receiverUsername', '$message')";


      if(mysqli_query($conn, $sql))  
      {  
           echo "Message Sent"; 
           header('refresh: 2; send_message.php'); 
      } 
  }
  else
  {
    echo "You can only send message to your friends";
    header('refresh: 2; send_message.php');
  }

	 
 }  
 ?>
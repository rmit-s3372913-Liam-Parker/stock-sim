<?php
 session_start();
 include 'connection.php';

if(isset($_POST['resend']))
{
	
	// set variable
	$email = mysqli_escape_string($conn, $_POST['email']);
	
	// get all fields from player with the corresponding email
	$email_query = mysqli_query($conn, "Select * FROM player1 WHERE email='".$email."'");
	
	$numrow_email = mysqli_num_rows($email_query);

	// check connetion with message
	if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	}

	// check email if it exists
	if($numrow_email != 1)
	{
		$_SESSION['email_error'] = "Invalid email address!";
		header("Location: login.php");
	}
	
    // close connection 
    mysqli_close($conn);
	
	
}

?>
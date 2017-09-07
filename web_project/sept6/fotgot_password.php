<?php
 session_start();
 include 'connection.php';

if(isset($_POST['resend']))
{
	
	
	$email = mysqli_escape_string($conn, $_POST['email']);
	

	$email_query = mysqli_query($conn, "Select * FROM player1 WHERE email='".$email."'");
	
	$numrow_email = mysqli_num_rows($email_query);

	
	if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	}


	if($numrow_email != 1)
	{
		$_SESSION['email_error'] = "Invalid email address!";
		header("Location: login.php");
	}
	else
	{
		
	}
    	
     

    mysqli_close($conn);
	
	
	
}

?>
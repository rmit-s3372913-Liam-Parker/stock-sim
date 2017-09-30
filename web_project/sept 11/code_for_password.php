<?php
 session_start();
 include 'connection.php';

if(isset($_POST['reset-confirm']))
{
	
	// set variable with session-email 
	$code_match = mysqli_escape_string($conn, $_POST['pass-reset-code']);
	$match = $_SESSION['$email-code'];
	
	// check if code receive if it matches
	if ($code_match == $match)
	{
		// redirect page to a form for creating a new password
		header('Refresh: 2; create_new_password.php');
	}
	else
	{
		// set a session variable for error message and redirect page
		$_SESSION['code_error'] = "Incorrect Code";
		header('Location: confirm_code_for_reset_password.php');
	}
	
	
	
}

?>
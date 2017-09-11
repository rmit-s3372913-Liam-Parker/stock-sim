<?php
 session_start();
 include 'connection.php';

if(isset($_POST['reset-confirm']))
{
	
	
	$code_match = mysqli_escape_string($conn, $_POST['pass-reset-code']);
	$match = $_SESSION['$email-code'];
	
	if ($code_match == $match)
	{
		header('Refresh: 2; create_new_password.php');
	}
	else
	{
		$_SESSION['code_error'] = "Incorrect Code";
		header('Location: confirm_code_for_reset_password.php');
	}
	
	
	
}

?>
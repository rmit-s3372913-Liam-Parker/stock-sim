<?php
session_start();
include 'connection.php';

if(isset($_POST['confirm']))
{
	
	// set variable for confirmation code
	$code_match = mysqli_escape_string($conn, $_POST['confimation-code']);
	$match = $_SESSION['$code'];

	// check if code matches
	if ($code_match == $match)
	{
		// redirect page to login for matching code
		header('Refresh: 3; login.php');
	}
	else
	{
		// set session variable for error message and redirect page
		$_SESSION['code_error'] = "Incorrect Code";
		header('Location: registration_confirmation_form.php');
	}
	
}

	

?>
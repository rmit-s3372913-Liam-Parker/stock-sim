<?php
session_start();
include 'connection.php';

if(isset($_POST['confirm']))
{
	
	echo "TEST!";
	$code_match = mysqli_escape_string($conn, $_POST['confimation-code']);
	$match = $_SESSION['$code'];

	if ($code_match == $match)
	{
		echo "Email confirmed!";
		header('Refresh: 3; login.php');
	}
	else
	{
		$_SESSION['code_error'] = "Incorrect Code";
		header('Location: registration_confirmation_form.php');
	}
	
}

	

?>
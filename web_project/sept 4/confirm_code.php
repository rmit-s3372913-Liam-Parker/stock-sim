<?php
session_start();

if(isset($_POST['confirm']))
{
	$conn = mysqli_connect('localhost', 'root', '', 'testing');
	$code_match = mysqli_escape_string($conn, $_POST['confimation-code']);
	$match = $_SESSION['$code'];
	
	echo "TEST!";
	if ($code_match == $match)
	{
		echo "Email confirmed!";
		header('Refresh: 3; login.php');
	}
	else
	{
		$_SESSION['code_error'] = "Incorrect Code";
		header('Location: confirmation_form.php');
	}
	
}

	

?>
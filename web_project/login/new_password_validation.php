<?php
session_start();
include '../config/connection.php';

if(isset($_POST['change']))
{
	// set variables
	$password = mysqli_escape_string($conn, $_POST['password']);
	$con_password = mysqli_escape_string($conn, $_POST['con-password']);
	
	// query rows from player table with a given email
	$query = mysqli_query($conn, "Select * from player where email='".$_SESSION['address']."' ");
	$numrow = mysqli_num_rows($query);

	// check if password and confirm-password match
	if($password == $con_password)
	{
		// check for a player email if it exists
		if($numrow != 0)
		{
			// hash new password using md5
			// query update password field with hashed password
			$password = md5($password);
			$sql = "update player set password='".$password."' where email='".$_SESSION['address']."' ";

			// check query if true
			if($conn->query($sql) == TRUE)
			{
				// display a session message
				// redirect to login page for successful password changed
				$_SESSION['new-password'] = "Password has been changed!";
				header("Location: ../index.php");
			}
		}
		else
		{
			echo "testing user does not exists!";
			// if user doesnt exists redirect to create new password form
			header("Location: create_new_password.php");
		}

	}
	else
	{
		echo "Password does not match!";
		// redirect page to create new password form if 
		// input for password and confirm-password doesnt match
		header("Location: create_new_password.php");
	}

}


?>
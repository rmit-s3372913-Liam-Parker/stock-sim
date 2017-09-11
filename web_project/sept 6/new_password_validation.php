<?php
session_start();
include 'connection.php';

if(isset($_POST['change']))
{
	$password = mysqli_escape_string($conn, $_POST['password']);
	$con_password = mysqli_escape_string($conn, $_POST['con-password']);
	

	$query = mysqli_query($conn, "Select * from player1 where email='".$_SESSION['address']."' ");


	$numrow = mysqli_num_rows($query);

	if($password == $con_password)
	{
		echo "password match!";
		if($numrow != 0)
		{
			$password = md5($password);
			$sql = "update player1 set password='".$password."' ";

			if($conn->query($sql) == TRUE)
			{
				$_SESSION['new-password'] = "Password has been changed!";
				header("Location: login.php");
			}
		}
		else
		{
			echo "testing user does not exists!";
			header("Location: create_new_password.php");
		}

	}
	else
	{
		echo "Password does not match!";
		header("Location: create_new_password.php");
	}

}


?>
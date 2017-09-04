<?php
session_start();
include 'connection.php';

if(isset($_POST['submit']))
{
	
	/*$conn = mysqli_connect('localhost', 'root', '', 'testing');*/
	$uname = mysqli_escape_string($conn, $_POST['username']);
	$email = mysqli_escape_string($conn, $_POST['email']);
	$password = mysqli_escape_string($conn, $_POST['password']);
	$con_password = mysqli_escape_string($conn, $_POST['con-password']);
	$check_username = '';
	$check_email = '';
	$errors = array();

	$query_uname = mysqli_query($conn, "Select * FROM test WHERE username='".$uname."' ");
	$numrow_uname = mysqli_num_rows($query_uname);

	$query_email = mysqli_query($conn, "Select * FROM test WHERE email='".$email."' ");
	$numrow_email = mysqli_num_rows($query_email);

	$_SESSION['email-name'] = $email;
	/*if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	}*/

	if ($password == $con_password) 
	{
		
		if ($numrow_uname != 0)
		{
			$_SESSION['username_error'] = "Username already exists!";
			header("Location: registration.php");
		}

		if ($numrow_email != 0)
		{
			$_SESSION['email_error'] = "Email address already exists!";
			header("Location: registration.php");
		}	

		
		if ($numrow_uname == 0 && $numrow_email == 0) 
		{
			$encrypt_password = md5($password);

			$sql = "INSERT INTO test(username, password, email) VALUES('$uname', '$encrypt_password', '$email')";

			if ($conn->query($sql) === TRUE) 
			{
			    echo "Account has been created successfully";
			    /* insert here call function generate 5 random numbers*/
			    function randomWithLength()
				{
					$length = 5;
					$number = '';
					for ($i = 0; $i < $length; $i++)
					{
						$number .= rand(0,9);
					}

				return (int)$number;

				}
				$_SESSION['$code'] = randomWithLength();
				$msg = "Your confirmation code is: ".$_SESSION['$code'];
			    $header_email = 'From ASX Simulator.com.au'; 
			    mail($email,"Code Confirmation",$msg, $header_email);
			    header('Refresh: 2; confirmation_form.php');
			} 
			else 
			{
			    echo "Error: " . $sql . "<br>" . $conn->error;
			}
		}
		else
		{
			echo "Invalid username or password";
			header('Refresh: 3; registration.php');
		}
	}
	else
	{
		$_SESSION['message'] = "Password does not match!";
		header("Location: registration.php");
	}
	
	
}

	

?>
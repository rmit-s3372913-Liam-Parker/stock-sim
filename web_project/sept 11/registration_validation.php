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
	

	$query_uname = mysqli_query($conn, "Select * FROM player WHERE username='".$uname."' ");
	$numrow_uname = mysqli_num_rows($query_uname);

	$query_email = mysqli_query($conn, "Select * FROM player WHERE email='".$email."' ");
	$numrow_email = mysqli_num_rows($query_email);

	$_SESSION['email-name'] = $email;
	/*if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	}*/

	// check if password & confirm password matches
	if ($password == $con_password) 
	{
		
		// check database if username exists
		if ($numrow_uname != 0)
		{
			$_SESSION['username_error'] = "Username already exists!";
			header("Location: registration.php");
		}

		// check database if email exists
		if ($numrow_email != 0)
		{
			$_SESSION['email_error'] = "Email address already exists!";
			header("Location: registration.php");
		}	

		
		// check database if username & email exists if not proceed
		if ($numrow_uname == 0 && $numrow_email == 0) 
		{
			// generate random hash
			$encrypt_password = md5($password);
			$hash = md5(rand(0, 1000));

			// database insert to table name "player"
			$sql = "INSERT INTO player(username, password, email, emailConfirmation, confirm) VALUES('$uname', '$encrypt_password', '$email', '$hash', 'no')";

			if ($conn->query($sql) === TRUE) 
			{
			    // use session for automatic login
			    $_SESSION['username'] = $uname;
			    $_SESSION['password'] = $password; 
			    $_SESSION['email'] = $email;
			    $to      = $email; // Send email to user
				$subject = 'Registration | Verification'; // email subject 
				// email message content
				$message = "Please confirm your email address \n
							$uname, confirming your email address will give you full access to \n
							ASX Simulator 2017 \n
							http://titan.csit.rmit.edu.au/~s3492681/ASX/email_verify.php?email=$email&code=$hash";
				  
				// Set header                   
				$headers = 'From: ASX Simulator.com.au' . "\r\n"; 
				mail($to, $subject, $message, $headers); 
				echo "Account has been created successfully. Please check your email to verify.";
				header('Refresh: 5; login.php');
			 
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
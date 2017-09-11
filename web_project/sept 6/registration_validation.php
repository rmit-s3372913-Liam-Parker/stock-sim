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

	$query_uname = mysqli_query($conn, "Select * FROM player1 WHERE username='".$uname."' ");
	$numrow_uname = mysqli_num_rows($query_uname);

	$query_email = mysqli_query($conn, "Select * FROM player1 WHERE email='".$email."' ");
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
			// generate random hash
			$encrypt_password = md5($password);
			$hash = md5(rand(0, 1000));


			$sql = "INSERT INTO player1(username, password, email, email_code, active) VALUES('$uname', '$encrypt_password', '$email', '$hash', '0')";

			if ($conn->query($sql) === TRUE) 
			{
			    
			    $_SESSION['username'] = $uname;
			    $_SESSION['password'] = $password; 
			    $_SESSION['email'] = $email;
			    $to      = $email; // Send email to user
				$subject = 'Registration | Verification'; // email subject 
				$message = "Please confirm your email address \n
							$uname, confirming your email address will give you full access to \n
							ASX Simulator 2017 \n
							http://localhost/p1/asx/email_verify.php?email=$email&code=$hash";
				                     
				$headers = 'From: ASX Simulator.com.au' . "\r\n"; // Set from headers
				mail($to, $subject, $message, $headers); // Send our email
				echo "Account has been created successfully. Please check your email to verify.";
				header('Refresh: 5; login.php');
			    /*
				$_SESSION['$code'] = randomWithLength();
				$msg = "Your confirmation code is: ".$_SESSION['$code'];
			    $header_email = 'From ASX Simulator.com.au'; 
			    mail($email,"Code Confirmation",$msg, $header_email);
			    header('Refresh: 2; registration_confirmation_form.php');*/
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
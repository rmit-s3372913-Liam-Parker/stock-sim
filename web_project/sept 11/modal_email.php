<?php
session_start();
include 'connection.php';

if(isset($_POST['reset']))
{
	// set variable
	// query player table with a given email
	$email = mysqli_escape_string($conn, $_POST['email']);
	$query = mysqli_query($conn, "Select * FROM player WHERE email='".$email."' ");
	$numrow = mysqli_num_rows($query);

	// set session variable for email
	$_SESSION['address'] = $email;

	// check if email exists in player table
	if($numrow != 0)
	{
		// generate 5 digit code to be sent to email
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

		// set session variable for email-code with a generated random number code to be sent
		// set mailing function and redirect to confirmation-code page
		$_SESSION['email-code'] = randomWithLength();
		$to      = $email;
		$subject = 'Forgot Password | Verification';
		$msg     = "Your confirmation code is: ".$_SESSION['email-code'];
		$header_email = 'From: ASX.Simulator.com.au\r\n';
		mail($to, $subject, $msg, $header_email);
		header('Refresh: 1; confirm_code_for_reset_password.php');


	}
	else
	{
		header('Refresh: 2; confirm_code_for_reset_password.php');
	}

}

?>
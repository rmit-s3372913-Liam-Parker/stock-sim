<?php
session_start();
include 'connection.php';

if(isset($_POST['reset']))
{
	$email = mysqli_escape_string($conn, $_POST['email']);

	$query = mysqli_query($conn, "Select * FROM player1 WHERE email='".$email."' ");

	$numrow = mysqli_num_rows($query);

	$_SESSION['address'] = $email;
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

		
		$_SESSION['$email-code'] = randomWithLength();
		$msg = "Your confirmation code is: ".$_SESSION['$email-code'];
		$header_email = 'From ASX Simulator.com.au'; 
		mail($email,"Code Confirmation",$msg, $header_email);
		header('Refresh: 1; confirm_code_for_reset_password.php');


	}
	else
	{
		header('Refresh: 2; confirm_code_for_reset_password.php');
	}

}



?>
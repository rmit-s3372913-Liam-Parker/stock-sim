<?php

if(isset($_POST['submit']))
{
	
	$conn = mysqli_connect('localhost', 'root', '', 'testing');
	$uname = mysqli_escape_string($conn, $_POST['username']);
	$password = mysqli_escape_string($conn, $_POST['password']);
	$email = mysqli_escape_string($conn, $_POST['email']);
	$check_username = '';
	$check_email = '';

	$query_uname = mysqli_query($conn, "Select * FROM test WHERE username='".$uname."' ");
	$numrow_uname = mysqli_num_rows($query_uname);

	$query_email = mysqli_query($conn, "Select * FROM test WHERE email='".$email."' ");
	$numrow_email = mysqli_num_rows($query_email);

	if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	}

	
	
		if ($numrow_uname == 0 && $numrow_email == 0) 
		{
			$encrypt_password = md5($password);

			$sql = "INSERT INTO test(username, password, email) VALUES('$uname', '$encrypt_password', '$email')";

			if ($conn->query($sql) === TRUE) 
			{
			    echo "Account has been created successfully";
			    /* insert here call function generate 5 random numbers*/
			    $length = 0;
				function randomWithLength($length){
				$length = 5;
				    $number = '';
				    for ($i = 0; $i < $length; $i++){
				        $number .= rand(0,9);
				    }

				    return (int)$number;

				}
			    $msg = "Your confirmation code is: ".randomWithLength($length);
			    $header_email = 'From ASX Simulator.com.au'; 
			    mail($email,"Code Confirmation",$msg, $header_email);
			    header('Refresh: 3; URL=http://crash.net');
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

	

?>
<?php

if(isset($_POST['login']))
{
	
  $conn = mysqli_connect('capstonedatabase.cszu3gvo32mp.ap-southeast-2.rds.amazonaws.com',
                         'admin', 'password', 'CapstoneDatabase');
	$uname = mysqli_escape_string($conn, $_POST['username']);
	$password = mysqli_escape_string($conn, $_POST['password']);
	

	$query = mysqli_query($conn, "Select * FROM player WHERE username='".$uname."' AND password='".$password."' ");
	$numrows = mysqli_num_rows($query);

	

	if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	}

	

    if ($numrows==1)
    {
    	
        session_start();
        $_SESSION['username'] = $_POST['uname'];
        $_SESSION['password'] = $_POST['password'];
        header("location: registration.php");
        }
    else
    {
        echo "Invalid username or password";
        }   

    mysqli_close($conn);
	
	
	
}

?>
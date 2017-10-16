<?php
 session_start();
 include '../config/connection.php';

if(isset($_POST['login']))
{
	
	// set variables
	$uname = mysqli_escape_string($conn, $_POST['username']);
	$password = mysqli_escape_string($conn, $_POST['password']);
	$password = md5($password);

	// select all fields from player with the corresponding username and email
	$query = mysqli_query($conn, "Select * FROM player WHERE username='".$uname."' AND password='".$password."'");
	$result = mysqli_num_rows($query);

	$user = mysqli_fetch_array($query);

	// query for unconfirmed player
	$validate_query = mysqli_query($conn, "Select * FROM player WHERE username='".$uname."' AND confirm='no'");
	$validate_result = mysqli_num_rows($validate_query);
	
	$sql = mysqli_query($conn, "select userId from player where username = '".$uname."' ");
	//$result = mysqli_num_rows($sql);
	$row = mysqli_fetch_array($sql);
	
	/* check for user */
	if($user)
	{
		if(!empty($_POST['remember']))
		{
			// set time for user to be login after closing browser
			setcookie("member_user", $uname, time() + 300);
			setcookie("member_password", $password, time() + 300);
		}
	}
	else
	{
		if(isset($_COOKIE["member_user"]))   
	    {  
	     setcookie ("member_user","");  
	    }  
	    if(isset($_COOKIE["member_password"]))   
	    {  
	     setcookie ("member_password","");  
	    } 
	}
	header("Location: ../index.php");

	// check row with username and unconfirmed player
	if ($validate_result != 1)
	{

		// check for correct username and password
		// redirect to dashboard for valid username and password
		if ($result == 1) {
			$_SESSION['username'] = $uname;
			$_SESSION['userId'] = $row['userId'];
			$_SESSION['success'] = "You are now logged in";
			header('location: ../dashboard/dashboard.php');
		}
		// redirect user to login page for wrong username or password
		else
		{
			$_SESSION['error_login'] = "Wrong username/password combination";
			header("Location: ../index.php");
		}    
	}
	// unconfirmed player must activate account using email
	// display error message
	// redirect to login page
	else
	{
			$_SESSION['error_login'] = "Please activate your account with your email";
			header("Location: ../index.php");
	}

	// close connection
    mysqli_close($conn);
	
	
}

?>
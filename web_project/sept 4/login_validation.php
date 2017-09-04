<?php
 session_start();
 include 'connection.php';

if(isset($_POST['login']))
{
	
	
	$uname = mysqli_escape_string($conn, $_POST['username']);
	$password = mysqli_escape_string($conn, $_POST['password']);
	$password = md5($password);

	$query = mysqli_query($conn, "Select * FROM test WHERE username='".$uname."' AND password='".$password."'");
	/* database query */
	$result = mysqli_num_rows($query);

	/* cookie session */
	$user = mysqli_fetch_array($query);

	/* check for user input */
	if($user)
	{
		if(!empty($_POST['remember']))
		{
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
	header("Location: login.php");

	/* check database */
	if ($result == 1) {
		$_SESSION['username'] = $uname;
		$_SESSION['success'] = "You are now logged in";
		header('location: dashboard.php');
	}
	else
	{
		$_SESSION['error_login'] = "Wrong username/password combination";
		header("Location: login.php");
	}    


    mysqli_close($conn);
	
	
	
}

?>
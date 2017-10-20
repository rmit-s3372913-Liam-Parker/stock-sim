<?php
	// connection
	$server = "capstonedatabase.cszu3gvo32mp.ap-southeast-2.rds.amazonaws.com";
	$username = "admin";
	$password = "password";
	$database = "CapstoneDatabase";
	$conn = mysqli_connect($server, $username, $password, $database);
	
	// message
	if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	}
?>
<?php
	// connection
	$conn = mysqli_connect('localhost', 'root', '', 'testing');
	
	// message
	if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	}

?>
<?php
include 'connection.php';

if(isset($_POST['login']))
{
$uname = $_GET['username'];
$winning = $_GET['winning'];

$uname = mysqli_escape_string($conn, $_POST['username']);
$query = mysqli_query($conn, "Select winning FROM player WHERE username='".$uname."' ");


	while ($row = mysqli_fetch_array($query))
	{
		echo $row['winning'];
	}
}

?>
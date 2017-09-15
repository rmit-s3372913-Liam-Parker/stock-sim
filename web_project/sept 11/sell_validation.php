<?php
session_start();
include 'connection.php';

if(isset($_POST['sell-submit']))
{

	$total = mysqli_escape_string($conn, $_POST['sell-total']);
	
	$sql = "select winning FROM player WHERE username = '" . $_SESSION['username'] . "'";
	$result = mysqli_query($conn, $sql);
	$row = mysqli_fetch_assoc($result);
	$currentMoney = $row['winning'];
	
	
	// set database winning
	$result = $currentMoney + $total;
	$sql = "update player set winning='".$result."' WHERE username = '" . $_SESSION['username'] . "'";
			
	if($conn->query($sql) == TRUE)
		{
			header('location: dashboard.php');
		}
			
	

}

?>


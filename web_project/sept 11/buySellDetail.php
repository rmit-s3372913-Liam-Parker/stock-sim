<?php
//session_start();
include 'connection.php';


$sql = "select transactionID FROM transaction WHERE username = '" . $_SESSION['username'] . "' ORDER BY transactionID DESC LIMIT 1";
$result = mysqli_query($conn, $sql);
$row = mysqli_fetch_assoc($result);
$currentID = $row['transactionID'];
		
//print_r($currentID);
if($currentID != 0)
{          

	$sql = "INSERT INTO buySellDetail(transactionID, stockId, quantity, price) 
	VALUES('$currentID', '".$_SESSION['code']."', '".$_SESSION['share']."', '".$_SESSION['price']."')";
	//print_r($sql);

	if($conn->query($sql) == TRUE)
			{
				header('location: dashboard.php');
			}
}

?>
<?php
include '../includes/connection.php';

// select transactionID field from transaction table
$sql = "select transactionID FROM transaction WHERE username = '" . $_SESSION['username'] . "' ORDER BY transactionID DESC LIMIT 1";
$result = mysqli_query($conn, $sql);
$row = mysqli_fetch_assoc($result);
$currentID = $row['transactionID'];
		
// check if transactionID exists
if($currentID != 0)
{          
	// insert query in buySellDetail
	$sql = "INSERT INTO buySellDetail(transactionID, stockId, quantity, price) 
	VALUES('$currentID', '".$_SESSION['code']."', '".$_SESSION['share']."', '".$_SESSION['price']."')";
	
	// redirect page to dashboard if sql is true
	if($conn->query($sql) == TRUE)
			{
				header('location: dashboard.php');
			}
}

?>
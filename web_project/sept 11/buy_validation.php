<?php
session_start();
include 'connection.php';

if(isset($_POST['buy-submit']))
{

	$total = mysqli_escape_string($conn, $_POST['buy-total']);
	$code = mysqli_escape_string($conn, $_POST['code']);
	$share = mysqli_escape_string($conn, $_POST['share']);
	$date = date('Y-m-d H:i:s');
	
	// select winning field from player table
	$sql = "select winning FROM player WHERE username = '" . $_SESSION['username'] . "'";
	$result = mysqli_query($conn, $sql);
	$row = mysqli_fetch_assoc($result);
	$currentMoney = $row['winning'];

	// select stockID from stock table
	$sql = "select stockID FROM stock WHERE username = '" . $_SESSION['username'] . "' AND stockID = '" . $code . "'";
	$result = mysqli_query($conn, $sql);
	$row = mysqli_fetch_assoc($result);
	$currentStockID = $row['stockID'];
	
	// select stock quantity from stock table
	$sql = "select stockQuantity FROM stock WHERE username = '" . $_SESSION['username'] . "' AND stockID = '" . $code . "'";
	$result = mysqli_query($conn, $sql);
	$row = mysqli_fetch_assoc($result);
	$currentStockQuantity = $row['stockQuantity'];

	// $sql = "Select transactionID from transaction WHERE username = '" . $_SESSION['username'] . "' order by transactionID DESC LIMIT 1";
	// $result = mysqli_query($conn, $sql);
	// $row = mysqli_fetch_assoc($result);
	// $lastID = $row['transactionID'];
	
	
	// check if current money is 0
	if($currentMoney != 0)
	{
		// check if purchase amount is greater than money on hand
		if($currentMoney >= $total)
		{
			// check if stock exists
			if($currentStockID == $code)
			{
				// update new quantity for existing stock
				$newQuantity = $currentStockQuantity + $share;
				$sql = "update stock set stockQuantity = '".$newQuantity."' WHERE username = '" . $_SESSION['username'] . "' 
						AND stockID = '" . $code . "';";

			}
			else
			{
				// insert new stock
				$uname = $_SESSION['username'];
				$sql = "INSERT INTO stock(stockID, username, stockQuantity) VALUES('$code', '$uname', '$share');";
			}
				
				// set database winning
				$result = $currentMoney - $total;
				$sql .= "update player set winning='".$result."' WHERE username = '" . $_SESSION['username'] . "';";

				// insert data for transaction table
				$uname = $_SESSION['username'];
				$sql .= "INSERT INTO transaction(username, transactionType, postWinning, timeOfTransaction) VALUES('$uname', 'Buy', '$result', '$date')";

				// get the last transactionID from transaction table
				//$sql = "Select transactionID from transaction order by transactionID DESC LIMIT 1";

				// set database table for buySellDetail
				// $sql .= "INSERT INTO buySellDetail(transactionID, stockId, quantity, price) VALUES('$lastID', '$code', '$share', '$total');";
				
				if (mysqli_multi_query($conn, $sql))
				{
					echo 'Query executed';
					header('location: dashboard.php');
				}
			// if($conn->query($sql) == TRUE)
			// {
			// 	header('location: dashboard.php');
			// }
			
		}
		else
		{
			$_SESSION['error_buy'] = "Please check your credits.";
			header('location: stock_list.php');
		}
	}
	else
	{
		$_SESSION['error_buy'] = "Please check your credits.";
		header('location: stock_list.php');

	}

	mysqli_close($conn);



}

?>


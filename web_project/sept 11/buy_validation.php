<?php
session_start();
include 'connection.php';

if(isset($_POST['buy-submit']))
{

	$total = mysqli_escape_string($conn, $_POST['buy-total']);
	$code = mysqli_escape_string($conn, $_POST['code']);
	$share = mysqli_escape_string($conn, $_POST['share']);
	$date = date('Y-m-d H:i:s');
	
	$sql = "select winning FROM player WHERE username = '" . $_SESSION['username'] . "'";
	$result = mysqli_query($conn, $sql);
	$row = mysqli_fetch_assoc($result);
	$currentMoney = $row['winning'];

	// check stock table
	$sql = "select stockID FROM stock WHERE username = '" . $_SESSION['username'] . "'";
	$result = mysqli_query($conn, $sql);
	$row = mysqli_fetch_assoc($result);
	$currentStockID = $row['stockID'];
	
	$sql = "select stockQuantity FROM stock WHERE username = '" . $_SESSION['username'] . "'";
	$result = mysqli_query($conn, $sql);
	$row = mysqli_fetch_assoc($result);
	$currentStockQuantity = $row['stockQuantity'];
	
	
	// check if current money is 0
	if($currentMoney != 0)
	{
		// check if purchase amount is greater than money on hand
		if($currentMoney >= $total)
		{
			// check if stock exists
			if($currentStockID == $code)
			{
				$newQuantity = $currentStockQuantity + $share;
				$sql = "update stock set stockQuantity='".$newQuantity."' WHERE username = '" . $_SESSION['username'] . "';";

			}
			else
			{
				$uname = $_SESSION['username'];
				$sql = "INSERT INTO stock(stockID, username, stockQuantity) VALUES('$code', '$uname', '$share');";
			}
				
				// set database winning
				$result = $currentMoney - $total;
				$sql .= "update player set winning='".$result."' WHERE username = '" . $_SESSION['username'] . "';";

				// set database table for buySellDetail
				//$sql .= "INSERT INTO buySellDetail(stockId, quantity, price) VALUES('$code', '$share', '$total');";

				// set database for transaction table
				$uname = $_SESSION['username'];
				$sql .= "INSERT INTO transaction(username, transactionType, postWinning, timeOfTransaction) VALUES('$uname', 'Buy', '$result', '$date')";
				
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


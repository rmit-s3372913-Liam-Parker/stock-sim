<?php
session_start();
include 'connection.php';

if(isset($_POST['buy-submit']))
{
	// set variables
	$total = mysqli_escape_string($conn, $_POST['buy-total']);
	$code = mysqli_escape_string($conn, $_POST['code']);
	$share = mysqli_escape_string($conn, $_POST['share']);
	$price = mysqli_escape_string($conn, $_POST['price']);
	$date = date('Y-m-d H:i:s');
	
	// select winning field from player table
	$sql = "select winning FROM player WHERE username = '" . $_SESSION['username'] . "'";
	$result = mysqli_query($conn, $sql);
	$row = mysqli_fetch_assoc($result);
	$currentMoney = $row['winning'];

	// select stockID field from stock table
	$sql = "select stockID FROM stock WHERE username = '" . $_SESSION['username'] . "' AND stockID = '" . $code . "'";
	$result = mysqli_query($conn, $sql);
	$row = mysqli_fetch_assoc($result);
	$currentStockID = $row['stockID'];
	
	// select stock quantity field from stock table
	$sql = "select stockQuantity FROM stock WHERE username = '" . $_SESSION['username'] . "' AND stockID = '" . $code . "'";
	$result = mysqli_query($conn, $sql);
	$row = mysqli_fetch_assoc($result);
	$currentStockQuantity = $row['stockQuantity'];

	
		// check if purchase amount is greater than or equal to the money on hand
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
				$sql .= "INSERT INTO transaction(username, transactionType, postWinning, timeOfTransaction) VALUES('$uname', 'buy', '$result', '$date');";

				// set session variables to be used in buySellDetail.php
				$_SESSION['code'] = $code;
				$_SESSION['share'] = $share;
				$_SESSION['price'] = $price;
				
				// check multiple query with connection
				if (mysqli_multi_query($conn, $sql))
				{
					// include buySellDetail with insert query
					include 'buySellDetail.php';

				}

		}
		else
		{
			// set session for display error message
			$_SESSION['error_buy'] = "Please check your credits.";
			header('location: stock_list.php');
		}
	
	// close connection
	mysqli_close($conn);

}

?>
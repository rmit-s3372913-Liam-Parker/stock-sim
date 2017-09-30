<?php
session_start();
include 'connection.php';

if(isset($_POST['sell-submit']))
{
	// set variables from form with POST
	$total = mysqli_escape_string($conn, $_POST['total']);
	$code = mysqli_escape_string($conn, $_POST['code']);
	$price = mysqli_escape_string($conn, $_POST['price']);
	$share = mysqli_escape_string($conn, $_POST['share']);
	// get current date
	$date = date('Y-m-d H:i:s');

	// query winning field form player table using a given username
	$sql = "select winning FROM player WHERE username = '" . $_SESSION['username'] . "'";
	$result = mysqli_query($conn, $sql);
	$row = mysqli_fetch_assoc($result);
	$currentMoney = $row['winning'];

	// query quantity stock using a given username and stockID
	$sql = "SELECT stockQuantity FROM stock WHERE username = '" . $_SESSION['username'] . "' AND stockID = '" . $code . "' ";
	$result = mysqli_query($conn, $sql);
	$row = mysqli_fetch_assoc($result);
	$currentShare = $row['stockQuantity'];
	
	// check if players stock share is greater than or equal to
	// the current share he/she is holding
	if($currentShare >= $share)
	{
		// set a new value for share and update stock table
		$newShare = $currentShare - $share;
		$sql = "update stock set stockQuantity='".$newShare."' WHERE username = '" . $_SESSION['username'] . "' AND stockID = '" . $code . "' ;";

		// set new balance(winning) for player
		$result = $currentMoney + $total;
		$sql .= "update player set winning='".$result."' WHERE username = '" . $_SESSION['username'] . "';";

		// if a stock has 0 quantity delete it from the table
		$sql .= "DELETE from stock where stockQuantity = '0';";

		// set session for username
		$uname = $_SESSION['username'];
		// insert values in transaction table
		$sql .= "INSERT INTO transaction(username, transactionType, postWinning, timeOfTransaction) VALUES('$uname', 'sell', '$result', '$date');";

		// set session variables
		$_SESSION['code'] = $code;
		$_SESSION['share'] = $share;
		$_SESSION['price'] = $price;

		if (mysqli_multi_query($conn, $sql))
				{
					// set session variable
					// include buySellDetail.php for added queries
					// redirect to dashboard
					$_SESSION['stock_message'] = "Success";
					include 'buySellDetail.php';
					header('location: dashboard.php');
				}

	}
	else
	{
		$_SESSION['error_stock'] = "Insufficient Stock shares.";
	}

	// if sql is true redirect to dashboard
	if($conn->query($sql) == TRUE)
		{
			header('location: dashboard.php');
		}
			
}

?>
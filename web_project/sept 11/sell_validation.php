<?php
session_start();
include 'connection.php';

if(isset($_POST['sell-submit']))
{

	$total = mysqli_escape_string($conn, $_POST['total']);
	$code = mysqli_escape_string($conn, $_POST['code']);
	$price = mysqli_escape_string($conn, $_POST['price']);
	$share = mysqli_escape_string($conn, $_POST['share']);
	$date = date('Y-m-d H:i:s');

	$sql = "select winning FROM player WHERE username = '" . $_SESSION['username'] . "'";
	$result = mysqli_query($conn, $sql);
	$row = mysqli_fetch_assoc($result);
	$currentMoney = $row['winning'];


	$sql = "SELECT stockQuantity FROM stock WHERE username = '" . $_SESSION['username'] . "' AND stockID = '" . $code . "' ";
	$result = mysqli_query($conn, $sql);
	$row = mysqli_fetch_assoc($result);
	$currentShare = $row['stockQuantity'];
	// set database winning
	
	if($currentShare >= $share)
	{
		$newShare = $currentShare - $share;
		$sql = "update stock set stockQuantity='".$newShare."' WHERE username = '" . $_SESSION['username'] . "' AND stockID = '" . $code . "' ;";


		$result = $currentMoney + $total;
		$sql .= "update player set winning='".$result."' WHERE username = '" . $_SESSION['username'] . "';";

		$sql .= "DELETE from stock where stockQuantity = '0';";

		$uname = $_SESSION['username'];
		$sql .= "INSERT INTO transaction(username, transactionType, postWinning, timeOfTransaction) VALUES('$uname', 'sell', '$result', '$date');";

		$_SESSION['code'] = $code;
		$_SESSION['share'] = $share;
		$_SESSION['price'] = $price;

		if (mysqli_multi_query($conn, $sql))
				{
					//echo 'Query executed';
					$_SESSION['stock_message'] = "Success";
					include 'buySellDetail.php';
					header('location: dashboard.php');


				}

	}
	else
	{
		$_SESSION['error_stock'] = "Insufficient Stock shares.";
	}

	
			

	

	if($conn->query($sql) == TRUE)
		{
			header('location: dashboard.php');
		}
			
	

}

?>


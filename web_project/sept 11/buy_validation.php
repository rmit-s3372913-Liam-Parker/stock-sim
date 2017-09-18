<?php
session_start();
include 'connection.php';

if(isset($_POST['buy-submit']))
{

	$total = mysqli_escape_string($conn, $_POST['buy-total']);
	
	$sql = "select winning FROM player WHERE username = '" . $_SESSION['username'] . "'";
	$result = mysqli_query($conn, $sql);
	$row = mysqli_fetch_assoc($result);
	$currentMoney = $row['winning'];
	
	//$result = $currentMoney - $total;
	//echo $row['winning'];
	//echo "<br><br>";
	//echo $total;
	//echo "<br><br>";
	//echo $result;
	
	// check if current money is 0
	if($currentMoney != 0)
	{
		// check if purchase amount is greater than money on hand
		if($currentMoney > $total)
		{
			// set database winning
			$result = $currentMoney - $total;
			$sql = "update player set winning='".$result."' WHERE username = '" . $_SESSION['username'] . "'";
			
			if($conn->query($sql) == TRUE)
			{
				header('location: dashboard.php');
			}
			
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

	


}

?>


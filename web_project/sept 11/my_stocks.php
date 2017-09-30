<?php

include 'connection.php';
include_once("callApi.php");

// query to select all fields from stock table using
// a session variable for username
// display only 5 stocks
$sql = "select * FROM stock WHERE username = '" . $_SESSION['username'] . "' LIMIT 5";
$result = mysqli_query($conn, $sql);

// display a players stock  per row
// call API for prices per stocks
$row_count = 1;
while ($stock = mysqli_fetch_assoc($result)) {
	echo "<tr>";

	echo "<td>". $row_count ."</td>";
	
	echo "<td>".$stock['stockID']."</td>";

	echo "<td>" . $stock['stockQuantity'] . "</td>";

	$data=CallAPI($stock['stockID']);

    echo "<td>" . $data["last_price"] . "</td>";

	echo "</tr>";

	$row_count++;
}

?>
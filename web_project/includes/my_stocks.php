<?php

include '../config/connection.php';
include_once("../config/callApi.php");

// query to select all fields from stock table using
// a session variable for username
// display only 5 stocks
$sql = "select * FROM stock WHERE username = '" . $_SESSION['username'] . "' ";
$result = mysqli_query($conn, $sql);

// display a players stock  per row
// call API for prices per stocks
$row_count = 1;
while ($stock = mysqli_fetch_assoc($result)) {
	echo "<tr>";

	echo "<td>". $row_count ."</td>";
	
	echo "<td>".$stock['stockID']."</td>";

	echo "<td class='quantity'>" . $stock['stockQuantity'] . "</td>";

	$data=CallAPI($stock['stockID']);

    echo "<td class='price'>" . $data["last_price"] . "</td>";

	echo "</tr>";

	$row_count++;
}

?>
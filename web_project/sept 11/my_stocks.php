<?php

include 'connection.php';

$sql = "select * FROM stock WHERE username = '" . $_SESSION['username'] . "' LIMIT 5";
$result = mysqli_query($conn, $sql);
//$row = mysqli_fetch_assoc($result);

while ($stock = mysqli_fetch_assoc($result)) {
	echo "<tr>";

	echo "<td>".$stock['stockID']."</td>";

	echo "<td>" . $stock['stockQuantity'] . "</td>";

	echo "</tr>";
}


?>
<?php
include 'connection.php';

// set all fields from player table and displaying 
// according to winning field in descending order
// display only 5 rows
$sql = "select * from player order by winning desc LIMIT 5";

$records = mysqli_query($conn, $sql);

// display username and winning fielf per row
while ($user = mysqli_fetch_assoc($records)) {
	echo "<tr>";

	echo "<td>".$user['username']."</td>";

	echo "<td>". "$" . number_format($user['winning'], 2)."</td>";

	echo "</tr>";
}

?>
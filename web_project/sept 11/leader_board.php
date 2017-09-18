<?php
include 'connection.php';
$sql = "select * from player order by winning desc LIMIT 5";

$records = mysqli_query($conn, $sql);
?>


<!-- <table width="300px" border="1">
	<tr>
		<th>username</th>
		<th>winning</th>
	</tr> -->

<?php
while ($user = mysqli_fetch_assoc($records)) {
	echo "<tr>";

	echo "<td>".$user['username']."</td>";

	echo "<td>". "$" . number_format($user['winning'], 2)."</td>";

	

	echo "</tr>";
}

?>
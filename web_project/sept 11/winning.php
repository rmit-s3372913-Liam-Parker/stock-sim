<?php
include 'connection.php';

$sql = "select winning FROM player WHERE username = '" . $_SESSION['username'] . "'";
$result = mysqli_query($conn, $sql);
$row = mysqli_fetch_assoc($result);
echo "$".$row['winning'];
?>



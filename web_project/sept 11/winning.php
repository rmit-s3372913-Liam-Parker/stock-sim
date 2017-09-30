<?php
// include connection file
include 'connection.php';

// query to select winning field from player table
// using a given username
$sql = "select winning FROM player WHERE username = '" . $_SESSION['username'] . "'";
$result = mysqli_query($conn, $sql);
$row = mysqli_fetch_assoc($result);
echo "$".number_format($row['winning'], 2);
?>



<?php
session_start();
include '../config/connection.php';

$userAdded = $_POST['row'];
$uname = $_SESSION['username'];
$sql = "INSERT INTO friend(username, friendUsername, confirm) VALUES('$uname', '$userAdded', 'no')";
$record = mysqli_query($conn, $sql);

echo "Friend request sent";
header('refresh: 2; add_friends.php');
mysqli_close($conn);

?>
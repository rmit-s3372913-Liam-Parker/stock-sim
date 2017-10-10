<?php
session_start();
include '../config/connection.php';

$userAdded = $_POST['row'];
$uname = $_SESSION['username'];

$sql = "select userId FROM player WHERE username = '" . $_SESSION['username'] . "'";
$result = mysqli_query($conn, $sql);
$row = mysqli_fetch_assoc($result);
$currentUser = $row['userId'];

$sql = "select userId FROM player WHERE username = '" . $userAdded . "'";
$result = mysqli_query($conn, $sql);
$row = mysqli_fetch_assoc($result);
$addedUser = $row['userId'];



$sql = "INSERT INTO friend(userId, username, friendUserId, friendUsername, confirm) VALUES('$currentUser', '$uname', '$addedUser', '$userAdded', 'no')";
$record = mysqli_query($conn, $sql);

echo "Friend request sent";
header('refresh: 2; add_friends.php');
mysqli_close($conn);

?>
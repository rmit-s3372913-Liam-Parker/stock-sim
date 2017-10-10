<?php
session_start();
include '../config/connection.php';

$acceptUser = $_POST['row'];
$uname = $_SESSION['username'];
//print_r($acceptUser);
// query for friend requesting a friend request
$sql = "select userId FROM player WHERE username = '" . $_SESSION['username'] . "'";
$result = mysqli_query($conn, $sql);
$row = mysqli_fetch_assoc($result);
$currentUser = $row['userId'];

// $sql = "select friendUserId FROM friend WHERE username = '" . $acceptUser . "' ";
// $result = mysqli_query($conn, $sql);
// $row = mysqli_fetch_assoc($result);
// $addedUser = $row['friendUserId'];
//print_r($addedUser);

$sql = "update friend set confirm = 'YES' WHERE userId = '" . $currentUser . "' AND friendUsername = '".$acceptUser."' ";


echo "Friend request accepted!";
//header('refresh: 2; add_friends.php');
mysqli_close($conn);

?>
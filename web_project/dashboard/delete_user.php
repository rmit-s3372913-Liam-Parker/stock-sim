<?php
session_start();
include '../config/connection.php';

$deleteUser = $_POST['row'];
//$uname = $_SESSION['username'];

// query for friend requesting a friend request
$sql = "select userId FROM player WHERE username = '" . $deleteUser . "'";
$result = mysqli_query($conn, $sql);
$row = mysqli_fetch_assoc($result);
$currentUser = $row['userId'];
print_r($currentUser);

$sql = "select userId FROM friend WHERE username = '" . $deleteUser . "'";
$result = mysqli_query($conn, $sql);
$row = mysqli_fetch_assoc($result);
$friendTableId = $row['userId'];
print_r($friendTableId);

$sql = "select senderUserId FROM message WHERE senderUsername = '" . $deleteUser . "'";
$result = mysqli_query($conn, $sql);
$row = mysqli_fetch_assoc($result);
$messageTableId = $row['userId'];
print_r($messageTableId);
// query for getting userID of friend to be added
// $sql = "select userId FROM player WHERE username = '" . $userAdded . "'";
// $result = mysqli_query($conn, $sql);
// $row = mysqli_fetch_assoc($result);
// $addedUser = $row['userId'];

if($messageTableId != '')
{
	$sql = "delete * from message where senderUserID = '".$messageTableId."' ";
}

if($friendTableId != '')
{
	$sql = "delete * from friend where userID = '".$friendTableId."' ";
}

$sql = "delete * from player where userID = '".$currentUser."' ";
// if (mysqli_multi_query($conn, $sql))
// 				{
// 					// include buySellDetail with insert query
// 					echo "User Deleted!";
// 					header('refresh: 2; user_list.php');
// 					mysqli_close($conn);

// 				}

$record = mysqli_query($conn, $sql);

echo "User Deleted!";
header('refresh: 2; user_list.php');
mysqli_close($conn);

?>
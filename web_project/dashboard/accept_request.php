<?php
session_start();
include '../config/connection.php';

// set variables
$acceptUser = $_POST['row'];
$uname = $_SESSION['username'];
//print_r($acceptUser);

// query for getting the userId of a friend who sent a request
$sql = "select userId FROM friend WHERE username = '".$acceptUser."' AND friendUsername =  '" . $_SESSION['username'] . "' ";
$result = mysqli_query($conn, $sql);
$row = mysqli_fetch_assoc($result);
$requestorId = $row['userId'];
//print_r($currentUser);


// query
$sql = "select userId FROM friend WHERE username = '" . $_SESSION['username'] . "' AND friendUsername = '".$acceptUser."'  ";
$result = mysqli_query($conn, $sql);
$row = mysqli_fetch_assoc($result);
$currentUserId = $row['userId'];


// query for updating the confirm column to yes for accepting a friend request
$sql = "update friend set confirm = 'yes' WHERE userId = '" . $requestorId . "' AND friendUsername = '".$uname."' ;";


$sql .= "update friend set confirm = 'yes' WHERE userId = '" . $currentUserId . "' AND friendUsername = '".$acceptUser."' ;";

//$sql = "update friend set confirm = 'yes' WHERE friendUsername = '" . $_SESSION['username'] . "' and username = '".$acceptUser."' ;";
//$record = mysqli_query($conn, $sql);
//if (mysqli_multi_query($conn, $sql))
if(mysqli_multi_query($conn, $sql))				{
					echo "Friend request accepted!";
					header('refresh: 2; notification.php');

				}


mysqli_close($conn);

?>
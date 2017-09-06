<?php
session_start();
include 'connection.php';

$email = $_GET['email'];
// $hash from registration_validation.php
$code = $_GET['code'];

$query = "select * from player1 where email='$email' ";
$result = mysqli_query($conn, $query);



while ($row = mysqli_fetch_assoc($result))
{
	$dbcode = $row['email_code'];

}

if ($dbcode == $code)
{
	$a_query = "update player1 set active='1'";
	$a_result = mysqli_query($conn, $a_query);
	
	$b_query = "update player1 set email_code='0'";
	$b_result = mysqli_query($conn, $b_query);
	echo "Your account has been activated.";
	header("Location: dashboard.php");


}
else
{
	echo "Activation error!";
	
}

?>
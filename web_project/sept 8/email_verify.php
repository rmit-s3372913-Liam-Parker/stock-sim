<?php
session_start();
include 'connection.php';

$email = $_GET['email'];
// $hash from registration_validation.php
$code = $_GET['code'];

$query = "select * from player where email='$email' ";
$result = mysqli_query($conn, $query);



while ($row = mysqli_fetch_assoc($result))
{
	$dbcode = $row['emailConfirmation'];

}

if ($dbcode == $code)
{
	$a_query = "update player set confirm='1'";
	$a_result = mysqli_query($conn, $a_query);
	
	$b_query = "update player set emailConfirmation='0'";
	$b_result = mysqli_query($conn, $b_query);
	echo "Your account has been activated.";
	header("Location: dashboard.php");


}
else
{
	echo "Activation error!";
	
}

?>
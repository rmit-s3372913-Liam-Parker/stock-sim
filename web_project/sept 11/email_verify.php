<?php
session_start();
include 'connection.php';

// set variables
$email = $_GET['email'];
// $hash from registration_validation.php
$code = $_GET['code'];
// select all fields from player table with a corresponding email
$query = "select * from player where email='$email' ";
$result = mysqli_query($conn, $query);


// set email confirmation code
while ($row = mysqli_fetch_assoc($result))
{
	$dbcode = $row['emailConfirmation'];

}

// check if code received matches the code in database
if ($dbcode == $code)
{
	// update confirm field from player table
	$a_query = "update player set confirm='yes'";
	$a_result = mysqli_query($conn, $a_query);
	
	// update emailConfirmation field from player table
	$b_query = "update player set emailConfirmation='0'";
	$b_result = mysqli_query($conn, $b_query);
	echo "Your account has been activated.";
	header("Location: dashboard.php");


}
else
{
	// display an error message if player havent activated his account through email
	echo "Activation error!";
	
}

?>
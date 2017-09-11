


$('#register').on('click', function (e) {
	global $conn;
     //your awesome code here
     $sql = "INSERT INTO test (username, password, email)
	VALUES ('$_POST['username']', '$_POST['password']', '$_POST['email']')";

})
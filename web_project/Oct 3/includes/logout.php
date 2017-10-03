<?php
// stop user session after logout
session_start();
unset($_SESSION['username']);
session_destroy();
header("Location: login.php");


?>
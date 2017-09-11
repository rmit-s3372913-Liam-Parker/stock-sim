<?php

if(!isset($_SESSION['username']))
{
	header("Location: login.php");
}
else
{
?>
<!DOCTYPE html>
<html>
<head>
	<title>ASX Simulator</title>
	<link rel="stylesheet" type="text/css" href="bootstrap-3.3.6-dist/css/bootstrap.css">
  	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>

<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">ASX</a>
    </div>
    <ul class="nav navbar-nav">
      <li class="active"><a href="#">Home</a></li>
      <li><a href="#">Page 1</a></li>
      <li><a href="#"><?= $_SESSION['username']; ?></a></li>
      <li><a href="logout.php">Logout</a></li>
    </ul>
  </div>
</nav>

</body>
</html>
<?php
}
?>
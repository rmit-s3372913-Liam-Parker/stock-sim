<?php
// use session variable to display a players username
// in the navigation bar
if(!isset($_SESSION['username']))
{
	header("Location: ../index.php");
}
else
{
?>
<!DOCTYPE html>
<html>
<head>
	<title>ASX Simulator</title>
	<!-- <link rel="stylesheet" type="text/css" href="bootstrap-3.3.6-dist/css/bootstrap.css">
  	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"> -->
</head>
<body>

<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">ASX</a>
    </div>
    <ul class="nav navbar-nav">
      <li class="active"><a href="../dashboard/dashboard.php">Home</a></li>
      <li><a href="../dashboard/stock_list.php">Stock</a></li>
      
    </ul>
    <ul class="nav navbar-nav navbar-right">
      <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#"><?php echo $_SESSION['username']; ?> <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><?php include 'winning.php'; ?></li>
          <li><a href="../dashboard/add_friends.php">Friends</a></li>
          <li><a href="../dashboard/send_message.php">Messages</a></li>
        </ul>
      </li>
      <li><a href="../includes/logout.php">Logout</a></li>
    </ul>
  </div>
</nav>

</body>
</html>
<?php
}
?>
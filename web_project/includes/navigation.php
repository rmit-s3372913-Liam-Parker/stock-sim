<?php
// use session variable to display a players username
// in the navigation bar
if(!isset($_SESSION['username']))
{
	header("Location: ../index.php");
}
?>
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
          <li><a href="../dashboard/my_friends.php">My Friends</a></li>
          <li><a href="../dashboard/add_friends.php">Add Friends</a></li>
          <li><a href="../dashboard/notification.php">Friend Requests</a></li>
          <li><a href="../dashboard/my_message.php">Messages</a></li>
          <li><a href="../dashboard/send_message.php">Send Message</a></li>
          <?php
          if($_SESSION['username'] == 'admin')
          {
          echo "<li><a href='../dashboard/transaction.php'>Date</a></li>";
          echo "<li><a href='../dashboard/user_list.php'>Users</a></li>";
          }
          ?>
        </ul>
      </li>
      <li><a href="../includes/logout.php">Logout</a></li>
    </ul>
  </div>
</nav>
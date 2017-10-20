<?php
// start a session
// include connection
session_start();
include '../config/connection.php';
// select all from player table in database
$sql = "select * from player WHERE username != '".$_SESSION['username']."' And userId NOT IN (select friendUserId FROM player NATURAL JOIN friend WHERE username = '". $_SESSION['username'] ."')";

$records = mysqli_query($conn, $sql);

?>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<?php include("../includes/navigation.php");?>
<div class="container" style="width: 600px;">
  <h3 align="center">Add Friends</h3> <br>

	<table class="table table-bordered">
      <?php
      $i = 1;
      while($row = mysqli_fetch_array($records))
      	{ 
      	?>
        <tr>
          <td><?php echo $i; ?></td>
          <td><?php echo $row["username"]; $_SESSION['addedUser'] = $row["username"];?></td>
          <td>
           <form action="friend_request.php" method="post">  
            <button type="submit" name="row" id="row_user" value="<?php echo $row["username"]; ?>" class="btn btn-info">ADD</button>
           </form>
            <?php $i++; ?>
          </td>
        </tr>
		<?php 
		 }
		 ?>
	</table>

</div>



 
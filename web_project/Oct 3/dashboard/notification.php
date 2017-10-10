<?php
session_start();
include '../config/connection.php';

$sql = "select * from friend WHERE username = '" . $_SESSION['username'] . "' AND confirm = 'no' ";

$records = mysqli_query($conn, $sql);

?>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<?php include("../includes/navigation.php");?>
<div class="container" style="width: 500px;">
  <h3 align="center">Friend Requests</h3> <br>
  <table class="table table-bordered">
      <?php
      $i = 1;
      while($row = mysqli_fetch_array($records))
        { 
        ?>
        <tr>
          <td><?php echo $i; ?></td>
          <td><?php echo $row["friendUsername"]; ?></td>
          <td>
           <form action="accept_request.php" method="post">  
            <button type="submit" name="row" id="row_user" value="<?php echo $row["friendUsername"]; ?>" class="btn btn-info">ACCEPT</button>
           </form>
            <?php $i++; ?>
          </td>
        </tr>
    <?php 
     }
     ?>
  </table>


</div>

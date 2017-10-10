<?php
include '../config/connection.php';
ini_set('display_errors', 1);
error_reporting(E_ALL);
//session_start();
//join query for transaction, buySellDetail & sendReceiveDetial tables
$results_per_page = '20';
if (isset($_GET["page"])) { $page  = $_GET["page"]; } else { $page=1; }; 
$start_from = ($page-1) * $results_per_page;

$query = "
select *
from transaction natural join buySellDetail
where username = '".$_SESSION['username']."'";
$result = mysqli_query($conn, $query);
$totalBuySell = 0;
while($buySell[$totalBuySell] = mysqli_fetch_array($result))
{
  $totalBuySell++;
}

$query = "
select *
from transaction natural join sendReceiveDetail
where username = '".$_SESSION['username']."'";
$result = mysqli_query($conn, $query);
$totalSendReceive = 0;
while($sendReceive[$totalSendReceive] = mysqli_fetch_array($result))
{
  $totalSendReceive++;
}

function drawTable($row){
  echo "  <tr>
    <td>".$row['transactionID']."</td>
    <td>".$row['username']."</td>
    <td>".$row['transactionType']."</td>
    <td>".$row['timeOfTransaction']."</td>
    
    <td>";
  switch ($row['transactionType']){
    case 'send':
      echo 'Send to '.$row['partnerUsername'].' '.$row['winningAmount'];
      break;
    case 'receive':
      echo 'Receive from '.$row['partnerUsername'].' '.$row['winningAmount'];
      break;
    case 'buy':
      echo 'Buy '.$row["quantity"].' '.$row["stockId"].' stocks at price '.$row["price"];
      break;
    case 'sell':
      echo 'Sell '.$row["quantity"].' '.$row["stockId"].' stocks at price '.$row["price"];
  }
  echo "</td>
      <td>$".number_format($row['postWinning'])."</td>
  </tr>";
}
?>  
    <div id="order_table">
    <table class="table table-bordered">
      <tr>
        <th width="3%">ID</th>
        <th width="8%">Username</th>
        <th width="4%">Type</th>
        <th width="20%">Date</th>
        
        <th width="10%">Detail</th>
        <th width="10%">Balance</th>
      </tr>

      <?php
      $sendReceiveCount = 0;
      for ($buySellCount = 0; $buySellCount<$totalBuySell; $buySellCount++){
        while($sendReceive[$sendReceiveCount]["transactionID"]<$buySell[$buySellCount]["transactionID"]
         && $sendReceiveCount<$totalSendReceive){
          drawTable($sendReceive[$sendReceiveCount]);
          $sendReceiveCount++;
        }
        if ($buySellCount<$totalBuySell)
        {
          drawTable($buySell[$buySellCount]);
        }
      }

      if ($totalBuySell==$buySellCount){
        while($sendReceiveCount<$totalSendReceive){
          drawTable($sendReceive[$sendReceiveCount]);
          $sendReceiveCount++;
        }
      }
      ?>
    </table>
    
  </div>

</div>
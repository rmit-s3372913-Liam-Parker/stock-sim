<?php
// include connection file
include 'connection.php';

// get total sum of all stock shares
$sql = "SELECT SUM(stockQuantity) FROM stock WHERE username = 'kenn'";
$result = mysqli_query($conn, $sql);
$row = mysqli_fetch_assoc($result);
echo number_format($row['SUM(stockQuantity)']);

?>
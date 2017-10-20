<?php
// include connection file
include_once '../config/connection.php';
// get total sum of all stock shares
$sql = "SELECT SUM(stockQuantity) FROM stock WHERE username = '".$_SESSION['username']."'";
$result = mysqli_query($conn, $sql);
$row = mysqli_fetch_assoc($result);
echo number_format($row['SUM(stockQuantity)']);
?>
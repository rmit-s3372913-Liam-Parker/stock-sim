<?php
include 'connection.php';
// select all from the asx_data table in database
$sql = "select * from asx_data order by `change` desc";

$records = mysqli_query($conn, $sql);

 if($records === FALSE) { 
        die(mysqli_error($conn)); // better error handling
    }
 
    $row = mysqli_fetch_assoc($records);

?>

<div class="container">
		
	<h3>Top 5</h3>
	<table id="table" class="table table-striped table-hover">
		<thead>
			<tr>
				<th>Code</th>
				<th>Company</th>
				<th>Last Price</th>
				<th>Change</th>
			</tr>
		</thead>
		<tbody>
		<?php
			while ($list = mysqli_fetch_assoc($records))
			{
				echo "<tr>";
				echo "<td>".$list['Code']."</td>";
				echo "<td>".$list['name']."</td>";
				echo "<td>". "$".$list['last_price']."</td>";
				echo "<td>".$list['change']."%"."</td>";
			}
		?>
		</tbody>
	</table>
			

		

</div>
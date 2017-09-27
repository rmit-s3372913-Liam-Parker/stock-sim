<?php
include 'connection.php';

$query = "SELECT * FROM transaction ORDER BY timeOfTransaction desc";

$result = mysqli_query($conn, $query);

?>
<!DOCTYPE html>
<html>
<head>
	<title>Date-Picker</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>  
           <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" />  
           <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>  
           <link rel="stylesheet" href="http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
</head>
<body>
<div class="container" style="width: 900px;">
	<h3 align="center">TRANSACTION</h3> <br>

	<div class="col-md-3">
		<input type="text" name="from_date" id="from_date" class="form-control" placeholder="From-Date">
	</div>
	<div class="col-md-3">
		<input type="text" name="to_date" id="to_date" class="form-control" placeholder="To-Date">
	</div>
	<div class="col-md-5">
		<input type="button" name="filter" id="filter" value="filter" class="btn btn-info">
	</div>
	<div style="clear:both"></div>
	<br>
	<div id="order_table">
		<table class="table table-bordered">
			<tr>
				<th width="15%">ID</th>
				<th width="15%">Username</th>
				<th width="15%">Type</th>
				<th width="15%">Money</th>
				<th width="20%">Date</th>
			</tr>

			<?php
			while($row = mysqli_fetch_array($result))
			{
			?>
				<tr>
					<td><?php echo $row["transactionID"]; ?></td>
					<td><?php echo $row["username"]; ?></td>
					<td><?php echo $row["transactionType"]; ?></td>
					<td><?php echo $row["postWinning"]; ?></td>
					<td><?php echo $row["timeOfTransaction"]; ?></td>
				</tr>
			<?php	
			}
			?>
		</table>
		
	</div>
	
</div>


</body>
</html>

<script>
	$(document).ready(function(){
		$.datepicker.setDefaults({
			dateFormat: 'yy-mm-dd'
		});
		$(function(){
			$("#from_date").datepicker();
			$("#to_date").datepicker();
		});
		$('#filter').click(function(){
			var from_date = $('#from_date').val();
			var to_date = $('#to_date').val();
			if(from_date != '' && to_date != '')
			{
				$.ajax({
					url:"filter.php",
					method: "POST",
					data:{from_date:from_date, to_date:to_date},
					success:function(data)
					{
						$('#order_table').html(data);
					}
				});
			}
			else
			{
				alert("Please Select Data");
			}
		});
		
	});
</script>
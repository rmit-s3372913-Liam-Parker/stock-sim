<?php
session_start();
include 'downloadCSV.php';

?>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8"> 
	<title>ASX Simlator</title>

	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  	<link rel="stylesheet" type="text/css" href="index.css">
  	<style>
  		.leader-board, .asx, .your-stock{
  			border: 1px solid;
  		}

  		.scrollable {
			height: 300px;
			overflow: scroll;
		}
		.table tr:hover td{
			background-color: #778899;
			color: white;
			cursor: pointer;
		}
  	</style>
  	
</head>
<body>
<?php include("navigation.php");?>
	<div class="container">
		<div class="row">
			<div class="col-md-4">
				<div class="leader-board">
					<h2 align="center">Leaderboard</h2>
					<table class="table table-striped">
						<thead>
							<tr>
								<th>username</th>
								<th>winning</th>

							</tr>
						</thead>
						<tbody>
							
							<tr>
								<?php include 'leader_board.php' ?>
							</tr>
							
						</tbody>
						
					</table>
				</div>
			</div>

			<div class="col-md-8">
				<h2 align="center">Winnings</h2>
				<h2 align="center"><?php include 'winning.php'; ?></h2>
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-4">
				<div class="your-stock">
					<h2 align="center">My Stocks</h2>
					<table class="table">
						<thead>
							<tr>
								<th>Symbol</th>
				        		<th>Shares</th>

							</tr>
						</thead>
						<tbody>
							<tr>
								<?php include 'my_stocks.php';?>
							</tr>
							
						</tbody>
						
					</table>
				</div>
			</div>

			<div class="col-md-8">
				<h2 align="center">ASX Companies</h2>
					<div class="scrollable">
					<table id="companies" class="table table-striped table-bordered text-center">
						<thead>
							<tr>
								<th>Code</th>
								<th>Description</th>
								<th>Price</th>
								
							</tr>
						</thead>
						<tbody>
							<tr>
								<?php include 'read_asx_csv.php'; ?>
							</tr>
							
						</tbody>
						
					</table>
					
				</div>
			</div>
		</div>

	</div>
<!-- script for selecting code in every row in the table -->
<script>
		$("#companies tr").click(function(){
		    alert($(this).children('td:first').html());
		});
	</script>
</body>
</html>
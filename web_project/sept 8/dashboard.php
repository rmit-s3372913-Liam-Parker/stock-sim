<?php
session_start();


?>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8"> 
	<title>ASX Simlator</title>

	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

  	<style>
  		.leader-board, .asx, .your-stock{
  			border: 1px solid;
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
								<th>Rank</th>
								<th>User</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td></td>
							</tr>
							<tr>
								<td></td>
							</tr>
							<tr>
								<td></td>
							</tr>
						</tbody>
						
					</table>
				</div>
			</div>

			<div class="col-md-8">
				<h2 align="center">Winnings</h2>
				<h2><?php include 'winning.php'; ?></h2>
			</div>
		</div>

		<div class="row">
			<div class="col-md-4">
				<div class="asx">
					<h2 align="center">ASX API LIVE</h2>
					<table class="table">
						<thead>
							<tr>
								<th>Top ASX</th>
								
							</tr>
						</thead>
						<tbody>
							<tr>
								<td></td>
							</tr>
							<tr>
								<td></td>
							</tr>
							<tr>
								<td></td>
							</tr>
						</tbody>
						
					</table>
				</div>
			</div>

			<div class="col-md-8">
				<div class="your-stock">
					<h2 align="center">Your Stocks</h2>
					<table class="table">
						<thead>
							<tr>
								<th></th>
								
							</tr>
						</thead>
						<tbody>
							<tr>
								<td></td>
							</tr>
							<tr>
								<td></td>
							</tr>
							<tr>
								<td></td>
							</tr>
						</tbody>
						
					</table>
					
				</div>
			</div>
		</div>

	</div>

</body>
</html>
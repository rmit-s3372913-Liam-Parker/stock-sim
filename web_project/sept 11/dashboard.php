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
						
						<tbody>
							<tr>
								<!-- display top 5 by winnings -->
								<td><?php include 'leader_board.php' ?></td>
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
				<h2 align="center"><?php include 'winning.php'; ?></h2>
			</div>
		</div>

		<div class="row">
			<div class="col-md-4">
				<div class="asx">
					<h2 align="center">ASX API LIVE</h2>
					<table class="table">
						<thead>
							<tr>
								<th>Symbol</th>
				        		<th>Last price</th>

							</tr>
						</thead>
						<tbody>
							<?php
							$url = "http://download.finance.yahoo.com/d/quotes.csv?s=^AORD+BHP.AX+BLT.L+AAPL+EBAY+^NDX+ASX.AX&f=sl1c";

							$line = file_get_contents($url);

							$data =  nl2br($line);

							$company = explode("\n", $data);
							for ($i = 0; $i<count($company)-1; $i++)
							{
							  $row = explode(",", $company[$i]);
							  echo "\t<tr>\r\n"
							  . "\t\t<td>" . $row[0] . "</td>"
							  . "\t\t<td>$" . $row[1] . "</td>"
							  . "\t</tr>";
							}


							?>
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
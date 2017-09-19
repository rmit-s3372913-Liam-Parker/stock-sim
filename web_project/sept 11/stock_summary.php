<?php
//session_start();
include 'connection.php';
?>
<!-- contents of the tab menu for stock_list.php -->
<div class="container">
		<div class="tab-content">
			<div id="summary" class="tab-pane fade in active">
				<h3>Stocks</h3>
				<table id="table" class="table table-striped table-hover">
				    <thead>
				      <tr>
				        <th>Symbol</th>
				        <th>Description</th>
				        <th>Last price</th>
				        <th>Change</th>
				      </tr>
				    </thead>
				    <tbody>
				      <?php
				      $url = "http://download.finance.yahoo.com/d/quotes.csv?s=^AORD+BHP.AX+BLT.L+AAPL+EBAY+^NDX+ASX.AX&f=snl1c";

						$line = file_get_contents($url);

						$data =  nl2br($line);

						$company = explode("\n", $data);
						for ($i = 0; $i<count($company)-1; $i++)
						{
						  // display
						  // code
						  // description
						  // current price
						  // change
						  $row = explode(",", $company[$i]);
						  $row[0] = substr($row[0], 1, -1);
						  $row[1] = substr($row[1], 1, -1);
						  echo "\t<tr>\r\n"
						  . "\t\t<td>" . $row[0] . "</td>"
						  . "\t\t<td>" . $row[1] . "</td>"
						  . "\t\t<td>" . $row[2] . "</td>"
						  . "\t\t<td>" . $row[3] . "</td>"
						  . "\t</tr>";

						  $_SESSION['current_Price'] = $row[2];
						}
				      ?>
				    </tbody>
				</table>
			</div>

			<div id="top5" class="tab-pane fade">
		      <?php include 'top5.php' ?>
		    </div>

		    <div>
		    	<!-- ?php include 'history.php' ? -->
		    </div>

		    <div id="chart" class="tab-pane fade">
		    	<?php include 'history.php' ?>
		    </div>
		    

		</div>
		

	</div>
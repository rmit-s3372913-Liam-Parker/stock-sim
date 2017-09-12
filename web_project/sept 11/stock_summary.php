<?php
include 'connection.php';
// select all from the asx_data table in database
//$sql = "select * from asx_data";

//$records = mysqli_query($conn, $sql);

?>
<div class="container">
		<div class="tab-content">
			<div id="summary" class="tab-pane fade in active">
				<h3>Stocks</h3>
				<table id="table" class="table table-striped table-hover">
				    <thead>
				      <tr>
				        <th>Symbol</th>
				        <th>Last price</th>
				        <th>Change</th>
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
						  . "\t\t<td>" . $row[1] . "</td>"
						  . "\t\t<td>" . $row[2] . "</td>"
						  . "\t</tr>";
						}
				      ?>
				    </tbody>
				</table>
			</div>

			<div id="top5" class="tab-pane fade">
		      <?php include 'top5.php' ?>
		    </div>

		</div>
		

	</div>
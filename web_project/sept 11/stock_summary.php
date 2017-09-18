<?php
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
				        <th>Last price</th>
				        <th>Change</th>
				      </tr>
				    </thead>
				    <tbody>
					
					<div>
				      <?php
				      $url = "http://download.finance.yahoo.com/d/quotes.csv?s=";

					  $asx = fopen("files/ASXListedCompanies.csv", "r");
							$company = explode("\n", fread($asx, filesize("files/ASXListedCompanies.csv")));
							for ($i = 0; $i<count($company); $i++)
							{
								echo $company[$i];
								$row = explode(",", $company[$i]);
								if ($i>2){
								  if ($i=3){
									$url = $url.$row[1];
								  }
								  $url = $url."+".$row[1];
								}
								if ($i>50)
									break;
							}
					  
					  fclose($asx);
					  
					  $url += "&f=sl1c";

						$line = file_get_contents($url);

						$data =  nl2br($line);

						$company = explode("\n", $data);
						for ($i = 0; $i<count($company)-1; $i++)
						{
						  $row = explode(",", $company[$i]);
						  $row[0] = substr($row[0], 1, -1);
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

		    <div>
		    	<!-- ?php include 'history.php' ? -->
		    </div>

		    <div id="chart" class="tab-pane fade">
		    	<?php include 'history.php' ?>
		    </div>
		    

		</div>
		

	</div>
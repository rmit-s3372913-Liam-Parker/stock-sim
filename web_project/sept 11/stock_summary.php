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
				      <?php include 'read_asx_csv.php'; ?>
				    </tbody>
				</table>
			</div>

			<div id="top5" class="tab-pane fade">
		      <?php //include 'top5.php' ?>
		    </div>

		    <div id="transaction" class="tab-pane fade">
		    	<?php include 'transaction_dates.php' ?>
		    </div>

		    <div id="chart" class="tab-pane fade">
		    	<?php //include 'history.php' ?>
		    </div>
		    

		</div>
		

	</div>
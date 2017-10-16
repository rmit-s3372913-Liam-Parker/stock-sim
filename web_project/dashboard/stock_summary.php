<?php
// include connection file
include '../config/connection.php';
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
				      </tr>
				    </thead>
				    <tbody>
				      <?php include '../config/read_asx_csv.php'; ?>
				    </tbody>
				</table>
			</div>

			<div id="my_stocks" class="tab-pane fade">
		      <table id="table" class="table table-striped table-hover">
		      	<thead>
							<tr>
								<td></td>
								<td></td>
								<td>No of Shares: <?php include 'stock_count.php'; ?></td>
								<td>Average Price </td>
							</tr>
							<tr>
								<th>No</th>
								<th>Symbol</th>
				        		<th>Shares</th>
				        		<th>Price</th>

							</tr>
							
						</thead>
						<tbody>

							<tr>
								<?php include '../includes/my_stocks.php';?>
							</tr>
							
						</tbody>
		      </table>
		      
		    </div>

		    <div id="user_transaction" class="tab-pane fade">
		    	<?php include 'user_transaction.php' ?>
		    </div>

		    <!-- <div id="chart" class="tab-pane fade">
		    	include 'top5.php' ?>
		    </div> -->
		    

		</div>
		

	</div>
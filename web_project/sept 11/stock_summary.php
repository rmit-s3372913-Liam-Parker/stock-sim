<?php
// include connection file
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

			<div id="my_stocks" class="tab-pane fade">
		      <table id="table" class="table table-striped table-hover">
		      	<thead>
							
							<tr>
								<th>No</th>
								<th>Symbol</th>
				        		<th>Shares</th>
				        		<th>Price</th>
				        		<th></th>

							</tr>
							<tr>
								<td></td>
								<td><input type="text" name="no_of_shares" id="no_of_shares" readonly></td>
								<td><input type="text" name="average_price" id="average_price" readonly></td>
							</tr>
						</thead>
						<tbody>

							<tr>
								<?php include 'my_stocks.php';?>
							</tr>
							
						</tbody>
		      </table>
		      
		    </div>

		    <div id="user_transaction" class="tab-pane fade">
		    	<?php include 'user_transaction.php' ?>
		    </div>

		    <!-- <div id="chart" class="tab-pane fade">
		    	<?php include 'top5.php' ?>
		    </div> -->
		    

		</div>
		

	</div>
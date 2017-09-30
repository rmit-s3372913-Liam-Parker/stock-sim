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
  	<!-- <script src="jquery.tabledit.min.js"></script> -->
  	<link rel="stylesheet" type="text/css" href="index.css">
  	<style>
  		.leader-board, .asx, .your-stock{
  			border: 1px solid;
  		}

  		.scrollable {
			height: 300px;
			overflow: scroll;
		}
		#stock tr:hover td{
			background-color: #778899;
			color: white;
			cursor: pointer;
		}
  	</style>
  	
</head>
<body>
<?php include("navigation.php");?>
	<!-- display error message with session -->
	<?php
      if (isset($_SESSION['error_stock']))
      {
        echo $_SESSION['error_stock']; unset($_SESSION['error_stock']); 
      }
      elseif (isset($_SESSION['stock_message'])) {
        echo $_SESSION['stock_message']; unset($_SESSION['stock_message']); 
      }
      ?>
	<div class="container">
		<!-- div container for displaying leaderboard -->
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
			<!-- div container for displaying users current stock/s with sell function -->
			<div class="col-md-4">
				<div class="your-stock">
					<h2 align="center">My Stocks</h2>
					<table id="stock" class="table">
						<form>
							&nbsp Code &nbsp <input type="text" name="code" id="code" style="width: 60px;" readonly> &nbsp
								
							&nbsp Price &nbsp <input type="text" name="price" id="price" style="width: 100px;" readonly> &nbsp&nbsp <br>

							&nbsp Share &nbsp <input type="text" name="share" id="share" style="width: 100px;"> &nbsp&nbsp

							
							<button type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#sell-function-modal" id="sell-button">Sell</button>
						</form>

						<thead>
							
							<tr>
								<th>Symbol</th>
				        		<th>Shares</th>
				        		<th>Price</th>
				        		<th></th>

							</tr>
						</thead>
						<tbody>
							<tr>
								<?php include 'my_stocks.php';?>
							</tr>
							
						</tbody>
						
					</table>
				</div>
				<div class="my_stocks_footer">
					<a href="" id="my_stocks">My Stocks</a>
				</div>
			</div>

			<!-- div container displaying a list of companies with prices using ASX API -->
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

<!-- Modal - Sell Function  -->
  <div class="modal fade" id="sell-function-modal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h2 class="modal-title">Sell share in: </h2>
        </div>
        <div class="modal-body">
          <div class="text-center">
            <form action="sell_validation.php" method="POST">
            <table class="table">
                    <tr>
                        <th>Code</th>
                        <td><input id="s-code" name="code" type="text" value="{{ request.form.s-code }}" readonly/></td>
                    </tr>
                    <tr>
                        <th>Price</th>
                        <td><input id="s-price" name="price" type="text" value="{{ request.form.s-price }}" readonly/></td>
                    </tr>
                    <tr>
                        <th>Shares</th>
                        <td><input id="s-share" name="share" type="text" value="{{ request.form.s-share }}" readonly/></td>
                    </tr>
                    <tr>
                        <th>Broker Fee:</th>
                        <td>$50</td>
                    </tr>
                    <tr>
                        <th>Purchase Fee:</th>
                        <td>0.25%</td>
                    </tr>
                    <tr>
                        <th>Total:</th>
                        <td><input type="text" id="sell-total" name="total" value="calcTotalSell()" readonly/></td>
                    </tr>
             </table>
                <button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
              <button type="submit" class="btn btn-success success" id="sell-submit" name="sell-submit">Confirm</button>
              </form>
          </div>
        </div>

        <div class="modal-footer">
          <div class="col-md-12">
            
          </div>
        </div>
      </div>
      
    </div>
  </div>

<!-- javascript for clicking a row in users stock and displaying in an input box -->
<script>
    
                var table = document.getElementById('stock');
                
                for(var i = 1; i < table.rows.length; i++)
                {
                    table.rows[i].onclick = function()
                    {
                         //rIndex = this.rowIndex;
                         document.getElementById("code").value = this.cells[0].innerHTML;
                         //document.getElementById("price-input").value = this.cells[2].innerHTML;
                         document.getElementById("price").value = this.cells[2].innerHTML;
                    };
                }
    
</script>

<!-- javascript for sell button to display in bootstrap modal form -->
<script>
    $('#sell-button').click(function(){
      $('#s-code').val($('#code').val());
      $('#s-price').val($('#price').val());
      $('#s-share').val($('#share').val());
      
      calcTotalSell();
    });
  </script>

  <!-- Sell function script calculation -->
    <script>
      function calcTotalSell()
      {
        var price = parseFloat(document.getElementById('s-price').value);
        var share = parseFloat(document.getElementById('s-share').value);
        var sub = price * share + 50;
        var percent = sub * 0.0025;
        var total = (sub + percent).toFixed(2);

        document.getElementById("sell-total").value = total;
      }


    </script>

</body>
</html>
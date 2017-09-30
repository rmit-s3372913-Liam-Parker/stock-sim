<?php
// start a session
// include connection
session_start();
include 'connection.php';
// select all from the asx_data table in database
$sql = "select * from asx_data";

$records = mysqli_query($conn, $sql);

?>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<?php include("navigation.php");?>
 <div class="container">
    <!-- display error message -->
    <?php
      if (isset($_SESSION['error_buy']))
      {
        echo $_SESSION['error_buy']; unset($_SESSION['error_buy']); 
      }
    ?>
    <!-- div display a selected row -->
    <form>
      <input type="text" name="search" placeholder="Search Company">
      Code &nbsp <input type="text" name="code" id="code-input" size="12"> &nbsp
      Price &nbsp <input type="text" name="price" id="price-input" size="12"> &nbsp
      Share &nbsp <input type="number" name="share" id="share-input" size="12" step="10" min="0">
      
      <!-- Buy button open modal -->
      <button type="button" class="btn btn-success btn-sm" data-toggle="modal" data-target="#buy-function-modal" id="buy-button">Buy</button>


      <!-- <!-- Sell button open modal --
      <button type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#sell-function-modal" id="sell-button">Sell</button> -->
    </form>
  </div>
  <br>
  <!-- bootstrap TAB stock_summary.php -->
  <div class="container">
    <ul class="nav nav-tabs">
      <li class="active"><a data-toggle="tab" href="#summary">Summary</a></li>
      <li><a data-toggle="tab" href="#my_stocks">My Stocks</a></li>
      <li><a data-toggle="tab" href="#user_transaction">Transaction</a></li>
      <!-- <li><a data-toggle="tab" href="#chart">Charts</a></li> -->
    </ul>
  </div>
  
  <!-- include file stock_summary.php display a table div -->
  <?php include 'stock_summary.php'; ?>

  <!-- Modal - Buy Function  -->
  <div class="modal fade" id="buy-function-modal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h2 class="modal-title">Buy share in: </h2>
        </div>
        <div class="modal-body">
          <div class="text-center">
            <!-- form table display of purchase summary -->
            <form action="buy_validation.php" method="POST">
            <table class="table">
                    <tr>
                        <th>Code</th>
                        <td><input id="code" name="code" type="text" value="{{ request.form.code }}" readonly/></td>
                    </tr>
                    <tr>
                        <th>Price</th>
                        <td><input id="price" name="price" type="text" value="{{ request.form.price }}" readonly/></td>
                    </tr>
                    <tr>
                        <th>Shares</th>
                        <td><input id="share" name="share" type="text" value="{{ request.form.share }}" readonly/></td>
                    </tr>
                    <tr>
                        <th>Broker Fee:</th>
                        <td>$50</td>
                    </tr>
                    <tr>
                        <th>Purchase Fee:</th>
                        <td>1.0%</td>
                    </tr>
                    <tr>
                        <th>Total:</th>
                        <td><input type="text" id="buy-total" name="buy-total" value="calcTotalBuy()" readonly/></td>
                    </tr>
            </table>
            <button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
            
            <button type="submit" class="btn btn-success success" id="buy-submit" name="buy-submit">Confirm</button>

            </form>
            <!-- end of form -->
          </div>
        </div>

        <div class="modal-footer">
          <div class="col-md-12">
            
            
          </div>
        </div>
      </div>
      
    </div>
  </div>
  <!-- End of Modal Buy Function -->


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
                        <td><input id="s-code" name="s-code" type="text" value="{{ request.form.s-code }}" readonly/></td>
                    </tr>
                    <tr>
                        <th>Price</th>
                        <td><input id="s-price" name="s-price" type="text" value="{{ request.form.s-price }}" readonly/></td>
                    </tr>
                    <tr>
                        <th>Shares</th>
                        <td><input id="s-share" name="s-share" type="text" value="{{ request.form.s-share }}" readonly/></td>
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
                        <td><input type="text" id="sell-total" name="sell-total" value="calcTotalSell()" readonly/></td>
                    </tr>
             </table>
                <button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
              <button type="submit" class="btn btn-success success" id="sell-submit" name="sell-submit">Submit</button>
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

  <!-- table row selection to be displayed to a div -->
    <script>
    
                var table = document.getElementById('table');
                
                for(var i = 1; i < table.rows.length; i++)
                {
                    table.rows[i].onclick = function()
                    {
                         //rIndex = this.rowIndex;
                         document.getElementById("code-input").value = this.cells[0].innerHTML;
                         document.getElementById("price-input").value = this.cells[2].innerHTML;
                         document.getElementById("share-input").value = this.cells[3].innerHTML;
                    };
                }
    
    </script>

  <!-- Buy-function script to display selection to modal -->
  <script>
      $('#buy-button').click(function(){
      $('#code').val($('#code-input').val());
      $('#price').val($('#price-input').val());
      $('#share').val($('#share-input').val());

      calcTotalBuy();
    });
  </script>

  <!-- Buy function script -->
  <script>
      function calcTotalBuy()
      {
        var price = parseFloat(document.getElementById('price').value);
        var share = parseFloat(document.getElementById('share').value);
        var sub = price * share + 50;
        var percent = sub * 0.01;
        var total = (sub + percent).toFixed(2);

        document.getElementById("buy-total").value = total;
      }

  </script>
<?php
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
    <form>

      <input type="text" name="search" placeholder="Search Company">
      Code &nbsp <input type="text" name="code" id="code-input" size="12"> &nbsp
      Price &nbsp <input type="text" name="price" id="price-input" size="12"> &nbsp
      Share &nbsp <input type="number" name="share" id="share-input" size="12" step="10" min="0">
      <!-- Change &nbsp <input type="text" name="price" id="price"> -->
      
      <button type="button" class="btn btn-success btn-sm" data-toggle="modal" data-target="#buy-function-modal" id="buy-button">Buy</button>

      <button type="button" class="btn btn-danger btn-sm">Sell</button>
    </form>
  </div>
  <br>
  <div class="container">
    <ul class="nav nav-tabs">
      <li class="active"><a data-toggle="tab" href="#summary">Summary</a></li>
      <li><a data-toggle="tab" href="#top5">Top 5</a></li>
      <li><a href="#">Value</a></li>
      <li><a href="#">Charts</a></li>
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
                        <td><p id="Total" name="Total" readonly></p></td>
                    </tr>
                </table>
            
          </div>
        </div>

        <div class="modal-footer">
          <div class="col-md-12">
            <button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
            <a href="" id="submit" class="btn btn-success success">Submit</a>
          </div>
        </div>
      </div>
      
    </div>
  </div>
  <!-- End of Modal Buy Function -->
  <!-- function to display selection to modal -->
  <script>
    $('#buy-button').click(function(){
      $('#code').val($('#code-input').val());
      $('#price').val($('#price-input').val());
      $('#share').val($('#share-input').val());
      calcTotal();
    });
  </script>

  <!-- table row selection to be displayed to a div -->
  <script>
    
                var table = document.getElementById('table');
                
                for(var i = 1; i < table.rows.length; i++)
                {
                    table.rows[i].onclick = function()
                    {
                         //rIndex = this.rowIndex;
                         document.getElementById("code-input").value = this.cells[0].innerHTML;
                         document.getElementById("price-input").value = this.cells[1].innerHTML;
                         document.getElementById("share-input").value = this.cells[2].innerHTML;
                    };
                }
    
    </script>


    <script>
      function calcTotal()
      {
        var price = parseFloat(document.getElementById('price').value);
        var share = parseFloat(document.getElementById('share').value);
        var sub = price * share + 50;
        var percent = sub * 0.01;
        var total = sub + percent;

        document.getElementById("Total").innerHTML = total;
      }


    </script>














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
		<input type="text" name="search" placeholder="Search Company">
		Code &nbsp <input type="text" name="code" id="code"> &nbsp
		Company &nbsp <input type="text" name="company" id="company"> &nbsp
		Price &nbsp <input type="text" name="price" id="price">
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
	<div class="container">
		<div class="tab-content">
			<div id="summary" class="tab-pane fade in active">
				<h3>Stocks</h3>
				<table id="table" class="table table-striped table-hover">
				    <thead>
				      <tr>
				        <th>Code</th>
				        <th>Company</th>
				        <th>Last Price</th>
				        <th>Change</th>
				      </tr>
				    </thead>
				    <tbody>
				      <?php
				      while ($list = mysqli_fetch_assoc($records))
				      {
				      	echo "<tr>";
				      	echo "<td>".$list['Code']."</td>";
				      	echo "<td>".$list['name']."</td>";
				      	echo "<td>". "$".$list['last_price']."</td>";
				      	echo "<td>".$list['change']."%"."</td>";
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

	<script>
    
                var table = document.getElementById('table');
                
                for(var i = 1; i < table.rows.length; i++)
                {
                    table.rows[i].onclick = function()
                    {
                         //rIndex = this.rowIndex;
                         document.getElementById("code").value = this.cells[0].innerHTML;
                         document.getElementById("company").value = this.cells[1].innerHTML;
                         document.getElementById("price").value = this.cells[2].innerHTML;
                    };
                }
    
         </script>


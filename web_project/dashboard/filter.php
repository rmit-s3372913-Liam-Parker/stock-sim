<?php
include '../config/connection.php';
//filter.php
if(isset($_POST["from_date"], $_POST["to_date"]))
{
	$output = '';
	$query = "SELECT * FROM transaction WHERE timeOfTransaction BETWEEN '" . $_POST["from_date"] . "' AND '" . $_POST["to_date"] . "' ";
	$result = mysqli_query($conn, $query);
	$output .= ' 
		<table class="table table-bordered">
			<tr>
				<th width="15%">ID</th>
				<th width="15%">Username</th>
				<th width="15%">Type</th>
				<th width="15%">Money</th>
				<th width="20%">Date</th>
			</tr>
			';
			if(mysqli_num_rows($result) > 0)
			{
				while($row = mysqli_fetch_array($result))
				{
					$output .= '
						<tr>
							<td>' . $row["transactionID"] . '</td>
							<td>' . $row["username"] . '</td>
							<td>' . $row["transactionType"] . '</td>
							<td>$' . $row["postWinning"] . '</td>
							<td>' . $row["timeOfTransaction"] . '</td>
						</tr> 
					';
				}
			}
			else
			{
				$output .= '
					<tr>
						<td colspan="5">Not Found</td>
					<tr>
				';
			}
			$output .= '</table>';
			echo $output;

}

?>
<?php

include 'connection.php';

$sql = "select username, password, email, winning FROM player";
$result = mysqli_query($conn, $sql);
//$row = mysqli_fetch_assoc($result);

?>

<div class="table-responsive">
		
	<h3>Users</h3>
	<table id="edit_users" class="table table-bordered table-hover">
		<thead>
			<tr>
				<th>Username</th>
				<th>Password</th>
				<th>Email</th>
				<!-- <th>Winning</th> -->
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
		<form action="" method="POST">
			Username: &nbsp<input type="text" name="usersname"> &nbsp
			Password: &nbsp<input type="text" name="password"> &nbsp
			Email:    &nbsp<input type="text" name="email">
			<button type="submit">Enter</button>
		</form>
<?php
while ($row = mysqli_fetch_assoc($result)) {
	echo "<tr>";

	echo "<td>".$row['username']."</td>";

	echo "<td>".$row['password']."</td>";

	echo "<td>" . $row['email'] . "</td>";

	//echo "<td>" . $row['winning'] . "</td>";

	echo "<td> <a href=''>Update</a> | <a href=''>Delete</a> </td>";

	echo "</tr>";
}


?>
		</tbody>
	</table>

<script>
	$(document).ready(function(){
		$('#edit_users').Tabledit({
			url:'users.action.php',
			columns:{
				identtifier: [0,"username"],
				editable: [[1, 'password'],[2, 'email']]
			},
			restoreButton: false,
			onSuccess: function(data, textStatus, JQXHR)
			{
				if(data.action == 'delete')
				{
					$('#' + data.username).remove();
				}
			}
		})
	});

</script>
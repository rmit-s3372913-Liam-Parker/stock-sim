<?php
include_once("callApi.php");
// ini_set('display_errors', 1);
// error_reporting(E_ALL);
?>

<!DOCTYPE html>
<html>
<head>
	<title>Read txt File</title>
</head>
<body>
<!-- <table width="300" border="1">
    <tr>
        <td width="100">Company name</td>
        <td width="200">Code</td>
    </tr>
-->
<?php
$asx = fopen("files/ASXListedCompanies.csv", "r");
$company = explode("\r\n", fread($asx, filesize("files/ASXListedCompanies.csv")));
for ($i = 0; $i<10; $i++)
{
  $row = explode("\"", $company[$i]);
  $column = explode(",", $row[2]);
  if ($i>2){
    if ($company[$i]=="")
      break;
    //echo "<tr><td height='30'>$part[0]</td><td>$part[1]</td></tr>";
    echo "<tr>";

    echo "<td>" . $column[1] . "</td>";

    echo "<td>" . $row[1] . "</td>";

    $data=CallAPI($column[1]);

    echo "<td>" . $data["last_price"] . "</td>";

    echo "</tr>";
  }
}



fclose($asx);
?>
<!-- </table>

</body>
</html>
 -->
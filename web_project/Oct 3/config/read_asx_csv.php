<?php
include_once("callApi.php");
ini_set('display_errors', 1);
error_reporting(E_ALL);
?>

<!DOCTYPE html>
<html>
<head>
  <title>Read txt File</title>
</head>
<body>
<!-- open saved file in folder file-->
<?php
$asx = fopen("files/ASXListedCompanies.csv", "r");
$company = explode("\r\n", fread($asx, filesize("files/ASXListedCompanies.csv")));
// display 20 companies from ASX list of companies
for ($i = 0; $i<20; $i++)
{
  $row = explode("\"", $company[$i]);
  $column = explode(",", $row[2]);
  if ($i>2){
    if ($company[$i]=="")
      break;
    echo "<tr>";

    echo "<td>" . $column[1] . "</td>";

    echo "<td>" . $row[1] . "</td>";

    $data=CallAPI($column[1]);

    echo "<td>" . $data["last_price"] . "</td>";

    echo "</tr>";
  }
}

// close file
fclose($asx);
?>
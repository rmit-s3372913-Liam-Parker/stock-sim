<?php
include_once("callApi.php");
ini_set('display_errors', 1);
error_reporting(E_ALL);
//open saved file in folder file
$asx = fopen("../config/files/ASXListedCompanies.csv", "r");
$company = explode("\r\n", fread($asx, filesize("../config/files/ASXListedCompanies.csv")));
// display 20 companies from ASX list of companies
for ($i = 0; $i<20; $i++)
{
  
  if ($i>2){
    if ($company[$i]=="")
      break;
    $row = explode("\"", $company[$i]);
    $column = explode(",", $row[2]);
    echo "<tr>";

    echo "<td>" . $column[1] . "</td>";

    echo "<td>" . $row[1] . "</td>";

    $data=CallAPI($column[1]);

    echo "<td>";
    if (isset($data["last_price"]))
      echo "$".$data["last_price"];
    else echo "Not Available";
    echo "</td>";

    echo "</tr>";
  }
}
// close file
fclose($asx);
?>
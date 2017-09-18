<?php


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
 --><?php
$file_handle = fopen("files/ASXListedCompanies.csv", "r");

$file = new SplFileObject('files/ASXListedCompanies.csv');
$fileIterator = new LimitIterator($file, 3);
foreach($fileIterator as $line) {
    $part = explode(",", $line);
    if ($part[0]=="")
      break;
    //echo "<tr><td height='30'>$part[0]</td><td>$part[1]</td></tr>";
   echo "<tr>";

   echo "<td>" . $part[1] . "</td>";

   echo "<td>" . $part[0] . "</td>";

   echo "</tr>";
}



fclose($file_handle);
?>
<!-- </table>

</body>
</html>
 -->
<?php


?>

<!DOCTYPE html>
<html>
<head>
	<title>Read txt File</title>
</head>
<body>
<table width="300" border="1">
    <tr>
        <td width="100">Company name</td>
        <td width="200">Code</td>
    </tr>
<?php
$file_handle = fopen("files/ASXListedCompanies.csv", "r");

$file = new SplFileObject('files/ASXListedCompanies.csv');
$fileIterator = new LimitIterator($file, 3, 15);
foreach($fileIterator as $line) {
    list($name, $code, $group) = explode(",", $line);

  
    echo "<tr><td height='30'>$name</td><td>$code</td></tr>";
   
}



fclose($file_handle);
?>
</table>

</body>
</html>

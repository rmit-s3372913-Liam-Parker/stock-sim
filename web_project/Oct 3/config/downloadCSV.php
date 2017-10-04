<!DOCTYPE html>
<html>
<head>
	<title></title>
</head>
<body>

<!-- download company list from ASX and save in a file folder -->
<?php
$url_to_csv = 'http://www.asx.com.au/asx/research/ASXListedCompanies.csv';
$save_dir = '../config/files/';
$filename = basename($url_to_csv);
$complete_save_loc = $save_dir . $filename;
file_put_contents($complete_save_loc, file_get_contents($url_to_csv));


?>
</body>
</html>
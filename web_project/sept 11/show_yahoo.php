<table>
	<tr>
		<th>Company</th>
		<th>Last Trade Date</th>
		<th>Last Trade Price</th>
	</tr>
<?PHP 
$url = "http://download.finance.yahoo.com/d/quotes.csv?s=^AORD+BHP.AX+BLT.L+AAPL&f=nd1l1";

$line = file_get_contents($url);

$data =  nl2br($line);

$company = explode("\n", $data);
for ($i = 0; $i<count($company)-1; $i++)
{
  $row = explode(",", $company[$i]);
  echo "\t<tr>\r\n"
  . "\t\t<td>" . $row[0] . "</td>"
  . "\t\t<td>" . $row[1] . "</td>"
  . "\t\t<td>$" . $row[2] . "</td>"
  . "\t</tr>";
}

?>
<?php 
function CallAPI($stockCode)
{
  // initialize
  $curl = curl_init();

  // get url and set to variable
  $url = "http://data.asx.com.au/data/1/share/".$stockCode;

  curl_setopt($curl, CURLOPT_URL, $url);
  curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);

  $result = curl_exec($curl);

  curl_close($curl);

  // return variable
  return $data = json_decode($result, true);
}
?>

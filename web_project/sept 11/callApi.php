<?php 
function CallAPI($stockCode)
{
  $curl = curl_init();

  $url = "http://data.asx.com.au/data/1/share/".$stockCode;

  curl_setopt($curl, CURLOPT_URL, $url);
  curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);

  $result = curl_exec($curl);

  curl_close($curl);

  return $data = json_decode($result, true);
}
?>

// registration confirmation generator code
function randomWithLength()
{
$length = 5;
$number = '';
for ($i = 0; $i < $length; $i++){
$number .= rand(0,9);
}

return (int)$number;

}


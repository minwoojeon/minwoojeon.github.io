<?php  
$con=mysqli_connect("localhost","root","autoset","test");  


$bbsno = $_POST['bbsno'];

$sear = "select cur_im from we_bbs where bbsno=$bbsno";

$res = mysqli_query($con, $sear);
$result = array(); 

while($row=mysqli_fetch_array($res)){
   array_push($result, array('cur_im'=>$row[0]));
}

echo json_encode(array("result"=>$result));
   
?>
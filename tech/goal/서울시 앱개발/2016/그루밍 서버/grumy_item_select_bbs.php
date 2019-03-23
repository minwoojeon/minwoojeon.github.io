<?php  
$con=mysqli_connect("localhost","root","autoset","test");  

mysqli_set_charset($con,"utf8");

$bbsno = $_GET['bbsno'];
$sear = "select * from we_bbs where bbsno=$bbsno";

$res = mysqli_query($con, $sear);
$result = array(); 

while($row=mysqli_fetch_array($res)){
	array_push($result, array('bbsno'=>$row[0],'title'=>$row[1],'content'=>$row[2],'seno'=>$row[3],'bbscode'=>$row[4],'pid'=>$row[5],'wether'=>$row[6],'tt'=>$row[7],'shott'=>$row[8],'cur_im'=>$row[9]));
}

echo json_encode(array("result"=>$result));
   
?>
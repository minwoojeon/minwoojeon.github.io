<?php  
$con=mysqli_connect("localhost","root","autoset","test");  

mysqli_set_charset($con,"utf8");

$bbsno = $_GET['bbsno'];
$sear = "select * from we_repl where bbsno=$bbsno";

$res = mysqli_query($con, $sear);
$result = array(); 

while($row=mysqli_fetch_array($res)){
	array_push($result, array('rno'=>$row[0],'pid'=>$row[1],'bbsno'=>$row[2],'content'=>$row[3],'ts'=>$row[4]));
}

echo json_encode(array("result"=>$result));
   
?>
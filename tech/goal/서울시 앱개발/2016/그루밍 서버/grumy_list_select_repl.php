<?php  
$con=mysqli_connect("localhost","root","autoset","test");  
 
  
if (mysqli_connect_errno($con))  
{  
   echo "Failed to connect to MySQL: " . mysqli_connect_error();  
}  
$bbsno = $_GET['bbsno'];

$res = mysqli_query($con,"select * from we_repl where bbsno = $bbsno ");

$result = array(); 
while($row=mysqli_fetch_array($res)){
	array_push($result, array('rno'=>$row[0],'pid'=>$row[1],'bbsno'=>$row[2],'content'=>$row[3],'ts'=>$row[4],));
}
  
echo json_encode(array("result"=>$result));
  
mysqli_close($con);  
?>
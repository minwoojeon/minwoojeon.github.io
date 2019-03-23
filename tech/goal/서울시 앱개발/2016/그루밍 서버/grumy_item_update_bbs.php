<?php  
$con=mysqli_connect("localhost","root","autoset","test");  
 
mysqli_set_charset($con,"utf8");
  
if (mysqli_connect_errno($con))  
{  
   echo "Failed to connect to MySQL: " . mysqli_connect_error();  
}  

$content = $_POST['content']; 
$bbsno = $_POST['bbsno']; 


$result = mysqli_query($con,"UPDATE we_bbs SET content = '$content' WHERE `bbsno` = $bbsno");  
  
  if($result){  
    echo 'success';  
  }  
  else{  
    echo 'failure';  
  }  
  
  
mysqli_close($con);  
?> 
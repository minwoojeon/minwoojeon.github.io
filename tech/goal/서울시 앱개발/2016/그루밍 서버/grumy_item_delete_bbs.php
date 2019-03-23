<?php  
$con=mysqli_connect("localhost","root","autoset","test");  
 
mysqli_set_charset($con,"utf8");
  
if (mysqli_connect_errno($con))  
{  
   echo "Failed to connect to MySQL: " . mysqli_connect_error();  
}  

$bbsno = $_POST['bbsno']; 
 

$result = mysqli_query($con,"DELETE FROM we_bbs WHERE `bbsno` = $bbsno");  
  
  if($result){  
    echo 'success';  
  }  
  else{  
    echo 'failure';  
  }  
  
  
mysqli_close($con);  
?> 
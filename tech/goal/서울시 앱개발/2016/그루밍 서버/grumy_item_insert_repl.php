<?php  
$con=mysqli_connect("localhost","root","autoset","test");  
 
mysqli_set_charset($con,"utf8");
  
if (mysqli_connect_errno($con))  
{  
   echo "Failed to connect to MySQL: " . mysqli_connect_error();  
}  
$rno = $_POST['rno']; 
$pid = $_POST['pid']; 
$bbsno = $_POST['bbsno']; 
$content = $_POST['content']; 


$result = mysqli_query($con,"insert into we_repl (rno,pid,bbsno,content,ts) values ('$rno','$pid','$bbsno','$content',now())");  
  
  if($result){  
    echo 'success';  
  }  
  else{  
    echo 'failure';  
  }  
  
  
mysqli_close($con);  
?> 
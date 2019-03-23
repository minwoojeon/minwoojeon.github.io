<?php  
$con=mysqli_connect("localhost","root","autoset","test");  
 
mysqli_set_charset($con,"utf8");
  
if (mysqli_connect_errno($con))  
{  
   echo "Failed to connect to MySQL: " . mysqli_connect_error();  
}  
$bbsno = $_POST['bbsno']; //->삭제
$title = $_POST['title']; 
$content = $_POST['content']; 
$seno = $_POST['seno']; 
$bbscode = $_POST['bbscode']; 
$pid = $_POST['pid'];  
$wether = $_POST['wether'];  

//$tt = $_POST['tt']; //->삭제
$cur_im = $_POST['cur_im'];

if($cur_im){
$result = mysqli_query($con,"insert into we_bbs (bbsno ,title, content, seno, bbscode, pid, wether, tt, shott, cur_im) values ('$bbsno','$title','$content','$seno','$bbscode','$pid','$wether',now(),0,'$cur_im')");  
}else{
$result = mysqli_query($con,"insert into we_bbs (bbsno ,title, content, seno, bbscode, pid, wether, tt, shott, cur_im) values ('$bbsno','$title','$content','$seno','$bbscode','$pid','$wether',now(),0,'default.jpge')");  
}
  
  if($result){  
    echo 'success';  
  }  
  else{  
    echo 'failure';  
  }  
  
  
mysqli_close($con);  
?> 
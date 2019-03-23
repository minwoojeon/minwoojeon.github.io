<?php  
$con=mysqli_connect("localhost","root","autoset","test");  
 

mysqli_set_charset($con,"utf8");
  

$a = 5001;

while($a<10000){
  $val = "";
  if($a%5 == 0){
    $val = "insert into we_bbs (bbsno,title,content,seno,bbscode,pid,wether,tt,shott,cur_im) values ($a,'$a + test','asdf',0,'02','test1',1,now(),0,'0')";
  }else if($a%5 == 1){
    $val = "insert into we_bbs (bbsno,title,content,seno,bbscode,pid,wether,tt,shott,cur_im) values ($a,'$a + test','asdf',0,'02','test1',2,now(),0,'0')";
  }else if($a%5 == 2){
    $val = "insert into we_bbs (bbsno,title,content,seno,bbscode,pid,wether,tt,shott,cur_im) values ($a,'$a + test','asdf',0,'02','test1',3,now(),0,'0')";
  }else if($a%5 == 3){
    $val = "insert into we_bbs (bbsno,title,content,seno,bbscode,pid,wether,tt,shott,cur_im) values ($a,'$a + test','asdf',0,'02','test1',4,now(),0,'0')";
  }else if($a%5 == 4){
    $val = "insert into we_bbs (bbsno,title,content,seno,bbscode,pid,wether,tt,shott,cur_im) values ($a,'$a + test','asdf',0,'02','test1',5,now(),0,'0')";
  }
  mysqli_query($con, $val);  
  $a += 1;
}
$a = 10000;

while($a<15000){
  $val = "";
  if($a%5 == 0){
    $val = "insert into we_bbs (bbsno,title,content,seno,bbscode,pid,wether,tt,shott,cur_im) values ($a,'$a + test','asdf',0,'02','test2',1,now(),0,'0')";
  }else if($a%5 == 1){
    $val = "insert into we_bbs (bbsno,title,content,seno,bbscode,pid,wether,tt,shott,cur_im) values ($a,'$a + test','asdf',0,'02','test2',2,now(),0,'0')";
  }else if($a%5 == 2){
    $val = "insert into we_bbs (bbsno,title,content,seno,bbscode,pid,wether,tt,shott,cur_im) values ($a,'$a + test','asdf',0,'02','test3',3,now(),0,'0')";
  }else if($a%5 == 3){
    $val = "insert into we_bbs (bbsno,title,content,seno,bbscode,pid,wether,tt,shott,cur_im) values ($a,'$a + test','asdf',0,'02','test2',4,now(),0,'0')";
  }else if($a%5 == 4){
    $val = "insert into we_bbs (bbsno,title,content,seno,bbscode,pid,wether,tt,shott,cur_im) values ($a,'$a + test','asdf',0,'02','test2',5,now(),0,'0')";
  }
  mysqli_query($con, $val);  
  $a += 1;
}
    
  
mysqli_close($con);  
?>
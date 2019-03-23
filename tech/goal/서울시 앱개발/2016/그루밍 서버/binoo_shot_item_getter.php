<?php  
//by junminwoo

  $tim = $_GET['tim'];
  $uid = $_GET['uid'];
  $bbcode = $_GET['bbcode'];

  $data = new item($tim, $uid, $bbcode);

  if($uid){
    $data->getShotSize(2);
    $data->getCurShotVal(2);
  }
  if($bbcode){
    $data->getShotSize(3);
    $data->getCurShotVal(3);
  }

  $data->getData();




class item{
 private $tim;
 private $uid;
 private $bbcode;
 private $con;

 private $a;
 private $b;
 private $result;

 function __construct($tim, $uid, $bbcode){
   $this->tim=$tim;
   $this->uid=$uid;
   $this->bbcode=$bbcode;
   $this->result=array(); 
   $this->con=mysqli_connect("localhost","root","autoset","test");
 }

 public function getShotSize($no){
     if($no == 1){
       $lec = "HitRateAllN";
       $res = mysqli_query($this->con,"select count(*) as 'shotsize' from we_bbs where tt < '$this->tim' and shott != 0");
     }else if($no == 2){
       $lec = "HitRateAllbyIDN";
       $res = mysqli_query($this->con,"select count(*) as 'shotsize' from we_bbs where tt < '$this->tim' and shott != 0 and pid = '$this->uid'");
     }else{
       $lec = "HitRateAllbyLOCN";
       $res = mysqli_query($this->con,"select count(*) as 'shotsize' from we_bbs where tt < '$this->tim' and shott != 0 and bbscode = $this->bbcode");
     }
     while($row=mysqli_fetch_array($res)){
	array_push($this->result, array('depall'=>$lec,'value'=>$row[0]));
        $this->b = $row[0];
     }
     //echo json_encode(array("result"=>$result));
 }
 public function endData(){
     mysqli_close($this->con);  
 }

 public function getCurShotVal($no){
     if($no == 1){
       $lec = "HitRate";
       $res = mysqli_query($this->con,"select count(*) as 'shotsize' from we_bbs where tt < '$this->tim' and shott = 2");
     }else if($no == 2){
       $lec = "HitRatebyID";
       $res = mysqli_query($this->con,"select count(*) as 'shotsize' from we_bbs where tt < '$this->tim' and shott = 2 and pid = '$this->uid'");
     }else{
       $lec = "HitRatebyLOC";
       $res = mysqli_query($this->con,"select count(*) as 'shotsize' from we_bbs where tt < '$this->tim' and shott = 2 and bbscode = $this->bbcode");
     }
     while($row=mysqli_fetch_array($res)){
	array_push($this->result, array('dep'=>$lec,'value'=>$row[0]));
        $this->a = $row[0];
     }
   $this->getRate();
 }
 private function getRate(){
   echo "\nhit_rate is ";
   echo ($this->a / $this->b * 100);
   echo "%";
 }
 public function getData(){
     echo json_encode(array("result"=>$this->result));   
 }
}
  

 
?>
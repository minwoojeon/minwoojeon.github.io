<?php  
//by junminwoo
$id = $_GET['id'];
$type = $_GET['type'];
$name = $_GET['name'];
echo $id;
if(!$id){
  echo "failure";
}else{
  $data = new item($id,$type,$name);
  $data->getLogin();
  $data->getData();
}

class item{
 private $id;
 private $type;
 private $name;
 private $con;

 function __construct($id, $type, $name){
   $this->id=$id;
   $this->type=$type;
   $this->name=$name;
   $this->con=mysqli_connect("localhost","root","autoset","test");
 }
 public function getLogin(){
   $res = mysqli_query($this->con,"select * from we_person where pid = '$this->id'");
   if($res){
     $re2 = mysqli_query($this->con,"insert into we_person(pid, ptype, puname, point) values('$this->id','$this->type','$this->name','')");
   }
 }
 public function getData(){
     $result = array(); 
     $res = mysqli_query($this->con,"select * from we_person where pid = '$this->id'");
     while($row=mysqli_fetch_array($res)){
	array_push($result, array('id'=>$row[0],'type'=>$row[1],'name'=>$row[2]));
     }
     echo json_encode(array("result"=>$result));
     mysqli_close($this->con);  
 }

}
  

 
?>
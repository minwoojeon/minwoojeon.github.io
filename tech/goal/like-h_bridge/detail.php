<?php
	if (!isset($_SESSION)) session_start();
	$user_id = $_SESSION['user_id'];

	// 사용자 세션이 없는 경우.
	if (!isset($user_id)) {
		header("Location:login.php"); 
		exit;
	}
	// 접근 경로/주소/난수 검증
	if(!isset($_SESSION['cfn_enc']) || !isset($_SESSION['cfn_svc_path']) || !isset($_SESSION['cfn_usr_addr']) || !isset($_SESSION['cfn_usr_rn1']) 
		|| !isset($_SESSION['cfn_usr_rn2']) || !isset($_SESSION['cfn_usr_rn3']) || !isset($_SESSION['cfn_usr_rn'])){
		// 접근 오류.
		session_destroy();
		header("Location:login.php"); 
		exit;
	}
	$cfn_usr_rn = '' . base64_encode('' . $_SESSION['cfn_usr_rn1'] . '' . $_SERVER['SERVER_NAME'] . '' . $_SESSION['cfn_usr_rn2'] . '' . base64_encode($_SERVER['DOCUMENT_ROOT']) . '' . $_SERVER['REMOTE_ADDR'] . '' . $_SESSION['cfn_usr_rn3']);
	if(($_SESSION['cfn_enc'] != $_SERVER['SERVER_NAME'])
		|| ($_SESSION['cfn_svc_path'] != $_SERVER['DOCUMENT_ROOT'])
		|| ($_SESSION['cfn_usr_addr'] != $_SERVER['REMOTE_ADDR'])
		|| ($_SESSION['cfn_usr_rn'] != $cfn_usr_rn) ){
		// 세션 오류 (중간에 세션이 끊기거나 변조가 일어남.)
		session_destroy();
		header("Location:login.php"); 
		exit;
	}

	if(!isset($_POST['type'])){
		?> 
		<script> 
			alert("파라미터가 변조되었습니다. 다시 조회하여 주세요~"); 
			document.location.href = "index.php"; 
		</script> 
		<?
		exit;
	}
	
	$HEADER_CONS_VALUES = 1;
	$API_KEY = "AIzaSyA65uCkShenE0TcfRcgTIuveOZP0aMGS7A";
	$PROJECT_ID = "do-dream";
	include_once('header.php');
	include_once('navigation.php');

	$type = $_POST['type'];
	// M 수정 화면
	// I 추가 화면
	// A 추가 
	// D 삭제
	// U 수정
	$target = $_POST['target'];
	$starget = $_POST['starget'];

	$mapurl = isset($_POST['map_url']) ? $_POST['map_url'] : '';
	$causetxt = isset($_POST['causetxt']) ? $_POST['causetxt'] : '';
?>

<script>
	var fn_index = function (){
		var  frm = document.getElementById('frm');
		frm.method = "POST";
		frm.action = "index.php";
		var target = document.getElementById('target');
		target.disabled = true;
		frm.submit();
	};
	var fn_modify = function (){
		var  frm = document.getElementById('frm');
		frm.method = "POST";
		frm.action = "detail.php";
		var type = document.getElementById('type');
		type.value = '<? echo '' . ($type == "I" ? "A" : "U"); ?>';
		var keyword = document.getElementById('target');
		var map_url = document.getElementById('map_url');
		if((keyword.value +' ').trim() == '' || (map_url.value +' ').trim() == ''){
			alert("입력값을 확인해주세요.");
			return;
		}
		var target = document.getElementById('target');
		target.disabled = false;
		frm.submit();
	};
	<? if($type == "M"){ ?>
	var isChkD = false;
	var fn_delete = function (){
		var  frm = document.getElementById('frm');
		frm.method = "POST";
		frm.action = "detail.php";
		var type = document.getElementById('type');
		type.value = "D";
		if(!isChkD){
			$("#modal-notification").modal();
			return false;
		}
		if(isChkD){
			var target = document.getElementById('target');
			target.disabled = false;
			frm.submit();
		}
	};
	<? } ?>
</script>

<div style="width: 60rem;margin:15;">
	<div class="ct-page-title">
		<h1 class="ct-title">키워드별 매핑 수정하기</h1>
		<div class="avatar-group mt-3" id="avatar-group">
			
			<script src="<? echo '' . $src_url; ?>/assets/vendor/jquery/dist/jquery.min.js"></script>
			<script>
				var isAuth = false;
				var impfnCfg = (function(){
					var config = {
						apiKey: "<? echo '' . $API_KEY; ?>",
						authDomain: "<? echo '' . $PROJECT_ID; ?>.firebaseapp.com",
						databaseURL: "https://<? echo '' . $PROJECT_ID; ?>.firebaseio.com",
						storageBucket: "<? echo '' . $PROJECT_ID; ?>.appspot.com"
					};
					firebase.initializeApp(config);
					
					window.onload = function() {
						// 매핑 게시판 접근 권한 확인하기
						var database = firebase.database();
						var input_id = '<? echo '' . $_SESSION['user_id']; ?>';
						var test = database.ref('like-h/allows/ip-name').once('value').then(function(snapshot) {
							snapshot.forEach(function(e){
								if(input_id == e.val().name){
									isAuth = true;
								}
							});
							if(!isAuth){
								alert("세션이 종료되었습니다. 다시 로그인 해주세요.");
								document.location.href = "logout.php";
							}
							$("#cols").html(" ");
						});
					};

					<? if($type == "A"){ ?>
					// 매핑 게시판 하나 추가하기
						var default_list_main = "index.php";	// 디폴트 이동 url 세팅하기
						var impfnAddOne = (function (){
							var database = firebase.database();
							var keyword = '<? echo '' . $target; ?>';
							var postData = {
								auth: '<? echo '' . $_SESSION['user_id']; ?>',
								regdt: '<? echo '' . date("YmdHis"); ?>',
								map_url: '<? echo '' . $mapurl; ?>',
								causetxt: '<? echo '' . $causetxt; ?>'
							};
							if(keyword == '' || keyword == 'undefined') return false;
							var test = database.ref('like-h/map_urls/'+keyword).set(postData).then(function(e){
								document.location.href = default_list_main;
							});
						})();
					<? } else if($type == "D"){ ?>
					// 매핑 게시판 하나 삭제하기
						var default_list_main = "index.php";	// 디폴트 이동 url 세팅하기
						var impfnDelOne = (function (){
							var database = firebase.database();
							var keyword = '<? echo '' . $target; ?>';
							if(keyword == '' || keyword == 'undefined') return false;
							var test = database.ref('like-h/map_urls/'+keyword).set(null).then(function(e){
								document.location.href = default_list_main;
							});
						})();
					<? } else if($type == "U"){ ?>
					// 매핑 게시판 하나 수정하기
						var default_list_main = "index.php";	// 디폴트 이동 url 세팅하기
						var impfnModify = (function (){
							var database = firebase.database();
							var keyword = '<? echo '' . $target; ?>';
							var postData = {
								auth: '<? echo '' . $_SESSION['user_id']; ?>',
								regdt: '<? echo '' . date("YmdHis"); ?>',
								map_url: '<? echo '' . $mapurl; ?>',
								causetxt: '<? echo '' . $causetxt; ?>'
							};
							if(keyword == '' || keyword == 'undefined') return false;
							database.ref('like-h/map_urls/'+keyword).set(null).then(function(e){
								database.ref('like-h/map_urls/'+keyword).set(postData).then(function(e){
									document.location.href = default_list_main;
								});
							});
						})();
					<? } else if($type == "M"){ ?>
					// 매핑 게시판 수정 화면
						var impfnReadOne = (function (){
							var database = firebase.database();
							var keyword = '<? echo '' . $target; ?>';
							var test = database.ref('like-h/map_urls/'+keyword).once('value').then(function(snapshot) {
								var items = { keys : new Array(), values : new Array() };
								var keyPt = 0;
								snapshot.forEach(function(e){
									items.keys[keyPt] = e.key;
									items.values[keyPt] = e.val();
									keyPt++;
									// 작성자, 작성일시, 매핑url
								});
								$('#map_url').val(snapshot.val().map_url);
								$('#causetxt').val(snapshot.val().causetxt);
								var regdt = snapshot.val().regdt + '';
								regdt = regdt.substring(0,4)+"-"+regdt.substring(4,6)+" "+regdt.substring(6,8)+":"+regdt.substring(8, 10)+":"+regdt.substring(10);
								$('#regdt').text(snapshot.val().auth + ' / ' + regdt);
							});
						})();
					<? } ?>

					$("#avatar-group").html(" ");
				})();
			</script>
          </div>
        </div>

	<form id="frm">
	  <input type="hidden" name="type" id="type">
	  <div class="row">
		<div class="col-md-6">
		  <div class="form-group" style="text-align:center;">
			키워드
		  </div>
		</div>
		<div class="col-md-6">
		  <div class="form-group">
			<input type="text" placeholder="Keyword" class="form-control" id="target" name="target" <? echo ''. ($type == "I" ? ' ' : 'disabled'); ?> value="<? echo '' . $target; ?>" />
		  </div>
		</div>
	  </div>

	  <div class="row">
		<div class="col-md-6">
		  <div class="form-group" style="text-align:center;">
			매핑 URL
		  </div>
		</div>
		<div class="col-md-6">
		  <div class="form-group">
			<input type="text" placeholder="url" class="form-control" id="map_url" name="map_url" value="" />
		  </div>
		</div>
	  </div>
	  
		<? if($type == "M"){ ?>
	  <div class="row">
		<div class="col-md-6">
		  <div class="form-group" style="text-align:center;">
			최종 작성자/최종 수정 시간
		  </div>
		</div>
		<div class="col-md-6">
		  <div class="form-group">
			<span id="regdt"></span>
		  </div>
		</div>
	  </div>
		<? } ?>

	  비고
	  <textarea class="form-control form-control-alternative" rows="3" id="causetxt" name="causetxt" placeholder="비고 및 설명 내용을 작성합니다."></textarea>
	</form>
	
	<div class="row justify-content-md-center">
		<div class="col-md-3">
			<button class="btn btn-icon btn-3 btn-primary btn-lg btn-block" type="button" onclick="fn_index()">
				<span class="btn-inner--icon"><i class="ni ni-bullet-list-67"></i></span>
				<span class="btn-inner--text">목록으로</span>
			</button>
		</div>
		<div class="col-md-3">
			<button class="btn btn-icon btn-3 btn-primary btn-lg btn-block" type="button" onclick="fn_modify()">
				<span class="btn-inner--icon"><i class="ni ni-fat-add"></i></span>
				<span class="btn-inner--text"><? echo ''. ($type == "I" ? '추가' : '수정'); ?>하기</span>
			</button>
		</div>
		<? if($type == "M"){ ?>
		<div class="col-md-3">
			<button class="btn btn-icon btn-3 btn-primary btn-lg btn-block" type="button" onclick="fn_delete()">
				<span class="btn-inner--icon"><i class="ni ni-fat-delete"></i></span>
				<span class="btn-inner--text">삭제하기</span>
			</button>
		</div>
		<? } ?>
	</div>
</div>

<div class="col-md-4">
      <div class="modal fade" id="modal-notification" tabindex="-1" role="dialog" aria-labelledby="modal-notification" aria-hidden="true">
    <div class="modal-dialog modal-danger modal-dialog-centered modal-" role="document">
        <div class="modal-content bg-gradient-danger">
        	
            <div class="modal-header">
                <h6 class="modal-title" id="modal-title-notification">경고 - 복구가 불가능한 작업입니다!</h6>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            
            <div class="modal-body">
            	
                <div class="py-3 text-center">
                    <i class="ni ni-bell-55 ni-3x"></i>
                    <h4 class="heading mt-4">키워드를 삭제합니다.</h4>
                    <p>존재하는 키워드에 대해 삭제를 하시는 경우, 기존 내용은 삭제됩니다.</p>
                </div>
                
            </div>
            
            <div class="modal-footer">
                <button type="button" class="btn btn-white" onclick="isChkD=true;fn_delete();">잘 알고 있습니다.</button>
                <button type="button" class="btn btn-link text-white ml-auto" data-dismiss="modal">취소</button> 
            </div>
            
        </div>
    </div>

<?
	include_once('footer.php');
?>
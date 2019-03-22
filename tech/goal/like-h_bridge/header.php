<?
	if (!isset($HEADER_CONS_VALUES)) {
		header("Location:index.php"); 
		exit;
	}

	$src_url = "/api/like-h/";
?>
	<html>
	<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<!-- Favicon -->
	<link href="<? echo '' . $src_url; ?>/assets/img/brand/favicon.png" rel="icon" type="image/png">
	<!-- Fonts -->
	<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700" rel="stylesheet">
	<!-- Icons -->
	<link href="<? echo '' . $src_url; ?>/assets/vendor/nucleo/css/nucleo.css" rel="stylesheet">
	<link href="<? echo '' . $src_url; ?>/assets/vendor/@fortawesome/fontawesome-free/css/all.min.css" rel="stylesheet">
	<!-- Argon CSS -->
	<link type="text/css" href="<? echo '' . $src_url; ?>/assets/css/argon.min.css" rel="stylesheet">


	<script src="<? echo '' . $src_url; ?>/assets/vendor/jquery/dist/jquery.min.js"></script>
	<script src="https://www.gstatic.com/firebasejs/5.8.6/firebase.js"></script>
	<script src="https://www.gstatic.com/firebasejs/5.8.6/firebase-app.js"></script>
	<script src="https://www.gstatic.com/firebasejs/5.8.6/firebase-auth.js"></script>
	<script src="https://www.gstatic.com/firebasejs/5.8.6/firebase-database.js"></script>
		
	</head>
	<body>

<?
	if (!isset($HEADER_CONS_VALUES)) {
		header("Location:index.php"); 
		exit;
	}
?>
	<!-- Core -->
	<script src="<? echo '' . $src_url; ?>/assets/vendor/jquery/dist/jquery.min.js"></script>
	<script src="<? echo '' . $src_url; ?>/assets/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
	<!-- Argon JS -->
	<script src="<? echo '' . $src_url; ?>/assets/js/argon.min.js"></script>
	</body></html>
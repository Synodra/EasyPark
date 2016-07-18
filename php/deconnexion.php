<?php
	session_start();

	if(isset($_POST['soumettre'])){
		$_SESSION = array();
		session_destroy();
		
		header('Location: ../accueil.php');
	}
?>		
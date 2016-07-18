<?php
	try{
		$bdd= new PDO('mysql:host=localhost;dbname=parkin;charset=utf8', 'root', '');
	}
	catch (Exeception $e)
	{
		die('Erreur : '. $e->getMessage());
	}
?>
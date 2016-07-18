<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>PARK'IN Registration</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />

		<!-- CSS style sheet -->
		<link href="ccs/navbar-fixed-top.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css" />

		<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
	</head>
	<body>
	<?php
		if(isset($_POST['submit'])){
			
		// test si email est confirmé	
		if($_POST['InputEmail1'] == $_POST['InputEmail2'])
		{
			// test si password est confirmé
			if($_POST['Inputpasswordl'] == $_POST['Inputpassword2'])
			{
				// connexion à la BDD
				try{
					$bdd= new PDO('mysql:host=localhost;dbname=parkin;charset=utf8', 'root', '');
				}
				catch (Exeception $e)
				{
					die('Erreur : '. $e->getMessage());
				}
			
				$reponse = $bdd->query('SELECT * FROM utilisateurs WHERE adressemail =\''.$_POST['InputEmail1'].'\'');
				$donnees = $reponse->fetch();
				
				// vérification email non utilisé
				if($donnees == 0) {
					// ajout BDD
					$req = $bdd->prepare("INSERT INTO utilisateurs(nom, prenom, motdepasse, adressemail) VALUES(:nom, :prenom, :password,:email)");
					$req->execute(array("nom" => $_POST['Inputnom'],"prenom" => $_POST['Inputprenom'],"email" => $_POST['InputEmail1'], "password" => $_POST['Inputpasswordl']));
					echo "Succes";
				}
				// sinon erreur
				else {
					echo "erreur : l'adresse mail est déjà utilisée";
				}
			}
			// sinon erreur
			else {
				echo "erreur : les mot de passes ne sont pas les mêmes !";
			}
		}
		// sinon erreur
		else{
			echo "erreur : les deux adresse mail ne sont pas les mêmes !";
		}
		}
	?>	
	</body>
</html>
<?php
	session_start();
		if(isset($_POST['soumettre'])){
			include_once('connexion_sql.php');
			$reponse = $bdd->query('SELECT * FROM kaggleutils WHERE adressemail =\''. $_POST['email']. '\'');
			$donnees = $reponse->fetch();
			if($donnees == 0) {
				echo '<h3> Nom d\'utilisateur erroné!</h3>';
			}
			else {
				if($_POST['pass'] != $donnees['motdepasse']) {
					echo '<h3>Mot de passe incorrect !</h3>';
				}
				else {
					echo '<h3> Mot de passe accepté</h3>';
					$_SESSION['prenom'] = $donnees['prenom'];
					$_SESSION['nom'] = $donnees['nom'];
					$_SESSION['id'] = $donnees['id'];
					$_SESSION['id_equipe'] = $donnees['equipe_id'];
					header('Location: ../id_main.php');
				}
			}
		}
		else {
			print_form();
		}
?>
			
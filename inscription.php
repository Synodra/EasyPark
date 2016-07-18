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
	<!-- Fixed navbar -->
		<nav class="navbar navbar-default navbar-fixed-top">
		  <div class="container">
			<div class="navbar-header">
			  <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			  </button>
			  <a class="navbar-brand" href="index.php">PARK'IN</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
			  <ul class="nav navbar-nav">
				<li><a href="index.php">Accueil</a></li>
				<li><a href="apropos.php">A Propos</a></li>
				<li><a href="contact.php">Contact</a></li>
				<li class="dropdown">
				  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
				  <ul class="dropdown-menu">
					<li><a href="#">Action</a></li>
					<li><a href="#">Another action</a></li>
					<li><a href="#">Something else here</a></li>
					<li role="separator" class="divider"></li>
					<li class="dropdown-header">Nav header</li>
					<li><a href="#">Separated link</a></li>
					<li><a href="#">One more separated link</a></li>
				  </ul>
				</li>
			  </ul>
			  <ul class="nav navbar-nav navbar-right">
				<li class="active"><a href="inscription.php">Inscription</a></li>
				<li><a href="connexion.php">Connexion <span class="sr-only">(current)</span></a></li>
			  </ul>
			</div><!--/.nav-collapse -->
		  </div>
		</nav>

	<div class="container">

	<div class="page-header">
		<h1><br /></h1>
		<h1>Registration form for PARK'IN</h1>
	</div>

	<!-- Registration form - START -->
	<div class="container">
		<div class="row">
			<form method="post" action="php/inscription.php">
				<div class="col-lg-6">
					<div class="well well-sm"><strong><span class="glyphicon glyphicon-asterisk"></span>Required Field</strong></div>
					<div class="form-group">
						<label for="InputName">Votre Prénom</label>
						<div class="input-group">
							<input type="text" class="form-control" name="Inputprenom" id="InputName" placeholder="Enter Prenom" required>
							<span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
						</div>
					</div>
					<div class="form-group">
						<label for="InputName">Votre Nom</label>
						<div class="input-group">
							<input type="text" class="form-control" name="Inputnom" id="InputName" placeholder="Enter Nom" required>
							<span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
						</div>
					</div>
					<div class="form-group">
						<label for="InputEmail">Votre Email</label>
						<div class="input-group">
							<input type="email" class="form-control" id="InputEmailFirst" name="InputEmail1" placeholder="Enter Email" required>
							<span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
						</div>
					</div>
					<div class="form-group">
						<label for="InputEmail">Confirmer Email</label>
						<div class="input-group">
							<input type="email" class="form-control" id="InputEmailSecond" name="InputEmail2" placeholder="Confirm Email" required>
							<span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
						</div>
					</div>
					<div class="form-group">
						<label for="InputEmail">Votre Mot de passe</label>
						<div class="input-group">
							<input type="password" class="form-control" id="InputpasswordFirst" name="Inputpasswordl" placeholder="Enter Password" required>
							<span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
						</div>
					</div>
					<div class="form-group">
						<label for="InputEmail">Confirmer Mot de passe</label>
						<div class="input-group">
							<input type="password" class="form-control" id="InputpasswordSecond" name="Inputpassword2" placeholder="Confirm Password" required>
							<span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
						</div>
					</div>
					<input type="submit" name="submit" id="submit" value="Submit" class="btn btn-info pull-right">
				</div>
			</form>
		</div>
	</div>
	<!-- Registration form - END -->

	</div>

	</body>
</html>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>EASYPARK INSCRIPTION</title>
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
                <a class="navbar-brand" href="index.php">EASYPARK</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="index.php">ACCUEIL</a></li>
                    <li><a href="apropos.php">A PROPOS</a></li>
                    <li><a href="contact.php">CONTACT</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="inscription.php">INSCRIPTION<span class="sr-only">(current)</span></a></li>
                    <li><a href="connexion.php">CONNEXION</a></li>
                </ul>
            </div><!--/.nav-collapse -->
        </div>
    </nav>

	<div class="container">

	<div class="page-header">
		<h1><br /></h1>
		<h1>INSCRIPTION POUR EASYPARK</h1>
	</div>

	<!-- Registration form - START -->
	<div class="container">
		<div class="row">
			<form role="form" id="inscriptionForm">
				<div class="col-lg-6">
					<div class="well well-sm"><strong><span class="glyphicon glyphicon-asterisk"></span>Required Field</strong></div>
					<div class="form-group">
						<label for="InputName">Votre Prénom</label>
						<div class="input-group">
							<input type="text" class="form-control" name="firstname" id="firstname" placeholder="Enter Prenom" required>
							<span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
						</div>
					</div>
					<div class="form-group">
						<label for="InputName">Votre Nom</label>
						<div class="input-group">
							<input type="text" class="form-control" name="name" id="name" placeholder="Enter Nom" required>
							<span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
						</div>
					</div>
					<div class="form-group">
						<label for="InputEmail">Votre Email</label>
						<div class="input-group">
							<input type="email" class="form-control" id="email" name="email" placeholder="Enter Email" required>
							<span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
						</div>
					</div>
					<div class="form-group">
						<label for="InputEmail">Votre Mot de passe</label>
						<div class="input-group">
							<input type="password" class="form-control" id="password" name="password" placeholder="Enter Password" required>
							<span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
						</div>
					</div>
					<input type="submit" name="submit" id="submit" value="Submit" class="btn btn-info pull-right" >
				</div>
			</form>
		</div>
	</div>
	<!-- Registration form - END -->

	</div>

	</body>

</html>


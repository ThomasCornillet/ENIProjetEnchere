<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html>


<html>

	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<title>Modifier le profil</title>
		<!-- import des feuilles de styles bootstrap, j'ai mis la version sur le web, c'est plus simple pour l'instant -->
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
		crossorigin="anonymous">
		<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	</head>
	
	<body>
		
		
		<div class="text-center" id ="titre" >
			<h1 id="titre">Eni Enchères !</h1>
			<p id ="Paragraphe">Bonjour ${sessionScope.sessionUtilisateur.prenom}.</p>
			<p id = "Paragraphe">Vous avez ${sessionScope.sessionUtilisateur.credit} crédit(s)</p>
		</div>
	

<div class="container">



	<div class="row justify-content-center">
		<div class="col-md-6">
			<div class="card">
				
				<article class="card-body">
				
					<form action="./ModifierProfil" method="post">
						<div class="form-row">
							<div class="col form-group">
								<label>Pseudo </label> <input type="text" class="form-control"
									placeholder="" name="sPseudo" value=${Pseudo} minlgth="2" maxlength="30">
							</div>
							<!-- form-group end.// -->
							<div class="col form-group">
								<label>Nom</label> <input type="text" class="form-control"
									placeholder=" " name="sNom" value=${Nom} pattern="[^0-9]{3,30}" >
							</div>
							<!-- form-group end.// -->
						</div>
						<!-- form-row end.// -->
						<div class="form-row">
							<div class="col form-group">
								<label>Prénom </label> <input type="text" class="form-control"
									placeholder="" name="sPrenom" value=${Prenom} pattern="[^0-9]{3,30}">
							</div>
							<!-- form-group end.// -->
							<div class="col form-group">
								<label>Email</label> <input type="email" class="form-control"
									placeholder="" name="sEmail" required value=${Mail} maxlength="50"> <small
									class="form-text text-muted">Votre adresse mail ne sera
									pas partagée.</small>
							</div>
							<!-- form-group end.// -->
						</div>
						<!-- form-row end.// -->
						
						<div class="form-row">
							<div class="col form-group">
								<label>Téléphone </label> <input type="text"
									class="form-control" placeholder="" name="sTelephone" value=${Telephone} pattern="[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}">
							</div>
							<!-- form-group end.// -->
							<div class="col form-group">
								<label>Rue</label> <input type="text" class="form-control"
									placeholder=" " name="sRue" value=${Rue} maxlength="50">
							</div>
							<!-- form-group end.// -->
						</div>
						<!-- form-row end.// -->

						<div class="form-row">
							<div class="col form-group">
								<label>Code Postal </label> <input type="text"
									class="form-control" placeholder="" name="sCp" value=${Cp} max="99999" pattern="[0-9]{5}">
							</div>
							<!-- form-group end.// -->
							
							<div class="col form-group">
								<label>Ville</label> <select id="inputState"
									class="form-control" name="sVille" value=${Ville}>
									<option>Sélectionner une ville</option>
									<option>Paris</option>
									<option>Bordeaux</option>
									<option selected="">Lille</option>
									<option>Toulon</option>
									<option>Marseille</option>
								</select>
							</div>
							<!-- form-group end.// -->
						</div>
						<!-- form-row end.// -->

						<div class="form-row">
							<div class="col form-group">
								<label>Modifier le mot de passe </label> <input class="form-control"
									type="password" name="sMdp" required="required" maxlength="30" value=${Mdp}>
							</div>
							<!-- form-group end.// -->
							
							<!-- form-group end.// -->
						</div>
						<!-- form-row end.// -->
							<div class="form-group">
							<button type="submit" class="btn btn-primary btn-block"
								value="valider" name="choix">Enregistrer les
								modifications</button><br>
								</form>
									<form action="./ModifierMonProfil" method="get">	
						<a href="./ServSuppression"><button type="submit" class="btn btn-primary btn-block"
								value="supprimer" name="choix">Supprimer mon compte</button></a><br>
													
							
						<a href="./Profil">	<button type="submit" class="btn btn-primary btn-block"
								value="retour" name="choix">Retour</button> </a>
							</form>
						</div>
						<!-- form-group// -->
						
					
				</article>
				
				<!-- card-body end .// -->
				
					
					
</div>

			<!-- card.// -->
		
		
		
		
		
	</body>
	
	
	
</html>
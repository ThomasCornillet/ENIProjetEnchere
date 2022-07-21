<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Modifier le profil</title>
<!-- import des feuilles de styles bootstrap, j'ai mis la version sur le web, c'est plus simple pour l'instant -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
<!-- AJOUTER HEADER (via fragment)  -->
	<c:if test="${!empty listeCodesErreur}">
		<div class="container">
			<div class="row">
		        <div class="col">
		          <div class="alert alert-danger alert-dismissible fade show" role="alert">
		          	<h4 class="alert-heading">Erreur</h4>
		          	<ul>
			          	<c:forEach var="code" items="${listeCodesErreur}">
			          		<li>${LecteurMessage.getMessageErreur(code)}</li>
			          	</c:forEach>
		          	</ul>
		          </div>
		         </div>
		     </div>
		</div>
	</c:if>
	
	<div class="container">
		<div class="form-group row">
			<h1 class="col-12 col-form-title row justify-content-center" >Mon profil</h1> <!-- TODO Centrer -->
				</div>
					<form method="post"	action="${pageContext.request.contextPath }/modificationProfile">
						<div class="form-group row">			
								<label class="col-2 col-form-label" for="pseudo">Pseudo : </label> 
								<input class="form-control col-4"  type="text" id="pseudo" name="pseudo" />
									
								<label class="col-2 col-form-label" for="nom">Nom : </label> 
								<input class="form-control col-4" type="text" id="nom" name="nom" />
						</div>
						<div class="form-group row">				
								<label class="col-2 col-form-label" for="prenom">Prenom : </label> 
								<input class="form-control col-4" type="text" id="prenom" name="prenom" />
								
								<label class="col-2 col-form-label" for="email">Adresse email : </label> 
								<input class="form-control col-4" type="email" id="email" name="email" />
						</div>
						<div class="form-group row">		
								<label class="col-2 col-form-label" for="telephone">Téléphone : </label> 
								<input class="form-control col-4" type="text" id="telephone" name="telephone"/>
								
								<label class="col-2 col-form-label" for="rue">Rue : </label> 
								<input class="form-control col-4" type="text" id="rue" name="rue" />
						</div>
						<div class="form-group row">		
								<label class="col-2 col-form-label" for="codePostal">Code postal : </label> 
								<input class="form-control col-4" type="text" id="codePostal" name="codePostal" />
						
								<label class="col-2 col-form-label" for="ville">Ville : </label> 
								<input class="form-control col-4" type="text" id="ville" name="ville" />
						</div>
						<div class="form-group row">		
								<label class="col-2 col-form-label" for="motDePasseActuel" >Mot de passe actuel: </label> 
								<input class="form-control col-4" type="password" id="motDePasseActuel" name="motDePasseActuel" required />
						</div>
						<div class="form-group row">		
								<label class="col-2 col-form-label" for="nouveauMotdepasse">Nouveau mot de passe : </label> 
								<input class="form-control col-4" type="password" id="nouveauMotdepasse" name="nouveauMotdepasse" />
								
								<label class="col-2 col-form-label" for="nouveauMotdepasseConfirmation">Confirmation : </label> 
								<input class="form-control col-4" type="password" id="nouveauMotdepasseConfirmation" name="nouveauMotdepasseConfirmation" />
						</div>
					
						<div class="form-group row">		
								<label class="col-2 col-form-label" for="motdepasse">Crédit : </label> <!-- Récupérer le crédit de X --> 
								
						</div>
						<div class="form-group row justify-content-center">		
								<input	class="btn btn-primary btn-lg col-3 " type="submit" value="Enregistrer"/>
								<a href="${pageContext.request.contextPath }/accueil"  class="btn btn-secondary btn-lg col-3 offset-1 "  role="button" aria-pressed="true">Supprimer mon compte</a>
						</div>
					</form>
	</div>


<!-- import javascript pour Boostrap -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"	crossorigin="anonymous"></script>
</body>
</html>
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
<title>Inscription</title>
<!-- import des feuilles de styles bootstrap, j'ai mis la version sur le web, c'est plus simple pour l'instant -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
</head>
<body>
	
	
	<form method="post"
		action="${pageContext.request.contextPath }/ServletInscription">
		<fieldset>

			<legend>Mon profil</legend>

			<label for="nom">Pseudo :<span class="requis">*</span></label> 
			<input type="text" id="pseudo" name="pseudo"	value="<c:out value="${utilisateur.pseudo}"/>" />
			
			<label for="nom">Nom :<span class="requis">*</span></label> 
			<input type="text" id="nom" name="nom"	value="<c:out value="${utilisateur.nom}"/>" />
			
			<label for="nom">Prenom :<span class="requis">*</span></label> 
			<input type="text" id="Prenom" name="Prenom"	value="<c:out value="${utilisateur.prenom}"/>" />
			
			<label for="nom">Adresse email :<span class="requis">*</span></label> 
			<input type="email" id="email" name="email"	value="<c:out value="${utilisateur.email}"/>" />
			
			<label for="nom">Téléphone :</label> 
			<input type="text" id="telephone" name="telephone"	value="<c:out value="${utilisateur.telephone}"/>" />
			
			<label for="nom">Rue :<span class="requis">*</span></label> 
			<input type="text" id="rue" name="rue"	value="<c:out value="${utilisateur.rue}"/>" />
			
			<label for="nom">Code postal :<span class="requis">*</span></label> 
			<input type="text" id="codePostal" name="codePostal"	value="<c:out value="${utilisateur.codePostal}"/>" />
			
			<label for="nom">Ville :<span class="requis">*</span></label> 
			<input type="text" id="ville" name="ville"	value="<c:out value="${utilisateur.ville}"/>" />
			
			<label for="motdepasse">Mot de passe :<span class="requis">*</span></label> 
			<input type="password" id="motdepasse" name="motdepasse" value="" />
			
			<label for="motdepasseConfirmation">Confirmation :<span class="requis">*</span></label> 
			<input type="password" id="motdepasseConfirmation" name="motdepasseConfirmation" value="" />
			
			<span> <input	type="submit" value="Créer" class="sansLabel" />
			<input	type="button" href="${pageContext.request.contextPath }/ServletAccueil" value="Annuler" class="sansLabel" /> </span>



			
		
		</fieldset>
	</form>



<!-- import javascript pour Boostrap -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
	integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
	integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
	integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
	crossorigin="anonymous"></script>
</body>
</html>
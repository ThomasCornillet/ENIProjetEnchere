<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="fr.eni.encheres.exceptions.LecteurMessage" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<head>
	<jsp:include page="/WEB-INF/jsp/fragments/head.jsp"></jsp:include>
	<title>Authentification</title>
</head>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/fragments/header.jsp"></jsp:include>

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
	${filtreInterdit != null}
	<c:if test="${filtreInterdit != null}">
		<div class="container">
			<div class="row alert alert-warning alert-dismissible fade show" role="alert">
				<h4 class="col alert-heading">Vous devez être connecté(e) pour accéder à la page demandée</h4>
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
   					 <span aria-hidden="true">&times;</span>
 				</button>
			</div>
		</div>
	</c:if>
	
	
	<div class="container">
		<form method="post" action="${pageContext.request.contextPath }/authentification">
			<div class="form-group row">
				<label class="col-2 col-form-label" for="identifiant">Identifiant :</label>
				<input class="form-control col-4" type="text" id="identifiant" name="identifiant" placeholder="votre mail ou pseudo" required/> 
			</div>	
			<div class="form-group row">
				<label class="col-2 col-form-label" for="motdepasse">Mot de passe :</label>
				<input class="form-control col-4" type="password" id="motdepasse" name="motdepasse" required/>
			</div>
			<div class="form-group row">
				<input class="col-2" type="submit" value="Connexion"/>
				<div class="col-10">
					<input type="checkbox" id="se souvenir de moi" name="se souvenir de moi" />
					<label for="se souvenir de moi">Se souvenir de moi</label>
					<p>
						<a href="${pageContext.request.contextPath }/recuperationMotDePasse">Mot de passe oublié</a>
					</p>
				</div>
			</div>
		</form>
				
		<div class="row">
		<a href="${pageContext.request.contextPath }/inscription"  class="btn btn-secondary col-3 offset-1 "  role="button" aria-pressed="true">Créer un compte</a>		
			
		</div>
				
		
	</div>
	<%-- Vérification de la présence d'un objet utilisateur en session --%>
		<c:if test="${connecte}">
			<%-- Si l'utilisateur existe en session, alors on affiche son adresse email. --%>
			<p class="succes">Vous êtes connecté.e avec l'adresse : ${UtilisateurConnecte.getPseudo()}</p>
		</c:if>
		<c:if test="${!connecte }">
			<p>Personne n'est connecté</p>
		</c:if>
		


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
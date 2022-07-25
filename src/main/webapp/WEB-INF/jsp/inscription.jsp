<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="fr.eni.encheres.exceptions.LecteurMessage" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/jsp/fragments/head.jsp"></jsp:include>
<title>Inscription</title>
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
	
	
	<div class="container">
		<div class="form-group row">
			<h1 class="col-12 col-form-title row justify-content-center" >Mon profil</h1> <!-- TODO Centrer -->
				</div>
					<form method="post"	action="${pageContext.request.contextPath }/inscription">
						<div class="form-group row">			
								<label class="col-2 col-form-label" for="pseudo">Pseudo : <span class="text-danger">*</span></label> 
								<input class="form-control col-4"  type="text" id="pseudo" name="pseudo" placeholder="Votre pseudo..." required/>
									
								<label class="col-2 col-form-label" for="nom">Nom : <span class="text-danger">*</span></label> 
								<input class="form-control col-4" type="text" id="nom" name="nom" placeholder="Votre nom..." required/>
						</div>
						<div class="form-group row">				
								<label class="col-2 col-form-label" for="prenom">Prenom : <span class="text-danger">*</span></label> 
								<input class="form-control col-4" type="text" id="prenom" name="prenom" placeholder="Votre prénom..." required/>
								
								<label class="col-2 col-form-label" for="email">Adresse email : <span class="text-danger">*</span></label> 
								<input class="form-control col-4" type="email" id="email" name="email" placeholder="Votre adresse mail..." required/>
						</div>
						<div class="form-group row">		
								<label class="col-2 col-form-label" for="telephone">Téléphone : </label> 
								<input class="form-control col-4" type="text" id="telephone" name="telephone" placeholder="Votre numéro de téléphone..." />
								
								<label class="col-2 col-form-label" for="rue">Rue : <span class="text-danger">*</span></label> 
								<input class="form-control col-4" type="text" id="rue" name="rue" placeholder="Rue..." required/>
						</div>
						<div class="form-group row">		
								<label class="col-2 col-form-label" for="codePostal">Code postal : <span class="text-danger">*</span></label> 
								<input class="form-control col-4" type="text" id="codePostal" name="codePostal" placeholder="Code postal..." required/>
						
								<label class="col-2 col-form-label" for="ville">Ville : <span class="text-danger">*</span></label> 
								<input class="form-control col-4" type="text" id="ville" name="ville" placeholder="Ville..." required/>
						</div>
						<div class="form-group row">		
								<label class="col-2 col-form-label" for="motdepasse">Mot de passe : <span class="text-danger">*</span></label> 
								<input class="form-control col-4" type="password" id="motdepasse" name="motdepasse" placeholder="Mot de passe..." required/>
								
								<label class="col-2 col-form-label" for="motdepasseConfirmation">Confirmation : <span class="text-danger">*</span></label> 
								<input class="form-control col-4" type="password" id="motdepasseConfirmation" name="motdepasseConfirmation" placeholder="Confirmation du mot de passe..." required/>
						</div>
						<div class="form-group row justify-content-center">		
								<input	class="btn btn-primary btn-lg col-3 " type="submit" value="Créer"/>
								<a href="${pageContext.request.contextPath }/accueil"  class="btn btn-secondary btn-lg col-3 offset-1 "  role="button" aria-pressed="true">Annuler</a>
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
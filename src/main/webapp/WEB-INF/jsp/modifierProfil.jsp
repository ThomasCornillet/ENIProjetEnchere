<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="fr.eni.encheres.exceptions.LecteurMessage" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/jsp/fragments/head.jsp"></jsp:include>
<title>Modifier Profil</title>
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
								<input	class="btn btn-primary btn-lg col-2 " type="submit" value="Enregistrer"/>
								

								</div>
						
					</form>
					
					
						<button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#exampleModal">
							Supprimer mon compte
						</button>
						
						<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-hidden="true">
						  <div class="modal-dialog" role="document">
						    <div class="modal-content">
						      <div class="modal-header">
						        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
						          <span aria-hidden="true">&times;</span>
						        </button>
						      </div>
						      <div class="modal-body">
								<form action="${pageContext.request.contextPath}/supprimerProfil" id="form" method="post">	      
						        
							        <p class="lead text-xs-center">Etes vous sûr.e.s de vouloir supprimer votre compte, ${sessionScope.pseudo} ${sessionScope.nom} ?</p>
							      
							        <button class="btn btn-primary " type="button" data-dismiss="modal" >Pas encore</button>
									<input type="hidden" name="noUtilisateur" value="${sessionScope.nom}"/>
									<button class="btn btn-secondary btn-lg col-3 offset-1 " type="submit">Yep, ciao et bon dev !</button>
									<!--  <a class="btn btn-secondary btn-lg col-3 offset-1 "  href="${pageContext.request.contextPath }/supprimerProfil"  role="button" aria-pressed="true">Yep, ciao les boloss !</a> -->
								</form>	 	   
						    </div>
						  </div>
						 </div>	
			</div>
	</div>
	<%-- Vérification de la présence d'un objet utilisateur en session --%>
		<c:if test="${!empty sessionScope}">
			<%-- Si l'utilisateur existe en session, alors on affiche son adresse email. --%>
			<p class="succes">Vous êtes connecté.e avec l'adresse : ${utilisateur.getPseudo()}</p>
		</c:if>	

<!-- import javascript pour Boostrap -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"	crossorigin="anonymous"></script>
</body>
</html>
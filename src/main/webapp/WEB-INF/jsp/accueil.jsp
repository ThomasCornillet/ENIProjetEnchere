<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="fr.eni.encheres.exceptions.LecteurMessage" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/WEB-INF/jsp/fragments/head.jsp"></jsp:include>
	<title>Accueil</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/fragments/header.jsp"></jsp:include>
	
	<c:if test="${!empty listeCodesErreur }">
		<div class="container">
			<div class="row">
	        	<div class="col">
	        		<div class="alert alert-danger alert-dismissible fade show" role="alert">
	          			<h4 class="alert-heading">Au moins une erreur est survenue</h4>
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
		<div class="row">
			<h1 class="col text-center">Liste des enchères</h1>
		</div>
		<div class="row">
			<h2 class="col">Filtres :</h2>
		</div>
			<form class="row" method="post" action="${pageContext.request.contextPath }/accueilfiltre">
				<div class="col-6">
					<input type="search" name="portionNom" placeholder="Le nom de l'article contient">
					<div>
						<label for="categorie">Catégorie :</label>
						<span>
							<select name="categorie">
								<c:if test="${!empty listeCategories }">
									<option value="toutes">Toutes</option>
									<c:forEach var="cat" items="${listeCategories }">
										<c:choose>
											<c:when test="${categorieSelectionnee != null && categorieSelectionnee == cat.getNoCategorie()}">
												<option value="${cat.getNoCategorie() }" selected>${cat.getLibelle()}</option>
											</c:when>
											<c:otherwise>
												<option value="${cat.getNoCategorie() }">${cat.getLibelle()}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:if>
							</select>
						</span>
					</div>
					<!-- si un utilisateur est connecté, on affiche des filtres en plus -->
					<c:if test="${connecte}">
						<div class="container">
							<div class="row">
								
							
								<div class="form-check col-6">
								<!-- filtre achat -->
									<div class="container">
										<div class="row">
											<input class="form-check-input" type="radio" name="filtreConnecte" id="radioAchat" value="achats" checked>
											<label class="form-check-label" for="filtreConnecte">Achats</label>
										</div>
										<div class="form-check row">
											<input class="form-check-input achats-ck" type="checkbox" name="encheresOuvertes" value="encheresOuvertes" id="encheresOuvertes">
											<label class="form-check-label" for="encheresOuvertes">enchères ouvertes</label>
										</div>
										<div class="form-check row">
											<input class="form-check-input achats-ck" type="checkbox" name="encheresEnCours" value="encheresEnCours" id="encheresEnCours">
											<label class="form-check-label" for="encheresEnCours">mes enchères en cours</label>
										</div>
										<div class="form-check row">
											<input class="form-check-input achats-ck" type="checkbox" name="encheresRemportees" value="encheresRemportees" id="encheresRemportees">
											<label class="form-check-label" for="encheresRemportees">mes enchères remportés</label>
										</div>
									</div>
								</div>
								<div class="form-check col-6">
								<!-- filtre mes ventes -->
									<div class="container">
										<div class="row">
											<input class="form-check-input" type="radio" name="filtreConnecte" id="radioMesVentes" value="mesVentes">
											<label class="form-check-label" for="filtreConnecte">Mes ventes</label>
										</div>
										<div class="form-check row">
											<input class="form-check-input ventes-ck" type="checkbox" name="ventesEnCours" value="ventesEnCours" id="ventesEnCours" disabled>
											<label class="form-check-label" for="ventesEnCours">mes ventes en cours</label>
										</div>
										<div class="form-check row">
											<input class="form-check-input ventes-ck" type="checkbox" name="ventesNonDebutees" value="ventesNonDebutees" id="ventesNonDebutees" disabled>
											<label class="form-check-label" for="ventesNonDebutees">mes ventes non débutées</label>
										</div>
										<div class="form-check row">
											<input class="form-check-input ventes-ck" type="checkbox" name="ventesTerminees" value="ventesTerminees" id="ventesTerminees" disabled>
											<label class="form-check-label" for="ventesTerminees">mes ventes terminées</label>
										</div>
									</div>
								</div>
									
									
							</div>
						</div>
					</c:if>
				</div>
				<input class="col-6" type="submit" value="Rechercher">
			</form>
	</div>
	
	<div class="container">
		<div class="row">
			<c:choose>
				<c:when test="${!empty listeArticles }">
					<c:forEach var="article" items="${listeArticles }">
						<div class="col-12 col-lg-6">
							<div class="card">
								<div class="card-body">
									<h5 class="card-title"><a href="${pageContext.request.contextPath }/detailVente?id=${article.getNoArticle()}">${article.getNomArticle()}</a></h5>
									<div class="card-test">
										<p class="card-text">Prix : ${article.getPrix_initial() } points</p>
										<p class="card-text">Fin de l'enchère : ${article.getDate_fin_enchere() }</p>
										<a href="${pageContext.request.contextPath }/afficherProfil?id=${article.getNo_utilisateur()}">Vendeur : ${article.getPseudoUtilisateur() }</a>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<div class="container">
						<div class="row">
				        	<div class="col">
				          		<div class="alert alert-warning alert-dismissible fade show" role="alert">
				          			Il n'y a pas encore d'articles en vente.
				          		</div>
				         	</div>
					     </div>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	
	
	
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/filtres-accueil.js"></script>
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
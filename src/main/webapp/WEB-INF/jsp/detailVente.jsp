<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="fr.eni.encheres.exceptions.LecteurMessage" %>
<%@ page import="fr.eni.encheres.bo.Utilisateurs" %>
<%@ page import="fr.eni.encheres.bo.Articles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/jsp/fragments/head.jsp"></jsp:include>
<title>Affichage détails vente - enchérir</title>
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
	
	<div class="container">
		<div class="row">
			<h1 class="col">Détail vente</h1>
		</div>
	</div>
	<div class="container">
		<c:choose>
    		 <c:when test="${!empty article ||connecte }">
		        <ul class="list-group col col-lg-4">
		         	<li class=" d-flex justify-content-between align-items-center">
		         		<p>${article.getNomArticle()}</p> 
					</li>
		         	<li class=" d-flex justify-content-between align-items-center">
		         		<p>Description : ${article.getDescription()}</p> 
					</li>
		         	<li class="d-flex justify-content-between align-items-center">
		         		<p>Catégorie : ${article.getLibelleCatagorie()}</p> 
					</li>
					<c:if test="${!empty enchere }">
					<li class="d-flex justify-content-between align-items-center">
		         		<p>Meilleure offre : ${enchere.getMontantEnchere()} pts par ${enchere.getEncherisseur()} </p> 
					</li>
					</c:if>
					<li class="d-flex justify-content-between align-items-center">
		         		<p class="font-weight-bold">Meilleure offre : vous êtes le premier enchérisseur</p> 
					</li>
					<li class="d-flex justify-content-between align-items-center">
		         		<p>Mise à prix : ${article.getPrix_initial()} pts</p> 
					</li>
					<li class=" d-flex justify-content-between align-items-center">
		         		<p>Fin de l'enchère: ${article.getDate_fin_enchere()}</p> 
					</li>
					<li class="d-flex justify-content-between align-items-center">
		         		<p>Retrait : ${article.getRue()}, ${article.getCodePostal()}, ${article.getVille()}</p>
					</li>
					<li class="d-flex justify-content-between align-items-center">
		         		<p>Vendeur : ${article.getPseudoUtilisateur()}</p> 
					</li>
				 </ul>
				 <form method="post" action="${pageContext.request.contextPath }/detailVente">
	         		<input type="number" min="0" max="1000" step="5" value="50"/>
	         		<input class="btn btn-primary btn-lg col-3 " type="submit" value="Enchérir"/>
		         </form>
				 <c:if test="">
				 <!-- Test connexion session pour afficher infos crédits et afficher le bouton modifier sont profile-->
				 </c:if>
	        </c:when> 
	        <c:otherwise>
	        	<p class="alert alert-danger alert-dismissible fade show" role="alert">Vous devez être connecté pour enchérir<p>
	        </c:otherwise>
        </c:choose>
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
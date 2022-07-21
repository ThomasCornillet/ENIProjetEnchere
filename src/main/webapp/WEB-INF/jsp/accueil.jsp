<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="fr.eni.encheres.exceptions.LecteurMessage" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Accueil</title>
<!-- import des feuilles de styles bootstrap, j'ai mis la version sur le web, c'est plus simple pour l'instant -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
</head>
<body>
	<header>
		<div class="container">
			<div class="row">
				<div class="col">
					<p>ENI-Encheres</p>
				</div>
				<div class="col">
					<a href="${pageContext.request.contextPath }/authentification">S'inscrire - Se connecter</a>
				</div>
			</div>
		</div>
	</header>
	
	<div class="container">
		<div class="row">
			<h1 class="col">Liste des enchères</h1>
		</div>
		<div class="row">
			<h2 class="col">Filtres :</h2>
		</div>
		<form class="row" method="post" action="${pageContext.request.contextPath }/accueil">
			<div class="col-6">
				<input type="search" name="portionNom" placeholder="Le nom de l'article contient">
				<div>
					<label for="categorie">Catégorie :</label>
					<span>
						<select name="categorie">
							<c:if test="${!empty listeCategories }">
								<option value="toutes">Toutes</option>
								<c:forEach var="cat" items="${listeCategories }">
									<option value="${cat.getLibelle().toLowerCase() }">${cat.getLibelle()}</option>
								</c:forEach>
							</c:if>
						</select>
					</span>
				</div>
			</div>
			<input class="col-6" type="submit" value="Rechercher">
		</form>
	</div>
	
	<div class="container">
		<c:choose>
			<c:when test="${!empty listeArticles }">
				<c:forEach var="article" items="${listeArticles }">
					<div class="col-12 col-md-6">
						<div class="card">
							<div class="card-body">
								<h5 class="card-title text-decoration-underline">${article.getNom()}</h5>
								<p class="card-text">${article.getDescription() }</p>
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
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
<title>Nouvelle Vente</title>
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
					<p>ENI-Enchères</p>
				</div>			
			</div>
		</div>		
	</header>
	
	<div class="container">
		<div class="row">
			<h1 class="col">Nouvelle vente</h1>
		</div>
	</div>
	
	<div class="container">
		<form action="${pageContext.request.contextPath }/nouvelleVente" method="post">
			<div class="form-group row">
				<label class="col col-form-label" for="nom">Article :</label>
				<input class="form-control col" type="text" name="nom" id="nom"  required />
			</div>
			
			<div class="form-group row">
				<label class="col col-form-label" for="description">Description :</label>
				<textarea class="form-control col" name="description" id="description"  required></textarea>
			</div>
			
			<div class="form-group row">
				<label class="col col-form-label" for="categorie">Catégorie :</label>
				<select multiple class="form-control col" name="categorie" id="categorie"  required>
						<c:choose>
							<c:when test="${!empty listeCategories }">
								<c:forEach var="categorie" items="${listeCategories}">
									<option value="${categorie.getNoCategorie()}">${categorie.getLibelle()}</option>
								</c:forEach>
							</c:when>
						</c:choose>
				</select>
				
			</div>
				
				
				
			
			
			
			
			
			
			
		
		
		
			
		</form>		
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
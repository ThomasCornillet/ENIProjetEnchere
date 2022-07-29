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
	
	<div class="container">
		<div class="row">
			<h1 class="col">Nouvelle Vente</h1>
		</div>
	</div>
	
	
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
				<label class="col col-form-label" for="categorie">Catégorie </label>
				<select class="form-control col" name="categorie" id="categorie"  required>
						<c:choose>
							<c:when test="${!empty listeCategories }">
								<c:forEach var="categorie" items="${listeCategories}">
									<option value="${categorie.getNoCategorie()}">${categorie.getLibelle()}</option>
								</c:forEach>
							</c:when>
						</c:choose>
				</select>
			</div>		
			
   			<div class="form-group row">	
            	<label class="col col-form-label" for="UPLOADER">Photo de l'article</label>
           		<input type="file" name="uploader" id="uploader" placeholder="UPLOADER" class="shadow-lg p-3 mb-5 bg-white"> 
           		           	
          	</div>
          	
          	<div class="form-group row">
          		<label class="col col-form-label" for="prix">Mise à prix :</label>
          		 <input class="input" type="number" name="prix" id="prix" value="150" required> 
            </div>
            
            <div class="form-group row">
            	<label class="col col-form-label"for="date_debut_encheres">Début de l'enchère :</label>
            	<input class="input" type="date" name="date_debut_encheres" id="date_debut_encheres" required> 
        	</div>
        	
        	<div class="form-group row">
            	<label class="col col-form-label" for="date_fin_encheres">Fin de l'enchère :</label>
            	<input class= "input" type="date" name="date_fin_encheres" id="date_fin_encheres" required> 
        	</div>
        	
        	<div class="form-group row">
        		<label class="col col-form-label" for="retrait">Retrait</label>
        	</div>
        	
        	<div class="form-group row">
        		<div class="form-check col">
        			<input class="form-check-input" type="checkbox" name="retrait" id="checkRetrait" value="retrait">
        			<label class="form-check-label" for="retrait">Point de retrait différent de l'adresse renseignée</label>
        		</div>
        	</div>
        	
        	<div class="form-group row">
      			<label class="col col-form-label"for="rue">Rue :</label>
          		<input class="input" type="text" name="rue" id="rue" maxlength="200" placeholder="Rue des mouettes">
      		</div>
      		
      		<div class="form-group row">
          		<label class="col col-form-label" for="code_postal ">Code Postale :</label>
         		<input class="input" type="text" name="code_postal " id="code_postal " step="1000" min="0" maxlength="5" placeholder="44800">
      		</div>

      		<div class="form-group row">
          		<label class="col col-form-label" for="ville">Ville :</label>
          		<input class="input" type="text" name="ville" id="ville" placeholder="Saint Herblain">
      		</div>
      		
      		
      		<c:choose>
      			<c:when test="${VendeurVisiteur != null && VendeurVisiteru == true && connecte != null && connecte == true}">
      				<div class="form-group row">
      					<button type="submit"  name="modifier">Modifier</button>
      					<a href="/modifierVente?idArticle=${articleModifie!=null?articleModifier.getNoArticle():null }">Remise à zéro</a>
      				</div>
      			</c:when>
      			<c:otherwise>
      				<div class="form-group row">	
						<button type="submit"  name="enregistrer">Enregistrer</button>	
						<a href="/accueil"><button type="submit" >Annuler</button></a>
						<a href="/nouvelleVente">Remise à zéro</a>
					</div>
      			</c:otherwise>
      		
      		</c:choose>
      		
      		
      		
      		
			
	
	
	
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
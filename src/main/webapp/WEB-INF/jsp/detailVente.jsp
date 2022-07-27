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
	    <div class="col-3 align-self-start">

	    </div>
	    <div class="colalign-self-center">
     	      <c:choose>
	     	      <c:when test="${UtilisateurConnecte.getNoUtilisateur() == enchere.getNoUtilisateur() && article.isVendu()}">
		     		 <h1>Vous avez remporté la vente</h1>
		     	 </c:when>
		     	 <c:when test="${!article.isVendu()}">
		     	 	<h1>Détail vente</h1>
		     	 </c:when>
	 		     <c:when test="${article.isVendu()}">
		     	 	<h1>${enchere.getEncherisseur()} a remporté la vente</h1>
		     	 </c:when>

	    	 </c:choose>
	    </div>
	    <div class="col-3 align-self-end">
	    
	    </div>
	  </div>
	</div>
	<div class="container">
		<c:choose>
    		 <c:when test="${!empty article ||connecte }">
				 <div class="col-12">
					 <table class="table table-responsive-xl table-hover">
						  <tbody>
						    <tr>
						      <td >${article.getNomArticle()}</td>
						      <td></td>
						    </tr>
						     <tr>
						      <th scope="row">Description : </th>
						      <td class="text-center">${article.getDescription()}</td>
						    </tr>
						     <tr>
						      <c:if test="${!article.isVendu()}"> 
							      <th scope="row">Catégorie : </th>
							      <td class="text-center">${article.getLibelleCatagorie()}</td>
						      </c:if>
						    </tr>
						      <tr>
						      
						      	 <th scope="row">Meilleure offre : </th>
						      	 <c:if test="${!empty encheres }">
						      	 	<c:if test="${!article.isVendu() or article.isVendu()}"> 
						     	 		<td class="text-center">${enchere.getMontantEnchere()} pts par ${enchere.getEncherisseur()} </td>
						     	 	</c:if>
						     	 	<c:if test="${UtilisateurConnecte.getNoUtilisateur() == enchere.getNoUtilisateur() && article.isVendu() }  "> 
						     	 		<td class="text-center">${enchere.getMontantEnchere()} pts</td>
						     	 	</c:if>
						     	 </c:if>
						     	 <c:if test="${empty encheres }">
						     	 	<td class="text-center">vous êtes le premier enchérisseur </td>
						     	 </c:if>
						   	  </tr>
						    <tr>
						      <th scope="row">Mise à prix : </th>
						      <td class="text-center">${article.getPrix_initial()} pts</td>
						    </tr>
   						    <tr>
   						     <c:if test="${!article.isVendu()}">
							      <th scope="row">Fin de l'enchère: </th>
							      <td class="text-center">${article.getDate_fin_enchere()}</td>
						     </c:if>
						     <c:if test="${article.isVendu()}">
							      <th scope="row">Fin de l'enchère: </th>
							      <td class="text-center">${article.getDate_fin_enchere()}</td>
						     </c:if>
						    </tr>
   						    <tr>
						      <c:if test="${!empty retrait }">
							      <th scope="row">Retrait : </th>
							      <td class="text-center">${retrait.getRue()}, ${retrait.getCodePostal()}, ${retrait.getVille()}</td>
						      </c:if>
						      <c:if test="${empty retrait }">
							      <th scope="row">Retrait : </th>
							      <td class="text-center">${article.getRue()}, ${article.getCodePostal()}, ${article.getVille()}</td>
						      </c:if>
						    </tr>
   						    <tr>
						      <th scope="row"> Vendeur : </th>
						      <td class="text-center">${article.getPseudoUtilisateur()}</td>
						      <c:if test="${!article.isVendu()}">
							    <tr>
							      <th scope="row"> Tel : </th>
							      <td class="text-center">${article.getPseudoUtilisateur()}</td>
							    </tr>
						      </c:if>
						    </tr>
						    <tr>
						      <th></th>
						      <td>
						      	 <c:if test="${!article.isVendu()}">
							       	 <form method="post" action="${pageContext.request.contextPath }/detailVente?noArticle=${article.getNoArticle()}">
						         		<c:if test="${!empty encheres }">
						         			<input type="number" min="${enchere.getMontantEnchere()}" name ="encherir" step="5" value="${enchere.getMontantEnchere()}"/>
						         		</c:if>
						         		<c:if test="${empty encheres }">
						         			<input type="number" min="${article.getPrix_initial()}" name ="encherir" step="5" value="${article.getPrix_initial()}"/>
						         		</c:if>
						         		<button data-toggle="modal" type="submit">Enchérir</button>
							         </form>
						         </c:if>
						         <c:if test="">
						         	<a href="${pageContext.request.contextPath }/accueil?noArticle=${UtilisateurConnecte.getNoUtilisateur()}"><button>Back</button></a>
						         </c:if>
						         <c:if test="${article.isVendu()}">
						         	<a href="${pageContext.request.contextPath }/accueil?noArticle=${UtilisateurConnecte.getNoUtilisateur()}"><button>Retrait effectué</button></a>
						         </c:if> 	
						      </td>
						    </tr>
 						  </tbody>
					</table>
				</div> 	
				 
				 
				 
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
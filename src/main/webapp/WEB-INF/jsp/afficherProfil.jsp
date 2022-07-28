<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="fr.eni.encheres.exceptions.LecteurMessage" %>
<%@ page import="fr.eni.encheres.bo.Utilisateurs" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/WEB-INF/jsp/fragments/head.jsp"></jsp:include>
	<title>Affichage profil</title>
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
		<c:choose>
    		<c:when test="${!empty utilisateur}">
				<div class="col-12">
					 <table class="table table-responsive-xl table-hover">
						  <tbody>
						    <tr>
						      <th scope="row">Pseudo : </th>
						      <td class="text-center">${utilisateur.getPseudo()}</td>
						    </tr>
						    <tr>
						      <th scope="row">Nom : </th>
						      <td class="text-center">${utilisateur.getNom()}</td>
						    </tr>
						    <tr>
						      <th scope="row">Prénom : </th>
						      <td class="text-center">${utilisateur.getPrenom()}</td>
						    </tr>
	   					    <tr>
						      <th scope="row">Email : </th>
						      <td class="text-center">${utilisateur.getEmail()}</td>
						    </tr>
	     					<tr>
						      <th scope="row">Téléphone : </th>
						      <td class="text-center">${utilisateur.getTelephone()}</td>
						    </tr>
	    					<tr>
						      <th scope="row">Rue : </th>
						      <td class="text-center">${utilisateur.getRue()}</td>
						    </tr>
	       					<tr>
						      <th scope="row">Code postal : </th>
						      <td class="text-center">${utilisateur.getCodePostal()}</td>
						    </tr>
	       					<tr>
						      <th scope="row">Ville : </th>
						      <td class="text-center">${utilisateur.getVille()}</td>
						    </tr>
						    <c:if test="${UtilisateurConnecte.getNoUtilisateur() == utilisateur.getNoUtilisateur()}" >
						 		<tr>
							      <th scope="row">Crédit :</th>
							      <td class="text-center">${utilisateur.getCredit()}</td>
							    </tr>
							    <tr>
								  <th></th>
								  	<td><a href="${pageContext.request.contextPath }/modificationProfil?id=${utilisateur.getNoUtilisateur()}" class="btn btn-secondary col-3 offset-1 "  role="button" aria-pressed="true">Modifier</a></td>
					 			</tr>
					 		</c:if>
						  </tbody>
						</table>
				</div> 	
	        </c:when>
	        <c:otherwise>
	        	<p class="alert alert-danger alert-dismissible fade show" role="alert">Pas de profile à afficher<p>
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
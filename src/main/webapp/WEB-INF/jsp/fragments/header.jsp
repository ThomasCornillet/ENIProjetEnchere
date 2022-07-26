<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<header>
	<div class="container">
		<div class="row ">
			<div class="col ">
				<a href="${pageContext.request.contextPath }/accueilfiltre?id=${UtilisateurConnecte.getNoUtilisateur()}">ENI-Encheres</a>
				<!-- ici mettre logo? -->
			</div>
			<c:choose>
				<c:when test="${!connecte }">
					<div class="col text-right">
						<a href="${pageContext.request.contextPath }/authentification">S'inscrire - Se connecter</a>
					</div>
				</c:when>
				<c:otherwise>
					<ul class="list-inline text-center">
		              	<li class="list-inline-item"><a href="${pageContext.request.contextPath }/nouvelleVente">Enchères</a></li>
		              	<li class="list-inline-item"><a href="${pageContext.request.contextPath }/afficherProfil?id=${UtilisateurConnecte.getNoUtilisateur()}">Mon profil</a></li>
		              	<li class="list-inline-item"><a href="${pageContext.request.contextPath }/deconnexion">Se déconnecter</a></li>
		            </ul>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</header>
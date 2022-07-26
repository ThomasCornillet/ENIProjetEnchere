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
					<div class="col">
						<a href="${pageContext.request.contextPath }/authentification">S'inscrire - Se connecter</a>
					</div>
				</c:when>
				<c:otherwise>
					<div class="col">
						<a href="${pageContext.request.contextPath }/nouvelleVente">Ench�res</a>
					</div>
					<div class="col">
						<a href="${pageContext.request.contextPath }/afficherProfil?id=${UtilisateurConnecte.getNoUtilisateur()}">Mon profil</a>
					</div>
					<div class="col">
						<a href="${pageContext.request.contextPath }/deconnexion">Se d�connecter</a>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</header>
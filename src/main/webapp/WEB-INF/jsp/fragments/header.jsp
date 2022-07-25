<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<header>
	<div class="container">
		<div class="row ">
			<div class="col ">
				<p>ENI-Encheres</p>
				<!-- ici mettre logo? -->
			</div>
			<c:choose>
				<c:when test="${empty sessionScope }">
					<div class="col">
						<a href="${pageContext.request.contextPath }/authentification">S'inscrire - Se connecter</a>
					</div>
				</c:when>
				<c:otherwise>
					<div class="col">
						<a href="${pageContext.request.contextPath }/deconnexion">Se déconnecter</a>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</header>
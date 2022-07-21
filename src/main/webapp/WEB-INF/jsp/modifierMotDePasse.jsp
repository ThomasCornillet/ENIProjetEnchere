<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
   
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

	<html>
		<head>
		<meta charset="UTF-8">
		<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<%@include file="integrationBootstrap.jsp"%>
		<title>Modifier mot de passe</title>
		</head>
				
		<body>			
			<div class="container">
			<div class="row justify-content-center">
			<div class="col-md-6">
			<div class="card">
				<header class="card-header">
					<h4 class="card-title mt-2">Veuillez saisir votre mot de passe
						pour continuer</h4>
				</header>
				
				<article class="card-body">
					<form action="ServMdpModifs" method="post">
						<div class="form-row">
							<div class="col form-group">

								<div class="form-row">
									<div class="col form-group">
										<label>Mot de passe </label> <input class="form-control"
											type="password" name="MotDePasseSecurite">
									</div>
									<!-- form-group end.// -->

									<!-- form-group end.// -->
								</div>
								<!-- form-row end.// -->
								<c:if test="${error != null}" var="test">
									<div class="alert alert-danger" role="alert">${error}</div>
								</c:if>




								<div class="form-group">
									<button type="submit" class="btn btn-primary btn-block"
										value="valider" name="Choix">Valider</button>
									<br> </a> <a href="./Profil">
										<button type="submit" class="btn btn-primary btn-block"
											value="retour" name="Choix">Retour</button>
									</a>
								</div>
							</div>
						</div>
						<!-- form-group// -->
					</form>

				</article>
				<!-- card-body end .// -->
				
			</div>
			<!-- card.// -->
		</div>
		<!-- col.//-->

	</div>
	<!-- row.//-->


</div>
		</body>
</html>
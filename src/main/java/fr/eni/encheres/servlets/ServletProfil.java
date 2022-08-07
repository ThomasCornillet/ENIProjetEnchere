package fr.eni.encheres.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.HashMotDePasse;
import fr.eni.encheres.bll.UtilisateursManager;
import fr.eni.encheres.bo.Utilisateurs;
import fr.eni.encheres.exceptions.BusinessException;

/**
 * Servlet implementation class ServletModifierProfil
 */
@WebServlet(urlPatterns = {"/modificationProfil",
							"/supprimerProfil",
							"/afficherProfil"})
public class ServletProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VUE_PROFILE = "/WEB-INF/jsp/afficherProfil.jsp";
	public static final String VUE_MODIFIER_PROFILE = "/WEB-INF/jsp/modifierProfil.jsp";
	public static final String VUE_ACCUEIL ="/WEB-INF/jsp/accueil.jsp";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Integer> listeCodesErreur= new ArrayList<>();
		if (request.getServletPath().equals("/afficherProfil")) {
			UtilisateursManager utilisateurMngr = UtilisateursManager.getInstance();
			try {				
				
				Utilisateurs vendeur = utilisateurMngr.selectById(Integer.parseInt(request.getParameter("id")));
				request.setAttribute("utilisateur", vendeur);
				
				HttpSession session = request.getSession();
//				session.setAttribute("connecte", true);
//				session.getAttribute("UtilisateurConnecte");
			
			} catch (BusinessException e) {
				for (int code : e.getListeCodesErreur()) {
					listeCodesErreur.add(code);
				}
			}
			if (!listeCodesErreur.isEmpty()) {
				request.setAttribute("listeCodesErreur", listeCodesErreur);
			}
			RequestDispatcher rd = request.getRequestDispatcher(VUE_PROFILE);
			rd.forward(request, response);

		} else if (request.getServletPath().equals("/modificationProfil")) {
			// TODO vérifier fonctionnement
			this.getServletContext().getRequestDispatcher(VUE_MODIFIER_PROFILE).forward( request, response );
			
		}  else if (request.getServletPath().equals("/supprimerProfil")) {
			UtilisateursManager utilisateurMngr = UtilisateursManager.getInstance();
			try {
				utilisateurMngr.deleteById(Integer.parseInt(request.getParameter("id")));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BusinessException e) {
				for (int code : e.getListeCodesErreur()) {
					listeCodesErreur.add(code);
				}
			}
			if (!listeCodesErreur.isEmpty()) {
				request.setAttribute("listeCodesErreur", listeCodesErreur);
			}
			RequestDispatcher rd = request.getRequestDispatcher(VUE_ACCUEIL);
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // permet d'avoir l'encodage en base de données, sinon les caractères spéciaux et accents s'affichent mal
////	HttpSession session = request.getSession();
//		Utilisateurs utilisateur = (Utilisateurs) session.getAttribute("UtilisateurConnecte");
		List<Integer> listeCodesErreur= new ArrayList<>();
	
		if(request.getServletPath().equals("/modificationProfil")) {
		
			UtilisateursManager utilisateurMngr = UtilisateursManager.getInstance();
			Utilisateurs utilisateur = new Utilisateurs();
			try {
				utilisateur = utilisateurMngr.selectById(Integer.parseInt(request.getParameter("id")));
				request.setAttribute("utilisateur", utilisateur);
			} catch (BusinessException e) {
				e.printStackTrace();
				for(Integer code: e.getListeCodesErreur()) {
					listeCodesErreur.add(code);
				}
				request.setAttribute("listeCodesErreur", listeCodesErreur);
				RequestDispatcher rd = request.getRequestDispatcher("/accueil");
				rd.forward(request, response);
		}
		
		// test de quelle(s) modification(s) apportées dans le formulaire et donc à prendre en compte pour l'update
		// TODO à terme, à mettre dans une méthode testsUpdate() séparé du doPost (plus facile à lire comme ça)
		if(request.getParameter("pseudo") != null){
			if(!request.getParameter("pseudo").isBlank()) {
				utilisateur.setPseudo(request.getParameter("pseudo"));
			}
		}
		if(request.getParameter("nom") != null){
			if(!request.getParameter("nom").isBlank()) {
				utilisateur.setNom(request.getParameter("nom"));
			}
		}
		if(request.getParameter("prenom") != null){
			if(!request.getParameter("prenom").isBlank()) {
				utilisateur.setPrenom(request.getParameter("prenom"));
			}
		}
		if(request.getParameter("email") != null){
			if(!request.getParameter("email").isBlank()) {
				utilisateur.setEmail(request.getParameter("email"));
			}
		}
		if(request.getParameter("telephone") != null){
			if(!request.getParameter("telephone").isBlank()) {
				utilisateur.setTelephone(request.getParameter("telephone"));
			}
		}
		if(request.getParameter("rue") != null){
			if(!request.getParameter("rue").isBlank()) {
				utilisateur.setRue(request.getParameter("rue"));
			}
		}
		if(request.getParameter("ville") != null){
			if(!request.getParameter("ville").isBlank()) {
				utilisateur.setVille(request.getParameter("ville"));
			}
		}
		if(request.getParameter("codePostal") != null){
			if(!request.getParameter("codePostal").isBlank()) {
				utilisateur.setCodePostal(request.getParameter("codePostal"));
			}
		}
						
		if(request.getParameter("nouveauMotdepasse") != null && !request.getParameter("nouveauMotdepasse").isBlank()){
				String motDePasseConfirmation = request.getParameter("motdepasseConfirmation");
				String nouveauMotdepasse = request.getParameter("nouveauMotdepasse");
				if(	formatMotDePasseValid(utilisateur, nouveauMotdepasse)) {
				if(motDePasseConfirmation == nouveauMotdepasse) {
					HashMotDePasse hashMdp = HashMotDePasse.getInstance();
					utilisateur.setMotDePasse(hashMdp.shaHash(request.getParameter("nouveauMotdepasse")));
				}else {
					listeCodesErreur.add(CodesResultatServlet.MOT_DE_PASSE_NON_CORRESPONDANT);
					request.setAttribute("listeCodesErreur", listeCodesErreur);
					
					doGet(request,response);
//					RequestDispatcher rd = request.getRequestDispatcher("/modificationProfil?id=" + utilisateur.getNoUtilisateur());
//					rd.forward(request, response);
				}
			}else {
				listeCodesErreur.add(CodesResultatServlet.MOT_DE_PASSE_NON_CORRESPONDANT);
				request.setAttribute("listeCodesErreur", listeCodesErreur);
				doGet(request,response);
//				RequestDispatcher rd = request.getRequestDispatcher("/modificationProfil?id=" + utilisateur.getNoUtilisateur());
//				rd.forward(request, response);
			}
			
		}

//		 utilisateur.getMotDePasse().equals(request.getParameter("motDePasseActuel")) ||
		// hash du mot de passe
		HashMotDePasse hashMdp = HashMotDePasse.getInstance();
		if(utilisateur.getMotDePasse().equals(hashMdp.shaHash(request.getParameter("motDePasseActuel")))) {
			
			try {
				utilisateurMngr.update(utilisateur);
				RequestDispatcher rd = request.getRequestDispatcher(VUE_PROFILE);
				rd.forward(request, response);
			} catch (BusinessException e) {
				e.printStackTrace();
				for(Integer code: e.getListeCodesErreur()) {
					listeCodesErreur.add(code);
				}
				request.setAttribute("listeCodesErreur", listeCodesErreur);
				doGet(request,response);
//				RequestDispatcher rd = request.getRequestDispatcher("/modificationProfil?id=" + utilisateur.getNoUtilisateur());
//				rd.forward(request, response);
			}
			
		}else {
			listeCodesErreur.add(CodesResultatServlet.MOT_DE_PASSE_NON_CORRESPONDANT);
			request.setAttribute("listeCodesErreur", listeCodesErreur);
			doGet(request,response);
//			RequestDispatcher rd = request.getRequestDispatcher("/modificationProfil?id=" + utilisateur.getNoUtilisateur());
//			rd.forward(request, response);
		}
	} else
	
	if(request.getServletPath().equals("/supprimerProfil")) {
		UtilisateursManager utilisateurMngr = UtilisateursManager.getInstance();
		Utilisateurs utilisateur = new Utilisateurs();
		try {
			//deleteById()
			HttpSession session = request.getSession();
			session.getAttribute("UtilisateurConnecte"); // pourquoi il y a besoin de ça ici ?
			request.setAttribute("UtilisateurConnecte", utilisateur);	
			utilisateurMngr.deleteById(Integer.parseInt(request.getParameter("id")));
			// déconnexion de la personne
			session.removeAttribute("connecte");
			session.removeAttribute("UtilisateurConnecte");
			// TODO est-ce qu'on ajout un message comme quoi le compte est bien supprimé ?
			RequestDispatcher rd = request.getRequestDispatcher("/accueil");
			rd.forward(request, response);
			
		} catch (BusinessException e) {
			e.printStackTrace();
			for(Integer code: e.getListeCodesErreur()) {
				listeCodesErreur.add(code);
			}
			request.setAttribute("listeCodesErreur", listeCodesErreur);
			RequestDispatcher rd = request.getRequestDispatcher(VUE_PROFILE);
			rd.forward(request, response);
		}											
	}
	
	}
	private boolean formatMotDePasseValid(Utilisateurs utilisateur, String motDePasse) {
		boolean retour = false;
		if (motDePasse.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{12,30}$")) {
			retour = true;
		}
		return retour;
	}
}

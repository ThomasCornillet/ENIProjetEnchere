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
     * @see HttpServlet#HttpServlet()
     */
    public ServletProfil() {
        super();
        // TODO Auto-generated constructor stub
    }

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
				session.setAttribute("connecte", true);
				session.getAttribute("UtilisateurConnecte");
			
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
////	HttpSession session = request.getSession();
//	Utilisateurs utilisateur = (Utilisateurs) session.getAttribute("UtilisateurConnecte");
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
			RequestDispatcher rd = request.getRequestDispatcher(VUE_PROFILE);
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
	
			
			// Avant ça 2 vliadations:
			// Entrez le bon mot de passe
			// Vérifier que nouveauMotdepasse == nouveauMotdepasseConfirmation
		//	
		//	if(request.getParameter("nouveauMotdepasse") != null){
		//		if(!request.getParameter("nouveauMotdepasse").isBlank()) {
		//			utilisateur.setMotDePasse(request.getParameter("nouveauMotdepasse"));
		//		}
		//	}
		//	String motDePasseConfirmation = request.getParameter("motdepasseConfirmation");
			
		if(utilisateur.getMotDePasse().equals(request.getParameter("motDePasseActuel"))) {
			
			try {
				utilisateurMngr.update(utilisateur);
			} catch (BusinessException e) {
				e.printStackTrace();
				for(Integer code: e.getListeCodesErreur()) {
					listeCodesErreur.add(code);
				}
				request.setAttribute("listeCodesErreur", listeCodesErreur);
				RequestDispatcher rd = request.getRequestDispatcher(VUE_PROFILE);
				rd.forward(request, response);
			}
			
		}else {
			listeCodesErreur.add(CodesResultatServlet.MOT_DE_PASSE_NON_CORRESPONDANT);
			request.setAttribute("listeCodesErreur", listeCodesErreur);
			RequestDispatcher rd = request.getRequestDispatcher(VUE_PROFILE);
			rd.forward(request, response);
		}
	} else
	
	if(request.getServletPath().equals("/supprimerProfil")) {
		UtilisateursManager utilisateurMngr = UtilisateursManager.getInstance();
		Utilisateurs utilisateur = new Utilisateurs();
		try {
			//deleteById()
			HttpSession session = request.getSession();
			session.getAttribute("UtilisateurConnecte");
			request.setAttribute("UtilisateurConnecte", utilisateur);
			utilisateurMngr.deleteById(Integer.parseInt(request.getParameter("id")));
			RequestDispatcher rd = request.getRequestDispatcher(VUE_ACCUEIL);
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

}

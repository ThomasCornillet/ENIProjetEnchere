package fr.eni.encheres.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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
							"/AfficherProfile"})
public class ServletModifierProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VUE_PROFILE = "/WEB-INF/jsp/AfficherProfil.jsp";
	public static final String VUE_MODIFIER_PROFILE = "/WEB-INF/jsp/modifierProfil.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletModifierProfil() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getServletPath().equals("/AfficherProfile")) {
			
			List<Integer> listeCodesErreur= new ArrayList<>();
			
	//		if(pseudo != null) { // A décommenter après la phase test puis supprimer ce commentaire
				UtilisateursManager utilisateurMngr = UtilisateursManager.getInstance();
				try {
					HttpSession session = request.getSession();
					
					String pseudo = (String) session.getAttribute("pseudo");
					
					Utilisateurs utilisateur = utilisateurMngr.selectByPseudo(pseudo);
					request.setAttribute("utilisateur", utilisateur);
					
					System.out.println(pseudo); // TODO delete
					
					this.getServletContext().getRequestDispatcher( VUE_PROFILE ).forward( request, response );
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	//		}else {
	//			listeCodesErreur.add(CodesResultatServlet.UTILISATEUR_INEXISTANT);
	//			request.setAttribute("listeCodesErreur", listeCodesErreur);
	//			RequestDispatcher rd = request.getRequestDispatcher(VUE_PROFILE);
	//			rd.forward(request, response);
	//		} // A décommenter après la phase test puis supprimer ce commentaire
			
			
		} else {
		
			this.getServletContext().getRequestDispatcher(VUE_MODIFIER_PROFILE).forward( request, response );
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
////	HttpSession session = request.getSession();
//	Utilisateurs utilisateur = (Utilisateurs) session.getAttribute("UtilisateurConnecte");
	
	if(request.getServletPath().equals("/modificationProfil")) {
		
		UtilisateursManager utilisateurMngr = UtilisateursManager.getInstance();
		Utilisateurs utilisateur = new Utilisateurs();
		
		try {
			utilisateur = utilisateurMngr.selectByPseudo("test");
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Integer> listeCodesErreur= new ArrayList<>();
		
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
		try {
			//deleteById()
			HttpSession session = request.getSession();
			session.getAttribute("UtilisateurConnecte");
			int NoUtilisateur = (int)session.getAttribute("NoUtilisateur");
			String pseudo = (String) session.getAttribute("pseudo");
			
			System.out.println(pseudo); // TODO delete
			System.out.println(NoUtilisateur); // TODO delete
			
			UtilisateursManager utilisateurMngr = UtilisateursManager.getInstance();
			utilisateurMngr.deleteById(NoUtilisateur);
			
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp");
			rd.forward(request, response);
			
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}											
	}
	
	}

}

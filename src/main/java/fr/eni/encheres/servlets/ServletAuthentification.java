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
 * Servlet implementation class ServletConnection
 */
@WebServlet("/authentification")
public class ServletAuthentification extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static final String VUE_AUTHENTIFICATION = "/WEB-INF/jsp/authentification.jsp";
    public static final String VUE_ACCUEIL = "/WEB-INF/jsp/accueil.jsp";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(VUE_AUTHENTIFICATION);
		rd.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateurs utilisateur = new Utilisateurs();
		List<Integer> listeCodesErreur=new ArrayList<>();
		String identifiant = request.getParameter("identifiant");
		String motDePasse = request.getParameter("motdepasse");
		
		// est-ce que l'identifiant est un mail ou un pseudo ?
		utilisateur = mailOuPseudo(identifiant);
		
		// est-ce que l'utilisateur existe ?
		if (utilisateur.getPseudo() == null) {
			// l'utilisateur n'existe pas
//			BusinessException be = new BusinessException();
//			be.ajouterErreur(CodesResultatServlet.UTILISATEUR_NON_TROUVE);
			listeCodesErreur.add(CodesResultatServlet.UTILISATEUR_NON_TROUVE);
			request.setAttribute("listeCodesErreur", listeCodesErreur);
			RequestDispatcher rd = request.getRequestDispatcher(VUE_AUTHENTIFICATION);
			rd.forward(request, response);
		} else {
			// l'utilistaeur existe
			// on teste alors le mot de passe
			if (!motDePasseValide(utilisateur, motDePasse)) {
				// pas le bon mot de passe
//				BusinessException be = new BusinessException();
//				be.ajouterErreur(CodesResultatServlet.MOT_DE_PASSE_NON_CORRESPONDANT);
				listeCodesErreur.add(CodesResultatServlet.MOT_DE_PASSE_NON_CORRESPONDANT);
				request.setAttribute("listeCodesErreur", listeCodesErreur);
				RequestDispatcher rd = request.getRequestDispatcher(VUE_AUTHENTIFICATION);
				rd.forward(request, response);
			} else {
				// bon mot de passe
				String pseudo = utilisateur.getPseudo(); // Pour la phase de tests 
				int NoUtilisateur = utilisateur.getNoUtilisateur();
				HttpSession session = request.getSession();
				session.setAttribute("UtilisateurConnecte", utilisateur); //Pour la phase de tests 
				session.setAttribute("pseudo", pseudo); //Pour la phase de tests
				session.setAttribute("NoUtilisateur", NoUtilisateur);
				response.sendRedirect("/ENIProjetEncheres/AfficherProfile"); 
//				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/modifierProfil.jsp");// TODO A rediriger vers accueil quand il n'y aura plus de bug dans l'acceuil et utilisateur Manager 
//				rd.forward(request, response);
			}
		}
	}
	
	private Utilisateurs mailOuPseudo(String identifiant) {
		Utilisateurs retour = new Utilisateurs();
		UtilisateursManager utilisateurMngr = UtilisateursManager.getInstance();
		if (identifiant.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
			// en présence d'un mail
			try {
				retour = utilisateurMngr.selectByMail(identifiant);	
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// en présence d'un pseudo
			try {
				retour = utilisateurMngr.selectByPseudo(identifiant);	
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return retour;
	}
	
	private boolean motDePasseValide(Utilisateurs utilisateur, String motDePasse) {
		boolean retour = false;
		if (motDePasse.equals(utilisateur.getMotDePasse())) {
			retour = true;
		}
		return retour;
	}

}

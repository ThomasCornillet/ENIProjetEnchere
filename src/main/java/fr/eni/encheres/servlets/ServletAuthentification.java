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
		UtilisateursManager utilisateurMngr = UtilisateursManager.getInstance();
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
				HttpSession session = request.getSession();
				session.setAttribute("UtilisateurConnecte", utilisateur); // TODO revoir comment on gère ça
				
				// Compteur d'accès pour la phase de testes uniquement
				int compteurAccesPendantSession = 0;
				String pseudo = utilisateur.getPseudo();
				System.out.println(pseudo);
				if(session.getAttribute("compteurAcces")!= null)
				{
					compteurAccesPendantSession = (int)session.getAttribute("compteurAcces");
					pseudo = (String)session.getAttribute("pseudo");
				}
				compteurAccesPendantSession+=1;
				session.setAttribute("compteurAcces", compteurAccesPendantSession);
				session.setAttribute("pseudo", pseudo);
				// supprimer les ligne sus-jacentes jusqu'au commentaire
				
				RequestDispatcher rd = request.getRequestDispatcher(VUE_ACCUEIL); // TODO réfléchir ou est-ce qu'on va
				rd.forward(request, response);
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

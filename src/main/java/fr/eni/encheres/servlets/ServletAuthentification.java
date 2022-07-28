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

import fr.eni.encheres.bll.HashMotDePasse;
import fr.eni.encheres.bll.UtilisateursManager;
import fr.eni.encheres.bo.Utilisateurs;
import fr.eni.encheres.exceptions.BusinessException;

/**
 * Servlet implementation class ServletConnection
 */
@WebServlet(urlPatterns = {"/authentification", "/deconnexion"})
public class ServletAuthentification extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static final String VUE_AUTHENTIFICATION = "/WEB-INF/jsp/authentification.jsp";
    public static final String VUE_ACCUEIL = "/WEB-INF/jsp/accueil.jsp";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getServletPath().equals("/deconnexion")) {
			System.out.println("deconnexion en cours");
			HttpSession session = request.getSession();
			session.removeAttribute("connecte");
			session.removeAttribute("UtilisateurConnecte");
		}
		
		if (request.getParameter("filtreInterdit") != null) {
			request.setAttribute("filtreInterdit", true);
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(VUE_AUTHENTIFICATION);
		rd.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // permet d'avoir l'encodage en base de données, sinon les caractères spéciaux et accents s'affichent mal
		Utilisateurs utilisateur = new Utilisateurs();
		List<Integer> listeCodesErreur=new ArrayList<>();
		if(request.getServletPath().equals("/authentification")) {
			String identifiant = request.getParameter("identifiant");
			
			// ajout du hash du mdp
			HashMotDePasse hashMdp = HashMotDePasse.getInstance();
			String motDePasse = hashMdp.shaHash(request.getParameter("motdepasse"));
			
			// est-ce que l'identifiant est un mail ou un pseudo ?
			utilisateur = mailOuPseudo(identifiant); // TODO revoir la méthode pour meilleur compréhension
			
			// est-ce que l'utilisateur existe ?
			if (utilisateur.getPseudo() == null) {
				// l'utilisateur n'existe pas
				listeCodesErreur.add(CodesResultatServlet.UTILISATEUR_NON_TROUVE);
				request.setAttribute("listeCodesErreur", listeCodesErreur);
				RequestDispatcher rd = request.getRequestDispatcher(VUE_AUTHENTIFICATION);
				rd.forward(request, response);
			} else {
				// l'utilistaeur existe
				// on teste alors le mot de passe
				if (!motDePasseValide(utilisateur, motDePasse)) {
					// pas le bon mot de passe
					listeCodesErreur.add(CodesResultatServlet.MOT_DE_PASSE_NON_CORRESPONDANT);
					request.setAttribute("listeCodesErreur", listeCodesErreur);
					RequestDispatcher rd = request.getRequestDispatcher(VUE_AUTHENTIFICATION);
					rd.forward(request, response);
				} else {
					// bon mot de passe
					HttpSession session = request.getSession();
					session.setAttribute("connecte", true); // voir si la session est avec un utilisateur connecté
					session.setAttribute("UtilisateurConnecte", utilisateur); // Pour la phase de tests 
					RequestDispatcher rd = request.getRequestDispatcher("/accueil");
					rd.forward(request, response);
				}
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

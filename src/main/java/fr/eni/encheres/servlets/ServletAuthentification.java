package fr.eni.encheres.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.UtilisateursManager;
import fr.eni.encheres.bo.Utilisateurs;
//import fr.eni.encheres.foms.ConnexionForm;
import fr.eni.encheres.exceptions.BusinessException;

/**
 * Servlet implementation class ServletConnection
 */
@WebServlet("/authentification")
public class ServletAuthentification extends HttpServlet {
//	public static final String ATT_USER         	= "utilisateur";
//    public static final String ATT_FORM        	 	= "form";
//    public static final String ATT_SESSION_USER 	= "sessionUtilisateur";
    public static final String VUE_AUTHENTIFICATION = "/WEB-INF/jsp/authentification.jsp";
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAuthentification() {
        super();
        // TODO Auto-generated constructor stub
    }

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
		String identifiant = request.getParameter("identifiant");
		String motDePasse = request.getParameter("motdepasse");
		
		if (identifiant.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
			// en présence d'un mail
			try {
				utilisateur = utilisateurMngr.selectByMail(identifiant);
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			// en présence d'un pseudo
			try {
				utilisateur = utilisateurMngr.selectByPseudo(identifiant);
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (utilisateur.getPseudo() == null) {
			// utilisateur pas trouvé
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatServlet.UTILISATEUR_NON_TROUVE);
			request.setAttribute("Exception", be);
			RequestDispatcher rd = request.getRequestDispatcher(VUE_AUTHENTIFICATION);
			rd.forward(request, response);
		} else {
			// on test le mot de pass
			if (!motDePasse.equals(utilisateur.getMotDePasse())) {
				BusinessException be = new BusinessException();
				be.ajouterErreur(CodesResultatServlet.MOT_DE_PASSE_NON_CORRESPONDANT);
				request.setAttribute("Exception", be);
				RequestDispatcher rd = request.getRequestDispatcher(VUE_AUTHENTIFICATION);
				rd.forward(request, response);
			} else {
				HttpSession session = request.getSession();
				session.setAttribute("UtilisateurConnecte", utilisateur);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/inscription.jsp"); // TODO réfléchir ou est-ce qu'on va
				rd.forward(request, response);
			}
		}
    }
	

}

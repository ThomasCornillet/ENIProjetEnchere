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

import fr.eni.encheres.bll.UtilisateursManager;
import fr.eni.encheres.bo.Utilisateurs;
import fr.eni.encheres.exceptions.BusinessException;

/**
 * Servlet implementation class ServletProfile
 */
@WebServlet("/profile")
public class ServletProfil extends HttpServlet {
	public static final String VUE_PROFILE = "/WEB-INF/jsp/profil.jsp";
	private static final long serialVersionUID = 1L;
       
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
		//Si on reçoit un pseudo ou un noUtilisateur en paramètre dans l'URL, c'est que l'on veut afficher un profile
		String noUtilisateur = request.getParameter("noUtilisateur");
		String pseudo = request.getParameter("pseudo");
		List<Integer> listeCodesErreur= new ArrayList<>();
		if(noUtilisateur != null) {
			UtilisateursManager utilisateurMngr = UtilisateursManager.getInstance();
			try {
				Utilisateurs utilisateur = new Utilisateurs();
				utilisateurMngr.selectByPseudo(pseudo);
				request.setAttribute("utilisateur", utilisateur);
				System.out.println(pseudo); //TODO Delete this line
				this.getServletContext().getRequestDispatcher( VUE_PROFILE ).forward( request, response );
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			listeCodesErreur.add(CodesResultatServlet.UTILISATEUR_INEXISTANT);
			request.setAttribute("listeCodesErreur", listeCodesErreur);
			RequestDispatcher rd = request.getRequestDispatcher(VUE_PROFILE);
			rd.forward(request, response);
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

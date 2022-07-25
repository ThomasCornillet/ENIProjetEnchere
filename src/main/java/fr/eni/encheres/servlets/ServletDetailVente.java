package fr.eni.encheres.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.ArticlesManager;
import fr.eni.encheres.bll.UtilisateursManager;
import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Utilisateurs;
import fr.eni.encheres.exceptions.BusinessException;

/**
 * Servlet implementation class ServletDetailVente
 */
@WebServlet("/detailVente")
public class ServletDetailVente extends HttpServlet {
	private static final String VUE_DETAIL_VENTE = "/WEB-INF/jsp/detailVente.jsp";
	private static final long serialVersionUID = 1L;
    
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//----------------------------------------------------	
// Il va falloir la fusionner avec la servlet Article?
//----------------------------------------------------
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Integer> listeCodesErreur= new ArrayList<>();
		
		
		ArticlesManager articleMngr = ArticlesManager.getInstance();
		UtilisateursManager utilisateurMngr = UtilisateursManager.getInstance();
		try {
			HttpSession session = request.getSession();
			String pseudo = (String) session.getAttribute("pseudo");
			Utilisateurs utilisateur = utilisateurMngr.selectByPseudo(pseudo);	
			int noUtilisateur = utilisateur.getNoUtilisateur();
			request.setAttribute("utilisateur", utilisateur);
			request.setAttribute("noUtilisateur", noUtilisateur);
			
			Articles article = articleMngr.selectArticleByNoUtilisateur(noUtilisateur);
			request.setAttribute("article", article);
			
			System.out.println(pseudo); // TODO delete
			System.out.println(article.toString()); // TODO delete
			this.getServletContext().getRequestDispatcher( VUE_DETAIL_VENTE ).forward( request, response );
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

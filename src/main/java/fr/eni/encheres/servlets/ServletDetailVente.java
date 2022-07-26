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
import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Encheres;
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
		
		try {
			HttpSession session = request.getSession();
			
			int noArticle = Integer.parseInt(request.getParameter("id"));
			
			request.setAttribute("noArticle", noArticle);
			
			Articles article = articleMngr.selectArticleByNoArticle(noArticle);
			
			List<Encheres> encheres = new ArrayList<>();
			if(article != null){
			encheres = article.getListeEncheres();
			System.out.println(article.toString()); // TODO delete
			Encheres enchere = new Encheres();
				if(encheres != null) {			
				enchere = encheres.get(0);
				}
				request.setAttribute("article", article);
				request.setAttribute("enchere", enchere);
			}
			
			this.getServletContext().getRequestDispatcher( VUE_DETAIL_VENTE ).forward( request, response );
			
		} catch (BusinessException e) {
			for (int code : e.getListeCodesErreur()) {
				listeCodesErreur.add(code);
			}
		}
		if (!listeCodesErreur.isEmpty()) {
			request.setAttribute("listeCodesErreur", listeCodesErreur);
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

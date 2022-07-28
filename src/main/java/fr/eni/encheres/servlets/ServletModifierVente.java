package fr.eni.encheres.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.ArticlesManager;
import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Utilisateurs;
import fr.eni.encheres.exceptions.BusinessException;

/**
 * Servlet implementation class ServletEncheres
 */
@WebServlet("/modifierVente")
public class ServletModifierVente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // permet d'avoir l'encodage en base de données, sinon les caractères spéciaux et accents s'affichent mal
		List<Integer> listeCodesErreur = new ArrayList<>();
		ArticlesManager articlesMngrArticlesManager = ArticlesManager.getInstance();
		try {
			Articles articleModifie = articlesMngrArticlesManager.selectArticleByNoArticle(Integer.valueOf(request.getParameter("idArticle")));
			
			if(request.getParameter("pseudo") != null && !request.getParameter("pseudo").isBlank()){
				articleModifie.setNomArticle(request.getParameter("pseudo"));
			}
			
			if(request.getParameter("description") != null && !request.getParameter("description").isBlank()){
				articleModifie.setNomArticle(request.getParameter("description"));
			}
			
			if(request.getParameter("date_debut_encheres") != null){
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // modification format
				String date1 = request.getParameter("date_debut_encheres");
				articleModifie.setDate_debut_enchere(LocalDate.parse(date1,dtf));
			}
			
			if(request.getParameter("date_fin_encheres") != null){
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // modification format
				String date1 = request.getParameter("date_debut_encheres");
				articleModifie.setDate_debut_enchere(LocalDate.parse(date1,dtf));
			}
			
			if(request.getParameter("prix") != null && !request.getParameter("prix").isBlank()){
				articleModifie.setNomArticle(request.getParameter("prix"));
			}
			
			if(request.getParameter("categorie") != null && !request.getParameter("categorie").isBlank()){
				articleModifie.setNo_categorie(Integer.valueOf(request.getParameter("categorie")));
			}
			articlesMngrArticlesManager.updateVente(articleModifie);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			listeCodesErreur.add(CodesResultatServlet.UPDATE_VENTE_REQUEST_PARAMATER_NON_VALID);
		} catch (BusinessException e) {
			e.printStackTrace();
			for (int code : e.getListeCodesErreur()) {
				listeCodesErreur.add(code);
			}
		}
	}
}

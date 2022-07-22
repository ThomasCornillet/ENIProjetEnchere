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

import fr.eni.encheres.bll.ArticlesManager;
import fr.eni.encheres.bll.CategoriesManager;
import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Categories;
import fr.eni.encheres.exceptions.BusinessException;

/**
 * Servlet implementation class ServletAccueil
 */
@WebServlet("/accueil")
public class ServletAccueil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAccueil() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CategoriesManager categoriesMnger = CategoriesManager.getInstance();
		ArticlesManager articlesMnger = ArticlesManager.getInstance();
		try {
			request.setAttribute("listeCategories", categoriesMnger.selectAll());
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			request.setAttribute("listeArticles", articlesMnger.selectAll());
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if ((request.getParameter("portionNom") == null || request.getParameter("portionNom").isBlank()) && request.getParameter("categorie").equals("toutes")) {
			doGet(request, response);
		} else {
			ArticlesManager articlesMngr = ArticlesManager.getInstance();
			// on met un paramètre comme quoi il y a un filtre (afin de savoir si on affiche tout ou juste ce qui est filtré dans la jsp
			request.setAttribute("filtre", true);
			if (request.getParameter("portionNom").isBlank() || request.getParameter("portionNom") == null) {
				// si pas de filtre sur le nom, on ne gère que le filtre sur la catégorie
				try {
					request.setAttribute("listeArticles", articlesMngr.selectByCategorie(request.getParameter("categorie")));
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (request.getParameter("categorie").equals("toutes")) {
				// si pas de filtre sur la catégorie, on ne gere que le nom
				try {
					request.setAttribute("listeArticles", articlesMngr.selectByPortionNom(request.getParameter("portionNom")));
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			} else {
				List<Articles> listeArticles = new ArrayList<>();
				try {
					for (Articles a : articlesMngr.selectByCategorie(request.getParameter("categorie"))) {
						listeArticles.add(a);
					}
					for (Articles a : articlesMngr.selectByPortionNom(request.getParameter("portionNom"))) {
						listeArticles.add(a);
					}
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("listeArticles", listeArticles);
			}
			// on recharge la liste des catégories pour l'affichage dans le menu déroulant
			CategoriesManager categoriesMnger = CategoriesManager.getInstance();
			try {
				request.setAttribute("listeCategories", categoriesMnger.selectAll());
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp");
			rd.forward(request, response);
		}
	}

}

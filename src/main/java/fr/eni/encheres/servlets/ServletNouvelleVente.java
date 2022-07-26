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

import fr.eni.encheres.bll.CategoriesManager;
import fr.eni.encheres.bo.Categories;
import fr.eni.encheres.exceptions.BusinessException;

/**
 * Servlet implementation class ServletNouvelleVente
 */
@WebServlet("/nouvelleVente")
public class ServletNouvelleVente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletNouvelleVente() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CategoriesManager categoriesMnger = CategoriesManager.getInstance();
		List<Categories> listeCategories = new ArrayList<>();//instanciation d'une liste de catégories, on déclare l'objet de type 'List' contenant des objets de type 'Categories' (nom de ma class java) avec un type specifique de List ArrayList.
		try {
			listeCategories = categoriesMnger.selectAll();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//transmettre notre liste de catégories à la jsp.
		//passer la liste de catégorie en attributs de requêtes
		//TODO gérer l'affichage du retrait
		//TODO gérer filtre : page uniquement visible par un utilisateur connecté. 
		request.setAttribute("listeCategories", listeCategories);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/nouvelleVente.jsp")  ;   //permet de dispatcher la requete en iindiquant type nom de variable
		rd.forward(request, response);//appel à la méthode forward de la variable rd requestDispatcher avec en parametre la requete et la reponse (request response)
	}

	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}	
	
	
	
}




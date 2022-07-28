package fr.eni.encheres.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import fr.eni.encheres.bll.RetraitsManager;
import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Categories;
import fr.eni.encheres.bo.Retraits;
import fr.eni.encheres.bo.Utilisateurs;
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
		request.setCharacterEncoding("UTF-8"); // permet d'avoir l'encodage en base de données, sinon les caractères spéciaux et accents s'affichent mal
		Articles article = new Articles();
		List<Integer> listeCodesErreur = new ArrayList<>();
		
		//FAIRE tout le traitement du formulaire 
		
		
		//Récupérer tout ce qui est not null dans la base de données afin de traites les infos dans la servlet.
		
		
		
		//nom article
		article.setNomArticle(request.getParameter("nom"));

		
		
	
		//description 
		article.setDescription(request.getParameter("description"));
		
		//date debut enchere
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // modification format
		String date1 = request.getParameter("date_debut_encheres");
		article.setDate_debut_enchere(LocalDate.parse(date1,dtf));
		
		//fin date enchere 
		
		String date2 = request.getParameter("date_fin_encheres");
		article.setDate_fin_enchere(LocalDate.parse(date2,dtf));
		
		//prix initial
		article.setPrix_initial(Integer.valueOf(request.getParameter("prix")));
		
		//numero utilisateur 
//		String numeroUtilisateur = (String) request.getSession().getAttribute("UtilisateurConnecte"); // modification
//		article.setNo_utilisateur(Integer.valueOf(numeroUtilisateur));
		Utilisateurs vendeur = (Utilisateurs) request.getSession().getAttribute("UtilisateurConnecte");
		article.setNo_utilisateur(vendeur.getNoUtilisateur());
		//numero categorie 
		article.setNo_categorie(Integer.valueOf(request.getParameter("categorie")));
		
		//numero variable vendu 0 equivaut à false, 1 équivaut à true
		article.setVendu(false);
		
		
		if (request.getAttribute("retrait") != null && (boolean)request.getAttribute("retrait") == true) {
			RetraitsManager retraitsMnger = RetraitsManager.getInstance();
			Retraits retrait = new Retraits();
			retrait.setRue(request.getParameter("rue"));
			retrait.setCodePostal(Integer.valueOf(request.getParameter("code_postal")));
			retrait.setVille(request.getParameter("ville"));
			try {
				retraitsMnger.insert(retrait);
			} catch (BusinessException e) {
				e.printStackTrace();
				for (int code : e.getListeCodesErreur()) {
					listeCodesErreur.add(code);
				}
			}
		}
		
		
		try {
			ArticlesManager.getInstance().insertArticle(article);
			response.sendRedirect("accueil");
		} catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			
			e.printStackTrace();
			
			for (int code : e.getListeCodesErreur()) {
				listeCodesErreur.add(code);
			}
			
			request.setAttribute("listeCodesErreur", listeCodesErreur);
		
			request.getRequestDispatcher("WEB-INF/jsp/nouvelleVente.jsp").forward(request, response);
		}
		
		
	}	
	
	
	
}




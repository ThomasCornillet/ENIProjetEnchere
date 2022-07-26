package fr.eni.encheres.servlets;

import java.io.IOException;
import java.time.LocalDate;
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
import fr.eni.encheres.bll.EncheresManager;
import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Categories;
import fr.eni.encheres.bo.Encheres;
import fr.eni.encheres.bo.Utilisateurs;
import fr.eni.encheres.dal.EncheresDAO;
import fr.eni.encheres.exceptions.BusinessException;

/**
 * Servlet implementation class ServletAccueil
 */
@WebServlet(urlPatterns = {"/accueil", "/accueilfiltre"})
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
		request.setCharacterEncoding("UTF-8"); // permet d'avoir l'encodage en base de données, sinon les caractères spéciaux et accents s'affichent mal
		CategoriesManager categoriesMnger = CategoriesManager.getInstance();
		ArticlesManager articlesMnger = ArticlesManager.getInstance();
		List<Integer> listeCodesErreur = new ArrayList<>();
		try {
			request.setAttribute("listeCategories", categoriesMnger.selectAll());
			request.setAttribute("listeArticles", articlesMnger.selectAll());
		} catch (BusinessException e) {
			e.printStackTrace();
			if (e.hasErreurs()) {
				for (int code : e.getListeCodesErreur()) {
					listeCodesErreur.add(code);
				}
			}
		}
		
		if (!listeCodesErreur.isEmpty()) {
			request.setAttribute("listeCodesErreur", listeCodesErreur);
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Integer> listeCodesErreur = new ArrayList<>();
		ArticlesManager articlesMngr = ArticlesManager.getInstance();
		EncheresManager encheresMngr = EncheresManager.getInstance();
		// url /accueilfiltre
		if(request.getServletPath().equals("/accueilfiltre")) {
			List<Articles> listesArticlesFiltres = new ArrayList<>();
			if ((request.getParameter("portionNom").isBlank() || request.getParameter("portionNom") == null) && (request.getParameter("categorie").equals("toutes"))) {
				// si pas de filtre, on charge tous les articles
				try {
					listesArticlesFiltres = articlesMngr.selectAll();
				} catch (BusinessException e) {
					e.printStackTrace();
					if (e.hasErreurs()) {
						for (int code : e.getListeCodesErreur()) {
							listeCodesErreur.add(code);
						}
					}
				}
			} else if (request.getParameter("portionNom") == null || request.getParameter("portionNom").isBlank()) {
				// si pas de filtre sur le nom, on ne gère que le filtre sur la catégorie
				filtrerCategorie(request, articlesMngr, listesArticlesFiltres, listeCodesErreur);
			} else if (request.getParameter("categorie").equals("toutes")) {
				// si pas de filtre sur la catégorie, on ne gere que le nom
				filtrerPortionNom(request, articlesMngr, listesArticlesFiltres, listeCodesErreur);
			} else {
				// sinon, filtre sur le nom et la catégorie
				// TODO à refaire
				filtrerCategorie(request, articlesMngr, listesArticlesFiltres, listeCodesErreur);
				filtrerPortionNom(request, articlesMngr, listesArticlesFiltres, listeCodesErreur);
			}
			if ( request.getSession().getAttribute("connecte") == null || !(boolean) request.getSession().getAttribute("connecte")) {
				// si pas d'utilisateur connecté, on s'arrête ici et on passe la liste filtrée en attribut de requête
				request.setAttribute("listeArticles", listesArticlesFiltres);
			} else {
				// si un utilisateur est connecté, on ajoute des "filtres" à la liste des premiers filtres qui sont toujours valide
				// on crée une nouvelle liste d'articles pour le filtre
				List<Articles> listesArticlesFiltresConnecte = new ArrayList<>();
				if (request.getParameter("achats") != null) {
					// nous n'auront alors que les filtres sur toutes les articles
					if (request.getParameter("encheresOuvertes") != null) {
						// enchères ouvertes
						filtrerEncheresOuvertes(request, articlesMngr, listesArticlesFiltres, listesArticlesFiltresConnecte, listeCodesErreur);
					}
					if (request.getParameter("encheresEnCours") != null) {
						// mes enchères en cours
						filtrerMesEncheresEnCours(request, encheresMngr, listesArticlesFiltres, listesArticlesFiltresConnecte, listeCodesErreur);
					}
					if (request.getParameter("encheresRemportees") != null) {
						// mes enchères remportées
						filtrerMesEncheresRemportes(request, encheresMngr, listesArticlesFiltres, listesArticlesFiltresConnecte, listeCodesErreur);
					}
					request.setAttribute("listeArticles", listesArticlesFiltresConnecte);
				} else if (request.getParameter("mesVentes") != null) {
					// nous n'auront alors que les filtres sur les ventes de l'utilisateur connecté
					List<Articles> mesVentes = new ArrayList<>();
					try {
						mesVentes = articlesMngr.selectArticleByNoUtilisateur(((Utilisateurs)request.getSession().getAttribute("utilisateurConnecte")).getNoUtilisateur());
					} catch (BusinessException e) {
						e.printStackTrace();
						if (e.hasErreurs()) {
							for (int code : e.getListeCodesErreur()) {
								listeCodesErreur.add(code);
							}
						}
					}
					if (request.getParameter("ventesEnCours") != null) {
						// mes ventes en cours
						filtrerMesVentesEnCours(listesArticlesFiltres, listesArticlesFiltresConnecte, mesVentes, listeCodesErreur);
					}
					if (request.getParameter("ventesNonDebutees") != null) {
						// mes ventes non débutées
						filtrerMesVentesNonDebutees(listesArticlesFiltres, listesArticlesFiltresConnecte, mesVentes, listeCodesErreur);
					}
					if (request.getParameter("ventesTerminees") != null) {
						// mes ventes terminées
						filtrerMesVentesTerminees(listesArticlesFiltres, listesArticlesFiltresConnecte, mesVentes, listeCodesErreur);
					}
					request.setAttribute("listeArticles", listesArticlesFiltresConnecte);
				} else {
					// pas de filtres connecté de sélectionné (TODO ce qui ne devrait pas arrivé quand on aura revu la jsp accueil)
					request.setAttribute("listeArticles", listesArticlesFiltres);
				}
			}
			
			// on recharge la liste des catégories pour l'affichage dans le menu déroulant
			CategoriesManager categoriesMnger = CategoriesManager.getInstance();
			try {
				request.setAttribute("listeCategories", categoriesMnger.selectAll());
			} catch (BusinessException e) {
				e.printStackTrace();
				if (e.hasErreurs()) {
					for (int code : e.getListeCodesErreur()) {
						listeCodesErreur.add(code);
					}
				}
			}
			
			if (!listeCodesErreur.isEmpty()) {
				request.setAttribute("listeCodesErreur", listeCodesErreur);
			}
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp");
			rd.forward(request, response);
			
		} else if (request.getServletPath().equals("/accueil")) {
			// normalement pas besoin puisque pas de POST sur l'url /accueil, mais au cas où quand même
			doGet(request, response);
		} 
	}

	private void filtrerCategorie(HttpServletRequest request, ArticlesManager articlesMngr, List<Articles> listesArticlesFiltres, List<Integer> listeCodesErreur) {
		try {
			for (Articles a : articlesMngr.selectByNoCategorie(Integer.valueOf(request.getParameter("categorie")))) {
				if (!listesArticlesFiltres.contains(a))
					listesArticlesFiltres.add(a);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			listeCodesErreur.add(CodesResultatServlet.FILTRE_CATEGORIE_ERREUR);
		} catch (BusinessException e) {
			e.printStackTrace();
			if (e.hasErreurs()) {
				for (int code : e.getListeCodesErreur()) {
					listeCodesErreur.add(code);
				}
			}
		}
	}
	
	private void filtrerPortionNom(HttpServletRequest request, ArticlesManager articlesMngr, List<Articles> listesArticlesFiltres, List<Integer> listeCodesErreur) {
		try {
			for (Articles a : articlesMngr.selectByPortionNom(request.getParameter("portionNom"))) {
				if (!listesArticlesFiltres.contains(a))
					listesArticlesFiltres.add(a);
			}
		} catch (BusinessException e) {
			e.printStackTrace();
			if (e.hasErreurs()) {
				for (int code : e.getListeCodesErreur()) {
					listeCodesErreur.add(code);
				}
			}
		}
	}
	
	private void filtrerEncheresOuvertes(HttpServletRequest request, ArticlesManager articlesMngr, List<Articles> listesArticlesFiltres, List<Articles> listesArticlesFiltresConnecte, List<Integer> listeCodesErreur) {
		for (Articles a : listesArticlesFiltres) {
			if (a.getDate_fin_enchere().isAfter(LocalDate.now())) {
				if (!listesArticlesFiltresConnecte.contains(a))
					listesArticlesFiltresConnecte.add(a);
			}
		}
	}
	
	private void filtrerMesEncheresEnCours(HttpServletRequest request, EncheresManager encheresMngr, List<Articles> listesArticlesFiltres, List<Articles> listesArticlesFiltresConnecte, List<Integer> listeCodesErreur) {
		for (Articles a : listesArticlesFiltres) {
			try {
				for (Encheres e : encheresMngr.selectByNoUtilisateur((Integer)request.getSession().getAttribute("utilisateurConnecte"))) {
					if (a.getNoArticle() == e.getNoArticle()) {
						if (!listesArticlesFiltresConnecte.contains(a))
							listesArticlesFiltresConnecte.add(a);
					}
				}
			} catch (NullPointerException e) {
				e.printStackTrace();
				BusinessException be = new BusinessException();
				be.ajouterErreur(CodesResultatServlet.LISTE_ARTICLE_PREMIER_FILTRE_VIDE);
			} catch (BusinessException e) {
				e.printStackTrace();
				if (e.hasErreurs()) {
					for (int code : e.getListeCodesErreur()) {
						listeCodesErreur.add(code);
					}
				}
			}
		}
	}
	
	private void filtrerMesEncheresRemportes(HttpServletRequest request, EncheresManager encheresMngr, List<Articles> listesArticlesFiltres, List<Articles> listesArticlesFiltresConnecte, List<Integer> listeCodesErreur) {
		try {
			for (Articles a : listesArticlesFiltres) {
				if (a.getDate_fin_enchere().isAfter(LocalDate.now())) {
					for (Encheres e : encheresMngr.selectByNoUtilisateur((Integer)request.getSession().getAttribute("utilisateurConnecte"))) {
						if (a.getNoArticle() == e.getNoArticle()) {
							// il faut maintenant savoir si c'est l'enchere qui a remporté
							if (e.getNoEnchere() == encheresMngr.selectEnchereGagnateByNoArticle(a.getNoArticle()).getNoEnchere()) {
								if (!listesArticlesFiltresConnecte.contains(a))
									listesArticlesFiltresConnecte.add(a);
							}
						}
					}
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatServlet.LISTE_ARTICLE_PREMIER_FILTRE_VIDE);
		} catch (BusinessException e) {
			e.printStackTrace();
			if (e.hasErreurs()) {
				for (int code : e.getListeCodesErreur()) {
					listeCodesErreur.add(code);
				}
			}
		}
	}
	
	private void filtrerMesVentesEnCours(List<Articles> listesArticlesFiltres, List<Articles> listesArticlesFiltresConnecte, List<Articles> mesVentes, List<Integer> listeCodesErreur) {
		try {
			for (Articles a : listesArticlesFiltres) {
				if (mesVentes.contains(a)) {
					if (!listesArticlesFiltresConnecte.contains(a))
						listesArticlesFiltresConnecte.add(a);
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatServlet.LISTE_ARTICLE_PREMIER_FILTRE_VIDE);
		}
	}
	
	private void filtrerMesVentesNonDebutees(List<Articles> listesArticlesFiltres, List<Articles> listesArticlesFiltresConnecte, List<Articles> mesVentes, List<Integer> listeCodesErreur) {
		try {
			for (Articles a : listesArticlesFiltres) {
				if (a.getDate_debut_enchere().isAfter(LocalDate.now()) && mesVentes.contains(a)) {
					if (!listesArticlesFiltresConnecte.contains(a))
						listesArticlesFiltresConnecte.add(a);
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatServlet.LISTE_ARTICLE_PREMIER_FILTRE_VIDE);
		}
	}
	
	private void filtrerMesVentesTerminees(List<Articles> listesArticlesFiltres, List<Articles> listesArticlesFiltresConnecte, List<Articles> mesVentes, List<Integer> listeCodesErreur) {
		try {
			for (Articles a : listesArticlesFiltres) {
				if (a.getDate_fin_enchere().isAfter(LocalDate.now()) && mesVentes.contains(a)) {
					if (!listesArticlesFiltresConnecte.contains(a))
						listesArticlesFiltresConnecte.add(a);
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatServlet.LISTE_ARTICLE_PREMIER_FILTRE_VIDE);
		}
	}

}

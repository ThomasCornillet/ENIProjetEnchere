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
		// url /accueilfiltre
		if(request.getServletPath().equals("/accueilfiltre")) {
			ArticlesManager articlesMngr = ArticlesManager.getInstance();
			List<Articles> listeArticlesPremierFiltre = new ArrayList<>();
			if ((request.getParameter("portionNom") == null || request.getParameter("portionNom").isBlank()) && request.getParameter("categorie").equals("toutes") 
					&& ((request.getSession().getAttribute("connecte")) == null || ( (Boolean)request.getSession().getAttribute("connecte")) == false )) {
				// si pas de filtre et pas connecté, on affiche tout avec doGet
				try {
					listeArticlesPremierFiltre = articlesMngr.selectAll();
				} catch (BusinessException e) {
					e.printStackTrace();
					if (e.hasErreurs()) {
						for (int code : e.getListeCodesErreur()) {
							listeCodesErreur.add(code);
						}
					}
				}
				doGet(request, response);
			} else {
				// on met un paramètre comme quoi il y a un filtre (afin de savoir si on affiche tout ou juste ce qui est filtré dans la jsp
					// TODO pas sur que ce soit nécessaire
				request.setAttribute("filtre", true);
				if (request.getParameter("portionNom").isBlank() || request.getParameter("portionNom") == null) {
					// si pas de filtre sur le nom, on ne gère que le filtre sur la catégorie
					try {
						listeArticlesPremierFiltre = articlesMngr.selectByCategorie(request.getParameter("categorie"));
//						request.setAttribute("listeArticles", articlesMngr.selectByCategorie(request.getParameter("categorie")));
					} catch (BusinessException e) {
						e.printStackTrace();
						if (e.hasErreurs()) {
							for (int code : e.getListeCodesErreur()) {
								listeCodesErreur.add(code);
							}
						}
					}
				} else if (request.getParameter("categorie").equals("toutes")) {
					// si pas de filtre sur la catégorie, on ne gere que le nom
					try {
						listeArticlesPremierFiltre = articlesMngr.selectByPortionNom(request.getParameter("portionNom"));
//						request.setAttribute("listeArticles", articlesMngr.selectByPortionNom(request.getParameter("portionNom")));
					} catch (BusinessException e) {
						e.printStackTrace();
						if (e.hasErreurs()) {
							for (int code : e.getListeCodesErreur()) {
								listeCodesErreur.add(code);
							}
						}
					}				
				} else {
					// sinon, filtre sur le nom et la catégorie
//					List<Articles> listeArticles = new ArrayList<>();
					try {
						for (Articles a : articlesMngr.selectByCategorie(request.getParameter("categorie"))) {
							listeArticlesPremierFiltre.add(a);
						}
						for (Articles a : articlesMngr.selectByPortionNom(request.getParameter("portionNom"))) {
							listeArticlesPremierFiltre.add(a);
						}
					} catch (BusinessException e) {
						e.printStackTrace();
						if (e.hasErreurs()) {
							for (int code : e.getListeCodesErreur()) {
								listeCodesErreur.add(code);
							}
						}
					}
//					request.setAttribute("listeArticles", listeArticles);
				}
				// maintenant on gère les filtres avec utilisateur connecté
				if ((request.getSession().getAttribute("connecte")) == null || ( (Boolean)request.getSession().getAttribute("connecte")) == false ) {
					// pas connecté, on charge la liste des articles à base des premiers filtre
					request.setAttribute("listeArticles", listeArticlesPremierFiltre);
				} else {
					Utilisateurs utilisateurConnecte = (Utilisateurs) request.getSession().getAttribute("UtilisateurConnecte");
					List<Articles> listeArticlesSecondFiltre = new ArrayList<>();
					EncheresManager encheresMnger = EncheresManager.getInstance();
					// un utilisateur est connecté, on attaque les nouveaux filtres
					if (request.getParameter("achats") != null) {
						// nous n'auront alors que les filtres sur toutes les articles
						if (request.getParameter("encheresOuvertes") != null) {
							// enchères ouvertes
							for (Articles a : listeArticlesPremierFiltre) {
								if (a.getDate_fin_enchere().isAfter(LocalDate.now())) {
									listeArticlesSecondFiltre.add(a);
								}
							}
						}
						if (request.getParameter("encheresEnCours") != null) {
							// mes enchères en cours
							try {
								for (Articles a : listeArticlesPremierFiltre) {
									for (Encheres e : encheresMnger.selectByNoUtilisateur((Integer)request.getSession().getAttribute("utilisateurConnecte"))) {
										if (a.getNoArticle() == e.getNoArticle()) {
											listeArticlesSecondFiltre.add(a);
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
						if (request.getParameter("encheresRemportees") != null) {
							// mes enchères remportées
							try {
								for (Articles a : listeArticlesPremierFiltre) {
									if (a.getDate_fin_enchere().isAfter(LocalDate.now())) {
										for (Encheres e : encheresMnger.selectByNoUtilisateur((Integer)request.getSession().getAttribute("utilisateurConnecte"))) {
											if (a.getNoArticle() == e.getNoArticle()) {
												// il faut maintenant savoir si c'est l'enchere qui a remporté
												if (e.getNoEnchere() == encheresMnger.selectEnchereGagnateByNoArticle(a.getNoArticle()).getNoEnchere()) {
													listeArticlesSecondFiltre.add(a);
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
					} else {
						// nous n'auront alors que les filtres sur les ventes de l'utilisateur connecté
						List<Articles> mesVentes = new ArrayList<>();
						try {
							mesVentes = articlesMngr.selectArticleByNoUtilisateur(utilisateurConnecte.getNoUtilisateur());
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
							try {
								for (Articles a : listeArticlesPremierFiltre) {
									if (mesVentes.contains(a)) {
										listeArticlesSecondFiltre.add(a);
									}
								}
							} catch (NullPointerException e) {
								e.printStackTrace();
								BusinessException be = new BusinessException();
								be.ajouterErreur(CodesResultatServlet.LISTE_ARTICLE_PREMIER_FILTRE_VIDE);
							}
						}
						if (request.getParameter("ventesNonDebutees") != null) {
							// mes ventes non débutées
							try {
								for (Articles a : listeArticlesPremierFiltre) {
									if (a.getDate_debut_enchere().isAfter(LocalDate.now()) && mesVentes.contains(a)) {
										listeArticlesSecondFiltre.add(a);
									}
								}
							} catch (NullPointerException e) {
								e.printStackTrace();
								BusinessException be = new BusinessException();
								be.ajouterErreur(CodesResultatServlet.LISTE_ARTICLE_PREMIER_FILTRE_VIDE);
							}
						}
						if (request.getParameter("ventesTerminees") != null) {
							// mes ventes terminées
							try {
								for (Articles a : listeArticlesPremierFiltre) {
									if (a.getDate_fin_enchere().isAfter(LocalDate.now()) && mesVentes.contains(a)) {
										listeArticlesSecondFiltre.add(a);
									}
								}
							} catch (NullPointerException e) {
								e.printStackTrace();
								BusinessException be = new BusinessException();
								be.ajouterErreur(CodesResultatServlet.LISTE_ARTICLE_PREMIER_FILTRE_VIDE);
							}
						}
					}
					request.setAttribute("listeArticles", listeArticlesSecondFiltre);
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
			}
		} else if (request.getServletPath().equals("/accueil")) {
			doGet(request, response);
		}
	}

}

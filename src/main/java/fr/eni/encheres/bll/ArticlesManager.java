package fr.eni.encheres.bll;

import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Categories;
import fr.eni.encheres.bo.Encheres;
import fr.eni.encheres.dal.ArticlesDAO;
import fr.eni.encheres.dal.CategoriesDAO;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.exceptions.BusinessException;

public class ArticlesManager {
	private ArticlesDAO articlesDAO;
	private CategoriesDAO categoriesDAO;
	private static ArticlesManager instance;

	private ArticlesManager() {
		this.articlesDAO = DAOFactory.getArticlesDAO();
		this.categoriesDAO = DAOFactory.getCategoriesDAO();
	}

	public static ArticlesManager getInstance() {
		if(instance == null) {
			instance = new ArticlesManager();
		}
		return instance;
	}
	
	public List<Articles> selectAll() throws BusinessException {
		List<Articles> retour = articlesDAO.selectAll();
		System.out.println(retour);
		EncheresManager encheresMngr = EncheresManager.getInstance();
		for (Articles a : retour) {
			List<Encheres> listeEncheres = encheresMngr.selectByNoArticle(a.getNoArticle());
			a.setListeEncheres(listeEncheres);
			if (!listeEncheres.isEmpty()) {
				a.setMontant_enchere(listeEncheres.get(0).getMontantEnchere());
			}
		}
		return retour;
	}
	
	public List<Articles> selectByCategorie(String categorie) throws BusinessException {
		List<Articles> retour = new ArrayList<>();
		if (categorie.isBlank() || categorie == null) {
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatBLL.CATEGORIE_VIDE_OU_NULL);
			throw be;
		}
		Integer numeroCategorie = null;
		List<Categories> listeCategories = categoriesDAO.selectAll();
		if (listeCategories != null) {
			for (Categories c : listeCategories) {
				if (categorie.equals(c.getLibelle())) {
					numeroCategorie = c.getNoCategorie();
				}
			}
		}
		if (numeroCategorie == null) {
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatBLL.CATEGORIE_INEXISTANTE);
			throw be;
		} else {
			retour = articlesDAO.selectByCategorie(numeroCategorie);
		}
		return retour;
	}
	
	public List<Articles> selectByPortionNom(String portionNom) throws BusinessException {
		if (portionNom.isBlank() || portionNom == null) {
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatBLL.PORTION_NOM_VIDE);
			throw be;
		}
		List<Articles> retour = new ArrayList<>();
		retour = articlesDAO.selectByPortionNom(portionNom);
		return retour;
	}

	public List<Articles> selectArticleByNoUtilisateur(int noUtilisateur) throws BusinessException {
		if (noUtilisateur <= 0 ) {
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatBLL.SELECT_ARTICLE_BY_NO_UTILISATEUR_NO_NEGATIF);
			throw be;
		}

		
		List<Articles> retour = articlesDAO.selectArticleByNoUtilisateur(noUtilisateur);
		return retour;
	}
	
	public Articles selectArticleByNoArticle(int noArticle) throws BusinessException {
		if (noArticle <= 0) {
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatBLL.NO_ARTICLE_NEGATIF);
			throw be;
		}
		return articlesDAO.selectArticleByNoArticle(noArticle);
	}
	
	public List<Articles> selectByNoCategorie(int noCategorie) throws BusinessException {
		if (noCategorie <= 0) {
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatBLL.NO_CATEGORIE_NEGATIF);
			throw be;
		}
		return articlesDAO.selectByCategorie(noCategorie);
	}
	
	public List<Articles> selectByCategorieAndPortionNom(int noCategorie, String portionNom) throws BusinessException {
		if (noCategorie <= 0) {
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatBLL.NO_CATEGORIE_NEGATIF);
			throw be;
		}
		if (portionNom == null || portionNom.isBlank()) {
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatBLL.PORTION_NOM_VIDE);
			throw be;
		}
		return articlesDAO.selectByCategorieAndPortionNom(noCategorie, portionNom);
	}

	public List<Articles> selectAllArticlesEnCours() throws BusinessException {
		List<Articles> retour = null;
		retour = articlesDAO.selectAllArticlesEnCours();
		return retour;
	}

	public void updateVenteTerminee(Articles article) throws BusinessException {
		if (article == null) {
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatBLL.UPDATE_ARTICLE_ARTICLE_NULL);
			throw be;
		}
		// TODO check métier à faire, mais pour l'isntant vu l'utilisation pas besoin (et pas le temps)
		articlesDAO.updateVenteTerminee(article);
	}
	
	public void insertArticle(Articles article) throws BusinessException {
		if (article == null) {
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatBLL.INSERT_ARTICLE_ARTICLE_NULL);
			throw be;
		}
		// TODO ajouter les vérifs métiers
		// 1 er test nom_article max 30 caractères
		if (article.getNomArticle().length() > 30) {
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatBLL.VERIF_INSERT_ARTICLE_NOM_TROP_GRAND);
			throw be;
		}
		
		articlesDAO.insertArticle(article);
	}

	public void updateVente(Articles articleModifie) throws BusinessException {
		if (articleModifie == null) {
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatBLL.UPDATE_VENTE_ARTICLE_NULL);
			throw be;
		}
		// TODO ajouter les vérifs métiers
		
		articlesDAO.updateVente(articleModifie);
	}
	
}

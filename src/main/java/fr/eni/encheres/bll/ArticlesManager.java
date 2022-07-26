package fr.eni.encheres.bll;

import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Categories;
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
		return articlesDAO.selectAll(); // j'ai décidé de renvoyer l'erreur et non de la traiter ici parce que la traiter consisterait en la renvoyer en tant que BLL exception
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

	public Articles selectArticleByNoUtilisateur(int noUtilisateur) throws BusinessException {
		Articles retour = articlesDAO.selectArticleByNoUtilisateur(noUtilisateur);
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
}

package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.exceptions.BusinessException;

public interface ArticlesDAO {
	
	public List<Articles> selectAll() throws BusinessException;
	public List<Articles> selectByCategorie(int no_categorie) throws BusinessException;
	public Articles selectByNom(String nom) throws BusinessException;
	public List<Articles> selectByPortionNom(String portionNom) throws BusinessException;
	public List<Articles> selectArticleByNoUtilisateur(int noUtilisateur) throws BusinessException;
	public Articles selectArticleByNoArticle(int noArticle) throws BusinessException; 

}

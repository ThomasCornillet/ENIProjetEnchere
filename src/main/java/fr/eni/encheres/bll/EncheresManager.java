package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Encheres;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.EncheresDAO;
import fr.eni.encheres.exceptions.BusinessException;

public class EncheresManager {
	private EncheresDAO encheresDAO;
	private static EncheresManager instance;
	
	private EncheresManager() {
		encheresDAO = DAOFactory.getEncheresDAO();
	}
	
	public static EncheresManager getInstance() {
		if (instance == null) {
			instance = new EncheresManager();
		}
		return instance;
	}
	
	public List<Encheres> selectByNoUtilisateur(int noUtilisateur) throws BusinessException {
		if (noUtilisateur <= 0) {
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatBLL.SELECT_ENCHERES_BY_NO_UTILISATEUR_NO_NEGATIF);
			throw be;
		}
		return encheresDAO.selectByNoUtilisateur(noUtilisateur);
	}
	
	public List<Encheres> selectByNoArticle(int noArticle) throws BusinessException {
		if (noArticle <= 0) {
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatBLL.SELECT_ENCHERES_BY_NO_ARTICLE_NO_NEGATIF);
			throw be;
		}
		return encheresDAO.selectByNoArticle(noArticle);
	}
	
	public Encheres selectEnchereGagnateByNoArticle(int noArticle) throws BusinessException {
		if (noArticle <= 0) {
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatBLL.SELECT_ENCHERES_BY_NO_ARTICLE_NO_NEGATIF);
			throw be;
		}
		return selectEnchereGagnateByNoArticle(noArticle);
	}

	public void insert(Encheres enchere) throws BusinessException{
		encheresDAO.insert(enchere);
	}

}

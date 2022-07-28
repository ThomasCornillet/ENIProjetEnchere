package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Retraits;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.RetraitsDAO;
import fr.eni.encheres.exceptions.BusinessException;

public class RetraitsManager {
	private RetraitsDAO retraitsDAO;
	private static RetraitsManager instance;
	
	private RetraitsManager() {
		this.retraitsDAO = DAOFactory.getRetraitsDAO();
	}

	public static RetraitsManager getInstance() {
		if(instance == null) {
			instance = new RetraitsManager();
		}
		return instance;
	}

	public Retraits selectByNoArticle(int noArticle) throws BusinessException {
		if (noArticle <= 0) {
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatBLL.NO_ARTICLE_NEGATIF_RETRAIT);
			throw be;
		}
		return retraitsDAO.selectByNoArticle(noArticle);
	}

	public void insert(Retraits retrait) throws BusinessException {
		if (retrait == null ) {
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatBLL.RETRAIT_NULL);
			throw be;
		}
		retraitsDAO.insert(retrait);
	}
}

package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Retraits;
import fr.eni.encheres.exceptions.BusinessException;

public interface RetraitsDAO {

	public Retraits selectByNoArticle(int noArticle) throws BusinessException;

	public void insert(Retraits retrait) throws BusinessException;

}

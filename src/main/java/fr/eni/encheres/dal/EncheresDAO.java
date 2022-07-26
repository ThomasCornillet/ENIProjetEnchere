package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Encheres;
import fr.eni.encheres.exceptions.BusinessException;

public interface EncheresDAO {
	
	public List<Encheres> selectByNoUtilisateur(int noUtilisateur) throws BusinessException;
	public List<Encheres> selectByNoArticle(int noArticle) throws BusinessException;
	public Encheres selectEnchereGagnateByNoArticle(int noArticle) throws BusinessException;

}

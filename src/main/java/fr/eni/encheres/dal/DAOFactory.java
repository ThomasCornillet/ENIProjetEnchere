package fr.eni.encheres.dal;

import fr.eni.encheres.dal.jdbc.ArticlesDAOJdbcImpl;
import fr.eni.encheres.dal.jdbc.CategoriesDAOJdbcImpl;
import fr.eni.encheres.dal.jdbc.UtilisateursDAOJdbcImpl;

public abstract class DAOFactory {
	
	public static UtilisateursDAO getUtilisateursDAO()
	{
		return new UtilisateursDAOJdbcImpl();
	}

	public static ArticlesDAO getArticlesDAO() {
		return new ArticlesDAOJdbcImpl();
	}
	
	public static CategoriesDAO getCategoriesDAO() {
		return new CategoriesDAOJdbcImpl();
	}
}
	
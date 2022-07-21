package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Categories;
import fr.eni.encheres.dal.CategoriesDAO;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.exceptions.BusinessException;

public class CategoriesManager {
	private CategoriesDAO categoriesDAO;
	private static CategoriesManager instance;
	
	private CategoriesManager() {
		this.categoriesDAO = DAOFactory.getCategoriesDAO();
	}
	
	public static CategoriesManager getInstance() {
		if (instance == null) {
			instance = new CategoriesManager();
		}
		return instance;
	}
	
	public List<Categories> selectAll() throws BusinessException {
		return categoriesDAO.selectAll();
	}

}

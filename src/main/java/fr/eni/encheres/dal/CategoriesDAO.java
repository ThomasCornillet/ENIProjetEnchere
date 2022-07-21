package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Categories;
import fr.eni.encheres.exceptions.BusinessException;

public interface CategoriesDAO {
	
	public List<Categories> selectAll() throws BusinessException;

}

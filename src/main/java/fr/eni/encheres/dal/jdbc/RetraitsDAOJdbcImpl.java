package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Retraits;
import fr.eni.encheres.dal.CodesResultatDAL;
import fr.eni.encheres.dal.RetraitsDAO;
import fr.eni.encheres.exceptions.BusinessException;

public class RetraitsDAOJdbcImpl implements RetraitsDAO {
	
	public static final String SELECT_BY_NO_ARTICLE = "SELECT * FROM RETRAITS WHERE no_article = ?";
	

	@Override
	public Retraits selectByNoArticle(int noArticle) throws BusinessException {
		Retraits retour = null;
			try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_NO_ARTICLE);
			pstmt.setInt(1, noArticle);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				retour = creerRetrait(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_RETRAIT_BY_NO_ARTICLE_CONNEXTION_ERROR); //ici pas seulement connexion echec mais echec de la sélection
			throw businessException;
		}
		return retour;
	}


	private Retraits creerRetrait(ResultSet rs) throws SQLException {
		Retraits retour = new Retraits();
		retour.setNoArticle(rs.getInt("no_article"));
		retour.setRue(rs.getString("rue"));
		retour.setCodePostal(Integer.valueOf(rs.getString("code_postal")));
		retour.setVille(rs.getString("ville"));
		return retour;
	}

}

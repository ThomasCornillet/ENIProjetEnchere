package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Encheres;
import fr.eni.encheres.dal.CodesResultatDAL;
import fr.eni.encheres.dal.EncheresDAO;
import fr.eni.encheres.exceptions.BusinessException;

public class EncheresDAOJdbcImpl implements EncheresDAO {
	private static final String SELECT_BY_NU_UTILISATEUR = "SELECT * FROM ENCHERES WHERE no_utilisateur = ?";

	@Override
	public List<Encheres> selectByNoUtilisateur(int noUtilisateur) throws BusinessException {
		List<Encheres> retour = new ArrayList<>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_NU_UTILISATEUR);
			pstmt.setInt(1, noUtilisateur);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Encheres enchere = creerEncheres(rs);
				retour.add(enchere);
			} 
			if (retour.isEmpty()) {
				BusinessException businessException = new BusinessException();
				businessException.ajouterErreur(CodesResultatDAL.SELECT_ENCHERES_BY_NO_UTILISATEUR_NO_RESULT); //ici pas seulement connexion echec mais echec de la sélection
				throw businessException;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ENCHERES_BY_NO_UTILISATEUR_CONNEXION_ECHEC);
			throw businessException;
		}
		return retour;
	}

	private Encheres creerEncheres(ResultSet rs) throws SQLException {
		Encheres retour = new Encheres();
		retour.setNoEnchere(rs.getInt("no_enchere"));
		retour.setDateEnchere(rs.getDate("date_enchere").toLocalDate());
		retour.setMontantEnchere(rs.getInt("montant_enchere"));
		retour.setNoArticle(rs.getInt("no_article"));
		retour.setNoUtilisateur(rs.getInt("no_utilisateur"));
		return retour;
	}

}

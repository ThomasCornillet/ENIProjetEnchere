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
	private static final String INSERT_BY_NO_ARTICLE = "INSERT INTO ENCHERES (date_enchere, montant_enchere, no_article, no_utilisateur) VALUES (?,?,?,?);";
	private static final String SELECT_BY_NO_UTILISATEUR = "SELECT * FROM ENCHERES WHERE no_utilisateur = ?";
	private static final String SELECT_BY_NO_ARTICLE = "SELECT * FROM ENCHERES WHERE no_article = ? ORDER BY montant_enchere DESC";

	@Override
	public List<Encheres> selectByNoUtilisateur(int noUtilisateur) throws BusinessException {
		List<Encheres> retour = new ArrayList<>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_NO_UTILISATEUR);
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
	
	@Override
	public List<Encheres> selectByNoArticle(int noArticle) throws BusinessException {
		List<Encheres> retour = new ArrayList<>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_NO_ARTICLE);
			pstmt.setInt(1, noArticle);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Encheres enchere = creerEncheres(rs);
				retour.add(enchere);
			}
			if (retour.isEmpty()) {
				BusinessException businessException = new BusinessException();
				businessException.ajouterErreur(CodesResultatDAL.SELECT_ENCHERES_BY_NO_ARTICLE_NO_RESULT); //ici pas seulement connexion echec mais echec de la sélection
				throw businessException;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ENCHERES_BY_NO_ARTICLE_CONNEXION_ECHEC);
			throw businessException;
		}
		
		return retour;
	}
	
	@Override
	public Encheres selectEnchereGagnateByNoArticle(int noArticle) throws BusinessException {
		Encheres retour = new Encheres();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_NO_ARTICLE);
			pstmt.setInt(1, noArticle);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			retour = creerEncheres(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ENCHERE_GAGNANTE_BY_NO_ARTICLE_CONNEXION_ECHEC);
			throw businessException;
		}
		return retour;
	}

	@Override
	public Encheres insert(Encheres enchere) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(INSERT_BY_NO_ARTICLE, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setDate(1, java.sql.Date.valueOf(enchere.getDateEnchere()));
			pstmt.setInt(2, enchere.getMontantEnchere());
			pstmt.setInt(3, enchere.getNoArticle());
			pstmt.setInt(4, enchere.getNoUtilisateur());
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				enchere.setNoEnchere(1);
			}
		}	catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_ENCHERE_CONNEXION_ECHEC);
			throw businessException;
		}
		return null;
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

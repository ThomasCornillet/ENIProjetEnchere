package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni.encheres.bll.CodesResultatBLL;
import fr.eni.encheres.bo.Utilisateurs;
import fr.eni.encheres.dal.UtilisateursDAO;
import fr.eni.encheres.exceptions.BusinessException;
import fr.eni.encheres.dal.CodesResultatDAL;

public class UtilisateursDAOJdbcImpl implements UtilisateursDAO {

	private static final String SELECT_BY_PSEUDO = "SELECT * FROM UTILISATEURS WHERE pseudo = ?";
	private static final String INSERT_UTILISATEUR = "INSERT INTO UTILISATEURS (pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

	@Override
	public Utilisateurs selectByPseudo(String pseudo)throws BusinessException {
		Utilisateurs retour = new Utilisateurs();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_PSEUDO);
			pstmt.setString(1, pseudo);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				retour.setNoUtilisateur(rs.getInt("no_utilisateur"));
				retour.setPseudo(rs.getString("pseudo"));
				retour.setNom(rs.getString("nom"));
				retour.setPrenom(rs.getString("Prenom"));
				retour.setEmail(rs.getString("email"));
				retour.setTelephone(rs.getString("telephone"));
				retour.setRue(rs.getString("rue"));
				retour.setCodePostal(rs.getString("code_postal"));
				retour.setVille(rs.getString("ville"));
				retour.setMotDePasse(rs.getString("mot_de_passe"));
				retour.setCredit(rs.getInt("credit"));
				retour.setAdministrateur(rs.getBoolean("administrateur"));
			}else {
				BusinessException businessException = new BusinessException();
				businessException.ajouterErreur(CodesResultatDAL.PSEUDO_INEXISTANT);
				throw businessException;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECTION_BY_PSEUDO_CONNEXION_ECHEC);
			throw businessException;
		}
		return retour;
	}
	
	@Override
	public void insert(Utilisateurs utilisateur) throws BusinessException {
		if (utilisateur == null) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_NULL);
			throw businessException;
		}
		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				cnx.setAutoCommit(false);
				PreparedStatement pstmt = cnx.prepareStatement(INSERT_UTILISATEUR, PreparedStatement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, utilisateur.getPseudo());
				pstmt.setString(2, utilisateur.getNom());
				pstmt.setString(3, utilisateur.getPrenom());
				pstmt.setString(4, utilisateur.getEmail());
				pstmt.setString(5, utilisateur.getTelephone());
				pstmt.setString(6, utilisateur.getRue());
				pstmt.setString(7, utilisateur.getCodePostal());
				pstmt.setString(8, utilisateur.getVille());
				pstmt.setString(9, utilisateur.getMotDePasse());
				pstmt.setInt(10, utilisateur.getCredit());
				pstmt.setBoolean(11, utilisateur.isAdministrateur());	// attention ici, vue que c'est un boolean c'est pas get... mais is...
				pstmt.executeUpdate();
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					utilisateur.setNoUtilisateur(1);
				}
				cnx.commit();
			} catch (Exception e) {
				e.printStackTrace();
				cnx.rollback();
				BusinessException businessException = new BusinessException();
				businessException.ajouterErreur(CodesResultatDAL.INSERT_ERREUR_INCONNUE);
				throw businessException;
			}	
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_CONNEXION_ECHEC);
			throw businessException;
		}
	}
}

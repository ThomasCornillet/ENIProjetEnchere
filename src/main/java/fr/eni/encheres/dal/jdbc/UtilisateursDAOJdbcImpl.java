package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Utilisateurs;
import fr.eni.encheres.dal.UtilisateursDAO;
import fr.eni.encheres.exceptions.BusinessException;
import fr.eni.encheres.dal.CodesResultatDAL;

public class UtilisateursDAOJdbcImpl implements UtilisateursDAO {

	private static final String SELECT_BY_PSEUDO = "SELECT * FROM UTILISATEURS WHERE pseudo = ?";
	private static final String SELECT_BY_MAIL = "SELECT * FROM UTILISATEURS WHERE email = ?";
	private static final String SELECT_ALL = "SELECT * FROM UTILISATEURS";
	private static final String INSERT_UTILISATEUR = "INSERT INTO UTILISATEURS (pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur) "
													+ "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE_UTILISATEUR = "UPDATE UTILISATEURS "
													+ "SET pseudo=?,nom=?,prenom=?,email=?,telephone=?,rue=?,code_postal=?,ville=?,mot_de_passe=?,credit=?,administrateur=? "
													+ "WHERE no_utilisateur = ?";
	private static final String DELETE_UTILISATEUR = "DELETE FROM UTILISATEURS WHERE no_utilisateur = ?";
	private static final String SELECT_BY_ID = "SELECT * FROM UTILISATEURS WHERE no_utilisateur = ?";
	
	@Override
	public Utilisateurs selectByPseudo(String pseudo) throws BusinessException {
		Utilisateurs retour = new Utilisateurs();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_PSEUDO);
			pstmt.setString(1, pseudo);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				setInfoUtilisateur(retour, rs);
			} else {
				BusinessException businessException = new BusinessException();
				businessException.ajouterErreur(CodesResultatDAL.PSEUDO_INEXISTANT);
				throw businessException; // TODO à enlever et réfléchir comment gérer une connexion avec un pseudo qui n'existe pas
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
	public Utilisateurs selectByMail(String mail) throws BusinessException {
		Utilisateurs retour = new Utilisateurs();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_MAIL);
			pstmt.setString(1, mail);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				setInfoUtilisateur(retour, rs);
			} else {
				BusinessException businessException = new BusinessException();
				businessException.ajouterErreur(CodesResultatDAL.MAIL_INEXISTANT);
				throw businessException;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECTION_BY_MAIL_CONNEXION_ECHEC);
			throw businessException;
		}
		return retour;
	}
	
	@Override
	public List<Utilisateurs> selectAll() throws BusinessException {
		List<Utilisateurs> retour = new ArrayList<>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			Statement stmt = cnx.createStatement();
			ResultSet rs = stmt.executeQuery(SELECT_ALL);
			while (rs.next()) {
				Utilisateurs utilisateur = new Utilisateurs();
				utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
				utilisateur.setPseudo(rs.getString("pseudo"));
				utilisateur.setNom(rs.getString("nom"));
				utilisateur.setPrenom(rs.getString("prenom"));
				utilisateur.setEmail(rs.getString("email"));
				utilisateur.setTelephone(rs.getString("telephone"));
				utilisateur.setRue(rs.getString("rue"));
				utilisateur.setCodePostal(rs.getString("code_postal"));
				utilisateur.setVille(rs.getString("ville"));
				utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
				utilisateur.setCredit(rs.getInt("credit"));
				utilisateur.setAdministrateur(rs.getBoolean("administrateur"));
				retour.add(utilisateur);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECTION_ALL_CONNEXION_ECHEC);
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
				preparationDuStatement(utilisateur, pstmt);
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
	
	@Override
	public void update(Utilisateurs utilisateur) throws BusinessException {
		if (utilisateur == null) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.UPDATE_OBJET_NULL);
			throw businessException;
		}
		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				cnx.setAutoCommit(false);
				PreparedStatement pstmt = cnx.prepareStatement(UPDATE_UTILISATEUR);
				preparationDuStatement(utilisateur, pstmt);
				pstmt.setInt(12, utilisateur.getNoUtilisateur());
				pstmt.executeUpdate();
				cnx.commit();
			
			}catch (Exception e) {
				e.printStackTrace();
				cnx.rollback();
				BusinessException businessException = new BusinessException();
				businessException.ajouterErreur(CodesResultatDAL.UPDATE_ERREUR_INCONNUE);
				throw businessException;
			}
		}catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.UPDATE_CONNEXION_ECHEC);
			throw businessException;
		}
	}
	
	@Override
	public void deleteById(int noUtilisateur) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(DELETE_UTILISATEUR);
			pstmt.setInt(1, noUtilisateur);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	@Override
	public Utilisateurs selectById(int noUtilisateur) throws BusinessException {
		Utilisateurs retour = new Utilisateurs();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_ID);
			pstmt.setInt(1, noUtilisateur);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				setInfoUtilisateur(retour, rs);
			} else {
				BusinessException be = new BusinessException();
				be.ajouterErreur(CodesResultatDAL.SELECT_BY_ID_EMPTY);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatDAL.SELECT_BY_ID_CONNECTION_ERROR);
			throw be;
		}
		return retour;		
	}

		
	private void preparationDuStatement(Utilisateurs utilisateur, PreparedStatement pstmt) throws SQLException {
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

	}
	
	private void setInfoUtilisateur(Utilisateurs utilisateur, ResultSet rs) throws BusinessException, SQLException {
		try {
			utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
			utilisateur.setPseudo(rs.getString("pseudo"));
			utilisateur.setNom(rs.getString("nom"));
			utilisateur.setPrenom(rs.getString("Prenom"));
			utilisateur.setEmail(rs.getString("email"));
			utilisateur.setTelephone(rs.getString("telephone"));
			utilisateur.setRue(rs.getString("rue"));
			utilisateur.setCodePostal(rs.getString("code_postal"));
			utilisateur.setVille(rs.getString("ville"));
			utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
			utilisateur.setCredit(rs.getInt("credit"));
			utilisateur.setAdministrateur(rs.getBoolean("administrateur"));
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}


}

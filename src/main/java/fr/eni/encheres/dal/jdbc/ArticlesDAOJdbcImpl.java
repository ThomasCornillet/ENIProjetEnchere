package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Encheres;
import fr.eni.encheres.dal.ArticlesDAO;
import fr.eni.encheres.dal.CodesResultatDAL;
import fr.eni.encheres.exceptions.BusinessException;
import java.time.LocalDate;

public class ArticlesDAOJdbcImpl implements ArticlesDAO {
//	private static final String SELECT_ALL = "SELECT * FROM ARTICLES ORDER BY date_fin_encheres DESC";
	private static final String SELECT_ALL = "SELECT no_article,nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,a.no_utilisateur,a.no_categorie,vendu,u.pseudo,c.libelle "
												+ "FROM ARTICLES a "
													+ "INNER JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur "
													+ "INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie "
													+ "ORDER BY date_fin_encheres DESC";
//	private static final String SELECT_BY_CATEGORIE = "SELECT * FROM ARTICLES WHERE no_categorie = ? ORDER BY date_fin_encheres DESC";
	private static final String SELECT_BY_CATEGORIE = "SELECT no_article,nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,a.no_utilisateur,a.no_categorie,vendu,u.pseudo,c.libelle "
														+ "FROM ARTICLES a "
															+ "INNER JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur "
															+ "INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie "
															+ "WHERE a.no_categorie = ? "
															+ "ORDER BY date_fin_encheres DESC";
//	private static final String SELECT_BY_NOM = "SELECT * FROM ARTICLES WHERE nom_article = ?"; // TODO est-ce qu'on utilise cette sélection ?
	private static final String SELECT_BY_NOM = "SELECT no_article,nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,a.no_utilisateur,a.no_categorie,vendu,u.pseudo,c.libelle "
													+ "FROM ARTICLES a "
														+ "INNER JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur "
														+ "INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie "
														+ "WHERE nom_article = ?";
//	private static final String SELECT_BY_PORTION_NOM = "SELECT * FROM ARTICLES WHERE nom_article LIKE ? ORDER BY date_fin_encheres DESC";
	private static final String SELECT_BY_PORTION_NOM = "SELECT no_article,nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,a.no_utilisateur,a.no_categorie,vendu,u.pseudo,c.libelle "
															+ "FROM ARTICLES a "
																+ "INNER JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur "
																+ "INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie "
																+ "WHERE nom_article LIKE ? "
																+ "ORDER BY date_fin_encheres DESC";

	private static final String SELECT_BY_NO_UTILISATEUR = "SELECT no_article,nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,a.no_utilisateur,a.no_categorie,vendu,u.pseudo,c.libelle "
																+"FROM ARTICLES a "
																	+"INNER JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur "
																	+"INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie "
																	+"WHERE u.no_utilisateur =?";
	
	private static final String SELECT_BY_NO_ARTICLE = "SELECT a.no_article,a.nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,vendu,a.no_utilisateur,u.pseudo, c.no_categorie, c.libelle, "
															+ "r.rue, r.code_postal, r.ville "
															+ "FROM ARTICLES a "
																+ "INNER JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur "
																+ "INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie "
																+ "LEFT JOIN RETRAITS r ON a.no_article = r.no_article "
																+ "WHERE a.no_article =?";
				
	private static final String SELECT_ENCHERES_BY_NO_ARTICLE = "SELECT e.no_enchere, u.pseudo AS encherisseur, e.date_enchere, e.montant_enchere, e.no_utilisateur, r.rue,r.code_postal,r.ville "
															+ "FROM ENCHERES e "
																+ "INNER JOIN UTILISATEURS u ON e.no_utilisateur = u.no_utilisateur "
																+ "INNER JOIN ARTICLES a ON a.no_article = e.no_article "
																+ "LEFT JOIN RETRAITS r ON a.no_article = r.no_article "
																+ "WHERE a.no_article =? ORDER BY montant_enchere DESC";
	
//	private static final String SELECT_BY_CATEGORIE = "SELECT * FROM ARTICLES WHERE no_categorie = ? ORDER BY date_fin_encheres DESC";
	private static final String SELECT_BY_CATEGORIE_AND_PORTION_NOM = "SELECT no_article,nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,a.no_utilisateur,a.no_categorie,vendu,u.pseudo,c.libelle "
														+ "FROM ARTICLES a "
															+ "INNER JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur "
															+ "INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie "
															+ "WHERE a.no_categorie = ? AND nom_article LIKE ? "
															+ "ORDER BY date_fin_encheres DESC";
	
	
	@Override
	public List<Articles> selectAll() throws BusinessException {
		List<Articles> retour = new ArrayList<>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			Statement stmt = cnx.createStatement();
			ResultSet rs =  stmt.executeQuery(SELECT_ALL);
			while (rs.next()) {
				Articles article = creerArticle(rs);
				
				retour.add(article);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ALL_ARTICLES_ECHEC); //ici pas seulement connexion echec mais echec de la sélection
			throw businessException;
		}
		return retour;
	}

	@Override
	public List<Articles> selectByCategorie(int no_categorie) throws BusinessException {
		List<Articles> retour = new ArrayList<>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_CATEGORIE);
			pstmt.setInt(1, no_categorie);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				retour.add(creerArticle(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ARTICLES_BY_CATEGORIE_ECHEC); //ici pas seulement connexion echec mais echec de la sélection
			throw businessException;
		}
		return retour;
	}

	@Override
	public Articles selectByNom(String nom) throws BusinessException {
		Articles retour = new Articles();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_NOM);
			pstmt.setString(1, nom);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				retour = creerArticle(rs);
			} else {
				BusinessException businessException = new BusinessException();
				businessException.ajouterErreur(CodesResultatDAL.NOM_ARTICLE_INEXISTANT); //ici pas seulement connexion echec mais echec de la sélection
				throw businessException;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ARTICLE_BY_NOM_ECHEC); //ici pas seulement connexion echec mais echec de la sélection
			throw businessException;
		}
		return retour;
	}
	
	@Override
	public List<Articles> selectByPortionNom(String portionNom) throws BusinessException {
		List<Articles> retour = new ArrayList<>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_PORTION_NOM);
			pstmt.setString(1, "%" + portionNom + "%");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				retour.add(creerArticle(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ARTICLE_BY_PORTION_NOM_ECHEC); //ici pas seulement connexion echec mais echec de la sélection
			throw businessException;
		}
		return retour;
	}
	
	@Override
	public List<Articles> selectArticleByNoUtilisateur(int noUtilisateur) throws BusinessException {
		List<Articles> retour = new ArrayList<>();
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_NO_UTILISATEUR);
			pstmt.setInt(1, noUtilisateur);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Articles articles = creerArticle(rs);
				retour.add(articles);
			}
			if (retour.isEmpty()) {
				BusinessException businessException = new BusinessException();
				businessException.ajouterErreur(CodesResultatDAL.SELECT_ARTICLES_BY_NO_UTILISATEUR_LISTE_VIDE);
				throw businessException;
			}
		}catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_BY_NO_UTILISATEUR_ECHEC); //ici pas seulement connexion echec mais echec de la sélection
			throw businessException;
		}
		return retour;
	}
	
	@Override
	public Articles selectArticleByNoArticle(int noArticle) throws BusinessException {
		Articles retour = null;
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_NO_ARTICLE);
			pstmt.setInt(1, noArticle);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				
				if(retour == null) {
				retour = creerArticle(rs);
				}
				retour.setRue(rs.getString("rue"));
				retour.setCodePostal(rs.getInt("code_postal"));
				retour.setVille(rs.getString("ville"));
				 
				try{
					PreparedStatement pstmt2 = cnx.prepareStatement(SELECT_ENCHERES_BY_NO_ARTICLE);
					pstmt2.setInt(1, noArticle);
					ResultSet rs2 = pstmt2.executeQuery();
					if (rs2.next()) {
					int noEnchere = rs2.getInt("no_enchere");
					String encherisseur = rs2.getString("encherisseur");
					LocalDate dateEnchere = rs2.getDate("date_enchere").toLocalDate();
					int montantEnchere = rs2.getInt("montant_enchere");
					int noUtilisateur = rs2.getInt("no_utilisateur");
					String rue =rs2.getString("rue");
					int codePostal= rs2.getInt("code_postal");
					String ville = rs2.getString("ville");
					List<Encheres> encheres = new ArrayList<>();
					Encheres enchere = new Encheres(noEnchere, encherisseur, dateEnchere, montantEnchere, noUtilisateur, rue, codePostal, ville);
					encheres.add(enchere);
					retour.setListeEncheres(encheres);
					}
				}catch (SQLException e){
					e.printStackTrace();
					BusinessException businessException = new BusinessException();
					businessException.ajouterErreur(CodesResultatDAL.SELECT_ENCHERES_BY_NO_ARTICLE_CONNEXION_ECHEC);
					throw businessException;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_BY_NO_ARTICLE_CONNEXION_ECHEC);
			throw businessException;
		}
	return retour;
	}
	
	@Override
	public List<Articles> selectByCategorieAndPortionNom(int noCategorie, String portionNom) throws BusinessException {
		List<Articles> retour = new ArrayList<>();
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_CATEGORIE_AND_PORTION_NOM);
			pstmt.setInt(1, noCategorie);
			pstmt.setString(2, "%" + portionNom + "%");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Articles article = creerArticle(rs);
				retour.add(article);
			}
			if (retour.isEmpty()) {
				BusinessException businessException = new BusinessException();
				businessException.ajouterErreur(CodesResultatDAL.SELECT_ARTICLES_BY_CAT_AND_POTION_NOM_LISTE_VIDE);
				throw businessException;
			}
		} catch (SQLException e){
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ARTICLES_BY_CAT_AND_POTION_NOM_CONNEXION_ECHEC);
			throw businessException;
		}
		return retour;
	}
	
	private Articles creerArticle(ResultSet rs) throws SQLException {
		Articles retour = new Articles();
		retour.setNoArticle(rs.getInt("no_article"));
		retour.setNomArticle(rs.getString("nom_article"));
		retour.setDescription(rs.getString("description"));
		retour.setDate_debut_enchere(rs.getDate("date_debut_encheres").toLocalDate());
		retour.setDate_fin_enchere(rs.getDate("date_fin_encheres").toLocalDate());
		retour.setPrix_initial(rs.getInt("prix_initial"));
		retour.setPrix_vente(rs.getInt("prix_vente"));
		retour.setNo_utilisateur(rs.getInt("no_utilisateur"));
		retour.setPseudoUtilisateur(rs.getString("pseudo"));
		retour.setNo_categorie(rs.getInt("no_categorie"));
		retour.setLibelleCatagorie(rs.getString("libelle"));
		retour.setVendu(rs.getBoolean("vendu"));
		return retour;
	}


}

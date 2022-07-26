package fr.eni.encheres.dal;

public abstract class CodesResultatDAL {
	
	// Codes communs (10***)
	public static final int PSEUDO_INEXISTANT = 10000; 
	public static final int SELECTION_BY_PSEUDO_CONNEXION_ECHEC = 10001;
	public static final int SELECTION_BY_MAIL_CONNEXION_ECHEC = 10002; 
	public static final int MAIL_INEXISTANT = 10003;
	public static final int UPDATE_OBJET_NULL = 10004;
	public static final int UPDATE_ERREUR_INCONNUE = 10005;
	public static final int UPDATE_CONNEXION_ECHEC = 10006;
	
	// Codes thomas (11***)
	public static final int INSERT_OBJET_NULL = 11000;
	public static final int INSERT_ERREUR_INCONNUE = 11001;
	public static final int INSERT_CONNEXION_ECHEC = 11002;
	public static final int SELECT_ALL_ARTICLES_ECHEC = 11003;
	public static final int SELECT_ARTICLES_BY_CATEGORIE_ECHEC = 11004;
	public static final int SELECT_ARTICLE_BY_NOM_ECHEC = 11005;
	public static final int NOM_ARTICLE_INEXISTANT = 11006;
	public static final int SELECT_ALL_CATEGORIES_ECHEC = 11007;
	public static final int SELECT_ARTICLE_BY_PORTION_NOM_ECHEC = 11008;
	public static final int SELECTION_ALL_CONNEXION_ECHEC = 11009;
	public static final int SELECT_BY_ID_EMPTY = 11010;
	public static final int SELECT_BY_ID_CONNECTION_ERROR = 11011;
	public static final int SELECT_ENCHERES_BY_NO_UTILISATEUR_CONNEXION_ECHEC = 11012;
	public static final int SELECT_ENCHERES_BY_NO_UTILISATEUR_NO_RESULT = 11013;
	public static final int SELECT_BY_NO_ARTICLE_CONNEXION_ECHEC = 11014;
	public static final int SELECT_ARTICLE_BY_NO_ARTICLE_VIDE = 11015;

	
	// Codes naoufel (12***)
	public static final int NO_UTILISATEUR_INEXISTANT = 12000;
    public static final int SELECT_BY_NO_UTILISATEUR_ECHEC = 12001;

	// Codes johanna (13***)

}

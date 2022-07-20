package fr.eni.encheres.dal;

public abstract class CodesResultatDAL {
	
	// Codes communs (10***)
	public static final int PSEUDO_INEXISTANT = 10000; 
	public static final int SELECTION_BY_PSEUDO_CONNEXION_ECHEC = 10001;
	public static final int SELECTION_BY_MAIL_CONNEXION_ECHEC = 10002; 
	public static final int MAIL_INEXISTANT = 10003;
	
	// Codes thomas (11***)
	public static final int INSERT_OBJET_NULL = 11000;
	public static final int INSERT_ERREUR_INCONNUE = 11001;
	public static final int INSERT_CONNEXION_ECHEC = 11002;
	
	// Codes naoufel (12***)
	
	// Codes johanna (13***)

}

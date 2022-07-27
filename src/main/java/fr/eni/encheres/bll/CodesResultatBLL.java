package fr.eni.encheres.bll;

public abstract class CodesResultatBLL {
	
	// Codes communs (20***)
	public static final int PSEUDO_VIDE_OU_NUL = 20000;
	public static final int MAIL_VIDE_OU_NUL = 20001;
	
	// Codes thomas (21***)
	public static final int CATEGORIE_VIDE_OU_NULL = 21000;
	public static final int CATEGORIE_INEXISTANTE = 21001;
	public static final int PORTION_NOM_VIDE = 21002;
	public static final int FORMATAGE_PSEUDO_ERREUR = 21003;
	public static final int FORMATAGE_NOM_ERREUR = 21004;
	public static final int FORMATAGE_PRENOM_ERREUR = 21005;
	public static final int FORMATAGE_EMAIL_ERREUR = 21006;
	public static final int FORMATAGE_TELEPHONE_ERREUR = 21007;
	public static final int FORMATAGE_RUE_ERREUR = 21008;
	public static final int FORMATAGE_CODE_POSTAL_ERREUR = 21009;
	public static final int FORMATAGE_VILLE_ERREUR = 21010;
	public static final int FORMATAGE_MOT_DE_PASSE_ERREUR = 21011;
	public static final int FORMATAGE_CREDIT_ERREUR = 21012;
	public static final int PSEUDO_DEJA_EXISTANT = 21013;
	public static final int EMAIL_DEJA_EXISTANT = 21014;
	public static final int SELECT_BY_ID_ID_NEGATIVE = 21015;
	public static final int SELECT_ENCHERES_BY_NO_UTILISATEUR_NO_NEGATIF = 21016;
	public static final int NO_ARTICLE_NEGATIF = 21017;
	public static final int SELECT_ARTICLE_BY_NO_UTILISATEUR_NO_NEGATIF = 21018;
	public static final int SELECT_ENCHERES_BY_NO_ARTICLE_NO_NEGATIF = 21019;
	public static final int NO_CATEGORIE_NEGATIF = 21020;
	
	
	// Codes naoufel (22***)
	public static final int INSERT_ENCHERES_BY_NO_ARTICLE_NO_NEGATIF = 22000;
	
	// Codes johanna (23***)

}

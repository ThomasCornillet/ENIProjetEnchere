package fr.eni.encheres.servlets;

public abstract class CodesResultatServlet {

	public static final int UTILISATEUR_NON_TROUVE = 30000;
	public static final int MOT_DE_PASSE_NON_CORRESPONDANT = 30001;
	

	public static final int LISTE_ARTICLE_PREMIER_FILTRE_VIDE = 31000;
	public static final int FILTRE_CATEGORIE_ERREUR = 31001;
	public static final int UPDATE_VENTE_REQUEST_PARAMATER_NON_VALID = 31002;
	
	public static final int UTILISATEUR_EXISTE_DEJA = 32000;
	public static final int MOT_DE_PASSE_NON_IDENTIQUES = 32001;
	public static final int MOT_DE_PASSE_NON_CONFROME = 32002;
	public static final int UTILISATEUR_INEXISTANT = 32003;
	public static final int FORMAT_DATE_ENCHERE_ERREUR = 32004;

}

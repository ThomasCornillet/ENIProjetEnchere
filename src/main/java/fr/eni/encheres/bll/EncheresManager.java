package fr.eni.encheres.bll;

import java.time.LocalDate;
import java.util.List;

import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Encheres;
import fr.eni.encheres.bo.Utilisateurs;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.EncheresDAO;
import fr.eni.encheres.exceptions.BusinessException;

public class EncheresManager {
	private EncheresDAO encheresDAO;
	private static EncheresManager instance;
	
	private EncheresManager() {
		encheresDAO = DAOFactory.getEncheresDAO();
	}
	
	public static EncheresManager getInstance() {
		if (instance == null) {
			instance = new EncheresManager();
		}
		return instance;
	}
	
	public List<Encheres> selectByNoUtilisateur(int noUtilisateur) throws BusinessException {
		if (noUtilisateur <= 0) {
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatBLL.SELECT_ENCHERES_BY_NO_UTILISATEUR_NO_NEGATIF);
			throw be;
		}
		return encheresDAO.selectByNoUtilisateur(noUtilisateur);
	}
	
	public List<Encheres> selectByNoArticle(int noArticle) throws BusinessException {
		if (noArticle <= 0) {
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatBLL.SELECT_ENCHERES_BY_NO_ARTICLE_NO_NEGATIF);
			throw be;
		}
		return encheresDAO.selectByNoArticle(noArticle);
	}
	
	public Encheres selectEnchereGagnateByNoArticle(int noArticle) throws BusinessException {
		if (noArticle <= 0) {
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatBLL.SELECT_ENCHERES_BY_NO_ARTICLE_NO_NEGATIF);
			throw be;
		}
		return selectEnchereGagnateByNoArticle(noArticle);
	}

	public boolean insert(Encheres enchere, List<Integer> listeErreursEnchere, int noVendeur) throws BusinessException{
		boolean retourOk = false;
		// TODO faire des tests de validité ici
		ArticlesManager articlesMngr = ArticlesManager.getInstance(); // TODO est-ce que besoin de passer par un autre manager ou c'est ok d'aller directement à articledao ?
		UtilisateursManager utilisateursMngr = UtilisateursManager.getInstance();
		Articles article = articlesMngr.selectArticleByNoArticle(enchere.getNoArticle());
		// vente pas commencée
		if (article.getDate_debut_enchere().isBefore(LocalDate.now())) {
			retourOk = true;	// TODO revoir l'enchainement des true pas bon
		} else {
			listeErreursEnchere.add(CodesResultatBLL.VERIF_ENCHERE_VENTE_NON_COMMENCEE);
		}
		// vente terminée
		if (article.getDate_fin_enchere().isBefore(LocalDate.now())) {
			retourOk = true;
		} else {
			listeErreursEnchere.add(CodesResultatBLL.VERIF_ENCHERE_VENTE_TERMINEE);
		}
		// sa propre vente
		if (article.getNo_utilisateur() == noVendeur) {
			retourOk = true;
		} else {
			listeErreursEnchere.add(CodesResultatBLL.VERIF_ENCHERE_VENDEUR);
		}
		// pas assez de crédit
		Utilisateurs encherisseur = utilisateursMngr.selectById(enchere.getNoUtilisateur());
		if (encherisseur.getCredit() > enchere.getMontantEnchere()) {
			retourOk = true;
		} else {
			listeErreursEnchere.add(CodesResultatBLL.VERIF_ENCHERE_VENDEUR);
		}
		
		if (listeErreursEnchere.isEmpty()) {
			encheresDAO.insert(enchere);
			retourOk = true;
		} else {
			retourOk = false;
		}
		
		
		
		
		
		return retourOk;
	}

}

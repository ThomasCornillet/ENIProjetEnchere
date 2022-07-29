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
		// TODO méthode qui ne fonctionne pas (là DAO en totu cas)
			// renvoie stack over flow
		if (noArticle <= 0) {
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatBLL.SELECT_ENCHERES_BY_NO_ARTICLE_NO_NEGATIF);
			throw be;
		}
		return selectEnchereGagnateByNoArticle(noArticle);
	}

	public boolean insert(Encheres enchere, List<Integer> listeErreursEnchere) throws BusinessException{
		boolean retourOk = false;
		// TODO faire des tests de validité ici
		ArticlesManager articlesMngr = ArticlesManager.getInstance(); // TODO est-ce que besoin de passer par un autre manager ou c'est ok d'aller directement à articledao ?
		UtilisateursManager utilisateursMngr = UtilisateursManager.getInstance();
		Articles article = articlesMngr.selectArticleByNoArticle(enchere.getNoArticle());
		Utilisateurs encherisseur = utilisateursMngr.selectById(enchere.getNoUtilisateur());
		EncheresManager encheresMngr = EncheresManager.getInstance();
		List<Encheres> encheresPrecedentes = encheresMngr.selectByNoArticle(article.getNoArticle());
		// Enchère trop basse
		if(encheresPrecedentes.size()>0) {
			Encheres meilleureEnchere = encheresPrecedentes.get(0);
			if (meilleureEnchere.getMontantEnchere() >= enchere.getMontantEnchere()) {
				listeErreursEnchere.add(CodesResultatBLL.VERIF_ENCHERE_TROP_BASSE);
			}
		}
		
		// vente pas commencée
		if (article.getDate_debut_enchere().isAfter(LocalDate.now())) {
			listeErreursEnchere.add(CodesResultatBLL.VERIF_ENCHERE_VENTE_NON_COMMENCEE);
		}
		// vente terminée
		if (article.getDate_fin_enchere().isBefore(LocalDate.now())) {
			listeErreursEnchere.add(CodesResultatBLL.VERIF_ENCHERE_VENTE_TERMINEE);
		}
		// sa propre vente
		if (article.getNo_utilisateur() == encherisseur.getNoUtilisateur()) {
			listeErreursEnchere.add(CodesResultatBLL.VERIF_ENCHERE_VENDEUR);
		}
		// pas assez de crédit
		if (encherisseur.getCredit() < enchere.getMontantEnchere()) {
			listeErreursEnchere.add(CodesResultatBLL.VERIF_ENCHERE_CREDIT_INSUFFISANT);
		}
		
		// on est déjà le meilleur encherisseur
		List<Encheres> listeEncheresArticle = selectByNoArticle(article.getNoArticle());
		if (listeEncheresArticle != null && !listeEncheresArticle.isEmpty()) {
			Encheres meilleureEnchere = listeEncheresArticle.get(0);
			if (encherisseur.getNoUtilisateur() == meilleureEnchere.getNoUtilisateur()) {
				listeErreursEnchere.add(CodesResultatBLL.VERIF_ENCHERE_DEJA_MEILLEUR_ENCHERISSEUR);
			}
		}
		
		if (listeErreursEnchere.isEmpty()) {
			encheresDAO.insert(enchere);
			retourOk = true;
		} else {
			BusinessException be = new BusinessException();
			for (int code : listeErreursEnchere) {
				be.ajouterErreur(code);
			}
			retourOk = false;
			throw be;
		}
		return retourOk;
	}

}

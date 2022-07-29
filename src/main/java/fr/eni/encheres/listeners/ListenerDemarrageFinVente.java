package fr.eni.encheres.listeners;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import fr.eni.encheres.bll.ArticlesManager;
import fr.eni.encheres.bll.EncheresManager;
import fr.eni.encheres.bll.UtilisateursManager;
import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Encheres;
import fr.eni.encheres.bo.Utilisateurs;
import fr.eni.encheres.exceptions.BusinessException;

/**
 * Application Lifecycle Listener implementation class ListenerDemarrageFinVente
 *
 */
@WebListener
public class ListenerDemarrageFinVente implements ServletContextListener {
	private static Date dateDemarrage;

	public static Date getDateDemarrage() {
		return dateDemarrage;
	}

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
         // TODO checker ici si une vente est terminée et si oui, la traitée comme telle
    	// on récupère les ventes en cours
    	ArticlesManager articlesMngr = ArticlesManager.getInstance();
    	List<Articles> listeArticles = new ArrayList<>();
    	try {
    		listeArticles = articlesMngr.selectAll();
    		for (Articles a : listeArticles) { // TODO il doit y avoir moyen de réccourcir la liste
        		if (a.getDate_fin_enchere().isEqual(LocalDate.now()) || a.getDate_fin_enchere().isBefore(LocalDate.now())) {
            		// si la date de fin est aujourd'hui ou avant aujourdh'ui la vente est terminée
        			// TODO voir ce qu'on fait si la date est passée mais que personne n'a enchérie
        			// on check d'abord si la vente n'est pas déjà indiquée comme terminée // TODO à améliorer avec des attributs d'application
        			if (!a.isVendu()) {

//        				System.out.println(a.toString());
        				// pas encore marqué comme vendu
        				// on effectue le paiement  
        				UtilisateursManager utilisateursMngr = UtilisateursManager.getInstance();
        				EncheresManager encheresMngr = EncheresManager.getInstance();
//        				Encheres enchereGagnante = encheresMngr.selectEnchereGagnateByNoArticle(a.getNoArticle());
//        				System.out.println(a.getNoArticle());
        				List<Encheres> listeEncheres = encheresMngr.selectByNoArticle(a.getNoArticle());
//        				System.out.println(listeEncheres.toString());
        				Utilisateurs vendeur = utilisateursMngr.selectById(a.getNo_utilisateur());
        				if (!listeEncheres.isEmpty()) {
        					Encheres enchereGagnante = listeEncheres.get(0);
        					a.setPrix_vente(enchereGagnante.getMontantEnchere());
	        				Utilisateurs acheteur = utilisateursMngr.selectById(enchereGagnante.getNoUtilisateur());
	        				if (acheteur.getCredit() >= enchereGagnante.getMontantEnchere()) {
	        					acheteur.setCredit(acheteur.getCredit() - enchereGagnante.getMontantEnchere());
	        					vendeur.setCredit(vendeur.getCredit() + enchereGagnante.getMontantEnchere());
	        					utilisateursMngr.update(acheteur);
	        					utilisateursMngr.update(vendeur);
	        				} else {
	        					// TODO peut-être que la personne n'a plus assez à ce moment...
	        						// faire une alerte, ou alors gérer le paiement dès l'enchère
	        				}
        				} else {
        					// TODO qu'est-ce qu'on fait si une vente est terminée est qu'il n'y a pas d'enchères ?
        				}
        				// on la met en vendu dans la BDD
        				articlesMngr.updateVenteTerminee(a);
        				
        				
        			}
        		}
        	}
		} catch (BusinessException e) {
			// TODO voir comment gérer une erreur dans un listener
			e.printStackTrace();
		}
    }
}

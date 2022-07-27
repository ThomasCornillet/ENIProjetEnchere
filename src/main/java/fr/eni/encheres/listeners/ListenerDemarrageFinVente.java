package fr.eni.encheres.listeners;

import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

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
    	
    	// si la date de fin est aujourd'hui la vente est terminée
    		// TODO voir ce qu'on fait si la date est passée mais que personne n'a enchérie
    	
    		// on effectue le paiement
    			// TODO peut-être que la personne n'a plus assez à ce moment...
    	
    	
    		// on la met en vendu dans la BDD
    }
	
}

package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Utilisateurs;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.UtilisateursDAO;
import fr.eni.encheres.exceptions.BusinessException;

public class UtilisateursManager {
	
	private UtilisateursDAO utilisateursDAO;
	
	private static UtilisateursManager instance;

	private UtilisateursManager() {
		this.utilisateursDAO = DAOFactory.getUtilisateursDAO();
	}


	public static UtilisateursManager getInstance() {
		if(instance == null) {
			instance = new UtilisateursManager();
		}
		return instance;
	}
	
	public Utilisateurs selectByPseudo(String pseudo) throws BusinessException {
		if(pseudo.isBlank() || pseudo == null) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatBLL.PSEUDO_VIDE_OU_NUL);
			throw businessException;
		}
		Utilisateurs retour = utilisateursDAO.selectByPseudo(pseudo);
		return retour;
	}
	
	public Utilisateurs selectByMail(String mail) throws BusinessException {
		if(mail.isBlank() || mail == null) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatBLL.MAIL_VIDE_OU_NUL);
			throw businessException;
		}
		Utilisateurs retour = utilisateursDAO.selectByMail(mail);
		return retour;
	}


	public void insert(Utilisateurs utilisateur) throws BusinessException {
		//TODO Vérifications
		utilisateursDAO.insert(utilisateur);
	}


	public void update(Utilisateurs utilisateur) throws BusinessException {
		//TODO Vérifications
		utilisateursDAO.update(utilisateur);
		
	}
	
}

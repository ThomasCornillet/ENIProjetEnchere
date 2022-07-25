package fr.eni.encheres.bll;

import java.util.ArrayList;
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

	private List<Utilisateurs> selectAll() throws BusinessException {
		// TODO des checks à faire ?
		return utilisateursDAO.selectAll();
	}

	public void insert(Utilisateurs utilisateur) throws BusinessException {
		List<Integer> codesErreurs = new ArrayList<>();
		if (verifUtilisateurs(utilisateur, codesErreurs)) {
			utilisateursDAO.insert(utilisateur);
		} else {
			BusinessException be = new BusinessException();
			for (Integer code : codesErreurs) {
				be.ajouterErreur(code);
			}
			throw be;
		}
	}


	public void update(Utilisateurs utilisateur) throws BusinessException {
		List<Integer> codesErreurs = new ArrayList<>();
		// tester si le numero d'utilisateur existe
		if (verifUtilisateurs(utilisateur, codesErreurs)) {
			utilisateursDAO.update(utilisateur);
		} else {
			BusinessException be = new BusinessException();
			for (Integer code : codesErreurs) {
				be.ajouterErreur(code);
			}
			throw be;
		}
		
	}
	
	public void deleteById(int noUtilisateur) throws BusinessException {
		// TODO vérifications
		utilisateursDAO.deleteById(noUtilisateur);
	}
	
	public Utilisateurs selectById(int noUtilisateur) throws BusinessException {
		if (noUtilisateur <= 0) {
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatBLL.SELECT_BY_ID_ID_NEGATIVE);
			throw be;
		}
		return utilisateursDAO.selectById(noUtilisateur);
	}
	
	private boolean verifUtilisateurs(Utilisateurs utilisateur, List<Integer> codesErreurs) throws BusinessException {
		boolean retour = false;
		List<Utilisateurs> listeUtilisateurs = new ArrayList<>();
		try {
			listeUtilisateurs = this.selectAll();
		} catch (BusinessException e) {
			e.printStackTrace();
			throw e;
		}
		// pseudo
		// unicité du pseudo
		if (listeUtilisateurs != null) {
			for (Utilisateurs u : listeUtilisateurs) {
				if (utilisateur.getNoUtilisateur() != u.getNoUtilisateur() && utilisateur.getPseudo().equals(u.getPseudo())) {
					codesErreurs.add(CodesResultatBLL.PSEUDO_DEJA_EXISTANT);
					break;
				}
			}
		}
		// formatage du pseudo
		if (codesErreurs.size() > 0) {
			retour = false;
		} else if (utilisateur.getPseudo() == null || utilisateur.getPseudo().isBlank() || utilisateur.getPseudo().length() > 30) {
			codesErreurs.add(CodesResultatBLL.FORMATAGE_PSEUDO_ERREUR);
		} else {
			retour = true;
		}
		// nom
		if (codesErreurs.size() > 0) {
			retour = false;
		} else if (utilisateur.getNom() == null  || utilisateur.getNom().isBlank() || utilisateur.getNom().length() > 50) {
			codesErreurs.add(CodesResultatBLL.FORMATAGE_NOM_ERREUR);
		} else {
			retour = true;
		}
		// prenom
		if (codesErreurs.size() > 0) {
			retour = false;
		} else if (utilisateur.getPrenom() == null  || utilisateur.getPrenom().isBlank() || utilisateur.getPrenom().length() > 50) {
			codesErreurs.add(CodesResultatBLL.FORMATAGE_PRENOM_ERREUR);
		} else {
			retour = true;
		}
		// email
		// unicité email
		if (listeUtilisateurs != null) {
			for (Utilisateurs u : listeUtilisateurs) {
				if (utilisateur.getNoUtilisateur() != u.getNoUtilisateur() && utilisateur.getEmail().equals(u.getEmail())) {
					codesErreurs.add(CodesResultatBLL.EMAIL_DEJA_EXISTANT);
					break;
				}
			}
		}
		// formatage email
		if (codesErreurs.size() > 0) {
			retour = false;
		} else if (utilisateur.getEmail() == null  || utilisateur.getEmail().isBlank() || utilisateur.getEmail().length() > 50) {
			codesErreurs.add(CodesResultatBLL.FORMATAGE_EMAIL_ERREUR);
		} else {
			retour = true;
		}
		// telephone
		if (codesErreurs.size() > 0) {
			retour = false;
		} else if (utilisateur.getTelephone() != null  && !utilisateur.getTelephone().isBlank() && utilisateur.getTelephone().length() != 10) {
			codesErreurs.add(CodesResultatBLL.FORMATAGE_TELEPHONE_ERREUR);
		} else {
			retour = true;
		}
		// rue
		if (codesErreurs.size() > 0) {
			retour = false;
		} else if (utilisateur.getRue() == null  || utilisateur.getRue().isBlank() || utilisateur.getRue().length() > 30) {
			codesErreurs.add(CodesResultatBLL.FORMATAGE_RUE_ERREUR);
		} else {
			retour = true;
		}
		// CP
		if (codesErreurs.size() > 0) {
			retour = false;
		} else if (utilisateur.getCodePostal() == null  || utilisateur.getCodePostal().isBlank() || utilisateur.getCodePostal().length() != 5) {
			codesErreurs.add(CodesResultatBLL.FORMATAGE_CODE_POSTAL_ERREUR);
		} else {
			retour = true;
		}
		// ville
		if (codesErreurs.size() > 0) {
			retour = false;
		} else if (utilisateur.getVille() == null  || utilisateur.getVille().isBlank() || utilisateur.getVille().length() > 40) {
			codesErreurs.add(CodesResultatBLL.FORMATAGE_VILLE_ERREUR);
		} else {
			retour = true;
		}
		// mdp 
		if (codesErreurs.size() > 0) {
			retour = false;
		} else if (utilisateur.getMotDePasse() == null  || utilisateur.getMotDePasse().isBlank() || utilisateur.getMotDePasse().length() > 30
						|| !utilisateur.getMotDePasse().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{12,30}$")){
			codesErreurs.add(CodesResultatBLL.FORMATAGE_MOT_DE_PASSE_ERREUR);
		} else {
			retour = true;
		}
		// credit
		if (codesErreurs.size() > 0) {
			retour = false;
		} else if (utilisateur.getCredit() < 0) {
			codesErreurs.add(CodesResultatBLL.FORMATAGE_CREDIT_ERREUR);
		} else {
			retour = true;
		}
		// pas besoin de check admin je crois
		
		return retour;
	}

}

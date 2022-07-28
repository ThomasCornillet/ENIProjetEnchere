package fr.eni.encheres.bll;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import fr.eni.encheres.exceptions.BusinessException;

public class HashMotDePasse {
	private static HashMotDePasse instance;
	
	private HashMotDePasse() {
		
	}
	
	public static HashMotDePasse getInstance() {
		if (instance == null) {
			instance = new HashMotDePasse();
		}
		return instance;
	}
	
	public String shaHash(String password) {
		String retour = null;
		try {
			MessageDigest md;
			md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
	        byte byteData[] = md.digest();
	        StringBuffer hexString = new StringBuffer();
	        for (int i=0;i<byteData.length;i++) {
				String hex=Integer.toHexString(0xff & byteData[i]);
				if(hex.length()==1) hexString.append('0');
				hexString.append(hex);
	        }
	        retour = hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesResultatBLL.HASH_MOT_DE_PASSE_ERREUR);
		}
		
		return retour;
        
	}
}

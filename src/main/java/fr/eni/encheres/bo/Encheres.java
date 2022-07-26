package fr.eni.encheres.bo;

import java.io.Serializable;
import java.time.LocalDate;

public class Encheres implements Serializable {
	private static final long serialVersionUID = 1L;
	private int noEnchere;
	private LocalDate dateEnchere;
	private int montantEnchere;
	private int noArticle;
	private int noUtilisateur;
	private String encherisseur;
	private String rue;
	private int codePostal;
	private String ville;
	
	public String getEncherisseur() {
		return encherisseur;
	}
	public void setEncherisseur(String encherisseur) {
		this.encherisseur = encherisseur;
	}
	public int getNoEnchere() {
		return noEnchere;
	}
	public void setNoEnchere(int noEnchere) {
		this.noEnchere = noEnchere;
	}
	public LocalDate getDateEnchere() {
		return dateEnchere;
	}
	public void setDateEnchere(LocalDate dateEnchere) {
		this.dateEnchere = dateEnchere;
	}
	public int getMontantEnchere() {
		return montantEnchere;
	}
	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}
	public int getNoArticle() {
		return noArticle;
	}
	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}
	public int getNoUtilisateur() {
		return noUtilisateur;
	}
	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}
	
	public String getRue() {
		return rue;
	}
	public void setRue(String rue) {
		this.rue = rue;
	}
	public int getCodePostal() {
		return codePostal;
	}
	public void setCodePostal(int codePostal) {
		this.codePostal = codePostal;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public Encheres() {}
	
	public Encheres(int noEnchere, String encherisseur, LocalDate dateEnchere, int montantEnchere, int noUtilisateur, String rue, int codePostal, String ville) {
		this();
		this.noEnchere = noEnchere;
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
		this.noUtilisateur = noUtilisateur;
		this.encherisseur= encherisseur;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
			
	}
	@Override
	public String toString() {
		return "Encheres [noEnchere=" + noEnchere + ", dateEnchere=" + dateEnchere + ", montantEnchere="
				+ montantEnchere + ", noArticle=" + noArticle + ", noUtilisateur=" + noUtilisateur + ", encherisseur="
				+ encherisseur + ", rue=" + rue + ", codePostal=" + codePostal + ", ville=" + ville + "]";
	}

	
}

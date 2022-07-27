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
	
	public Encheres(LocalDate dateEnchere, int montantEnchere, int noArticle, int noUtilisateur) {
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
		this.noUtilisateur = noUtilisateur;
		this.noArticle = noArticle;
	}
	
	
	public Encheres(int noEnchere, LocalDate dateEnchere, int montantEnchere, int noArticle, int noUtilisateur,
			String encherisseur) {
		this.noEnchere = noEnchere;
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
		this.noArticle = noArticle;
		this.noUtilisateur = noUtilisateur;
		this.encherisseur = encherisseur;
	}
	@Override
	public String toString() {
		return "Encheres [noEnchere=" + noEnchere + ", dateEnchere=" + dateEnchere + ", montantEnchere="
				+ montantEnchere + ", noArticle=" + noArticle + ", noUtilisateur=" + noUtilisateur + ", encherisseur="
				+ encherisseur + "]";
	}

	
}

package fr.eni.encheres.bo;

import java.io.Serializable;

public class Retraits implements Serializable {
	private static final long serialVersionUID = 1L;
	private int noArticle;
	private String rue;
	private int codePostal;
	private String ville;
	public int getNoArticle() {
		return noArticle;
	}
	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Retraits() {
	}
	public Retraits(String rue, int codePostal, String ville) {
		this();
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}
	@Override
	public String toString() {
		return "Retraits [noArticle=" + noArticle + ", rue=" + rue + ", codePostal=" + codePostal + ", ville=" + ville
				+ "]";
	}
	
}

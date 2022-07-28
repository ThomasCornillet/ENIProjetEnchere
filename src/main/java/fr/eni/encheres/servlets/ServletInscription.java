package fr.eni.encheres.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.HashMotDePasse;
import fr.eni.encheres.bll.UtilisateursManager;
import fr.eni.encheres.bo.Utilisateurs;
import fr.eni.encheres.exceptions.BusinessException;

/**
 * Servlet implementation class ServletInscription
 */
@WebServlet("/inscription")
public class ServletInscription extends HttpServlet {
	public static final String VUE_INSCRIPTION = "/WEB-INF/jsp/inscription.jsp";
	public static final String VUE_ACCUEIL = "/WEB-INF/jsp/accueil.jsp";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 this.getServletContext().getRequestDispatcher( VUE_INSCRIPTION ).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // permet d'avoir l'encodage en base de données, sinon les caractères spéciaux et accents s'affichent mal
		Utilisateurs utilisateur = new Utilisateurs();
		List<Integer> listeCodesErreur= new ArrayList<>();
		String pseudo = request.getParameter("pseudo");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		String rue = request.getParameter("rue");
		String ville = request.getParameter("ville");
		String codePostal = request.getParameter("codePostal");
		String motDePasse = request.getParameter("motdepasse");
		String motDePasseConfirmation = request.getParameter("motdepasseConfirmation");
	
		//Est-ce que le mot de passe remplit nos exigeances	
		if(!formatMotDePasseValid(utilisateur, motDePasse)) {
			listeCodesErreur.add(CodesResultatServlet.MOT_DE_PASSE_NON_CONFROME); // TODO re voir ce que client veut pour format mot de passe
			request.setAttribute("listeCodesErreur", listeCodesErreur);
			RequestDispatcher rd = request.getRequestDispatcher(VUE_INSCRIPTION);
			rd.forward(request, response);
		}else 
			//Est-ce que le mot de passe et sa confirmation sont égaux?
			{if(!motDePasse.contentEquals(motDePasseConfirmation)) {
				listeCodesErreur.add(CodesResultatServlet.MOT_DE_PASSE_NON_IDENTIQUES);
				request.setAttribute("listeCodesErreur", listeCodesErreur);
				RequestDispatcher rd = request.getRequestDispatcher(VUE_INSCRIPTION);
				rd.forward(request, response);
			}else {// est-ce que le pseudo existe déjà?
				if(pseudoExists(utilisateur, pseudo)) {
					listeCodesErreur.add(CodesResultatServlet.UTILISATEUR_EXISTE_DEJA);
					request.setAttribute("listeCodesErreur", listeCodesErreur);
					RequestDispatcher rd = request.getRequestDispatcher(VUE_INSCRIPTION);
					rd.forward(request, response);
				} 
				else {
					// hash mot de passe
					// TODO pas le bon endroit pour le hash
//						HashMotDePasse hashMdp = HashMotDePasse.getInstance();
//						String mdpHash = hashMdp.shaHash(motDePasse);
//						System.out.println(mdpHash);
					
					utilisateur.setPseudo(pseudo);
					utilisateur.setNom(nom);
					utilisateur.setPrenom(prenom);
					utilisateur.setEmail(email);
					utilisateur.setTelephone(telephone);
					utilisateur.setRue(rue);
					utilisateur.setVille(ville);
					utilisateur.setCodePostal(codePostal);
					utilisateur.setMotDePasse(motDePasse);
					utilisateur.setCredit(1000); //TODO où mettre l'attribution du crédit?
					
					try {UtilisateursManager utilisateurMngr = UtilisateursManager.getInstance();

						utilisateurMngr.insert(utilisateur);
						HttpSession session = request.getSession();
						session.setAttribute("UtilisateurConnecte", utilisateur);
						session.setAttribute("connecte", true);
						RequestDispatcher rd = request.getRequestDispatcher("/accueil"); 
						rd.forward(request, response);
						} catch (BusinessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					}
				}
			}
		}
	}
	
	private boolean pseudoExists(Utilisateurs utilisateur, String pseudo) {
		boolean retour = false;
		if (pseudo.equals(utilisateur.getPseudo())) {
			retour = true;
		}
		return retour;
	}
	
// Fonction vérification du format du mot de passe
	
//		The regex for a password that
//				
//			 must contain at least one digit [0-9] : (?=.*[0-9])
//			 must contain at least one lowercase Latin character [a-z]: (?=.*[a-z])
//			 must contain at least one uppercase Latin character [A-Z] : (?=.*[A-Z])
//			 must contain at least one special character like ! @ # & ( ) : (?=.*[!@#&()–[{}]:;',?/*~$^+=<>])
//			 must contain a length of at least 8 characters and a maximum of 20 characters. {8,20}
//			 ^ = Start of line / $ = End of line
//	
//			 is :
//	
//			 	^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$
	
	// TODO revérifier les demandes client format password
	private boolean formatMotDePasseValid(Utilisateurs utilisateur, String motDePasse) {
		boolean retour = false;
		if (motDePasse.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{12,30}$")) {
			retour = true;
		}
		return retour;
	}
}

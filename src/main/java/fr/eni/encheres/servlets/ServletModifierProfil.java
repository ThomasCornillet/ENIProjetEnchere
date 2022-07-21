package fr.eni.encheres.servlets;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;




/**
 * Servlet implementation class ModifierProfil
 */
@WebServlet("/ModifierProfil")
public class ServletModifierProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletModifierProfil() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/modifierProfil.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String choix = request.getParameter("choix");
		request.setAttribute("choix", choix);
		if (choix.equals("supprimer")) {
			request.setAttribute("suppression", "Votre compte est supprimé.");
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/accueil.jsp").forward(request, response);
		} else

		if (choix.equals("valider")) {
			request.setAttribute("ModificationOK", "Votre profil est modifié.");
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/PagePrincipale.jsp").forward(request, response);

		} else {

			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/profil.jsp").forward(request, response);
		}
		
		//je créée utilisateur dans fr.eni.encheres.servlets 
		noUtilisateur noUtilisateur = new noUtilisateur();
		// récupération des paramètres

		
		String pseudo = request.getParameter("pseudo");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prénom");
		String mail = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		String rue = request.getParameter("rue");
		String codePostal = request.getParameter("code postal");
		String ville = request.getParameter("ville");
		String motDePasse = request.getParameter("mot de passe");
		
		//j'ai importé une servlet httpSession car java me l'a proposé
		HttpSession session = request.getSession();
	

		// Ajout dans la bdd 
		//et je créée aussi une classe utilisateurDao et je créée aussi une méthode modification et j'ai créée une autre classe DALException

		UtilisateurDAO.modification(noUtilisateur, pseudo, nom, prenom, mail, telephone, rue, codePostal, ville, motDePasse,
				(int) session.getAttribute("noUtilisateur"));
		
	}

}

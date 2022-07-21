package fr.eni.encheres.servlets;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * Servlet implementation class servletModifierMotDePasse
 */


@WebServlet("/servletModifierMotDePasse")
public class servletModifierMotDePasse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletModifierMotDePasse() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/modifierMotDePasse.jsp").forward(request, response);

	
		
		/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String choix = request.getParameter("Choix");
		request.setAttribute("Choix", choix);
		noUtilisateur	noUtilisateur = new noUtilisateur();//creation utilisateur.java dans la bo  par java .
		
		HttpSession session = request.getSession();//import  servlet httpSession 
		int nuUtilisateur = (int) session.getAttribute("numUtil");
		String motDePasse = request.getParameter("MdpSecu");
	    UtilisateurDAO noUtilisateurDAO = new UtilisateurDAO();//creation classe noUtilisateurDAO
		if (motDePasse != null) {
	
		try {
			Object utilisateurDAO;
			Object Utilisateur = ((Object) utilisateurDAO).verificationMotDePasse(noUtilisateur);
		} catch (DALException e) {
		
			e.printStackTrace();
		}
		}
		String test = Utilisateur.getMotDePasse();
	
		
		if (choix.equals("retour")) {
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/profil.jsp").forward(request, response);
		}
			if (motDePasse.equals(test)){
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/modifierProfil.jsp").forward(request, response);
	} else {
		request.setAttribute("error", "Mot de passe incorrect");
		doGet(request, response);

		
		
		
		
		
		
		
	}
}

		
		
		
}


	

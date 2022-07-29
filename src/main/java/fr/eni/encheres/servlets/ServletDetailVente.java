package fr.eni.encheres.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.ArticlesManager;
import fr.eni.encheres.bll.EncheresManager;
import fr.eni.encheres.bll.RetraitsManager;
import fr.eni.encheres.bll.UtilisateursManager;
import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Encheres;
import fr.eni.encheres.bo.Retraits;
import fr.eni.encheres.bo.Utilisateurs;
import fr.eni.encheres.exceptions.BusinessException;

/**
 * Servlet implementation class ServletDetailVente
 */
@WebServlet("/detailVente")
public class ServletDetailVente extends HttpServlet {
	private static final String VUE_DETAIL_VENTE = "/WEB-INF/jsp/detailVente.jsp";
	private static final long serialVersionUID = 1L;
    
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//----------------------------------------------------	
// Il va falloir la fusionner avec la servlet Article?
//----------------------------------------------------
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Integer> listeCodesErreur= new ArrayList<>();
		ArticlesManager articleMngr = ArticlesManager.getInstance();
		UtilisateursManager utilisateurMngr = UtilisateursManager.getInstance();
		EncheresManager encheresMngr = EncheresManager.getInstance();
		RetraitsManager retraitsMngr = RetraitsManager.getInstance();
		
		try {
			HttpSession session = request.getSession();
			
			int noArticle = Integer.parseInt(request.getParameter("id"));
			
			request.setAttribute("noArticle", noArticle);
			
			Articles article = articleMngr.selectArticleByNoArticle(noArticle);
			
			/*
			Boolean encherePasCommencee;
			if (article.getDate_debut_enchere().isAfter(LocalDate.now())) {
				encherePasCommencee = true;
			} else {
				encherePasCommencee = false;
			}
			request.setAttribute("encherePasCommencee", encherePasCommencee);
			*/

			if(article != null){
				Utilisateurs vendeur = utilisateurMngr.selectById(article.getNo_utilisateur());
				request.setAttribute("vendeur", vendeur);
				List<Encheres> encheres = encheresMngr.selectByNoArticle(article.getNoArticle());
				if(encheres.size()>0) {
					Encheres meilleureEnchere = encheres.get(0);
					request.setAttribute("enchere", meilleureEnchere);
					Utilisateurs encherisseur = utilisateurMngr.selectById(meilleureEnchere.getNoUtilisateur());
					request.setAttribute("encherisseur", encherisseur);
				}
				Retraits retrait = retraitsMngr.selectByNoArticle(article.getNoArticle());
				request.setAttribute("article", article);
				request.setAttribute("encheres", encheres);
				request.setAttribute("retrait", retrait);
			}
			
			
//			if(article != null){
//				List<Encheres> encheres  = article.getListeEncheres();	// TODO à revoir, faire un select encheres by article dans enchere BLL et DAL
//				Retraits retrait = article.getRetrait();	// TODO IDEM
//				System.out.println(article.toString()); // TODO delete
//				if(encheres.size()>0) {
//					Encheres enchere = encheres.get(0);
//					request.setAttribute("enchere", enchere);
//				}
//				request.setAttribute("article", article);
//				request.setAttribute("encheres", encheres);
//				request.setAttribute("retrait", retrait);
//			}
			
			this.getServletContext().getRequestDispatcher( VUE_DETAIL_VENTE ).forward( request, response );
			
		} catch (BusinessException e) {
			for (int code : e.getListeCodesErreur()) {
				listeCodesErreur.add(code);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // permet d'avoir l'encodage en base de données, sinon les caractères spéciaux et accents s'affichent mal
		List<Integer> listeCodesErreur= new ArrayList<>();
		HttpSession session = request.getSession();
		Utilisateurs utilisateur = (Utilisateurs) session.getAttribute("UtilisateurConnecte");
		
		int noArticle = Integer.parseInt(request.getParameter("noArticle"));
		LocalDate dateEnchere = null;
		int montantEnchere = Integer.parseInt(request.getParameter("encherir"));
		int noUtilisateur = utilisateur.getNoUtilisateur();
		dateEnchere = LocalDate.now();
		Encheres enchere = new Encheres(dateEnchere, montantEnchere, noArticle, noUtilisateur);
		boolean insertOk = false;
		List<Integer> listeErreursEnchere = new ArrayList<>();
		try {
			EncheresManager encheresMngr = EncheresManager.getInstance();
			 // TODO ne pas oublier de la gérer
			insertOk = encheresMngr.insert(enchere, listeErreursEnchere); // mettre en place un boolean pour voir si ça respecte les règles et que l'enchèe s'est bien produite
		}catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		if (!listeCodesErreur.isEmpty()) {
			request.setAttribute("listeCodesErreur", listeCodesErreur);
		}
		
//		if (insertOk == false && !listeErreursEnchere.isEmpty()) {
//			request.setAttribute("listeErreursEnchere", listeErreursEnchere);
////			Utilisateurs utilisateurConnecte = (Utilisateurs) session.getAttribute("UtilisateurConnecte");
//			request.setAttribute("id", request.getParameter("noArticle"));
//			doGet(request,response);
//		} else {
//			RequestDispatcher rd = request.getRequestDispatcher("/accueil"); // TODO renvoyer vers détail de l'enchère
//			rd.forward(request, response);
//		}
		request.setAttribute("listeErreursEnchere", listeErreursEnchere);
		RequestDispatcher rd = request.getRequestDispatcher("/accueil"); // TODO renvoyer vers détail de l'enchère
		rd.forward(request, response);
	}

}

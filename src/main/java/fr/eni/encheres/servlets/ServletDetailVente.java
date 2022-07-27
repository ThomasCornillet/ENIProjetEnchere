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
		
		try {
			HttpSession session = request.getSession();
			
			int noArticle = Integer.parseInt(request.getParameter("id"));
			
			request.setAttribute("noArticle", noArticle);
			Articles article = articleMngr.selectArticleByNoArticle(noArticle);
			if(article != null){
			List<Encheres> encheres  = article.getListeEncheres();
			Retraits retrait = article.getRetrait();
			System.out.println(article.toString()); // TODO delete
				if(encheres.size()>0) {
					Encheres enchere = encheres.get(0);
					request.setAttribute("enchere", enchere);
				}
				request.setAttribute("article", article);
				request.setAttribute("encheres", encheres);
				request.setAttribute("retrait", retrait);
			}
			
			this.getServletContext().getRequestDispatcher( VUE_DETAIL_VENTE ).forward( request, response );
			
		} catch (BusinessException e) {
			for (int code : e.getListeCodesErreur()) {
				listeCodesErreur.add(code);
			}
		}
		if (!listeCodesErreur.isEmpty()) {
			request.setAttribute("listeCodesErreur", listeCodesErreur);
		}
		RequestDispatcher rd = request.getRequestDispatcher(VUE_DETAIL_VENTE);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // permet d'avoir l'encodage en base de données, sinon les caractères spéciaux et accents s'affichent mal
		List<Integer> listeCodesErreur= new ArrayList<>();
		HttpSession session = request.getSession();
		Utilisateurs utilisateur = (Utilisateurs) session.getAttribute("UtilsiateurConnecte");
		
		int noArticle = Integer.parseInt(request.getParameter("id"));
		LocalDate dateEnchere = null;
		int montantEnchere = Integer.parseInt(request.getParameter("encherir"));
		int noUtilisateur = utilisateur.getNoUtilisateur();
		try {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		dateEnchere = LocalDate.parse(request.getParameter("dateEnchere"), dtf);
		} catch (DateTimeParseException e) {
			e.printStackTrace();
			listeCodesErreur.add(CodesResultatServlet.FORMAT_DATE_ENCHERE_ERREUR);
		}
		Encheres enchere = new Encheres(dateEnchere, montantEnchere, noArticle, noUtilisateur);
		try {
			EncheresManager encheresMngr = EncheresManager.getInstance();
			encheresMngr.insert(enchere);
		}catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		if (!listeCodesErreur.isEmpty()) {
			request.setAttribute("listeCodesErreur", listeCodesErreur);
		}
		RequestDispatcher rd = request.getRequestDispatcher(VUE_DETAIL_VENTE);
		rd.forward(request, response);
	}

}

package fr.eni.encheres.filters;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class FiltreRessourcesInterdites
 */
@WebFilter(
		urlPatterns= {"/detailVente",
						"/ServletEncheres",
						"/nouvelleVente",
						"/modificationProfil",
						"/supprimerProfil",
						"/afficherProfil"}, // TODO faire la liste des urls concernées
		dispatcherTypes = {DispatcherType.REQUEST,
							DispatcherType.FORWARD
							}
		)
public class FiltreRessourcesInterdites extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public FiltreRessourcesInterdites() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		if (session.getAttribute("connecte") != null && (boolean)session.getAttribute("connecte") == true) {
			httpRequest.setAttribute("filtreInterdit", false);
			chain.doFilter(request, response);
		} else {
//			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpRequest.setAttribute("filtreInterdit", true);
//			httpResponse.sendRedirect(session.getServletContext().getContextPath() + "/authentification"); 
			RequestDispatcher rd = request.getRequestDispatcher("/authentification");
			rd.forward(request, response);	// un response.sendRedirect() ne serait pas mieux ? mais au moins là j'ai l'affichage du message sur la page d'authentification
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

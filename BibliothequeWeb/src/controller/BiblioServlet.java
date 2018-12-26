/**
 * 
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.picketbox.util.StringUtil;

import metier.entities.Article;
import metier.entities.Livre;
import metier.session.IBibliothequeLocal;


/**
 * @author Administrator Auteur HDN Crée le Dec 26, 2018
 *
 *         Cette classe permet de ...
 * 
 */
@WebServlet(name = "cs", urlPatterns = { "/article" })
public class BiblioServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1249668125357006952L;
	@EJB
	private IBibliothequeLocal metier;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String action = request.getParameter("action");
			if (action != null) {
				if (action.equals("consulter")) {
					List<Article> articles = new ArrayList<Article>();
					// recherche par ref
					if (!StringUtil.isNullOrEmpty(request.getParameter("reference"))) {
						Long ref = Long.parseLong(request.getParameter("reference"));
						request.setAttribute("reference", ref);
						Article article = metier.consulterArticle(ref);
						articles.add(article);
					}
					else {
						String titre = request.getParameter("intitule");
						request.setAttribute("intitule", titre);
						articles = metier.consulterArticlesParTitre(titre);
					}
					request.setAttribute("articles", articles);
				} else if (action.equals("inventaire")) {
					List<Article> inventaire = metier.consulterInventaire();
					request.setAttribute("inventaire", inventaire);
				} else if (action.equals("ajouter")) {
					Long ref = Long.parseLong(request.getParameter("reference"));
					String titre = request.getParameter("intitule");
					metier.ajouterStock(new Livre(ref, titre, new Date()));
				}
			}
		} catch (Exception exception) {
			request.setAttribute("exception", exception.getMessage());
		}
		request.getRequestDispatcher("rechercheArticles.jsp").forward(request, response);
	}
}
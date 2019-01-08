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

import metier.constantes.ActionEnum;
import metier.entities.Article;
import metier.entities.Livre;
import metier.entities.Personne;
import metier.session.IBibliothequeLocal;
import utils.BiblioUtil;
import utils.PersonneUtils;


/**
 * @author Administrator Auteur HDN Crée le Dec 26, 2018
 *
 *         Cette classe permet de ...
 * 
 */
@WebServlet(name = "BiblioServlet", urlPatterns = { BiblioServlet.SERVLET_PATH_ARTICLE,
		BiblioServlet.SERVLET_PATH_PERSONNE, BiblioServlet.SERVLET_PATH_EMPRUNT })
public class BiblioServlet extends HttpServlet {

	public final static String SERVLET_PATH_ARTICLE = "/article";

	public final static String SERVLET_PATH_PERSONNE = "/personne";

	public final static String SERVLET_PATH_EMPRUNT = "/emprunt";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1249668125357006952L;
	@EJB
	private IBibliothequeLocal metier;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (SERVLET_PATH_ARTICLE.equals(request.getServletPath())) {
			traiterArticles(request, response);
		} else if (SERVLET_PATH_PERSONNE.equals(request.getServletPath())) {
			traiterPersonnes(request, response);
		} else if (SERVLET_PATH_EMPRUNT.equals(request.getServletPath())) {
			traiterEmrpunt(request, response);
		}
		request.getRequestDispatcher("main.jsp").forward(request, response);
	}

	private void traiterArticles(HttpServletRequest request, HttpServletResponse response) {
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
					} else {
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
			request.setAttribute("articleException", exception.getMessage());
		}
	}

	/**
	 * @param request
	 * @param response
	 */
	private void traiterPersonnes(HttpServletRequest request, HttpServletResponse response) {
		try {
			ActionEnum actionEnum = BiblioUtil.recupActionEnum(request.getParameter("action"));
			Personne personne = null;
			switch (actionEnum) {
				case LOGIN : {
					personne = PersonneUtils.loguerPersonne(request, metier);
					request.getSession().setAttribute("adherent", personne);
					break;
				}
				case CREER : {
					metier.ajouterPersonne(personne = PersonneUtils.creerPersonne(request), ActionEnum.CREER);
					break;
					} 
				case MODIFIER : {
					metier.ajouterPersonne(personne = PersonneUtils.creerPersonne(request), ActionEnum.MODIFIER);
					request.getSession().setAttribute("adherent", personne);
					break;
				}
				case LOGOUT : {
					request.getSession().removeAttribute("adherent");
					break;
				}
			}
			PersonneUtils.enregistrerPersonneRequest(request, personne);
	
		} catch (Exception exception) {
			request.setAttribute("personneException", exception.getMessage());
			request.setAttribute("nom", request.getParameter("nom"));
			request.setAttribute("prenom", request.getParameter("prenom"));
		}
	}

	private void traiterEmrpunt(HttpServletRequest request, HttpServletResponse response) {
		try {
			Personne abonne = (Personne) request.getSession().getAttribute("adherent");
			if (abonne != null) {
				Long idAbonne = abonne.getId();
				Long refArticle = Long.parseLong(request.getParameter("reference"));
				metier.emprunter(refArticle, idAbonne);
				Personne personne = metier.recupererPersonne(idAbonne);
				request.getSession().setAttribute("adherent", personne);
			}
			
		} catch (Exception exception) {
			request.setAttribute("empruntException", exception.getMessage());
		}
	}
}
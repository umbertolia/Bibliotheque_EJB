/**
 * 
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import metier.BibliothequeException;
import metier.constantes.ActionEnum;
import metier.entities.Article;
import metier.entities.Personne;
import metier.session.IBibliothequeLocal;
import utils.ArticleUtil;
import utils.BiblioUtil;
import utils.PersonneUtils;


/**
 * @author Administrator Auteur HDN Cr�e le Dec 26, 2018
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

	@SuppressWarnings("serial")
	private void traiterArticles(HttpServletRequest request, HttpServletResponse response) {
		try {
			ActionEnum actionEnum = BiblioUtil.recupActionEnum(request.getParameter("action"));
			List<Article> articles = new ArrayList<Article>();
			Article article = null;
			switch (actionEnum) {
				case CONSULTER : {
					articles = ArticleUtil.recupererArticles(request, metier);
					if (!articles.isEmpty()) {
						ArticleUtil.enregistrerArticleInRequest(request, articles.get(0));
					}
					break;
				}
				case INVENTAIRE : {
					List<Article> inventaire = metier.consulterInventaire();
					request.setAttribute("inventaire", inventaire);
					break;
				}
				case CREER : {
					metier.ajouterStock(article = ArticleUtil.instancierArticle(request), ActionEnum.CREER);
					List<Article> inventaire = metier.consulterInventaire();
					request.setAttribute("inventaire", inventaire);
					break;
				}
				case MODIFIER : {
					article = ArticleUtil.instancierArticle(request);
					metier.ajouterStock(article, ActionEnum.MODIFIER);
					articles.add(metier.consulterArticle(article.getReference()));
					break;
				}
			}
			request.setAttribute("articles", articles);
			ArticleUtil.enregistrerArticleInRequest(request, article);
		} catch (BibliothequeException bibliothequeException) {
			request.setAttribute("articleException", bibliothequeException.getMessage());
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
			PersonneUtils.enregistrerPersonneInRequest(request, personne);
	
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
package utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.picketbox.util.StringUtil;

import metier.BibliothequeException;
import metier.entities.Article;
import metier.entities.Livre;
import metier.session.IBibliothequeLocal;


public class ArticleUtil {

	public static List<Article> recupererArticles(HttpServletRequest request, IBibliothequeLocal metier) throws BibliothequeException {
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
		return articles;
	}

	public static Article instancierArticle(HttpServletRequest request) {
		Long ref = Long.parseLong(request.getParameter("reference"));
		String titre = request.getParameter("intitule");
		return new Livre(ref, titre, new Date());
	}
	
	public static void enregistrerArticleInRequest(HttpServletRequest request, Article article) {
		if (article != null) {
			request.setAttribute("reference", article.getReference());
			request.setAttribute("intitule", article.getIntitule());
		}
	}
}

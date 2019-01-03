package service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import metier.entities.Article;
import metier.entities.Livre;
import metier.session.IBibliothequeLocal;


/**
 * @author Administrator Auteur HDN Crée le Dec 27, 2018
 *
 *         Cette classe permet de ...
 * 
 */

@WebService
public class BibliothequeSoapService {

	@EJB
	private IBibliothequeLocal metier;

	@WebMethod
	public void ajouterLivre(@WebParam(name = "reference") long ref, @WebParam(name = "intitule") String titre,
			@WebParam(name = "date") String datePublication) throws Exception {
		Date datePublic = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			datePublic = sdf.parse(datePublication);
			Article article = new Livre(ref, titre, datePublic);
			metier.ajouterStock(article);
		} catch (ParseException parseException) {
			throw new Exception("Date incorrecte", parseException);
		}
	}
	
	@WebMethod
	public Article consulterParRef(@WebParam(name = "reference") long ref) throws Exception {
		if (ref <=0 ) {
			throw new Exception("La reference de l'article doit etre > 0");
		}
		return metier.consulterArticle(ref);
	}
	
	@WebMethod
	public List<Article> consulterParTitre(@WebParam(name = "intitule") String titre) throws Exception {
		if (titre == null || "".equals(titre)) {
			throw new Exception("Le titre de l'article doit etre renseigné");
		}
		return metier.consulterArticlesParTitre(titre);
	}
	

}

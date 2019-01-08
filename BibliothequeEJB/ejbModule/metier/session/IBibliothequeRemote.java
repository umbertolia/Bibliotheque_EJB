package metier.session;

import java.util.List;

import javax.ejb.Remote;

import metier.constantes.ActionEnum;
import metier.entities.Article;
import metier.entities.Personne;


/**
 * @author Administrator Auteur HDN Crée le Dec 25, 2018
 *
 *         Cette classe permet de ...
 * 
 */
@Remote
public interface IBibliothequeRemote {
	
	public void ajouterStock(Article article);
	
	public void ajouterPersonne(Personne personne, ActionEnum typeAction);
	
	public List<Article> consulterInventaire();

	public Article consulterArticle(Long reference);
	
	public List<Article> consulterArticlesParTitre(String titre);
	
	public List<Article> consulterEmprunts(Long idPersonne);

	public Personne recupererPersonne(Long idPersonne);
	
	public Personne recupererPersonneIdNomPrenom(Personne personne);
	
	public void emprunter(Long refArticle, Long idPersonne);
	
	public void restituer(Article article, Long idPersonne);

}
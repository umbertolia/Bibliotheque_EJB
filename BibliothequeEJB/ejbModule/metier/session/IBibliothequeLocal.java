package metier.session;

import java.util.List;

import javax.ejb.Local;

import metier.BibliothequeException;
import metier.constantes.ActionEnum;
import metier.entities.Article;
import metier.entities.Personne;


/**
 * @author Administrator Auteur HDN Crée le Dec 25, 2018
 *
 *         Cette classe permet de ...
 * 
 */
@Local
public interface IBibliothequeLocal {
	
	public void ajouterStock(Article article, ActionEnum actionEnum) throws BibliothequeException;
	
	public void ajouterPersonne(Personne personne, ActionEnum typeAction) throws BibliothequeException;
	
	public List<Article> consulterInventaire();

	public Article consulterArticle(Long reference) throws BibliothequeException;
	
	public List<Article> consulterArticlesParTitre(String titre);
	
	public List<Article> consulterEmprunts(Long idPersonne) throws BibliothequeException;

	public Personne recupererPersonne(Long idPersonne) throws BibliothequeException;
	
	public Personne recupererPersonneIdNomPrenom(Personne personne) throws BibliothequeException;
	
	public void emprunter(Long refArticle, Long idPersonne) throws BibliothequeException;
	
	public void restituer(Article article, Long idPersonne) throws BibliothequeException;

}
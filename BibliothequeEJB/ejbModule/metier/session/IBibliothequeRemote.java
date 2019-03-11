package metier.session;

import java.util.List;

import javax.ejb.Remote;

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
@Remote
public interface IBibliothequeRemote {
	
	public void ajouterStock(Article article, ActionEnum actionEnum) throws BibliothequeException;
	
	public void ajouterPersonne(Personne personne, ActionEnum typeAction) throws BibliothequeException;
	
	public List<Article> consulterInventaire() throws BibliothequeException;

	public Article consulterArticle(Long reference) throws BibliothequeException;
	
	public List<Article> consulterArticlesParTitre(String titre)  throws BibliothequeException;;
	
	public List<Article> consulterEmprunts(Long idPersonne) throws BibliothequeException;

	public Personne recupererPersonne(Long idPersonne) throws BibliothequeException;
	
	public List<Personne> recupererPersonnes()  throws BibliothequeException;;
	
	public Personne recupererPersonneIdNomPrenom(Personne personne) throws BibliothequeException;
	
	public void emprunter(Long refArticle, Long idPersonne) throws BibliothequeException;
	
	public void restituer(Long refArticle, Long idPersonne) throws BibliothequeException;
	
	public void initialiser() throws BibliothequeException;

}
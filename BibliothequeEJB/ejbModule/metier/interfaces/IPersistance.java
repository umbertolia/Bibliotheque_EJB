package metier.interfaces;

import java.util.List;

import metier.BibliothequeException;
import metier.constantes.ActionEnum;
import metier.entities.Article;
import metier.entities.Personne;

public interface IPersistance {
	
	void initialiser() throws BibliothequeException;
	
	List<Article> consulterInventaire() throws BibliothequeException;
	
	Article consulterArticle(Long reference) throws BibliothequeException;
	
	List<Article> consulterArticlesParTitre(String titre);
	
	public void ajouterStock(Article article, ActionEnum actionEnum) throws BibliothequeException;
	
	List<Article> consulterEmprunts(Long idPersonne) throws BibliothequeException;
	
	public void ajouterPersonne(Personne personne, ActionEnum actionEnum) throws BibliothequeException;
	
	public Personne recupererPersonne(Long idPersonne) throws BibliothequeException;
	
	public List<Personne> recupererPersonnes() throws BibliothequeException;
	
	public void emprunter(Long refArticle, Long idPersonne, int empruntTailleMax) throws BibliothequeException;
	
	public void restituer(Long refArticle, Long idPersonne) throws BibliothequeException;
	
	public Personne recupererPersonneIdNomPrenom(Personne personne) throws BibliothequeException;
	
	
	
	
}

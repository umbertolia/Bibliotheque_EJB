/**
 * 
 */
package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import metier.BibliothequeException;
import metier.constantes.ActionEnum;
import metier.entities.Article;
import metier.entities.Livre;
import metier.entities.Personne;
import metier.interfaces.IPersistance;

/**
 * @author Administrator
 * Auteur HDN
 * Crée le Dec 26, 2018
 *
 * Cette classe permet de ...

 */
public class Stock implements IPersistance, Serializable {

	private static final long serialVersionUID = 3481178017615741392L;
	
	private Map<Long, Article> inventaire = new HashMap<Long, Article>();
	
	private Map<Long, Personne> personnes = new HashMap<Long, Personne>();

	private static Logger logger = Logger.getLogger(Stock.class);


	/**
	 * 
	 */
	public Stock() {
		super();
	}


	/**
	 * @param inventaire
	 */
	public Stock(Map<Long, Article> inventaire) {
		super();
		this.inventaire = inventaire;
	}




	public Map<Long, Article> getInventaire() {
		return inventaire;
	}




	public void setInventaire(Map<Long, Article> inventaire) {
		this.inventaire = inventaire;
	}




	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
		if (this.inventaire == null || (this.inventaire != null && this.inventaire.isEmpty())) {
			sb.append("Aucun article en stock");
		}
		else {
			sb.append(inventaire);
		}
		return sb.toString();
	}


	@Override
	public void initialiser() {
		try {
			ajouterStock(new Livre(1L, "Livre 111", new Date()), ActionEnum.CREER);
			ajouterStock(new Livre(2L, "Livre 222", new Date()), ActionEnum.CREER);
			ajouterStock(new Livre(3L, "Livre 333", new Date()), ActionEnum.CREER);
			ajouterStock(new Livre(4L, "Livre 444", new Date()), ActionEnum.CREER);
			ajouterStock(new Livre(5L, "Livre 555", new Date()), ActionEnum.CREER);
			ajouterStock(new Livre(6L, "Livre 666", new Date()), ActionEnum.CREER);
			ajouterStock(new Livre(7L, "Livre 777", new Date()), ActionEnum.CREER);
			ajouterStock(new Livre(8L, "Livre 888", new Date()), ActionEnum.CREER);
			ajouterStock(new Livre(9L, "Livre 999", new Date()), ActionEnum.CREER);
			ajouterStock(new Livre(10L, "Livre 1010", new Date()), ActionEnum.CREER);
			ajouterStock(new Livre(11L, "Livre 1111", new Date()), ActionEnum.CREER);
			ajouterStock(new Livre(12L, "Livre 1212", new Date()), ActionEnum.CREER);
			ajouterStock(new Livre(13L, "Livre 1313", new Date()), ActionEnum.CREER);
			ajouterStock(new Livre(14L, "Livre 1414", new Date()), ActionEnum.CREER);
			ajouterStock(new Livre(15L, "Livre 1515", new Date()), ActionEnum.CREER);
			logger.info("Initialisation Ajout d'articles depuis l'incercepteur");
			ajouterPersonne(new Personne(1l, "Jules", "Vernes"), ActionEnum.CREER);
			logger.info("Initialisation Ajout d'une personne depuis l'incercepteur");
		}
		catch (BibliothequeException bibliothequeException) {
		}
		
	}
	
	public List<Article> consulterInventaire() throws BibliothequeException {
		if (this.inventaire == null) {
			throw new BibliothequeException("Pb avec la base Stock");
		}
		logger.info("consulterInventaire()");
		
		return new ArrayList<Article>(this.inventaire.values());
	}
	
	public Article consulterArticle(Long reference, ActionEnum actionEnum) throws BibliothequeException {
		Article article = this.inventaire.get(reference);
		if (article == null && actionEnum.equals(ActionEnum.CONSULTER)) {
			throw new BibliothequeException("Article id=[" + reference + "] introuvable");
		}
		return article;
	}
	
	public List<Article> consulterArticlesParTitre(String titre) {
		List<Article> articles = new ArrayList<Article>();
		if (this.inventaire != null) {
			for (Article article : this.inventaire.values()) {
				if (article.getIntitule().contains(titre)) {
					articles.add(article);
				}
			}
		}
		return articles;
	}
	
	public List<Article> consulterEmprunts(Long idPersonne) throws BibliothequeException {
		List<Article> emprunts = new ArrayList<Article>();
		if (idPersonne != null && personnes != null) {
			// recup de la personne
			for (Article article : recupererPersonne(idPersonne, ActionEnum.CONSULTER).getEmprunts().values()) {
				emprunts.add(article);
			}
		}
		return emprunts;
	}
	
	public void ajouterPersonne(Personne personne, ActionEnum actionEnum) throws BibliothequeException {
		if (personne != null) {
			switch (actionEnum) {
				case CREER: {
					if (personnes.containsKey(personne.getId())) {
						throw new BibliothequeException("L'id " + personne.getId() + " existe deja !");
					}
					personnes.put(personne.getId(), personne);
					break;
				}
				case MODIFIER: {
					// verif si la personne est en DB
					Personne personneDB = recupererPersonne(personne.getId(), actionEnum);
					personneDB.setNom(personne.getNom());
					personneDB.setPrenom(personne.getPrenom());
					personnes.put(personneDB.getId(), personneDB);
					break;
				}
				default: {
					// on ne fait rien
					break;
				}
			}
		}
	}
	
	public Personne recupererPersonne(Long idPersonne, ActionEnum actionEnum) throws BibliothequeException {
		Personne personne = null;
		if (idPersonne != null && personnes != null) {
			personne = personnes.get(idPersonne);
		}
		if (personne == null  && !actionEnum.equals(ActionEnum.CREER)) {
			throw new BibliothequeException("Personne introuvable avec l'id " + idPersonne);
		}
		return personne;
	}

	public List<Personne> recupererPersonnes() throws BibliothequeException {
		if (personnes == null) {
			throw new BibliothequeException("Pb avec la base Personnes");
		}
		return new ArrayList<Personne>(personnes.values());
	}
	
	public void emprunter(Long refArticle, Long idPersonne, int empruntTailleMax) throws BibliothequeException {
		Personne adherent = null;
		Article article = null;
		// emprunt possible ?
		if (consulterEmprunts(idPersonne).size() >= empruntTailleMax) {
			throw new BibliothequeException("Emprunt impossible : Vous avez atteint le nombre max d'emprunts");
		}
		// on verifie si le livre existe puis on l'enleve du stock
		try {
			article = consulterArticle(refArticle, ActionEnum.CONSULTER);
		} catch (BibliothequeException exception) {
			// on regarde si le livre est deja emprunté sinon on laisse
			// l'exeception telle qu'elle
			adherent = recupererPersonne(idPersonne, ActionEnum.CONSULTER);
			if (adherent != null) {
				if (adherent.getEmprunts().get(refArticle) != null) {
					throw new BibliothequeException("Emprunt impossible : Livre id=[" + refArticle + "] deja emprunté");
				} else
					throw exception;
			}
		}

		// MAJ des maps
		recupererPersonne(idPersonne, ActionEnum.CONSULTER).getEmprunts().put(article.getReference(), article);
		this.inventaire.remove(article.getReference());
	}
	
	public void restituer(Long refArticle, Long idPersonne) throws BibliothequeException {
		Personne personne = recupererPersonne(idPersonne, ActionEnum.CONSULTER);

		Article article = personne.getEmprunts().get(refArticle);
		if (article == null) {
			throw new BibliothequeException("Restitution impossible : livre inexistant en base");
		}
		personne.getEmprunts().remove(article.getReference());
		this.inventaire.put(article.getReference(), article);
	}
	
	public Personne recupererPersonneIdNomPrenom(Personne personne) throws BibliothequeException {
		Personne personneDB = null;

		personneDB = recupererPersonne(personne.getId(), ActionEnum.CONSULTER);
		if (!personneDB.equals(personne)) {
			throw new BibliothequeException(personne + " inexistante en base");
		}
		return personneDB;
	}
	
	public void ajouterStock(Article article, ActionEnum actionEnum) throws BibliothequeException {
		if (article != null) {
			switch (actionEnum) {
				case CREER: {
					if (consulterArticle(article.getReference(), actionEnum) != null) {
						throw new BibliothequeException(article + " existe deja !");
					}
					this.inventaire.put(article.getReference(), article);
					break;
				}
				case MODIFIER: {
					if (!this.inventaire.containsKey(article.getReference())) {
						throw new BibliothequeException(article + " n'existe pas !");
					}
					Article articleDB = consulterArticle(article.getReference(), actionEnum);
					articleDB.setIntitule(article.getIntitule());
					this.inventaire.put(articleDB.getReference(), articleDB);
					break;
				}
				default: {
					// on ne fait rien
					break;
				}
			}
		}

	}


}

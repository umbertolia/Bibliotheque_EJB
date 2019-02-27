package metier.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.interceptor.Interceptors;

import org.jboss.logging.Logger;

import interceptor.BibliothequeInterceptor;
import metier.BibliothequeException;
import metier.constantes.ActionEnum;
import metier.entities.Article;
import metier.entities.Livre;
import metier.entities.Personne;
import metier.entities.Stock;


/**
 * @author Administrator Auteur HDN Crée le Dec 25, 2018
 *
 *         Cette classe permet de ...
 * 
 */

@Singleton(name = "Biblio")
@Startup
// si <name> non spécifié, c'est le nom de la classe qui sera indiqué dans le
// JNDI
// au format : Nom_Projet_EAR/Nom_Projet_EJB/<name>!Package.NomInterface
@Interceptors({ BibliothequeInterceptor.class })
public class BibliothequeEjbImpl implements IBibliothequeLocal, IBibliothequeRemote {

	private Logger log = Logger.getLogger(BibliothequeEjbImpl.class);

	private Stock stock = new Stock();
	
	private final int EMPRUNTS_TAILLE_MAX = 5;

	private Map<Long, Personne> personnes = new HashMap<Long, Personne>();

	@PostConstruct
	public void initialisation() {
		// en presence d'un intercepteur, cette init est by-passée
		try {
			ajouterStock(new Livre(123L, "Livre 1", new Date()), ActionEnum.CREER);
			ajouterStock(new Livre(456L, "Livre 2", new Date()), ActionEnum.CREER);
			ajouterStock(new Livre(789L, "Livre 3", new Date()), ActionEnum.CREER);
			log.info("Initialisation Ajout de 3 Livres depuis l'EJB");
		}
		catch (BibliothequeException bibliothequeException) {
			//TODO ajout dans les logs
		}
	}

	@Override
	@Lock(LockType.READ)
	public List<Article> consulterInventaire() throws BibliothequeException {
		if (stock == null) {
			throw new BibliothequeException("Pb avec la base Stock");
		}
		return new ArrayList<Article>(stock.getInventaire().values());
	}

	@Override
	@Lock(LockType.READ)
	public Article consulterArticle(Long reference) throws BibliothequeException {
		Article article = stock.getInventaire().get(reference);
		if (article == null) {
			throw new BibliothequeException("Article id=["+reference+"] introuvable");
		}
		return article;
	}

	@Override
	public List<Article> consulterArticlesParTitre(String titre) {
		List<Article> articles = new ArrayList<Article>();
		if (stock.getInventaire() != null) {
			for (Article article : stock.getInventaire().values()) {
				if (article.getIntitule().contains(titre)) {
					articles.add(article);
				}
			}
		}
		return articles;
	}

	@Override
	@Lock(LockType.READ)
	public List<Article> consulterEmprunts(Long idPersonne) throws BibliothequeException {
		List<Article> emprunts = new ArrayList<Article>();
		if(idPersonne != null && personnes != null) {
			// recup de la personne
			for (Article article : recupererPersonne(idPersonne).getEmprunts().values()) {
				emprunts.add(article);
			}
		}
		return emprunts;
	}
	
	@Override
	@Lock(LockType.WRITE)
	public void ajouterPersonne(Personne personne, ActionEnum actionEnum) throws BibliothequeException {
		if (personne != null) {
			switch (actionEnum) {
				case CREER : {
					if (personnes.containsKey(personne.getId())) {
						throw new BibliothequeException("L'id "+personne.getId() + " existe deja !");
					}
					personnes.put(personne.getId(), personne);
					break;
				}
				case MODIFIER : {
					// verif si la personne est en DB
					Personne personneDB = recupererPersonne(personne.getId());
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
	
	@Override
	@Lock(LockType.READ)
	public Personne recupererPersonne(Long idPersonne) throws BibliothequeException {
		Personne personne = null;
		if (idPersonne != null && personnes != null) {
			personne = personnes.get(idPersonne);
		}
		if (personne == null) {
			throw new BibliothequeException("Personne introuvable avec l'id "+idPersonne);
		}
		return personne;
	}
	
	

	@Override
	public List<Personne> recupererPersonnes() throws BibliothequeException {
		if (personnes == null) {
			throw new BibliothequeException("Pb avec la base Personnes");
		}
		return new ArrayList<Personne>(personnes.values());
	}

	@Override
	@Lock(LockType.WRITE)
	public void ajouterStock(Article article, ActionEnum actionEnum) throws BibliothequeException {
		if (article != null) {
			switch (actionEnum) {
				case CREER : {
					if (stock.getInventaire().containsKey(article.getReference())) {
						throw new BibliothequeException(article + " existe deja !");
					}
					stock.getInventaire().put(article.getReference(), article);
					break;
				}
				case MODIFIER : {
					if (!stock.getInventaire().containsKey(article.getReference())) {
						throw new BibliothequeException(article + " n'existe pas !");
					}
					Article articleDB = consulterArticle(article.getReference());
					articleDB.setIntitule(article.getIntitule());
					stock.getInventaire().put(articleDB.getReference(), articleDB);
					break;
				}
				default: {
					// on ne fait rien
					break;
				}
			}
		}
		
	}

	@Override
	@Lock(LockType.WRITE)
	public void emprunter(Long refArticle, Long idPersonne) throws BibliothequeException {
		Personne adherent = null;
		Article article = null;
		// emprunt possible ?
		if (consulterEmprunts(idPersonne).size() >= EMPRUNTS_TAILLE_MAX) {
			throw new BibliothequeException("Emprunt impossible : Vous avez atteint le nombre max d'emprunts");
		}
		// on verifie si le livre existe puis on l'enleve du stock
		try {
			article = consulterArticle(refArticle);
		}
		catch (BibliothequeException exception) {
			// on regarde si le livre est deja emprunté sinon on laisse l'exeception telle qu'elle
			adherent = recupererPersonne(idPersonne);
			if (adherent != null) {
				if (adherent.getEmprunts().get(refArticle) != null) {
					throw new BibliothequeException("Emprunt impossible : Livre id=["+refArticle+"] deja emprunté");
				}
				else throw exception;
			}
		}

		// MAJ des maps
		recupererPersonne(idPersonne).getEmprunts().put(article.getReference(), article);
		stock.getInventaire().remove(article.getReference());
	}

	@Override
	@Lock(LockType.WRITE)
	public void restituer(Long refArticle, Long idPersonne) throws BibliothequeException {
		Personne personne = recupererPersonne(idPersonne);
		
		Article article = personne.getEmprunts().get(refArticle);
		if (article == null) {
			throw new BibliothequeException("Restitution impossible : livre inexistant en base");
		}
		personne.getEmprunts().remove(article.getReference());
		stock.getInventaire().put(article.getReference(), article);
	}

	@Override
	public Personne recupererPersonneIdNomPrenom(Personne personne) throws BibliothequeException {
		Personne personneDB = null;
		
		personneDB = recupererPersonne(personne.getId());
		if (!personneDB.equals(personne)) {
			throw new BibliothequeException(personne + " inexistante en base");
		}
		return personneDB;
	}
	
	
}

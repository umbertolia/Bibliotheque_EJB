/**
 * 
 */
package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import metier.BibliothequeException;
import metier.constantes.ActionEnum;
import metier.entities.Article;
import metier.entities.Personne;
import metier.interfaces.IPersistance;


/**
 * @author Administrator Auteur HDN Crée le Mar 19, 2019
 *
 *         Cette classe permet de ...
 * 
 */
public class StockHibernate implements IPersistance, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4353911314652365982L;

	private static Logger logger = Logger.getLogger(StockHibernate.class);

	private EntityManagerFactory entityManagerFactory;

	private EntityManager entityManager;

	@Override
	public void initialiser() throws BibliothequeException {
		logger.info("initialiser()");
		entityManagerFactory = Persistence.createEntityManagerFactory("BIBLIO");
		entityManager = entityManagerFactory.createEntityManager();
	}

	@Override
	public List<Article> consulterInventaire() throws BibliothequeException {
		logger.info("consulterInventaire()");
		entityManager.getTransaction().begin();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Article> crit = cb.createQuery(Article.class);
		Root<Article> rootEntry = crit.from(Article.class);
		crit.select(rootEntry);
		TypedQuery<Article> query = entityManager.createQuery(crit);
		entityManager.getTransaction().commit();
		return query.getResultList();
	}

	@Override
	public Article consulterArticle(Long reference, ActionEnum actionEnum) throws BibliothequeException {
		logger.info("consulterArticle()");
		entityManager.getTransaction().begin();
		Article article = entityManager.find(Article.class, reference);
		if (article == null && !actionEnum.equals(ActionEnum.CREER)) {
			entityManager.getTransaction().rollback();
			throw new BibliothequeException("Article id=[" + reference + "] introuvable");
		}
		entityManager.getTransaction().commit();
		return article;
	}

	@Override
	public List<Article> consulterArticlesParTitre(String titre) {
		logger.info("consulterArticlesParTitre()");
		entityManager.getTransaction().begin();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Article> crit = cb.createQuery(Article.class);
		Root<Article> rootEntry = crit.from(Article.class);
		crit.select(rootEntry);
		crit.where(cb.equal(rootEntry.get("intitule"), titre));
		TypedQuery<Article> query = entityManager.createQuery(crit);
		entityManager.getTransaction().commit();
		return query.getResultList();
	}

	@Override
	public void ajouterStock(Article article, ActionEnum actionEnum) throws BibliothequeException {
		logger.info("ajouterStock()");
		if (article != null) {
			
			switch (actionEnum) {
				case CREER: {
					if (consulterArticle(article.getReference(), actionEnum) != null) {
						throw new BibliothequeException(article + " existe deja !");
					}
					entityManager.getTransaction().begin();
					entityManager.persist(article);
					entityManager.getTransaction().commit();
					break;
				}
				case MODIFIER: {
					Article articleDB = consulterArticle(article.getReference(), actionEnum);
					if (articleDB == null) {
						throw new BibliothequeException(article + " n'existe pas !");
					}
					entityManager.getTransaction().begin();
					articleDB.setIntitule(article.getIntitule());
					entityManager.merge(articleDB);
					entityManager.getTransaction().commit();
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
	public List<Article> consulterEmprunts(Long idPersonne) throws BibliothequeException {
		logger.info("consulterEmprunts()");
		List<Article> emprunts = new ArrayList<Article>();
		if (idPersonne != null) {
			// recup de la personne
			for (Article article : recupererPersonne(idPersonne, ActionEnum.CONSULTER).getEmprunts().values()) {
				emprunts.add(article);
			}
		}
		return emprunts;
	}

	@Override
	public void ajouterPersonne(Personne personne, ActionEnum actionEnum) throws BibliothequeException {
		logger.info("ajouterPersonne()");
		if (personne != null) {
			switch (actionEnum) {
				case CREER: {
					if (recupererPersonne(personne.getId(), actionEnum) != null) {
						throw new BibliothequeException("L'id " + personne.getId() + " existe deja !");
					}
					entityManager.getTransaction().begin();
					entityManager.persist(personne);
					entityManager.getTransaction().commit();
					break;
				}
				case MODIFIER: {
					// verif si la personne est en DB
					Personne personneDB = recupererPersonne(personne.getId(), actionEnum);
					personneDB.setNom(personne.getNom());
					personneDB.setPrenom(personne.getPrenom());
					entityManager.getTransaction().begin();
					entityManager.merge(personneDB);
					entityManager.getTransaction().commit();
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
	public Personne recupererPersonne(Long idPersonne, ActionEnum actionEnum) throws BibliothequeException {
		logger.info("recupererPersonne()");
		entityManager.getTransaction().begin();
		Personne personne = null;
		if (idPersonne != null) {
			personne = entityManager.find(Personne.class, idPersonne);
		}
		if (personne == null && !actionEnum.equals(ActionEnum.CREER)) {
			entityManager.getTransaction().rollback();
			throw new BibliothequeException("Personne introuvable avec l'id " + idPersonne);
		}
		entityManager.getTransaction().commit();
		return personne;
	}

	@Override
	public List<Personne> recupererPersonnes() throws BibliothequeException {
		logger.info("recupererPersonnes()");
		entityManager.getTransaction().begin();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Personne> crit = cb.createQuery(Personne.class);
		Root<Personne> rootEntry = crit.from(Personne.class);
		crit.select(rootEntry);
		TypedQuery<Personne> query = entityManager.createQuery(crit);
		entityManager.getTransaction().commit();
		return query.getResultList();
	}

	@Override
	public void emprunter(Long refArticle, Long idPersonne, int empruntTailleMax) throws BibliothequeException {
		logger.info("emprunter()");
		// emprunt possible ?
		if (consulterEmprunts(idPersonne).size() >= empruntTailleMax) {
			throw new BibliothequeException("Emprunt impossible : Vous avez atteint le nombre max d'emprunts");
		}
		Article article = entityManager.find(Article.class, refArticle);
		Personne adherent = entityManager.find(Personne.class, idPersonne);
		if (adherent.getEmprunts().get(refArticle) != null) {
			throw new BibliothequeException("Emprunt impossible : Livre id=[" + refArticle + "] deja emprunté");
		}
		// MAJ de la BDD
		entityManager.getTransaction().begin();
		adherent.getEmprunts().put(article.getReference(), article);
		article.setPersonne(adherent);
		article.setDisponible(false);
		entityManager.getTransaction().commit();
	}

	@Override
	public void restituer(Long refArticle, Long idPersonne) throws BibliothequeException {
		logger.info("restituer()");
		
		Personne personne = recupererPersonne(idPersonne, ActionEnum.CONSULTER);
		Article article = personne.getEmprunts().get(refArticle);
		try {
			consulterArticle(article.getReference(), ActionEnum.CONSULTER);
		}
		catch (BibliothequeException bibliothequeException) {
			throw new BibliothequeException("Restitution impossible : livre inexistant en base");
		}
		entityManager.getTransaction().begin();
		personne.getEmprunts().remove(article.getReference());
		article.setPersonne(null);
		article.setDisponible(true);
		entityManager.getTransaction().commit();
	}

	@Override
	public Personne recupererPersonneIdNomPrenom(Personne personne) throws BibliothequeException {
		logger.info("recupererPersonneIdNomPrenom()");
		entityManager.getTransaction().begin();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Personne> crit = cb.createQuery(Personne.class);
		Root<Personne> rootEntry = crit.from(Personne.class);
		crit.select(rootEntry);
		crit.where(
				 cb.and(
				        cb.equal(rootEntry.get("nom"), personne.getNom()),
				        cb.equal(rootEntry.get("prenom"), personne.getPrenom())
				 )
		);
		TypedQuery<Personne> query = entityManager.createQuery(crit);
		entityManager.getTransaction().commit();
		return query.getSingleResult();
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	
	
}

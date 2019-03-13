package metier.session;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;

import interceptor.BibliothequeInterceptor;
import metier.BibliothequeException;
import metier.constantes.ActionEnum;
import metier.entities.Article;
import metier.entities.Livre;
import metier.entities.Personne;
import metier.interfaces.DAOFactory;
import metier.interfaces.IPersistance;


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

	private IPersistance persistance;
	
	public BibliothequeEjbImpl() throws BibliothequeException {
		persistance = new DAOFactory().getDaoSystem();
	}

	private static Logger logger = Logger.getLogger(BibliothequeEjbImpl.class);

	private final int EMPRUNTS_TAILLE_MAX = 5;


	@PostConstruct
	public void initialisation() {
		// en presence d'un intercepteur, cette init est by-passée
		try {
			ajouterStock(new Livre(123L, "Livre 1", new Date()), ActionEnum.CREER);
			ajouterStock(new Livre(456L, "Livre 2", new Date()), ActionEnum.CREER);
			ajouterStock(new Livre(789L, "Livre 3", new Date()), ActionEnum.CREER);
			logger.info("Initialisation Ajout de 3 Livres depuis l'EJB");
		} catch (BibliothequeException bibliothequeException) {
			// TODO ajout dans les logs
		}
	}
	
	// appelé depuis l'intercepteur
	public void initialiser() throws BibliothequeException {
		persistance.initialiser();
	}

	@Override
	@Lock(LockType.READ)
	public List<Article> consulterInventaire() throws BibliothequeException {
		return persistance.consulterInventaire();	
	}

	@Override
	@Lock(LockType.READ)
	public Article consulterArticle(Long reference) throws BibliothequeException {
		return persistance.consulterArticle(reference);
	}

	@Override
	@Lock(LockType.READ)
	public List<Article> consulterArticlesParTitre(String titre) {
		return persistance.consulterArticlesParTitre(titre);
	}

	@Override
	@Lock(LockType.READ)
	public List<Article> consulterEmprunts(Long idPersonne) throws BibliothequeException {
		return persistance.consulterEmprunts(idPersonne);
	}

	@Override
	@Lock(LockType.WRITE)
	public void ajouterPersonne(Personne personne, ActionEnum actionEnum) throws BibliothequeException {
		persistance.ajouterPersonne(personne, actionEnum);
	}

	@Override
	@Lock(LockType.READ)
	public Personne recupererPersonne(Long idPersonne) throws BibliothequeException {
		return persistance.recupererPersonne(idPersonne);
	}

	@Override
	@Lock(LockType.READ)
	public List<Personne> recupererPersonnes() throws BibliothequeException {
		return persistance.recupererPersonnes();
	}

	@Override
	@Lock(LockType.WRITE)
	public void ajouterStock(Article article, ActionEnum actionEnum) throws BibliothequeException {
		persistance.ajouterStock(article, actionEnum);
	}

	@Override
	@Lock(LockType.WRITE)
	public void emprunter(Long refArticle, Long idPersonne) throws BibliothequeException {
		persistance.emprunter(refArticle, idPersonne, EMPRUNTS_TAILLE_MAX);
	}

	@Override
	@Lock(LockType.WRITE)
	public void restituer(Long refArticle, Long idPersonne) throws BibliothequeException {
		persistance.restituer(refArticle, idPersonne);
	}

	@Override
	public Personne recupererPersonneIdNomPrenom(Personne personne) throws BibliothequeException {
		return persistance.recupererPersonneIdNomPrenom(personne);
	}

}

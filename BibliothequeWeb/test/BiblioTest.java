
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import dao.StockHibernate;
import junit.framework.TestCase;
import metier.constantes.ActionEnum;
import metier.constantes.Property;
import metier.entities.Article;
import metier.entities.Livre;
import metier.entities.Personne;
import utils.BiblioUtil;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BiblioTest extends TestCase {

	private static Logger logger = Logger.getLogger(BiblioTest.class);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test1_EnumAction() {
		System.out.println("test1_EnumAction");
		System.out.println("----------------");
		try {
			// test sur action qui existe
			String action1 = "modifier";
			assertNotNull(BiblioUtil.recupActionEnum(action1));
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			fail(exception.getMessage());
		}

		try {
			// test sur action inexistante
			String action2 = "actionbidon";
			BiblioUtil.recupActionEnum(action2);
		} catch (Exception exception) {
			System.out.println("\n\n" + exception.getMessage());
		}

	}

	@Test
	public void test2_LoadPropertiesFile() {
		System.out.println("\ntest2_LoadPropertiesFile");
		System.out.println("------------------------");
		String nomFichier = "conf.properties";
		try {
			Properties prop = new Properties();
			prop.load(BiblioTest.class.getClassLoader().getResourceAsStream(nomFichier));
			System.out.println("Fichier " + nomFichier + " charg�");
			System.out.println("nom du champ properties :" + prop.get("champ.personne.id"));
		} catch (IOException exception) {
			System.out.println("Erreur lors du chargement du fichier " + nomFichier);
			fail(exception.getMessage());
		}
	}

	@Test
	public void test3_TestParcoursEnum() {
		System.out.println("\ntest3_TestParcoursEnum");
		System.out.println("----------------------");
		BiblioUtil.parcoursEnum(Property.class);

	}

	@Test
	public void test4_TestConfLog4J() {
		System.out.println("\ntest4_TestConfLog4J");
		System.out.println("----------------------");
		Logger rootLogger = Logger.getRootLogger();
		if (rootLogger == null)
			System.out.println("Root Logger non configur� !");
		else {
			System.out.println("Root Logger configur� !");
			Enumeration<?> appenders = rootLogger.getAllAppenders();
			assertTrue(appenders.hasMoreElements());
			while (appenders.hasMoreElements()) {
				String appenderName = ((Appender) appenders.nextElement()).getName();
				System.out.println("Nom de l'appender : " + appenderName);
			}

		}

	}

	@Test
	public void test5_JdbcConnection() {
		System.out.println("\ntest5_JdbcConnection");
		System.out.println("----------------------");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bibliotheque", "root", "admin");
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("show tables from catalogue");
			assertTrue("connection a la base OK via DriverManager", true);
			System.out.println("Connection � la DB OK");
		} catch (Exception exception) {
			fail("connection a la base KO via DriverManager");
		}
	}

	
	@Test
	public void test6_HibernateConf() {
		System.out.println("\ntest6_HibernateConf");
		System.out.println("----------------------");

		try {
			Map<String, String> props = new HashMap<String, String>();
			// permet un reset du schema et des donnees de la database
			props.put("hibernate.hbm2ddl.auto", "update");
			EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BIBLIO", props);
			EntityManager entityManager = entityManagerFactory.createEntityManager();

			// ajout personne en base
		/*	entityManager.getTransaction().begin();
			Personne personne = new Personne(1L, "Jules", "Vernes");
			entityManager.persist(personne);
			entityManager.getTransaction().commit();

			// ajout d'articles en base
			entityManager.getTransaction().begin();
			entityManager.persist(new Livre(1l, "Livre 1", new Date()));
			entityManager.persist(new Livre(2l, "Livre 2", new Date()));
			entityManager.persist(new Livre(3l, "Livre 3", new Date()));
			entityManager.getTransaction().commit();*/

			// ajout liaison persoonne - articles
			entityManager.getTransaction().begin();
			Personne personne = entityManager.find(Personne.class, 1L);
			Article articleDB = entityManager.find(Article.class, 1L);
			personne.getEmprunts().put(articleDB.getReference(), articleDB);
			
			/*articleDB = entityManager.find(Article.class, 2L);
			personne.getEmprunts().put(articleDB.getReference(), articleDB);
			articleDB = entityManager.find(Article.class, 3L);
			personne.getEmprunts().put(articleDB.getReference(), articleDB);*/
			
			// ajout de la foreign key
			for (Article article : personne.getEmprunts().values()) {
			//	article.setPersonne(personne);
			}

			entityManager.getTransaction().commit();

			// ----------------------------------------

			// MAJ infos
			entityManager.getTransaction().begin();
			Personne personneDB = entityManager.find(Personne.class, 1L);
			personneDB.setNom("Mandela");
			personneDB.setPrenom("Nelson");

			Article livre2 = entityManager.find(Article.class, 2L);
			livre2.setIntitule("Nouveau titre");

			entityManager.merge(personneDB);
			entityManager.merge(livre2);

			entityManager.getTransaction().commit();

			// suppr du lien : on libere la foreign key
			entityManager.getTransaction().begin();
			personneDB = entityManager.find(Personne.class, 1L);

			entityManager.find(Article.class, 2L).setPersonne(null);
			// personneDB.getEmprunts().remove(2L);
			entityManager.getTransaction().commit();

			entityManager.getTransaction().begin();
			Personne personne2 = new Personne(2L, "De Amorin", "Humberto");
			entityManager.persist(personne2);
			personne2.getEmprunts().put(livre2.getReference(), livre2);
			livre2.setPersonne(personne2);

			entityManager.getTransaction().commit();

			personneDB = entityManager.find(Personne.class, 1L);
			personne2 = entityManager.find(Personne.class, 2L);

			entityManager.close();
			entityManagerFactory.close();
		}

		catch (Throwable exception) {
			fail("connection a la base KO via EntityManager");
		}
	}
	
	@Ignore
	@Test
	public void test7_StockHibernate() {
		System.out.println("\test7_StockHibernate");
		System.out.println("----------------------");

		try {
			// permet un reset du schema et des donnees de la database
			EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BIBLIO");
			EntityManager entityManager = entityManagerFactory.createEntityManager();

			StockHibernate stockHibernate = new StockHibernate();
			stockHibernate.setEntityManager(entityManager);
			stockHibernate.setEntityManagerFactory(entityManagerFactory);
			Personne personne = stockHibernate.recupererPersonne(1L, ActionEnum.CONSULTER);
		}	
		catch (Throwable exception) {
			fail("connection a la base KO via EntityManager");
		}
	}
}

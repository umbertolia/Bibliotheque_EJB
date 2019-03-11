

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import junit.framework.TestCase;
import metier.constantes.Property;
import metier.session.BibliothequeEjbImpl;
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
			System.out.println("Fichier " + nomFichier + " chargé");
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
			System.out.println("Root Logger non configuré !");
		else {
			System.out.println("Root Logger configuré !");
			Enumeration<?> appenders = rootLogger.getAllAppenders();
			assertTrue(appenders.hasMoreElements());
			while (appenders.hasMoreElements()) {
				String appenderName = ((Appender) appenders.nextElement()).getName();
				System.out.println("Nom de l'appender : "+appenderName);
			}

		}

	}
	

}

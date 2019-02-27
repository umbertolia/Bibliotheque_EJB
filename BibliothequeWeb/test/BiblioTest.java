
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import controller.BiblioServlet;
import junit.framework.TestCase;
import metier.constantes.Property;
import utils.BiblioUtil;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BiblioTest extends TestCase {

	@Spy
	private BiblioServlet biblioServlet;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@Mock
	private ServletConfig servletConfig;

	@Mock
	private ServletOutputStream outputStream;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
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
	public void test4_TestServlet() {
		System.out.println("\test4_TestServlet");
		System.out.println("----------------------");
		try {
			/*
			 * when(biblioServlet.getServletConfig()).thenReturn(servletConfig);
			 * when(response.getOutputStream()).thenReturn(outputStream);
			 * when(request.getParameter("user")).thenReturn("admin");
			 * when(request.getParameter("password")).thenReturn("pwd"); //
			 * StringWriter sw = new StringWriter(); PrintWriter pw = new
			 * PrintWriter(sw); when(response.getWriter()).thenReturn(pw);
			 * 
			 * biblioServlet = new BiblioServlet(); biblioServlet.doPost(request,
			 * response); String result = sw.getBuffer().toString().trim();
			 * assertTrue(sw.toString().contains("adminfezs"));
			 * assertEquals(result, new String("Full Name: Vinod Kashyap"));
			 */

			when(biblioServlet.getServletConfig()).thenReturn(servletConfig);
			when(response.getOutputStream()).thenReturn(outputStream);
			biblioServlet.doPost(request, response);
			verify(outputStream).println("Hello World!");
		} catch (ServletException servletException) {
			System.out.println("Pb avec le doGet() de test4_TestServlet");
		} catch (IOException ioException) {
			System.out.println("Pb avec test4_TestServlet");
		} catch (Exception exception) {
			System.out.println("Pb avec test4_TestServlet");
		}
	}

	

}

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import metier.entities.Article;
import metier.entities.Livre;
import metier.entities.Personne;
import metier.session.IBibliothequeRemote;


public class Runner {

	@EJB(name = "Biblio")
	private static IBibliothequeRemote beanBiblio;

	public static void main(String[] args) throws IOException {

		Properties prop = new Properties();

		try {

			InputStream inputStream = Runner.class.getClassLoader().getResourceAsStream("config.properties");

			prop.load(inputStream);

			IBibliothequeRemote stub = lookupRemoteBibliotheque();
			List<Article> articles = stub.consulterInventaire();
			System.out.println("Inventaire de la bibliotheque :");
			for (Article article : articles) {
				System.out.println(article);
			}

			// ajout livre
			stub.ajouterStock(new Livre(1451L, "Le Hobbit", new Date()));
			//
			Personne abonne = stub.recupererPersonne(1L);

			try {
				stub.emprunter(12L, abonne.getId());
				stub.emprunter(3L, abonne.getId());
				stub.emprunter(9L, abonne.getId());
				System.out.println("Articles empruntés par la personne :" + abonne);
				for (Article article : stub.consulterEmprunts(abonne.getId())) {
					System.out.println(article);
				}
			} catch (Exception exception) {

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Pb avec les services de L'EJB : " + exception.getMessage());
			// si l'ejb est deployé dans le ear alors appelle via l'annotation @EJB
			try {
				Properties jndiProps = new Properties();
				jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
				jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
				jndiProps.put(Context.PROVIDER_URL,"remote://localhost:4447");
				jndiProps.put(Context.SECURITY_PRINCIPAL, "admin");
				jndiProps.put(Context.SECURITY_CREDENTIALS, "admin");
				// create the context
				Context ctx = new InitialContext(jndiProps);
				IBibliothequeRemote remote = (IBibliothequeRemote) ctx.lookup("java:comp/env/Biblio");

			} catch (NamingException e) {
				e.printStackTrace();
			}

		}

	}

	public static IBibliothequeRemote lookupRemoteBibliotheque() throws NamingException {
		final String appName = "";
		String moduleEjbName = "BibliothequeEJB";
		final String distinctName = "Biblio";
		String viewClassName = IBibliothequeRemote.class.getName();
		final Context context = new InitialContext();

		String ejbRemoteJNDIName = "ejb:" + appName + "/" + moduleEjbName + "/" + distinctName + "!" + viewClassName;
		System.out.println(ejbRemoteJNDIName);

		return (IBibliothequeRemote) context.lookup(ejbRemoteJNDIName);
	}

}

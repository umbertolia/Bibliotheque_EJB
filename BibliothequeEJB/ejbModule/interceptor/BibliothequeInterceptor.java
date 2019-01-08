package interceptor;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.jboss.logging.Logger;

import metier.constantes.ActionEnum;
import metier.entities.Livre;
import metier.entities.Personne;
import metier.session.IBibliothequeLocal;


public class BibliothequeInterceptor {
	private Logger log = Logger.getLogger(BibliothequeInterceptor.class);

	@PostConstruct
	public void initialisation(InvocationContext invocationContext) {
		IBibliothequeLocal metier = (IBibliothequeLocal) invocationContext.getTarget();
		metier.ajouterStock(new Livre(1L, "Livre 111", new Date()));
		metier.ajouterStock(new Livre(2L, "Livre 222", new Date()));
		metier.ajouterStock(new Livre(3L, "Livre 333", new Date()));
		metier.ajouterStock(new Livre(4L, "Livre 444", new Date()));
		metier.ajouterStock(new Livre(5L, "Livre 555", new Date()));
		metier.ajouterStock(new Livre(6L, "Livre 666", new Date()));
		metier.ajouterStock(new Livre(7L, "Livre 777", new Date()));
		metier.ajouterStock(new Livre(8L, "Livre 888", new Date()));
		metier.ajouterStock(new Livre(9L, "Livre 999", new Date()));
		metier.ajouterStock(new Livre(10L, "Livre 1010", new Date()));
		metier.ajouterStock(new Livre(11L, "Livre 1111", new Date()));
		metier.ajouterStock(new Livre(12L, "Livre 1212", new Date()));
		metier.ajouterStock(new Livre(13L, "Livre 1313", new Date()));
		metier.ajouterStock(new Livre(14L, "Livre 1414", new Date()));
		metier.ajouterStock(new Livre(15L, "Livre 1515", new Date()));
		log.info("Initialisation Ajout d'articles depuis l'incercepteur");
		
		metier.ajouterPersonne(new Personne(1l, "Jules", "Vernes"), ActionEnum.CREER);
	}

	@PreDestroy
	public void destruction() {
		// appelé par exemple lors de l'arret/redemarrage du serveur 
		System.out.println("Destruction de l'instance de l'EJB");
	}


	@AroundInvoke
	public Object test(InvocationContext invocationContext) throws Exception {
		long t1 = System.currentTimeMillis();
		long t2;
		try {
			Object o = invocationContext.proceed();
			return o;
		} finally {
			t2 = System.currentTimeMillis();
			String methodName = invocationContext.getMethod().getName();
			Object[] parameters = invocationContext.getParameters();
			String params = "";
			for (Object p : parameters)
				params += p;
			log.info(new Date() + " Appel de la méthode " + methodName + " Params " + params + " Durée :" + (t2 - t1));
		}

	}

}
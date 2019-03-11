package interceptor;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.jboss.logging.Logger;

import metier.BibliothequeException;
import metier.session.IBibliothequeLocal;


/**
 * @author Administrator
 * Auteur HDN
 * Crée le Mar 4, 2019
 *
 * Cette classe permet de ...

 */
public class BibliothequeInterceptor {
	private Logger logger = Logger.getLogger(BibliothequeInterceptor.class);

	@PostConstruct
	public void initialisation(InvocationContext invocationContext) throws BibliothequeException {
		IBibliothequeLocal metier = (IBibliothequeLocal) invocationContext.getTarget();
		metier.initialiser();
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
			logger.info(new Date() + " Appel de la méthode " + methodName + " Params " + params + " Durée :" + (t2 - t1));
		}

	}

}
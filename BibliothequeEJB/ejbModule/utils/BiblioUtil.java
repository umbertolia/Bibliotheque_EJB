package utils;

import java.io.IOException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import metier.BibliothequeException;
import metier.PropertytEnumAvecFils;
import metier.constantes.ActionEnum;
import metier.constantes.DaoEnum;


public class BiblioUtil {

	private static Logger logger = Logger.getLogger(BiblioUtil.class);
	
	private static Properties props = null;

	private static final String CONTEXT_PARAM_PROPERTY_FILENAME = "propertiesFileName";
	
	private static final String PROPERTY_FILENAME = "conf.properties";
	
	
	public static DaoEnum getDaoEnum() {
		
		DaoEnum daoEnum = null;
		Properties prop = new Properties();
		try {
			prop.load(BiblioUtil.class.getClassLoader().getResourceAsStream("conf.properties"));
			String typeDaoFromProps = (String) prop.get("databaseType");
			for (DaoEnum enumer : DaoEnum.values()) {
				if (enumer.getDaoType().equalsIgnoreCase(typeDaoFromProps)) {
					daoEnum = enumer;
				}
			}
		} catch (IOException e) {
			logger.debug("Impossible de lire le fichier "+PROPERTY_FILENAME);
			logger.debug("Mode DEV -> Le dao sera basé sur les maps");
		}
		
		return daoEnum;
	}

	/**
	 * @param nomAction
	 * @return
	 * @throws Exception
	 */
	public static ActionEnum recupActionEnum(String nomAction) throws BibliothequeException {

		ActionEnum actionEnum = null;
		if (!isEmptyOrNull(nomAction)) {
			for (ActionEnum action : ActionEnum.values()) {
				if (action.getNomAction().equalsIgnoreCase(nomAction)) {
					actionEnum = action;
				}
			}
		}
		if (actionEnum == null) {
			throw new BibliothequeException("Action " + nomAction + " non identifiée");
		}
		return actionEnum;
	}

	/**
	 * @param chaine
	 * @return
	 */
	public static boolean isEmptyOrNull(String chaine) {
		boolean empty = true;
		if (chaine != null && !chaine.isEmpty()) {
			empty = false;
		}
		return empty;
	}

	public static void readPropertiesFile(Class<?> clazz) throws BibliothequeException {
		try {
			Context ctx = new InitialContext();
			Context env = (Context) ctx.lookup("java:comp/env");
			final String fileName = (String) env.lookup(CONTEXT_PARAM_PROPERTY_FILENAME);
			try {
				props = new Properties();
				props.load(clazz.getClassLoader().getResourceAsStream(fileName));
			}
			catch (IOException ioException) {
				throw new BibliothequeException("Impossible de charger le fichier : " + fileName);
			}
		} catch (NamingException namingException) {
			throw new BibliothequeException("Pb d'acces au contexte");
		}
	}

	public static Object getValue(Class<?> clazz, String key) throws BibliothequeException {
		if (props == null) {
			readPropertiesFile(clazz);
		}
		return props.get(key);
	}

	public static void parcoursEnum(Class<?> classe) {
		Object[] objets = classe.getEnumConstants();
		if (objets != null) {
			for (Object object : objets) {
				if (object instanceof PropertytEnumAvecFils) {
					// enums de tête
					parcoursEnum((PropertytEnumAvecFils) object, classe.getSimpleName());
				}
			}
		}
	}

	public static void parcoursEnum(PropertytEnumAvecFils noeud, String cheminOrigine) {
		System.out.println(cheminOrigine + "." + noeud);
		if (noeud.getEnfants() != null) {
			for (Enum<?> enfant : noeud.getEnfants()) {
				parcoursEnum((PropertytEnumAvecFils) enfant, cheminOrigine + "." + noeud);
			}
		}
	}
	
}

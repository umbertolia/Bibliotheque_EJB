package utils;

import metier.BibliothequeException;
import metier.constantes.ActionEnum;

public class BiblioUtil {
	
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
			throw new BibliothequeException("Action "+nomAction+ " non identifiée");
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
}

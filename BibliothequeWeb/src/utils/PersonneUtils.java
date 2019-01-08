package utils;

import javax.servlet.http.HttpServletRequest;

import org.picketbox.util.StringUtil;

import metier.BibliothequeException;
import metier.entities.Personne;
import metier.session.IBibliothequeLocal;

public class PersonneUtils {
	
	public static Personne loguerPersonne(HttpServletRequest request, IBibliothequeLocal metier) throws BibliothequeException {
		Personne personne = null;
		// recherche peresonne
		if (!StringUtil.isNullOrEmpty(request.getParameter("id"))) {
			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			Long id = Long.parseLong(request.getParameter("id"));
			if (!StringUtil.isNullOrEmpty(nom) && !StringUtil.isNullOrEmpty(prenom)) {
				personne = metier.recupererPersonneIdNomPrenom(creerPersonne(request));
			}
			else {
				personne = metier.recupererPersonne(id);
			}
		}
		return personne;
	}
	
	/**
	 * @param request
	 * @return
	 */
	public static Personne creerPersonne(HttpServletRequest request) {
		Personne personne = null;
		try {	
			Long id = Long.parseLong(request.getParameter("id"));
			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			personne = new Personne(id, nom, prenom);
		}
		catch (NumberFormatException numberFormatException) {
			// etat impossible grace aux controles de surface
			throw new RuntimeException("Impossible de créer une personne depuis la requête");
		}
		return personne;
	}
	
	
	/**
	 * @param request
	 * @param personne
	 */
	public static void enregistrerPersonneInRequest(HttpServletRequest request, Personne personne) {
		if (personne != null) {
			request.setAttribute("id", personne.getId());
			request.setAttribute("nom", personne.getNom());
			request.setAttribute("prenom", personne.getPrenom());
		}
	}
	
	

}

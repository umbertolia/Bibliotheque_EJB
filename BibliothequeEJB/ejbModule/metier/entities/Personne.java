/**
 * 
 */
package metier.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * Auteur HDN
 * Crée le Dec 26, 2018
 *
 * Cette classe permet de ...

 */
public class Personne implements Serializable {
	
	private static final long serialVersionUID = -2568301396379877095L;

	private Long id;
	
	private String nom;
	
	private String prenom;
	
	private Map<Long, Article> emprunts = new HashMap<Long, Article>();
	
	/**
	 * 
	 */
	public Personne() {
		super();
	}



	/**
	 * @param id
	 * @param nom
	 * @param prenom
	 */
	public Personne(Long id, String nom, String prenom) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getNom() {
		return nom;
	}



	public void setNom(String nom) {
		this.nom = nom;
	}



	public String getPrenom() {
		return prenom;
	}



	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	

	public Map<Long, Article> getEmprunts() {
		return emprunts;
	}
	


	public void setEmprunts(Map<Long, Article> emprunts) {
		this.emprunts = emprunts;
	}



	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
		sb.append(" ");
		sb.append(prenom);
		sb.append(" ");
		sb.append(nom);
		sb.append(" [id=");
		sb.append(id);
		sb.append(" ]");
		return sb.toString();
	}

}

/**
 * 
 */
package metier.entities;

import java.io.Serializable;

/**
 * @author Administrator
 * Auteur HDN
 * Crée le Dec 26, 2018
 *
 * Cette classe permet de ...

 */
public abstract class Article implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1637926835755685531L;

	private Long reference;
	
	private String intitule;
	
	private boolean disponible;

	
	
	public Article() {
		super();
	}


	public Article(Long reference, String intitule) {
		super();
		this.reference = reference;
		this.intitule = intitule;
		this.disponible = true;
	}


	public Long getReference() {
		return reference;
	}


	public void setReference(Long reference) {
		this.reference = reference;
	}


	public String getIntitule() {
		return intitule;
	}


	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}


	public boolean isDisponible() {
		return disponible;
	}


	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
	
	public String getDispo() {
		if (disponible)
			return "oui";
		return "non";
	}

	
	public String toString() {
		return this.getClass().getSimpleName() + " " +intitule+ " [ref." + reference + "] " + " dispo : " + getDispo();
	}
	

}

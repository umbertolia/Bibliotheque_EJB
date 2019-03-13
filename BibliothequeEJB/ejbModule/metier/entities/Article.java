/**
 * 
 */
package metier.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Administrator
 * Auteur HDN
 * Crée le Dec 26, 2018
 *
 * Cette classe permet de ...

 */

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE",discriminatorType=DiscriminatorType.STRING,length=3)
@Table(name="ARTICLE")
public abstract class Article implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1637926835755685531L;

	@Id
	@Column(name = "ID_ARTICLE")
	private Long reference;
	
	@NotEmpty
	private String intitule;
	
	private boolean disponible;

	@ManyToOne
	private Personne adherent;
	
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

	public Personne getPersonne() {
		return adherent;
	}

	public void setPersonne(Personne personne) {
		this.adherent = personne;
	}


	
	public String toString() {
		return this.getClass().getSimpleName() + " " +intitule+ " [ref." + reference + "] " + " dispo : " + getDispo();
	}
	
	

}

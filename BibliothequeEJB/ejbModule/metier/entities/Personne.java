/**
 * 
 */
package metier.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
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
@Table(name="PERSONNE")
public class Personne implements Serializable {
	
	private static final long serialVersionUID = -2568301396379877095L;

	@Id
	@Column(name="ID_PERSON")
	private Long id;
	
	@NotEmpty
	private String nom;
	
	@NotEmpty
	private String prenom;
	
	@OneToMany(mappedBy="adherent",fetch=FetchType.EAGER)
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Personne other = (Personne) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (prenom == null) {
			if (other.prenom != null)
				return false;
		} else if (!prenom.equals(other.prenom))
			return false;
		return true;
	}

	
	
}


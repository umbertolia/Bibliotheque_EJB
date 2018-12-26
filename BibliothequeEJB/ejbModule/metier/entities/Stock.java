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
public class Stock implements Serializable {

	private static final long serialVersionUID = 3481178017615741392L;
	
	private Map<Long, Article> inventaire = new HashMap<Long, Article>();

	/**
	 * 
	 */
	public Stock() {
		super();
	}


	/**
	 * @param inventaire
	 */
	public Stock(Map<Long, Article> inventaire) {
		super();
		this.inventaire = inventaire;
	}




	public Map<Long, Article> getInventaire() {
		return inventaire;
	}




	public void setInventaire(Map<Long, Article> inventaire) {
		this.inventaire = inventaire;
	}




	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
		if (this.inventaire == null || (this.inventaire != null && this.inventaire.isEmpty())) {
			sb.append("Aucun article en stock");
		}
		else {
			sb.append(inventaire);
		}
		return sb.toString();
	}
	
	
	
	

}

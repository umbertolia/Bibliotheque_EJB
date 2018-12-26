package metier.entities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * @author Administrator Auteur HDN Cr�e le Dec 25, 2018
 *
 *         Cette classe permet de ...
 * 
 */
public class Livre extends Article {

	private static final long serialVersionUID = 742676347206880856L;
	
	private Date datePublication;

	public Livre() {
		super();
	}

	public Livre(Long reference, String intitule, Date datePublication) {
		super(reference, intitule);
		this.datePublication = datePublication;
	}


	public String getDatePublicationParsee() {
		if (datePublication != null) {
			
			SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
			GregorianCalendar calendar = new GregorianCalendar();
		    calendar.setTime(datePublication);
			fmt.setCalendar(calendar);    
			calendar.roll(Calendar.MONTH, -1); //or add
			String dateFormatted = fmt.format(calendar.getTime());
			return dateFormatted;
		}
		return "N/A";
	}

	public String toString() {
		return super.toString() + " Publi� le "+ getDatePublicationParsee();

	}

	
}
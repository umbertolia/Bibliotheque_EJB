package metier.constantes;

import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.Set;

import metier.PropertytEnumAvecFils;

public enum Property implements PropertytEnumAvecFils {
	
	champ, exception;
	
	Set<Enum<?>> enfants;
	
	public enum MiddleProp implements PropertytEnumAvecFils {
		personne, article, emprunt, restitution;
		
		Set<Enum<?>> enfants;
		
		public enum LastProp implements PropertytEnumAvecFils {
			id, nom, prenom, base, doublon, inconnu, inconnufull, impossible;
			
			@Override
			public Set<Enum<?>> getEnfants() {
				return null;
			}
		}

		static {
			personne.enfants = new LinkedHashSet<Enum<?>>();
			personne.enfants.addAll(EnumSet.allOf(LastProp.class));
			emprunt.enfants = new LinkedHashSet<Enum<?>>();
			emprunt.enfants.addAll(EnumSet.allOf(LastProp.class));
		}
		
		@Override
		public Set<Enum<?>> getEnfants() {
			return enfants != null ? Collections.unmodifiableSet(enfants) : null;
		}	
	}
	

	static {
		exception.enfants = new LinkedHashSet<Enum<?>>();
		exception.enfants.addAll(EnumSet.allOf(MiddleProp.class));
	}


	@Override
	public Set<Enum<?>> getEnfants() {
		return enfants != null ? Collections.unmodifiableSet(enfants) : null;
	}
	
	
}



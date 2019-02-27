import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.Set;

import metier.PropertytEnumAvecFils;


enum Citrus implements PropertytEnumAvecFils {
	lemon, lime, orange;
	Set<Enum<?>> children;

	enum Orange implements PropertytEnumAvecFils {
		navel, valencia, blood;
		Set<Enum<?>> children;

		enum Navel implements PropertytEnumAvecFils {
			washinton, lateLane, caraCaraPink;
			public Set<Enum<?>> getEnfants() {
				return null;
			}
		}

		static {
			navel.children = new LinkedHashSet<Enum<?>>();
			navel.children.addAll(EnumSet.allOf(Navel.class));
		}

		enum Blood implements PropertytEnumAvecFils {
			moro, taroco;
			public Set<Enum<?>> getEnfants() {
				return null;
			}
		}

		static {
			blood.children = new LinkedHashSet<Enum<?>>();
			blood.children.addAll(EnumSet.allOf(Blood.class));
		}

		public Set<Enum<?>> getEnfants() {
			return children != null ? Collections.unmodifiableSet(children) : null;
		}
	}

	static {
		orange.children = new LinkedHashSet<Enum<?>>();
		orange.children.addAll(EnumSet.allOf(Orange.class));
		System.out.println(orange);
	}

	public Set<Enum<?>> getEnfants() {
		return children != null ? Collections.unmodifiableSet(children) : null;
	}
}
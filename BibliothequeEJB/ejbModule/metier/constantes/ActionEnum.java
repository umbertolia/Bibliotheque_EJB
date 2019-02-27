package metier.constantes;

public enum ActionEnum {
	CREER("ajouter"), MODIFIER("modifier"), LOGIN("login"), LOGOUT("logout"), CONSULTER("consulter"), INVENTAIRE("inventaire"), ABONNES("Adherents"), EMPRUNTER("emprunter"), RESTITUER("restituer");
	
	String nomAction;

	private ActionEnum(String nomAction) {
		this.nomAction = nomAction;
	}

	public String getNomAction() {
		return nomAction;
	}

	public void setNomAction(String nomAction) {
		this.nomAction = nomAction;
	}
	
	

}

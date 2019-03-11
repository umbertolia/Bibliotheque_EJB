package metier.constantes;

public enum DaoEnum {
	
	DEV("dev"), PROD("prod");
	
	private String daoType;

	private DaoEnum(String daoType) {
		this.daoType = daoType;
	}

	public String getDaoType() {
		return daoType;
	}

	public void setDaoType(String daoType) {
		this.daoType = daoType;
	}
	
	

}

package metier.interfaces;

import org.apache.log4j.Logger;

import dao.Stock;
import metier.constantes.DaoEnum;
import utils.BiblioUtil;

public class DAOFactory {
	
	private static Logger logger = Logger.getLogger(DAOFactory.class);

	
	public IPersistance getDaoSystem() {
		logger.info("-----------------------");
		DaoEnum daoEnum = BiblioUtil.getDaoEnum();
		
		if (daoEnum == null) {
		// InMemory DAO (maps)
			return new Stock();
		}
		switch (daoEnum) {
			case DEV : {
					// InMemory DAO (maps)
					logger.info("DAO utilisé : RAM (maps)");
					logger.info("-----------------------");
					return new Stock();
			}
			case PROD : {
				// TODO hibernate 
				logger.info("-----------------------");
				return null;
			}
			default : {
				return new Stock();
			}
		}
		
	}

}

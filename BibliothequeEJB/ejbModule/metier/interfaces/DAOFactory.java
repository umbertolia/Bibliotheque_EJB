package metier.interfaces;

import org.apache.log4j.Logger;

import dao.Stock;
import metier.BibliothequeException;
import metier.constantes.DaoEnum;
import utils.BiblioUtil;

public class DAOFactory {
	
	private static Logger logger = Logger.getLogger(DAOFactory.class);

	
	public IPersistance getDaoSystem() throws BibliothequeException {
		logger.info("-----------------------");
		DaoEnum daoEnum = BiblioUtil.getDaoEnum();
		
		if (daoEnum == null) {
			logger.fatal("Aucun DAO initialisť !!!");
			logger.info("DAO utilisť : RAM (maps)");
			throw new BibliothequeException("Aucun DAO initialisť !!!");
		}	
		switch (daoEnum) {
			case DEV : {
					// InMemory DAO (maps)
					logger.info("DAO utilisť : RAM (maps)");
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

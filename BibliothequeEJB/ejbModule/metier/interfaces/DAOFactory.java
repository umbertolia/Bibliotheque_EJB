package metier.interfaces;

import org.apache.log4j.Logger;

import dao.Stock;
import dao.StockHibernate;
import metier.BibliothequeException;
import metier.constantes.DaoEnum;
import utils.BiblioUtil;

public class DAOFactory {
	
	private static Logger logger = Logger.getLogger(DAOFactory.class);

	
	public IPersistance getDaoSystem() throws BibliothequeException {
		logger.info("-----------------------");
		DaoEnum daoEnum = BiblioUtil.getDaoEnum();
		
		if (daoEnum == null) {
			logger.fatal("Aucun DAO initialis� !!!");
			throw new BibliothequeException("Aucun DAO initialis� !!!");
		}	
		switch (daoEnum) {
			case DEV : {
					// InMemory DAO (maps)
					logger.info("DAO utilis� : RAM (maps)");
					logger.info("-----------------------");
					return new Stock();
			}
			case PROD : {
				//  hibernate 
				logger.info("DAO utilis� : Hibernate");
				logger.info("-----------------------");
				return new StockHibernate();
			}
			default : {
				return new Stock();
			}
		}
	}

}

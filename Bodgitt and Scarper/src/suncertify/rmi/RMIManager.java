package suncertify.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RMIManager {
	
	private static DBFactory dbFactory;
	private static Logger logger = 
			 Logger.getLogger("suncertify.rmi.RMIManager");
	
	public void startRegister(String dbLocation, int rmiPort) 
			throws RemoteException {
		
		dbFactory = new  DBFactoryImpl(dbLocation);
		String host = "localhost";
		logger.log(Level.INFO, "Port: " + rmiPort);
		logger.log(Level.INFO, "FileLocation: " + dbLocation);
		logger.log(Level.INFO, "Host: " + host);
		
		Registry register = 
				java.rmi.registry.LocateRegistry.createRegistry(rmiPort);
		String url = "BodgittScarper";
		logger.info("Starting server on: " + url);
		register.rebind(url, dbFactory);

		
	}

}

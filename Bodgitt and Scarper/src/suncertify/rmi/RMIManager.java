/* Project: Bodgitt and Scarper Version 2.3.3
 * @author: Patrick Garvey
 * Last Modified: 28th Oct 2013 
 * RMIManager.java
 */
package suncertify.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The Class RMIManager is used to start the registry and register the
 * <code>ContractorDBremoteImpl</code> for the RMI naming service.
 */
public class RMIManager {
	
	/** The DBfactory instance implementation. */
	private static DBFactory dbFactory;
	
	/** The logger instance. */
	private static Logger logger = 
			 Logger.getLogger("suncertify.rmi.RMIManager");
	
	/**
	 * Starts the register opening a connection to the database file and
	 * listening on a port for incoming connections. 
	 *
	 * @param dbLocation the database location location
	 * @param rmiPort the port to listen on
	 * @throws RemoteException the remote exception in case of connection errors
	 */
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

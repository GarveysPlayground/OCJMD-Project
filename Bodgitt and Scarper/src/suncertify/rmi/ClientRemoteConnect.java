/* Project: Bodgitt and Scarper Version 2.3.3
 * @author: Patrick Garvey
 * Last Modified: 28th Oct 2013 
 * ClientRemoteConnect.java
 */
package suncertify.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The Class <code>ClientRemoteConnect</code> is called when the application is
 * started in Network client mode and the end user wishes to connect to the
 * database through RMI. This class is considered a utility class as we only 
 * use it to have its static <code>getConnection</code>class called. 
 */
public class ClientRemoteConnect {
	
	 /** The logger instance. */
 	private static Logger logger = Logger.getLogger("suncertify.rmi");
	 
	 /**
 	 * The <code>getConnection</code> method is used to establish an RMI 
 	 * connection.
 	 *
 	 * @param host The hostname / IP-address of the host
 	 * @param port Port the registry listens on.
 	 * @return a connection instance
 	 * @throws RemoteException the remote exception indicating that a
 	 * 			connection issue has occurred
 	 */
 	public static ContractorDBRemote 
 					getConnection(final String host, final int port)
	            throws RemoteException {
		 
		
		 logger.info("Establishing Client Connection to host : " + host);
		 String url = "rmi://" + host + ":" + port + "/BodgittScarper";
		 logger.info("Client connecting to: " + url);
		 
		 try {
			DBFactory factory =  (DBFactory) Naming.lookup(url);
			return (ContractorDBRemote) factory.getClient();
			
		 } catch (MalformedURLException e) {
			System.err.println("Invalid URL:" + e.getMessage());
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new RemoteException("Problem with Room connection: ", e);
            
		 } catch (NotBoundException e) {
			System.err.println("Not bound Exception: " + e.getMessage());
            throw new RemoteException("Not bound Exception: ", e);
		}
	 }

}
	 


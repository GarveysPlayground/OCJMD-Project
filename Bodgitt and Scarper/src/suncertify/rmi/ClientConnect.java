package suncertify.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClientConnect {
	
	 private static Logger logger = Logger.getLogger("suncertify.rmi");
	 
	 public static ContractorDBRemote getConnection(String host, int port)
	            throws RemoteException {
		 
		
		 logger.info("Establishing Client Connection to host : " + host);
		 String url = "rmi://" + host + ":" + port + "/BodgittScarper";
		 logger.info("Client connecting to: " + url);
		 
		 try {
			DBFactory factory =  (DBFactory) Naming.lookup(url);
			return (ContractorDBRemote) factory.getClient();
			
		 }catch (MalformedURLException e) {
			System.err.println("Invalid URL:" + e.getMessage());
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new RemoteException("Problem with Room connection: ", e);
            
		 }catch (NotBoundException e) {
			System.err.println("Not bound Exception: " + e.getMessage());
            throw new RemoteException("Not bound Exception: ", e);
		}
	 }

}
	 


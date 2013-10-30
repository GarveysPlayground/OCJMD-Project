/* Project: Bodgitt and Scarper Version 2.3.3
 * @author: Patrick Garvey
 * Last Modified: 28th Oct 2013 
 * DBFactoryImpl.java
 */
package suncertify.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


/**
 * The Class DBFactoryImpl implements our Factory for our connecting client.
 */
public class DBFactoryImpl extends UnicastRemoteObject implements DBFactory {
	
	
	
	/**
     * A version number to support serialization and de-serialization.
     */
    private static final long serialVersionUID = 1L;
    
	
    /**
     * The location of the database.
     */
    private String dbLocation = null;
    
	
   /**
    * Instantiates a new database factory implementation indicating where
    * the database location is.
    *
    * @param dbLocation the path to the database file
    * @throws RemoteException the remote exception in case of connectivity 
    * 		issues
    */
   public DBFactoryImpl(String dbLocation) throws RemoteException {
	   this.dbLocation = dbLocation;	   
   }
    
   /* (non-Javadoc)
    * @see suncertify.rmi.DBFactory#getClient()
    */
   public ContractorDBRemote getClient() throws RemoteException {
	   return new ContractorDBremoteImpl(dbLocation);
   }
  	

}

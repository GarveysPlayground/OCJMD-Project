package suncertify.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DBFactoryImpl extends UnicastRemoteObject implements DBFactory{
	
	
	
	/**
     * A version number to support serialization and de-serialization.
     */
    private static final long serialVersionUID = 1L;
    
	
    /**
     * The physical location of the database.
     */
    private static String dbLocation = null;
    
	
   public DBFactoryImpl(String dbLocation) throws RemoteException{
	   this.dbLocation = dbLocation;	   
   }
    
   public ContractorDBRemote getClient() throws RemoteException{
	   return new ContractorDBremoteImpl(dbLocation);
   }
   	

}

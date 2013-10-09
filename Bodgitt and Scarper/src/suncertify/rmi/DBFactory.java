package suncertify.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface DBFactory  extends Remote{
	
	/**
     * Returns a reference to a remote instance of a class unique to the
     * connecting
     * **/
	 public contractorRemote getClient() throws RemoteException;

}

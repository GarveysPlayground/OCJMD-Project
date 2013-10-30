/* Project: Bodgitt and Scarper Version 2.3.3
 * @author: Patrick Garvey
 * Last Modified: 28th Oct 2013 
 * DBFactory.java
 */
package suncertify.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * A factory for creating DB objects. This is the interface used
 * for our Factory design pattern
 */
public interface DBFactory  extends Remote {
	
	/**
	 * Returns a reference to a remote instance of a class unique to the
	 * connecting client which will contain all methods that the client 
	 * can remotely call and implement.
	 * 
	 *
	 * @return the client
	 * @throws RemoteException the remote exception
	 */
	 public ContractorDBRemote getClient() throws RemoteException;

}

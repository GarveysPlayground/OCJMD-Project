/* Project: Bodgitt and Scarper Version 2.3.3
 * @author: Patrick Garvey
 * Last Modified: 28th Oct 2013 
 * ContractorDBremoteImpl.java
 */
package suncertify.rmi;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

import suncertify.db.DBMainRmiConnector;
import suncertify.db.DataRemote;
import suncertify.db.DuplicateKeyException;
import suncertify.db.RecordNotFoundException;


/**
 * The Class <code>ContractorDBremoteImpl</code> implements the remote 
 * interface used by network clients during an RMI connection.
 */
public class ContractorDBremoteImpl extends UnicastRemoteObject 
	implements ContractorDBRemote {

	  
	/** The Constant serialVersionUID used for serialization and 
	 * deserialization */
	private static final long serialVersionUID = 1L;
	
	/** The database interface for RMI connections. */
	private DBMainRmiConnector database = null;

	/**
	 * The <code>ContractorDBremoteImpl</code> constructor that passes the
	 * database location to a new instance of <code>DataRemote</code>.
	 *
	 * @param dbLocation the database location
	 * @throws RemoteException the remote exception in case of connection issues
	 * @see suncertify.db.DataRemote
	 */
	public ContractorDBremoteImpl(final String dbLocation) 
			throws RemoteException {
		
		try {
			database = new DataRemote(dbLocation);
		} catch (IOException e) {
			System.out.println("Issue connecting to DB");
			e.printStackTrace();
		}
	}
	
	/** Logger instance . */
    private Logger logger = Logger.getLogger("suncertify.rmi");


	/**
	 * This method calls on the read implementation to read a single
	 * specified record from the database.
	 * 
	 * @param recNo : The record number to be read
	 * @return returns a String [] of the read record
	 * @see suncertify.db.DBMainRmiConnector#read(int)
	 * @throws RemoteException the remote exception in case of connection issues
	 * @throws RecordNotFoundException if @param recNo is not found
	 */
	@Override
	public final String[] read(final int recNo) 
			throws RecordNotFoundException,	RemoteException {
		return database.read(recNo);
	}

	
	/**
	 * This method calls on the update(int, String []) implementation 
	 * to modify a record with new values.
	 * 
	 * @param recNo : The record number to be read
	 * @param data : String [] to update the record with
	 * @throws RecordNotFoundException if @param recNo is not found
	 * @throws RemoteException the remote exception in case of connection issues
	 * @see suncertify.db.DBMainRmiConnector#update(int, java.lang.String[])
	 */
	@Override
	public final void update(final int recNo, final String[] data)
			throws RecordNotFoundException, RemoteException {
		database.update(recNo, data);
		
	}

	/**
	 * This method calls on the delete(int) implementation 
	 * to delete a specified record.
	 * 
	 * @param recNo : The record number to be read
	 * @throws RecordNotFoundException if @param recNo is not found
	 * @throws RemoteException the remote exception in case of connection issues
	 * @see suncertify.db.DataAccess#delete(int)
	 * @see suncertify.db.DBMainRmiConnector#delete(int)
	 */
	@Override
	public final void delete(final int recNo) 
			throws RecordNotFoundException,	RemoteException {
		database.delete(recNo);
		
	}
	/**
	 * This method calls on the find(String []) implementation 
	 * to return record numbers matching specified criteria.	
	 * 
	 * @param criteria : String [] to update the record with
	 * @throws RecordNotFoundException if @param recNo is not found
	 * @throws RemoteException the remote exception in case of connection issues
	 * @return int [] or matching record numbers
	 * @see suncertify.db.DBMainRmiConnector#find(java.lang.String[])
	 */
	@Override
	public final int[] find(final String[] criteria) 
			throws RecordNotFoundException,	RemoteException {
		return database.find(criteria);
	}


	/**
	 * This method calls on the create(String []) implementation 
	 * to add a new record to the database.
	 * 
	 * @param data : String [] of new record values
	 * @throws DuplicateKeyException if new record already exists
	 * @throws RemoteException the remote exception in case of connection issues
	 * @return int of new record number
	 * @see suncertify.db.DBMainRmiConnector#create(java.lang.String[])
	 */
	@Override
	public final int create(final String[] data) 
			throws DuplicateKeyException, RemoteException {
		return database.create(data);
	}

	/**
	 * This method calls on the lock(int) implementation 
	 * to lock a specified record.
	 * 
	 * @param recNo : record number to be locked
	 * @throws RecordNotFoundException if new record does'nt exists
	 * @throws RemoteException the remote exception in case of connection issues
	 * @see suncertify.db.DBMainRmiConnector#lock(int)
	 */
	@Override
	public final void lock(final int recNo) 
			throws RecordNotFoundException, RemoteException {
		database.lock(recNo);
		
	}

	/**
	 * This method calls on the unlock(int) implementation 
	 * to lock a specified record.
	 * 
	 * @param recNo : record number to be locked
	 * @throws RecordNotFoundException if new record does'nt exists
	 * @throws RemoteException the remote exception in case of connection issues
	 * @see suncertify.db.DBMainRmiConnector#unlock(int)
	 */
	@Override
	public final void unlock(final int recNo) 
			throws RecordNotFoundException,	RemoteException {
		System.out.println("ContractorDBmain lock");
		database.unlock(recNo);
		
	}

	/**
	 * This method calls on the islocked(int) implementation 
	 * to check if a record is locked.
	 * 
	 * @param recNo : record number to be locked
	 * @throws RecordNotFoundException if new record does'nt exists
	 * @throws RemoteException the remote exception in case of connection issues
	 * @see suncertify.db.DBMainRmiConnector#isLocked(int)
	 * @return true if record is locked
	 */
	@Override
	public final boolean isLocked(final int recNo) 
			throws RecordNotFoundException,	RemoteException {
		return database.isLocked(recNo);
	}
}

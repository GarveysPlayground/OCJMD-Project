/* Project: Bodgitt and Scarper Version 2.3.3
 * @author: Patrick Garvey
 * Last Modified: 29th Oct 2013 
 * DataRemote.java
 */
package suncertify.db;


import java.io.IOException;
import java.rmi.RemoteException;



/**
 * The Class DataRemote implements DBMainRmiConnector. This class is
 * very similar to Data.java however implements @throws RemoteException
 * to cater for RMI connections.
 * 
 * This Class should be used by programmers when trying to access or
 * manipulate records in the database remotely. This is to help maintain the 
 * facade pattern used in our database by having methods in this class
 * call from other classes. By doing this we distance the end user from 
 * from the functional code and are left with with some simple to understand
 * APIs that future coders can implement. 
 * 
 * It also allows us to break the code into multiple classes that focus on a
 * single purpose i.e {@link #DataAccess()} for record manipulation and 
 * {@link #LockManager()} for record locking.
 */
public class DataRemote implements DBMainRmiConnector {

	/** The database. */
	private DBMain database = null;
	
	/** The lock instance. */
	private static LockManager lockInstance = LockManager.getInstance();
	
	/**
	 * Instantiates a new data remote.
	 * Constructor that takes the database location as a parameter
	 * and calls on the <code>Data</code> class constructor to open
	 * a connection to the database file.
	 *
	 * @param dbLocation : The location of the local database File
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public DataRemote(final String dbLocation) 
			throws IOException {
		database = new Data(dbLocation);
		
	}
	
	
	/**
	 * This method calls on the read implementation located at 
	 * {@link suncertify.db.DataAccess#read(int)}.
	 * 
	 * @param recNo : The record number to be read
	 * @return returns a String [] of the read record
	 * @throws RecordNotFoundException if @param recNo is not found
	 * @throws RemoteException if connection issues arise
	 * @see suncertify.db.Data#read(int)
	 * @see suncertify.db.DBMainRmiConnector#read(int)
	 */
	@Override
	public final String[] read(final int recNo) 
			throws RecordNotFoundException, RemoteException {
		return database.read(recNo);
	}

	/**
	 * This method calls on the update(int, String []) implementation 
	 * located at  {@link suncertify.db.DataAccess#update(int, String[])}.
	 * 
	 * @param recNo : The record number to be read
	 * @param data : String [] to update the record with
	 * @throws RecordNotFoundException if @param recNo is not found
	 * @throws RemoteException if connection issues arise
	 * @see suncertify.db.Data#update(int, String[])
	 * @see suncertify.db.DBMainRmiConnector#update(int, String[])
	 */
	@Override
	public final void update(final int recNo, final String[] data) 
			throws RecordNotFoundException, RemoteException {
		database.update(recNo, data);		
	}

	
	/**
	 * This method calls on the delete(int) implementation 
	 * located at  {@link suncertify.db.DataAccess#delete(int)}.
	 * 
	 * @param recNo : The record number to be read
	 * @throws RecordNotFoundException if @param recNo is not found
	 * @throws RemoteException if connection issues arise
	 * @see suncertify.db.Data#delete(int)
	 * @see suncertify.db.DBMainRmiConnector#delete(int)
	 */
	@Override
	public final void delete(final int recNo) 
			throws RecordNotFoundException, RemoteException {
		database.delete(recNo);
	}


	/**
	 * This method calls on the find(String []) implementation 
	 * located at  {@link suncertify.db.DataAccess#find(String[])}.
	 * 

	 * @param criteria : String [] to update the record with
	 * @throws RecordNotFoundException if @param recNo is not found
	 * @throws RemoteException if connection issues arise
	 * @see suncertify.db.Data#find(String[])
	 * @see suncertify.db.DBMainRmiConnector#find(String[])
	 * @return int [] or matching record numbers
	 */
	@Override
	public final int[] find(final String[] criteria) 
			throws RecordNotFoundException, RemoteException {
		return database.find(criteria);
	}


	/**
	 * This method calls on the create(String []) implementation 
	 * located at  {@link suncertify.db.DataAccess#create(String[])}.
	 * 

	 * @param data : String [] of new record values
	 * @throws DuplicateKeyException if new record already exists
	 * @throws RemoteException if connection issues arise
	 * @see suncertify.db.Data#create(String[])
	 * @see suncertify.db.DBMainRmiConnector#create(String[])
	 * @return int of new record number
	 */
	@Override
	public final int create(final String[] data) 
			throws DuplicateKeyException, RemoteException {
		int newRecord = 0;	
		newRecord = database.create(data);
		return newRecord;
	}


	/**
	 * This method calls on the lock(int) implementation 
	 * located at  {@link suncertify.db.LockManager#lock(int)}.
	 * 
	 * @param recNo : record number to be locked
	 * @throws RecordNotFoundException if new record does'nt exists
	 * @throws RemoteException if connection issues arise
	 * @see suncertify.db.LockManager#lock(int)
	 * @see suncertify.db.DBMainRmiConnector#lock(int)
	 */
	@Override
	public final void lock(final int recNo) 
			throws RecordNotFoundException, RemoteException {
		lockInstance.lock(recNo);
		
	}


	/**
	 * This method calls on the unlock(int) implementation 
	 * located at  {@link suncertify.db.LockManager#unlock(int)}.
	 * 
	 * @param recNo : record number to be unlocked
	 * @throws RecordNotFoundException if new record does'nt exists
	 * @throws RemoteException if connection issues arise
	 * @see suncertify.db.LockManager#unlock(int)
	 * @see suncertify.db.DBMainRmiConnector#unlock(int)
	 */
	@Override
	public final void unlock(final int recNo) 
			throws RecordNotFoundException, RemoteException {
		lockInstance.unlock(recNo);
	}


	/**
	 * This method calls on the isLocked(int) implementation 
	 * located at  {@link suncertify.db.LockManager#isLocked(int)}.
	 * 
	 * @param recNo : record number to be locked
	 * @throws RecordNotFoundException if new record does'nt exists
	 * @throws RemoteException if connection issues arise
	 * @see suncertify.db.LockManager#isLocked(int)
	 * @see suncertify.db.DBMainRmiConnector#isLocked(int)
	 * @return boolean : if the record is already locked return true
	 */
	@Override
	public final boolean isLocked(final int recNo) 
			throws RecordNotFoundException, RemoteException {
		return lockInstance.isLocked(recNo);
	}

}

/* Project: Bodgitt and Scarper Version 2.3.3
 * @author: Patrick Garvey
 * Last Modified: 29th Oct 2013 
 * DataRemote.java
 */
package suncertify.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;

// TODO: Auto-generated Javadoc
/**
 * The Class DataRemote.
 */
public class DataRemote implements DBMainRmiConnector {

	/** The database. */
	private DBMain database = null;
	
	/** The lock instance. */
	public static LockManager lockInstance = LockManager.getInstance();
	
	/**
	 * Instantiates a new data remote.
	 *
	 * @param dbLocation the db location
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public DataRemote(String dbLocation) 
			throws FileNotFoundException, IOException {
		database = new Data(dbLocation);
		
	}
	
	/* (non-Javadoc)
	 * @see suncertify.db.DBMainRmiConnector#read(int)
	 */
	@Override
	public String[] read(int recNo) 
			throws RecordNotFoundException, RemoteException {
		return database.read(recNo);
	}

	/* (non-Javadoc)
	 * @see suncertify.db.DBMainRmiConnector#update(int, java.lang.String[])
	 */
	@Override
	public void update(int recNo, String[] data) 
			throws RecordNotFoundException, RemoteException {
		database.update(recNo, data);		
	}

	/* (non-Javadoc)
	 * @see suncertify.db.DBMainRmiConnector#delete(int)
	 */
	@Override
	public void delete(int recNo) 
			throws RecordNotFoundException, RemoteException {
		database.delete(recNo);
	}

	/* (non-Javadoc)
	 * @see suncertify.db.DBMainRmiConnector#find(java.lang.String[])
	 */
	@Override
	public int[] find(String[] criteria) 
			throws RecordNotFoundException, RemoteException {
		return database.find(criteria);
	}

	/* (non-Javadoc)
	 * @see suncertify.db.DBMainRmiConnector#create(java.lang.String[])
	 */
	@Override
	public int create(String[] data) 
			throws DuplicateKeyException, RemoteException {
		int newRecord = 0;	
		newRecord = database.create(data);
		return newRecord;
	}

	/* (non-Javadoc)
	 * @see suncertify.db.DBMainRmiConnector#lock(int)
	 */
	@Override
	public void lock(int recNo) 
			throws RecordNotFoundException, RemoteException {
		lockInstance.lock(recNo);
		
	}

	/* (non-Javadoc)
	 * @see suncertify.db.DBMainRmiConnector#unlock(int)
	 */
	@Override
	public void unlock(int recNo) 
			throws RecordNotFoundException, RemoteException {
		lockInstance.unlock(recNo);
	}

	/* (non-Javadoc)
	 * @see suncertify.db.DBMainRmiConnector#isLocked(int)
	 */
	@Override
	public boolean isLocked(int recNo) 
			throws RecordNotFoundException, RemoteException {
		return lockInstance.isLocked(recNo);
	}

}

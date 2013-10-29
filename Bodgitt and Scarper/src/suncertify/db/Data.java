/* Project: Bodgitt and Scarper Version 2.3.3
 * Author: Patrick Garvey
 * Last Modified: 28th Oct 2013
 * Data.java
 */
package suncertify.db;


import java.io.IOException;


/**
 * The Class Data implements DBMain as dictated by instructions.html. 
 * This Class should be used by programmers when trying to access or
 * manipulate records in the database. This is to help maintain the 
 * facade pattern used in our database by having methods in this class
 * call from other classes. By doing this we distance the end user from 
 * from the functional code and are left with with some simple to understand
 * APIs that future coders can implement. 
 * 
 * It also allows us to break the code into multiple classes with a single
 * purpose i.e <code>DataAccess</code> for record manipulation and 
 * <code>LockManager</code> for record locking.
 */
public class Data implements DBMain {

	/** Gets the running instance of the singleton LockManager */
	private static LockManager lockManager = LockManager.getInstance();
	
	/**
	 * Constructor that takes the database location as a parameter
	 * and calls on the <code>DataAccess</code> class constructor to open
	 * a connection to the database file.
	 *
	 * @param dbLocation : The location of the local database File
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Data(final String dbLocation) throws IOException {
		new DataAccess(dbLocation);		
	}
	
	
	/**
	 * Empty Constructor.
	 */
	public Data() {
	}
	
	
	/* (non-Javadoc)
	 * @see suncertify.db.DBMain#read(int)
	 */
	@Override
	public String[] read(final int recNo) throws RecordNotFoundException {
			return DataAccess.read(recNo);
	}

	
	/* (non-Javadoc)
	 * @see suncertify.db.DBMain#update(int, java.lang.String[])
	 */
	@Override
	public void update(final int recNo, final String[] data) 
			throws RecordNotFoundException {
		DataAccess.update(recNo, data);		
	}

	
	/* (non-Javadoc)
	 * @see suncertify.db.DBMain#delete(int)
	 */
	@Override
	public void delete(final int recNo) throws RecordNotFoundException {
		DataAccess.delete(recNo);
	}

	
	/* (non-Javadoc)
	 * @see suncertify.db.DBMain#find(java.lang.String[])
	 */
	@Override
	public int[] find(final String[] criteria) throws RecordNotFoundException {
		return DataAccess.find(criteria);
	}

	
	/* (non-Javadoc)
	 * @see suncertify.db.DBMain#create(java.lang.String[])
	 */
	@Override
	public int create(final String[] data) throws DuplicateKeyException {
		int newRecord = 0;	
		try {
			newRecord = DataAccess.create(data);
			return newRecord;
			} catch (IOException e) {
				System.err.println("Issue encountered while creating file");
				e.printStackTrace();
			}
		return newRecord;


	}

	
	/* (non-Javadoc)
	 * @see suncertify.db.DBMain#lock(int)
	 */
	@Override
	public void lock(final int recNo) throws RecordNotFoundException {
		lockManager.lock(recNo);
	}

	
	/* (non-Javadoc)
	 * @see suncertify.db.DBMain#unlock(int)
	 */
	@Override
	public void unlock(final int recNo) throws RecordNotFoundException {
		lockManager.unlock(recNo);
	}

	
	/* (non-Javadoc)
	 * @see suncertify.db.DBMain#isLocked(int)
	 */
	@Override
	public boolean isLocked(final int recNo) throws RecordNotFoundException {
		return lockManager.isLocked(recNo);
	}
}

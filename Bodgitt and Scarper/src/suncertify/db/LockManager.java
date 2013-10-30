/* Project: Bodgitt and Scarper Version 2.3.3
 * Author: Patrick Garvey
 * Last Modified: 18th Oct 2013
 * LockManager.java
 */
package suncertify.db;


import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


/**
 * The Class LockManager handles the locking 
 * and unlocking of records through the use of a HashMap.
 * Records are locked to prevent multiple end users from 
 * writing to the same record at a single point in time.
 * This class functions as a singleton since at any
 * given time only one instance is needed
 */
public final class LockManager {
	
	/** The instance. */
	private static LockManager instance;
	
	/** The logger instance. */
	private static Logger logger = 
		Logger.getLogger("suncertify.db.LockManager");
	
	/** The reservations hashMap that will hold record of what is locked. */
	private static Map<Integer, Long> reservations = 
			new HashMap<Integer, Long>();
	  
	/**
	 * Private empty constructor as only one instance is needed.
	 */
	private LockManager() {
	}
	
	  
	/**
	 * Gets the single instance of LockManager.
	 *
	 * @return single instance of LockManager
	 */
	public static LockManager getInstance() {
		if (instance == null) {
			logger.info("Create a new Locking instance");
			instance = new LockManager();
	    }
	    	return instance;
	    }
	

	/**
	 * This locking method adds the record number
	 * to the hashMap along with the current Thread ID. 
	 *
	 * @param recNo : The record number to be locked.
	 */
	public synchronized  void lock(final int recNo) {
		final long lockCookie = Thread.currentThread().getId();
		try {   
			while (isLocked(recNo)) {
				logger.info("Thread " + lockCookie + " waiting for record "
					+ recNo);
				wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.info("Thread " + lockCookie + "  locking  record " + recNo);
		reservations.put(recNo, lockCookie);
	}
	

	/**
	 * The unlock method is passed a record number to unlock.
	 * It checks is the record currently locked by the running thread 
	 * in <code>isOwnerOfLock</code> and if so it then removes the 
	 * record from the hashMap
	 *
	 *  @param recNo : The record number to be unlocked
	 */
	public synchronized  void unlock(final int recNo) {
		final long lockCookie = Thread.currentThread().getId();	  
		try {
			if (isLocked(recNo)) {
				if (isOwnerOfLock(recNo, lockCookie)) {
					logger.info("Thread " + lockCookie + " unlocking record " 
						+ recNo);
					reservations.remove(recNo);
				} else {
					logger.warning("Thread " + lockCookie + " not owner of " 
							+ "record " + recNo);
				}
			} else {
				logger.warning("Record " + recNo + " not locked");
			}
		} finally {
			notifyAll();
		}		
	}
	

	 /**
  	 * Checks if the specified thread is the owner of the 
  	 * locked record.
  	 *
  	 * @param recNo : The record Number.
  	 * @param lockCookie : A thread ID
  	 * @return true, if the record is locked by the given lockCookie. 
  	 */
  	public boolean isOwnerOfLock(final int recNo, final long lockCookie) {
		  if (isLocked(recNo)) {
			  return reservations.get(recNo).equals(lockCookie);
		  }
		return false;
	  }
  	
	  
	/**
	 * Checks if the record Number is locked.
	 *
	 * @param recNo : The record Number.
	 * @return true, if record number is locked
	 */
	public boolean isLocked(final int recNo) {
		return reservations.containsKey(recNo);
	}

}

package suncertify.db;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;

public class LockManager {
	
	
	private static LockManager instance;
	
	
	  private static Map<Integer, Long> reservations
      = new HashMap<Integer, Long>();
	  
	  private LockManager() {
	  }
	  
	  public static LockManager getInstance() {
	        if (instance == null) {
	        //    logger.log(Level.INFO, "RecordLockManager, getInstance\n");
	            instance = new LockManager();
	        }
	        return instance;
	    }

	  public synchronized  void lock(int recNo){
		  final long lockCookie = Thread.currentThread().getId();
		  try {   
             while (isLocked(recNo)) {
            	 wait();
             }
            reservations.put(recNo, lockCookie);
		  } catch (InterruptedException e) {
			e.printStackTrace();
		  } 
		  reservations.put(recNo, lockCookie);
    }

	  public synchronized  void unlock(int recNo) {
		  final long lockCookie = Thread.currentThread().getId();	  
//	 System.out.println("Current locks pre unlock:" + reservations);
    try{	
    	if(isLocked(recNo)){
    		if (isOwnerOfLock(recNo, lockCookie)) {
    			reservations.remove(recNo);
    		}
    	}
    }finally{
    	notifyAll();
    }
		
	}

	  public boolean isOwnerOfLock(int recNo, long lockCookie) {
		  return reservations.get(recNo).equals(lockCookie);
	  }
	  
	public boolean isLocked(int recNo) {
		if (reservations.containsKey(recNo)) {
	//		System.out.println("isLocked says true");
            return true;
        }else{
    //    	System.out.println("isLocked says false");
        	return false;
        	
        }
	}

}

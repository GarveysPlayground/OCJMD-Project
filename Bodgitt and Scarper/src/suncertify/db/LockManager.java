package suncertify.db;

import java.util.HashMap;
import java.util.Map;

public final class LockManager {
	
	private static LockManager instance;
	
	private static Map<Integer, Long> reservations = new HashMap<Integer, Long>();
	  
	private LockManager() {
	}
	  
	public static LockManager getInstance() {
		if (instance == null) {
			instance = new LockManager();
	    }
	    	return instance;
	    }

	public synchronized  void lock(final int recNo){
		final long lockCookie = Thread.currentThread().getId();
		System.out.println(lockCookie + "  wants  " + recNo);
		try {   
			while (isLocked(recNo)) {
				System.out.println(lockCookie + "  in wait for  " + recNo);
				wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(lockCookie + "  locking  " + recNo);
		reservations.put(recNo, lockCookie);
	}

	public synchronized  void unlock(final int recNo) {
		final long lockCookie = Thread.currentThread().getId();	  
		System.out.println(lockCookie + "  wants to unlock  " + recNo);
		try{	
			if(isLocked(recNo)){
				if (isOwnerOfLock(recNo, lockCookie)) {
					System.out.println(lockCookie + "  unlocking " + recNo);
					reservations.remove(recNo);
				}else{
					System.out.println(lockCookie + " not owner of " + recNo);
				}
			}else{System.out.println(recNo + "  Not locked  (" + lockCookie+")");}
		}finally{
			notifyAll();
		}		
	}

	  public boolean isOwnerOfLock(final int recNo, final long lockCookie) {
		  if(isLocked(recNo)){
			  return reservations.get(recNo).equals(lockCookie);
		  }
		return false;
	  }
	  
	public boolean isLocked(final int recNo) {
		return reservations.containsKey(recNo);
	}

}

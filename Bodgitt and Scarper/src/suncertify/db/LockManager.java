package suncertify.db;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockManager {
	
	
	private static Lock lock = new ReentrantLock();
	
	 /** Length of time we will wait for a lock. */
    private static final int TIMEOUT = 5 * 1000;
	
	  private static Map<Integer, DBMain> reservations
      = new HashMap<Integer, DBMain>();
	  
	  private static Condition lockReleased  = lock.newCondition();

	public void lock(int recNo, DBMain dbMain){
		System.out.println("Locking");
		lock.lock();
        try {   
        	 long endTimeMSec = System.currentTimeMillis() + TIMEOUT;
             while (reservations.containsKey(recNo)) {
            	 System.out.println("Already Locked");
                 long timeLeftMSec = endTimeMSec - System.currentTimeMillis();
                 if (!lockReleased.await(timeLeftMSec, TimeUnit.MILLISECONDS)) {
                 }
             }
            System.out.println("Record"+recNo+" locked by " + dbMain);
            reservations.put(recNo, dbMain);
            System.out.println(reservations);
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            // ensure lock is always released, even if an Exception is thrown
        //	System.out.println("UnLocking");
        //	lock.unlock();
        }
    }

	public void unlock(int recNo, DBMain dbMain) {
		System.out.println(reservations);
     lock.lock();
     if (reservations.get(recNo) == dbMain) {
         reservations.remove(recNo);

         lockReleased.signal();
     } else {

     }
     lock.unlock();

		
	}

	public boolean isLocked(int recNo) {
		if (reservations.containsKey(recNo)) {
			System.out.println("isLocked says true");
            return true;
        }else{
        	System.out.println("isLocked says false");
        	return false;
        	
        }
	}

}

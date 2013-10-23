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
	
	  private static Map<Integer, DataRemote> reservations
      = new HashMap<Integer, DataRemote>();
	  
	  private static Condition lockReleased  = lock.newCondition();

	public static void lock(int recNo, DataRemote dataRemote){
		System.out.println("Locking");
		System.out.println("Current Locks before call : " + reservations);
		lock.lock();
		System.out.println("Record"+recNo+" locked by " + dataRemote);
        try {   
        	 long endTimeMSec = System.currentTimeMillis() + TIMEOUT;
             while (reservations.containsKey(recNo)) {
            	 System.out.println("Already Locked");
                 long timeLeftMSec = endTimeMSec - System.currentTimeMillis();
                 if (!lockReleased.await(timeLeftMSec, TimeUnit.MILLISECONDS)) {
                 }
             }
            reservations.put(recNo, dataRemote);
            System.out.println(reservations);
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }

	public static void unlock(int recNo,  DataRemote dataRemote) {
	 System.out.println("Current locks pre unlock:" + reservations);
     if (reservations.get(recNo) == dataRemote) {
         reservations.remove(recNo);
         lock.unlock();
       //  lockReleased.signal();
     } else {

     }
     System.out.println("Current post unlock:" + reservations);
     //lock.unlock();

		
	}

	public static boolean isLocked(int recNo) {
		if (reservations.containsKey(recNo)) {
			System.out.println("isLocked says true");
            return true;
        }else{
        	System.out.println("isLocked says false");
        	return false;
        	
        }
	}

}

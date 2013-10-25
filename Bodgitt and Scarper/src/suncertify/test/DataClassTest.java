package suncertify.test;

import suncertify.db.Data;
import suncertify.db.FileAccess;
import suncertify.db.Subcontractor;


public class DataClassTest {

	 private static final String DB_PATH = "C:\\Users\\epagarv\\Google Drive\\Java\\SCJD\\mine\\db-2x3.db";
 
	 private static Data data = null;
	 
	 private static FileAccess database = null;
    /* 
     * If any preparation has to be done before using the Data class, it can be 
     * done in a static block; in this case, before using the Data class, the 
     * loadDbRecords method has to be called prior to any other operation, so 
     * the records in the physical .db file can be placed in the Map that keeps 
     * them in memory; I also have a method called persistDbRecords, which 
     * writes each record back to the physical .db file, but this test aims only 
     * to test the functionalities without altering the database, so this method 
     * is never called anywhere 
     */  
     
    public static void main(String [] args) {  
    	 try {  
         	
         	data = new Data(DB_PATH);
         	//data.loadDbRecords();  
         } catch (Exception e) {  
             System.out.println(e);  
         }  
        new DataClassTest().startTests();  
    }  
  
    public void startTests() {  
        try {  
              
            /* 
             * Practically, it is not necessary to execute this loop more than 1 
             * time, but if you want, you can increase the controller variable, 
             * so it is executed as many times as you want 
             */  
            for (int i = 0; i <= 5; i++) {  
                Thread updatingRandom = new UpdatingRandomRecordThread();  
                updatingRandom.start();  
             //   Thread updatingRecord1 = new UpdatingRecord1Thread();  
             //   updatingRecord1.start();  
            //    Thread creatingRecord = new CreatingRecordThread();  
            //    creatingRecord.start();  
               // Thread deletingRecord = new DeletingRecord1Thread();  
               // deletingRecord.start();  
               // Thread findingRecords = new FindingRecordsThread();  
               // findingRecords.start();  
            }  
        } catch (Exception e) {  
            System.out.println(e.getMessage());  
        }  
  
    }  
  
    private class UpdatingRandomRecordThread extends Thread {  
  
        @SuppressWarnings("deprecation")  
        public void run() {  
   //     	 final Subcontractor subcon = new Subcontractor();  
	//            subcon.setName("Sir Update");  
	//            subcon.setLocation("Athlone");  
	//            subcon.setSpecialties("Programming");  
	//            subcon.setSize("5");  
	//            subcon.setRate("$150.00");    
	//            subcon.setOwner("12451245");   
  
            final int recNo = (int) (Math.random() * 50);  
            try {  
  //              System.out.println(Thread.currentThread().getId()  
  //                      + " trying to lock record #" + recNo  
  //                      + " on UpdatingRandomRecordThread");  
  
                /* 
                 * The generated record number may not exist in the database, so 
                 * a RecordNotFoundException must be thrown by the lock method. 
                 * Since the database records are in a cache, it is not 
                 * necessary to put the unlock instruction in a finally block, 
                 * because an exception can only occur when calling the lock 
                 * method (not when calling the update/delete methods), 
                 * therefore it is not necessary to call the unlock method in a 
                 * finally block, but you can customize this code according to 
                 * your reality 
                 */  
//                data.lock(recNo);  
//                System.out.println(Thread.currentThread().getId()  
//                        + " trying to update record #" + recNo  
//                        + " on UpdatingRandomRecordThread");  
  
                /* 
                 * An exception cannot occur here, otherwise, the unlock 
                 * instruction will not be reached, and the record will be 
                 * locked forever. In this case, I created a class called 
                 * RoomRetriever, which transforms from Room to String array, 
                 * and vice-versa, but it could also be done this way: 
                 * 
                 * data.update(recNo, new String[] {"Palace", "Smallville", "2", 
                 * "Y", "$150.00", "2005/07/27", null}); 
                 */  
                final String[] dataEntry = new String[6];
    			dataEntry[0] = "Random";
    			dataEntry[1] = "Updater";
    			dataEntry[2] = "Playing, Trouble Making";
    			dataEntry[3] = "44";
    			dataEntry[4] = "$1.00";
    			dataEntry[5] = "12345678";
    			System.out.println("Thread "+Thread.currentThread().getId() +" Updateing Rec No " + recNo);
    			data.update(recNo,new String[] {"Palace", "Smallville", "2", 
    	                  "20", "$150.00", "55555555"}); 
               // System.out.println(Thread.currentThread().getId()  
               //         + " trying to unlock record #" + recNo  
               //         + " on UpdatingRandomRecordThread");  
//                data.unlock(recNo);  
            } catch (Exception e) {  
                System.out.println(e);  
            }  
        }  
    }  
  
    public class UpdatingRecord1Thread extends Thread {  
  
        @SuppressWarnings("deprecation")  
        public void run() {  
       // 	final Subcontractor subcon = new Subcontractor();  
      //      subcon.setName("Sir UpdateRe1");  
      //      subcon.setLocation("AthloneRec1");  
     ///       subcon.setSpecialties("ProgrammingRec1");  
    //        subcon.setSize("51");  
    //        subcon.setRate("$510.00");    
    //        subcon.setOwner("11111111");  
            
            
            final String[] dataEntry = new String[6];
			dataEntry[0] = "Garvey";
			dataEntry[1] = "TheeAthlone";
			dataEntry[2] = "Trouble Making";
			dataEntry[3] = "11";
			dataEntry[4] = "$1.00";
			dataEntry[5] = "12345678";
  
            try {  
                System.out.println(Thread.currentThread().getId()  
                        + " trying to lock record #1 on"  
                        + " UpdatingRecord1Thread");  
//                data.lock(1);  
                System.out.println(Thread.currentThread().getId()  
                        + " trying to update record #1 on"  
                        + " UpdatingRecord1Thread");  
                data.update(1, dataEntry);  
                System.out.println(Thread.currentThread().getId()  
                        + " trying to unlock record #1 on"  
                        + "UpdatingRecord1Thread");  
                  
                /* 
                 * In order to see the deadlock, this instruction can be 
                 * commented, and the other Threads, waiting to update/delete 
                 * record #1 will wait forever and the deadlock will occur 
                 */  
//                data.unlock(1);  
            } catch (Exception e) {  
                System.out.println(e);  
            }  
        }  
    }  
  
    private class CreatingRecordThread extends Thread {  
  
        @SuppressWarnings("deprecation")  
        public void run() {  
        //	final Subcontractor subcon = new Subcontractor();  
          //  subcon.setName("Sir Creating");  
          //  subcon.setLocation("AthloCreate");  
          //  subcon.setSpecialties("CreateRecT");  
          //  subcon.setSize("21");  
          //  subcon.setRate("$120.00");    
          //  subcon.setOwner("23342334");  
            
            final String[] dataEntry = new String[6];
			dataEntry[0] = "Crevey";
			dataEntry[1] = "TheeAthlone";
			dataEntry[2] = "Trouble Making";
			dataEntry[3] = "11";
			dataEntry[4] = "$25.00";
			dataEntry[5] = "12345678";
  
            try {  
                System.out.println(Thread.currentThread().getId()  
                        + " trying to create a record");  
                data.create(dataEntry);  
            } catch (Exception e) {  
                System.out.println(e);  
            }  
        }  
    }  
  
    private class DeletingRecord1Thread extends Thread {  
  
        public void run() {  
            try {  
//                System.out.println(Thread.currentThread().getId()  
//                        + " trying to lock record #1 on "  
//                        + "DeletingRecord1Thread");  
              //  data.lock(1);  
//                System.out.println(Thread.currentThread().getId()  
//                        + " trying to delete record #1 on "  
//                        + "DeletingRecord1Thread");  
                data.delete(1);  
//                System.out.println(Thread.currentThread().getId()  
//                        + " trying to unlock record #1 on "  
//                        + "DeletingRecord1Thread");  
               // data.unlock(1);  
            } catch (Exception e) {  
                System.out.println(e);  
            }  
        }  
    }  
  
    public class FindingRecordsThread extends Thread {  
  
        public void run() {  
            try {  
                System.out.println(Thread.currentThread().getId()  
                        + " trying to find records");  
                final String [] criteria = {"Bitter", null, null, null,  
                        null, null};  
                final int [] results = data.find(criteria);  
  
                for (int i = 0; i < results.length; i++) {  
                    System.out.println(results.length + " results found.");  
                    try {  
                        final String message = Thread.currentThread().getId()  
                                + " going to read record #" + results[i]  
                                + " in FindingRecordsThread - still "  
                                + ((results.length - 1) - i) + " to go.";  
                        System.out.println(message);  
                        final String [] room = data.read(results[i]);  
                        System.out.println("Hotel (FindingRecordsThread): "  
                                + room[0]);  
                        System.out.println("Has next? "  
                                + (i < (results.length - 1)));  
                    } catch (Exception e) {  
                        /* 
                         * In case a record was found during the execution of 
                         * the find method, but deleted before the execution of 
                         * the read instruction, a RecordNotFoundException will 
                         * occur, which would be normal then 
                         */  
                        System.out.println("Exception in "  
                                + "FindingRecordsThread - " + e);  
                    }  
                }  
                System.out.println("Exiting for loop");  
            } catch (Exception e) {  
                System.out.println(e);  
            }  
        }  
    }  
}  
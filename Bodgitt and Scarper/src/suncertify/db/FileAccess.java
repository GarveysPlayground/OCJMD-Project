package suncertify.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.logging.Logger;



public class FileAccess {
	
	 private static RandomAccessFile database = null;
	 private static int initial_offset = 0;
	 
	 //Start of data file
	 private static final int MAGIC_COOKIE_BYTES = 4;
	 private static final int NUMBER_OF_FIELDS_BYTES = 2;
	 
	 //for each field
	 private static final int FIELD_LENGTH_BYTES = 1;
	 private static final int FIELD_NAME_BYTES = 1;  
	 
	 //data section
	 private static final int RECORD_FLAG_BYTES = 1; 
	 private static final byte VALID = 00;
	 private static final byte INVALID = (byte) 0xFF;
	 private static final String ENCODING = "US-ASCII";
	 
	 /**Set the individual lengths of the field record*/
	 static int[] FIELD_LENGTHS = {Subcontractor.name_Length,
    		 Subcontractor.location_Length,
    		 Subcontractor.specialties_Length,
    		 Subcontractor.size_Length,
    		 Subcontractor.rate_Length,
    		 Subcontractor.owner_Length}; 
	 

	 final static int fullRecordSize = Subcontractor.entry_Length + RECORD_FLAG_BYTES;
	 
	 private static LockManager lockManager = LockManager.getInstance();
	 
	 private static Logger logger = Logger.getLogger("suncertify.db");
	 
	 public FileAccess(String dbLocation) throws FileNotFoundException,IOException {
		 logger.entering("FileAccess", "connectToDB", dbLocation);
		 logger.info("Connecting to Database dbLocation");
		 database = new RandomAccessFile(new File(dbLocation), "rw");;
		 initial_offset = getInitialOffset();
		 
		 
		 try {
			 delete(1);
			 delete(0);
			 delete(2);
			delete(12);
			
			
		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}
	 
	 private static int getInitialOffset() throws IOException{
//		 logger.info("Calculating the files initial offset bytes");
		 database.seek(0);
		 //Read the start of the file as per the Data File Format
		 final byte [] magicCookieByteArray = new byte[MAGIC_COOKIE_BYTES];    
         final byte [] numberOfFieldsByteArray = new byte[NUMBER_OF_FIELDS_BYTES];  
         database.read(magicCookieByteArray);    
         database.read(numberOfFieldsByteArray);  
     
                  
		 /** The bytes that store the length of each field name */
         final String [] fieldNames = new String[Subcontractor.number_Of_Fields];
         final int [] fieldLengths = new int[Subcontractor.number_Of_Fields];  
		    
         /** Get the value of the field name titles   */   
		 for (int i = 0; i < Subcontractor.number_Of_Fields; i++) {  
             final byte nameLengthByteArray[] = new byte[FIELD_NAME_BYTES];  
             database.read(nameLengthByteArray);  
             final int nameLength = getValue(nameLengthByteArray);  

             final byte [] fieldNameByteArray = new byte[nameLength];  
             database.read(fieldNameByteArray);  
             fieldNames[i] = new String(fieldNameByteArray, ENCODING);  
             
             final byte [] fieldLength = new byte[FIELD_LENGTH_BYTES];  
             database.read(fieldLength);  
             fieldLengths[i] = getValue(fieldLength);  
         } 
		 /**Setting the initial_offset to point at the begining of the first record*/
		 int initial_offset = (int) database.getFilePointer();
	     return initial_offset;
	 }
	 
	 
	 private static String[] getSingleRecord() throws IOException{
		 if(database.getFilePointer() > database.length()){
		      	logger.warning("Not that many records exist"); 
		 }
        String[] recordValues = new String[Subcontractor.number_Of_Fields]; 
        final byte [] flagByteArray = new byte[RECORD_FLAG_BYTES];
 	    database.read(flagByteArray);
  	    final int flag = getValue(flagByteArray);
 	   if(flag == VALID){ 	    
 		  
 		}
 	   for (int i = 0; i < recordValues.length; i++) {  
      	byte[] bytes = new byte[FIELD_LENGTHS[i]];  
      	database.read(bytes);
      	recordValues[i] = new String(bytes, ENCODING); 
		   }  	    
  	    return recordValues;
	 }
	 	 
	 public static int getNoOfRecords() { 
		 System.out.println("Getting no of records");
		 int numberOfRecords = 0;
		// int[] validRecs[] = null;
		 try{
			database.seek(initial_offset);

			 System.out.println("About to get single record");
         	while (database.getFilePointer() < database.length()) {
         		String record[] = getSingleRecord();
	           	numberOfRecords++;
	   	 	}
		 }catch(IOException e){
			 logger.severe("Error getting number of records: " + e.getMessage());
		 }
   //      logger.info("Total Number of records in file: " + numberOfRecords);
         return numberOfRecords;
     } 
	 
	 
	 public static int[] getValidRecords() throws IOException, RecordNotFoundException { 
        int recNo = 0;
        ArrayList<Integer> recNumArray = new ArrayList<Integer>();
        int flag;
        final byte [] flagByteArray = new byte[RECORD_FLAG_BYTES];
        database.seek(initial_offset);
        while (database.getFilePointer() < database.length()) {
 	    	database.read(flagByteArray);
  	    	flag = getValue(flagByteArray);
  	    	if (flag == VALID) { 
  	   // 		System.out.println("VALID");
				recNumArray.add(recNo);
				read(recNo);
				recNo++;
        	}else{  
        //		System.out.println("-----INVALID");
        		read(recNo);
        		recNo++;
        	}
        }
      //  System.out.println("Number of valid records" + recNumArray.size());
 	  //  int[] validRecs = new int[recNumArray.size()];
 	   // int i = 0;
 	   // for (Integer integer: recNumArray) {
 		//   	validRecs[i] = integer;
 		//   	i++;
     //  }
 	   int[] arr = new int[recNumArray.size()];

 	  for(int i = 0; i < recNumArray.size(); i++) {
 	      if (recNumArray.get(i) != null) {
 	          arr[i] = recNumArray.get(i);
 	      }
 	  }

 	    return arr;
	 }
	 
	
	 public static String[] read(int recNo) throws RecordNotFoundException{	 
		 try{
			database.seek(initial_offset + (fullRecordSize*recNo)); 
		 	String record[] = getSingleRecord();
		 	return record;
		 }
		 catch (Exception e) {
			 logger.warning("Error reading record Number : " + recNo);
             throw new RecordNotFoundException("Problem encountered reading recNo " +recNo+": "+ e.getMessage());		 
		 }
	 }
	 
	 
	 public static synchronized void update(int recNo, String[] data) throws RecordNotFoundException{

		
	
		 try {
			if(recNo < 0 || recNo > getNoOfRecords()){throw new RecordNotFoundException("The record: " + recNo 
						 								+ " was not found");
			 }		
		 int recordLocation = initial_offset + (recNo * fullRecordSize);
			 lockManager.lock(recNo);
		//	 System.out.println("Thread " +  Thread.currentThread().getId() + " Updateing recNo : " + recNo);
			 database.seek(recordLocation);
			 byte[] record = new byte[fullRecordSize];
			 database.read(record);            

			 if (record[0] == INVALID) {				 
				 logger.severe("Cannot update Record. Record was Deleted.");
			 }
             else if(record[0] == VALID){
     //       	 logger.info("Record Valid and Thus Updateable.");
                 database.seek(recordLocation);
    			 byte b = VALID; //valid file byte
    			 database.write(b);
    			 //for each field output the new value + white space
    			 for(int i = 0; i < Subcontractor.number_Of_Fields; i++){
    				 int padding = FIELD_LENGTHS[i] - data[i].getBytes().length;
    				 database.write(data[i].getBytes());
    				 while(padding != 0){
    					 database.write(' ');
    					 padding --;
    				 }
    			 }
             }else{
            	 System.out.println("-------NEITHER VALID NOR INVALID-------");
             } 
         }catch (Exception e){
                 throw new RecordNotFoundException("The record: " + recNo 
                         			+ " was not found, " + e.getMessage());
         } finally{
        	 lockManager.unlock(recNo);
         }
		 
		 try {
			System.out.println("Number of valid records after update: " + getValidRecords().length);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
	}
	 
 
	 public synchronized static int create(String [] data) throws DuplicateKeyException, IOException{
		 int numOfRecords =  getNoOfRecords();
		 database.seek(initial_offset);
		 byte[] record = new byte[fullRecordSize];
		 database.read(record);
		 //Find deleted record space
		 int currentRec = 0;
		 while(record[0] == VALID && currentRec < numOfRecords){
			 database.read(record);
			 currentRec++;
		 }
		 if(record[0] == INVALID){
			 database.seek(initial_offset + (currentRec*fullRecordSize));
		 }
		try{
			lockManager.lock(currentRec);
			int recordLocation = initial_offset + (currentRec * fullRecordSize);
			database.seek(recordLocation);
			byte b = VALID; //valid file byte
			database.write(b);
			//for each field output the new value + white space
			for(int i = 0; i < Subcontractor.number_Of_Fields; i++){
				int padding = FIELD_LENGTHS[i] - data[i].getBytes().length;
				database.write(data[i].getBytes());
				while(padding != 0){
					database.write(' ');
					padding--;
				}
			}
		}finally{
			lockManager.unlock(currentRec);
		}
		
		 try {
				System.out.println("Number of valid records after create: " + getValidRecords().length);
			} catch (RecordNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		return currentRec;
	 }
	 	 
	 static synchronized void delete(int recNo) throws RecordNotFoundException{
		 try{
		 if(recNo < 0 || recNo >=  getNoOfRecords()){
			 throw new RecordNotFoundException("The record you wish to delete:"
					 							+ recNo	+ " was not found");
		 }

			lockManager.lock(recNo);
			int recordLocation = initial_offset + (recNo * fullRecordSize); 
		 	database.seek(recordLocation);		 
		 	byte b = (byte) INVALID; //valid file byte
		 	database.write(b);
		 	int padding = Subcontractor.entry_Length;
		 	while(padding != 0){
				database.write(' ');
				padding --;
		 	}
		 }catch (Exception e){
             throw new RecordNotFoundException("The record: " + recNo 
          			+ " was not found, " + e.getMessage());
		 }finally{
			 lockManager.unlock(recNo);
		 }
		 
		 
		 try {
				System.out.println("Number of valid records after delete: " + getValidRecords().length);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		 
	 }
	 
	 
	 public static int [] find(String [] criteria) throws RecordNotFoundException{
		 String[] allColumns = new String[6];		 
		 System.arraycopy(criteria, 0, allColumns, 0, criteria.length);
		 criteria = allColumns;		 
		 for(int i = 0 ; i < allColumns.length; i++){
			 if (allColumns[i] == null){allColumns[i] = "";}
		 }
		  
		 int[] searchResults = null;
		 try {
			int[] records = new int[getValidRecords().length];
			records = getValidRecords();
			String[] record = null;
			ArrayList<Integer> matchingRecords = new ArrayList<Integer>();
			for(int i = 0; i < getValidRecords().length; i++){
				System.out.println("Records i : " + records[i]);
				 record = read(records[i]);
				 if(record[0].contains(allColumns[0]) && 
					record[1].contains(allColumns[1]) &&
					record[2].contains(allColumns[2]) &&
					record[3].contains(allColumns[3]) &&
					record[4].contains(allColumns[4])){
					 matchingRecords.add(records[i]);
				 }
			}
		 	searchResults = new int[matchingRecords.size()];
		  	for(int i = 0; i < matchingRecords.size(); i++){
		  	      if (matchingRecords.get(i) != null) {
		  	    	  System.out.println("Matching record" + matchingRecords.get(i));
		  	    	searchResults[i] = matchingRecords.get(i);
		  	      }
		  	}
		  	
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return searchResults;
	 }
	 
	 
	 private static int getValue(final byte [] byteArray) {  
         int value = 0;  
         final int byteArrayLength = byteArray.length;  
   
         for (int i = 0; i < byteArrayLength; i++) {  
             final int shift = (byteArrayLength - 1 - i) * 8;  
             value += (byteArray[i] & 0x000000FF) << shift;  
         }  
         return value;  
     } 	 
}
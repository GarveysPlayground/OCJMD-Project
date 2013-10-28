package suncertify.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.logging.Logger;



public class DataAccess {
	
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
	 
	 static int NO_OF_FIELDS = FIELD_LENGTHS.length;
	 

	 static final int fullRecordSize = Subcontractor.entry_Length + RECORD_FLAG_BYTES;
	 
	 private static LockManager lockManager = LockManager.getInstance();
	 
	 private static Logger logger = Logger.getLogger("suncertify.db");
	 
	 public DataAccess(final String dbLocation) 
			 throws IOException {
		 logger.entering("FileAccess", "connectToDB", dbLocation);
		 logger.info("Connecting to Database dbLocation");
		 database = new RandomAccessFile(new File(dbLocation), "rw");
		 initial_offset = getInitialOffset();
		 }
	 
	 private static int getInitialOffset() throws IOException{
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
             final byte[] nameLengthByteArray = new byte[FIELD_NAME_BYTES];
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
	 
	 
	private static String[] read() throws IOException {
		String[] columnValues = new String[Subcontractor.number_Of_Fields];
		if (database.getFilePointer() > database.length()) {
			logger.warning("Not that many records exist");
			throw new IOException("No records to read in at " +
								"this point in the database"); 
		} else {
			final byte [] flagByteArray = new byte[RECORD_FLAG_BYTES];
			database.read(flagByteArray);
			byte[] bytes;
			for (int i = 0; i < columnValues.length; i++) {  
				bytes = new byte[FIELD_LENGTHS[i]];  
				database.read(bytes);
				columnValues[i] = new String(bytes, ENCODING);
			}
		}
		return columnValues;
	}
	 
	
	 //Get no of both valid and invalid records
	public static int getNoOfRecords() { 
		int numberOfRecords = 0;
		try{
			database.seek(initial_offset);
         	while (database.getFilePointer() < database.length()) {
         		read();
	           	numberOfRecords++;
	   	 	}
		 }catch(IOException e){
			logger.severe("Error getting record count: " + e.getMessage());
		 }
         return numberOfRecords;
    } 
	 
	
	 //Go through all record spaces adding valid records to ArrayList. Then return the
	// valid records in an int []
	public static int[] getValidRecords() throws IOException, RecordNotFoundException { 
		ArrayList<Integer> recNumArray = new ArrayList<Integer>();
        int recNo = 0;
        final byte [] flagByteArray = new byte[RECORD_FLAG_BYTES];
        database.seek(initial_offset);
        while (database.getFilePointer() < database.length()) {
 	    	database.read(flagByteArray);
  	    	if (getValue(flagByteArray) == VALID) { 
				recNumArray.add(recNo);
				read(recNo);
				recNo++;
        	}else{  
           		read(recNo);
        		recNo++;
        	}
        }
 	    int[] validRecords = new int[recNumArray.size()];
 	    for(int i = 0; i < recNumArray.size(); i++) {
 	    	if(recNumArray.get(i) != null) {
 	    		validRecords[i] = recNumArray.get(i);
 	    	}
 	    }
 	    return validRecords;
	 }
	 
	
	public static String[] read(final int recNo) throws RecordNotFoundException{	 
		try{
			database.seek(initial_offset + (fullRecordSize*recNo)); 
		 	String[] record = read();
		 	return record;
		 } catch (Exception e) {
			logger.warning("Error reading record Number : " + recNo);
            throw new RecordNotFoundException("Problem encountered reading " +
             							"recNo " +recNo+": "+ e.getMessage());	 
		 }
	}
	 
	 
	public static synchronized void update(final int recNo, final String[] data) 
			throws RecordNotFoundException{
		try {
			if (recNo < 0 || recNo > getNoOfRecords()) {
				throw new RecordNotFoundException("The record: " + recNo 
						+ " was not found");
			}		
			int recordLocation = initial_offset + (recNo * fullRecordSize);
			lockManager.lock(recNo);
			database.seek(recordLocation);
			byte[] record = new byte[fullRecordSize];
			database.read(record);            

			if (record[0] == INVALID) {				 
				logger.severe("Cannot update Record. Record was Deleted.");
				throw new RecordNotFoundException("The record : " + recNo 
						+"has been deleted");
			}
			else if (record[0] == VALID) {
				database.seek(recordLocation);
				byte b = VALID; 
				database.write(b);
				for(int i = 0; i < Subcontractor.number_Of_Fields; i++){
					int padding = FIELD_LENGTHS[i] - data[i].getBytes().length;
					database.write(data[i].getBytes());
					while (padding != 0) {
						database.write(' ');
						padding --;
					}
				}
			}
		} catch (IOException e){
			System.err.println("Problem encountered while updating recNo + "
					+ recNo +"\n" + e.getMessage());
		} finally {
			lockManager.unlock(recNo);
		}
	}
	 
 
	public static synchronized int create(final String [] data) 
			throws DuplicateKeyException, IOException{
		int currentRec = 0;
		final byte [] flagByteArray = new byte[RECORD_FLAG_BYTES];
		try{
			if(isDuplicate(data)){
				throw new DuplicateKeyException("Create failed, " +
						"Records already exists!");
			}
			database.seek(initial_offset);
			while (database.getFilePointer() < database.length()) {
				database.read(flagByteArray);
	  	    		if (getValue(flagByteArray) == VALID) { 
	  	    			read(currentRec);
	  	    			currentRec++;
	  	    		}else{  
	  	    			lockManager.lock(currentRec);
	  	    			break;
	  	    		}	  	    
			}			
			int recordLocation = initial_offset + (currentRec * fullRecordSize);
			database.seek(recordLocation);
			byte b = VALID; //valid file byte
			database.write(b);
			//for each field output the new value + white space
			for (int i = 0; i < Subcontractor.number_Of_Fields; i++) {
				int padding = FIELD_LENGTHS[i] - data[i].getBytes().length;
				database.write(data[i].getBytes());
				while (padding != 0) {
					database.write(' ');
					padding--;
				}
			}
		} catch (RecordNotFoundException e) {
			System.err.println("Error while finding space to create record." 
					+ " Record" + currentRec + " not found" + e.getMessage());
		} finally {
			lockManager.unlock(currentRec);
		} 
		return currentRec;
	 }
	 	 
	
	 static synchronized void delete(final int recNo) 
			 throws RecordNotFoundException {
		 try {
		 if (recNo < 0 || recNo >=  getNoOfRecords()) {
			 throw new RecordNotFoundException("The record you wish to delete:"
					 							+ recNo	+ " was not found");
		 }

			lockManager.lock(recNo);
			int recordLocation = initial_offset + (recNo * fullRecordSize); 
		 	database.seek(recordLocation);		 
		 	byte b = (byte) INVALID; //valid file byte
		 	database.write(b);
		 } catch (Exception e) {
             throw new RecordNotFoundException("The record: " + recNo 
          			+ " was not found, " + e.getMessage());
		 } finally {
			 lockManager.unlock(recNo);
		 }
	 }
	 
	 
	 public static int [] find(String [] criteria) 
			 throws RecordNotFoundException {
		 String[] allColumns = 
				 new String[Subcontractor.number_Of_Fields];		 
		 System.arraycopy(criteria, 0, allColumns, 0, criteria.length);
		 criteria = allColumns;		 
		 for (int i = 0; i < allColumns.length; i++) {
			 if (allColumns[i] == null){
				 allColumns[i] = "";
			}
		 }
		  
		 int[] searchResults = null;
		 try {
			int[] records = new int[getValidRecords().length];
			records = getValidRecords();
			String[] record = null;
			ArrayList<Integer> matchingRecords = new ArrayList<Integer>();
			for (int i = 0; i < getValidRecords().length; i++) {
				 record = read(records[i]);
				 if (record[0].contains(allColumns[0]) 
						 &&	record[1].contains(allColumns[1]) 
						 &&	record[2].contains(allColumns[2]) 
						 && record[3].contains(allColumns[3]) 
						 &&	record[4].contains(allColumns[4])) {
					 matchingRecords.add(records[i]);
				 }
			}
		 	searchResults = new int[matchingRecords.size()];
		  	for (int i = 0; i < matchingRecords.size(); i++) {
		  	      if (matchingRecords.get(i) != null) {
		  	    	searchResults[i] = matchingRecords.get(i);
		  	      }
		  	}
		  	
		 } catch (IOException e) {
			System.err.println("Error searching for records : ");
			e.printStackTrace();
		}
		 return searchResults;
	 }
	 
	 public static boolean isDuplicate(final String [] criteria){
		 try {
			int [] duplicateRecs = find(criteria);
			if (duplicateRecs.length > 0) {
				return true;
			}			
		} catch (Exception e) {
			System.err.println("Error encountered while checking for record" 
								+ " dublication : " + e.getMessage());
		}
		 return false;
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
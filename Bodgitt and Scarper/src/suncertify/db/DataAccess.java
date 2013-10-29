/* Project: Bodgitt and Scarper Version 2.3.3
 * @author: Patrick Garvey
 * Last Modified: 29th Oct 2013 
 * DataAccess.java
 */
package suncertify.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.logging.Logger;

import suncertify.test.DataClassTest.UpdatingRecord1Thread;



/**
 * The Class DataAccess is a worker class that is used 
 * for reading and manipulating the database file. This
 * makes it easier to modify how the system manipulates
 * the data at a later date if needed. It is possible to
 * change how these methods function without any effect 
 * from the end user perspective. It can be said that we 
 * are using the delegation pattern as this class mostly 
 * consists of helper methods helping the Data.java class.
 */
public class DataAccess {
	
	 /** The database. */
 	private static RandomAccessFile database = null;
	 
 	/** The initial offset that offsets the initial information of the database file */
 	private static int initialOffset = 0;
	 
	 /** The Constant MAGIC_COOKIE_BYTES as declared by instructions.html. */
 	private static final int MAGIC_COOKIE_BYTES = 4;
	 
 	/** The Constant NUMBER_OF_FIELDS_BYTES as declared by instructions.html. */
 	private static final int NUMBER_OF_FIELDS_BYTES = 2;
	 
	 //for each field
	 /** The Constant FIELD_LENGTH_BYTES as declared by instructions.html. */
 	private static final int FIELD_LENGTH_BYTES = 1;
	 
 	/** The Constant FIELD_NAME_BYTES as declared by instructions.html. */
 	private static final int FIELD_NAME_BYTES = 1;  
	 
	 //data section
	 /** The Constant RECORD_FLAG_BYTES as declared by instructions.html. */
 	private static final int RECORD_FLAG_BYTES = 1; 
	 
 	/** The Constant VALID as declared by instructions.html. */
 	private static final byte VALID = 00;
	 
 	/** The Constant INVALID as declared by instructions.html. */
 	private static final byte INVALID = (byte) 0xFF;
	 
 	/** The Constant ENCODING as declared by instructions.html. */
 	private static final String ENCODING = "US-ASCII";
	 
	 /** Set the individual lengths of the field record as declared by instructions.html. */
	 private static final int[] FIELD_LENGTHS = {Subcontractor.name_Length,
    		 Subcontractor.location_Length,
    		 Subcontractor.specialties_Length,
    		 Subcontractor.size_Length,
    		 Subcontractor.rate_Length,
    		 Subcontractor.owner_Length}; 
	 
	 

	 /** The Constant FULL_RECORD_SIZE. */
 	private static final int FULL_RECORD_SIZE = Subcontractor.entry_Length 
			 + RECORD_FLAG_BYTES;
	 
	 /** The lock manager instance. */
 	private static LockManager lockManager = LockManager.getInstance();
	 
	 /** The logger. */
 	private static Logger logger = Logger.getLogger("suncertify.db");
	 
	 /**
 	 * Instantiates a new data access taking in the location of the
 	 * local database file. This method establishes the connection
 	 * to the database file through the use of 
 	 * <code>RandomAccessFile</code>. Once connected to the database
 	 * the total initial offset value is determined.
 	 *
 	 * @param dbLocation : The database location
 	 * @throws IOException Signals that an I/O exception has occurred.
 	 */
 	public DataAccess(final String dbLocation) 
			 throws IOException {
		 logger.entering("FileAccess", "connectToDB", dbLocation);
		 logger.info("Connecting to Database dbLocation");
		 database = new RandomAccessFile(new File(dbLocation), "rw");
		 initialOffset = getInitialOffset();
		 }
	 
	 /**
 	 * Gets the initial offset. The initial offset is the one time
 	 * constants declared at the start of the file such as the
 	 * table headers, magic Cookie, etc. 
 	 *
 	 * @return the initial offset
 	 * @throws IOException Signals that an I/O exception has occurred.
 	 */
 	private static int getInitialOffset() throws IOException {
		 database.seek(0);
		 final byte [] magicCookieByteArray = new byte[MAGIC_COOKIE_BYTES];    
         final byte [] numberOfFieldsByteArray = 
        		 new byte[NUMBER_OF_FIELDS_BYTES];  
         database.read(magicCookieByteArray);    
         database.read(numberOfFieldsByteArray);  
                  
		 /** The bytes that store the length of each field name */
         final String [] fieldNames = 
        		 new String[Subcontractor.number_Of_Fields];
         final int [] fieldLengths = new int[Subcontractor.number_Of_Fields];  
		    
         /** for each database field get the length of the header 
          * and read in that field . At the end of this loop the file
          * pointer will be at the beginning of Record number 1 */   
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
		 
		 /**Set the initial_offset to point at the beginning of the first record*/
		 initialOffset = (int) database.getFilePointer();
	     return initialOffset;
	 }
	 
	 
	/**
	 * Read in a single record from the wherever the database file
	 * pointer currently is. This method is called by <code>read(int recNo)
	 * </code> and <code>getNoOfRecords()</code> and returns the record
	 * it just read.
	 *
	 * @return the string[] : Returns a String containing all 
	 * 						  Columns it just read.
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @see #getNoOfRecords()
	 * @see #read(int)
	 */
	private static String[] read() throws IOException {
		String[] columnValues = new String[Subcontractor.number_Of_Fields];
		if (database.getFilePointer() > database.length()) {
			logger.warning("Not that many records exist");
			throw new IOException("No records to read in at " 
						+ "this point in the database"); 
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
	

	/**
 	 * The method <code>getNoOfRecords()</code> gets the 
 	 * total number of records in the database file. This
 	 * includes both valid and invalid/deleted records
 	 * and returns the value as an int. This helps
 	 * with the update and delete methods in case
 	 * somebody specifies a record to edit that is greater
 	 * than the total number of records.
 	 *
 	 * @return numberOfRecords : The total number of records
 	 * in the database.
 	 * @see #delete(int)
 	 * @see #update(int, String[])
 	 */
 	public static int getNoOfRecords() { 
		int numberOfRecords = 0;
		try {
			database.seek(initialOffset);
         	while (database.getFilePointer() < database.length()) {
         		read();
	           	numberOfRecords++;
	   	 	}
		 } catch (IOException e) {
			logger.severe("Error getting record count: " + e.getMessage());
		 }
         return numberOfRecords;
    } 
	 
	
	/**
 	 * The <code>getValidRecords</code> method gets all the valid
 	 * records in the database file and returns their record numbers
 	 * in an int []. It does this by going threw the database and
 	 * adding each valid record to an arrayList. The contents of that 
 	 * arrayList is then fed into the in [] to be returned. The <code>
 	 * find(String[])</code> only searches these records to return.
 	 *
 	 * @return validRecords : int [] of valid records
 	 * @throws IOException : Signals that an I/O exception has occurred.
 	 * @throws RecordNotFoundException : the record was not found exception
 	 * @see #find(String[])
 	 */
 	public static int[] getValidRecords() 
			throws IOException, RecordNotFoundException {
 		logger.entering("DataAccess", "getValidRecords");
		ArrayList<Integer> recNumArray = new ArrayList<Integer>();
        int recNo = 0;
        final byte [] flagByteArray = new byte[RECORD_FLAG_BYTES];
        database.seek(initialOffset);
        while (database.getFilePointer() < database.length()) {
 	    	database.read(flagByteArray);
  	    	if (getValue(flagByteArray) == VALID) { 
				recNumArray.add(recNo);
				read(recNo);
				recNo++;
        	} else {  
           		read(recNo);
        		recNo++;
        	}
        }
 	    int[] validRecords = new int[recNumArray.size()];
 	    for (int i = 0; i < recNumArray.size(); i++) {
 	    	if (recNumArray.get(i) != null) {
 	    		validRecords[i] = recNumArray.get(i);
 	    	}
 	    }
 	    return validRecords;
	 }
	 
	
	/**
	 * The <code>read(int)</code> method is passed in a record number to read.
	 * The method sets the database file pointer to the beginning of the 
	 * record and then calls the <code>read()</code> method to read the record.
	 *
	 * @param recNo : The record number to be returned
	 * @return the string[] record containing the record numbers contents
	 * @throws RecordNotFoundException : the record not found exception if the 
	 * record number is out of bounds.
	 */
	public static String[] read(final int recNo) 
			throws RecordNotFoundException {
		logger.entering("DataAccess", "read(int)");
		try {
			if (recNo < 0 || recNo > getNoOfRecords()) {
				throw new RecordNotFoundException("The record: " + recNo 
						+ " was not found");
			}
			
			database.seek(initialOffset + (FULL_RECORD_SIZE * recNo)); 
		 	String[] record = read();
		 	return record;
		 } catch (Exception e) {
			logger.warning("Error reading record Number : " + recNo);
            throw new RecordNotFoundException("Problem encountered reading " 
             					+ "recNo " + recNo + ": " + e.getMessage());
		 }
	}
	 
	 
	/**
	 * Update.
	 *
	 * @param recNo the rec no
	 * @param data the data
	 * @throws RecordNotFoundException the record not found exception
	 */
	static synchronized void update(final int recNo, final String[] data)
			throws RecordNotFoundException {
		logger.entering("DataAccess", "update(int, String[])");
		try {
			if (recNo < 0 || recNo > getNoOfRecords()) {
				throw new RecordNotFoundException("The record: " + recNo 
						+ " was not found");
			}		
			int recordLocation = initialOffset + (recNo * FULL_RECORD_SIZE);
			lockManager.lock(recNo);
			database.seek(recordLocation);
			byte[] record = new byte[FULL_RECORD_SIZE];
			database.read(record);            

			if (record[0] == INVALID) {				 
				logger.severe("Cannot update Record. Record was Deleted.");
				throw new RecordNotFoundException("The record : " + recNo 
						+ "has been deleted");
			} else if (record[0] == VALID) {
				database.seek(recordLocation);
				byte b = VALID; 
				database.write(b);
				for (int i = 0; i < Subcontractor.number_Of_Fields; i++) {
					int padding = FIELD_LENGTHS[i] - data[i].getBytes().length;
					database.write(data[i].getBytes());
					while (padding != 0) {
						database.write(' ');
						padding--;
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Problem encountered while updating recNo + "
					+ recNo + "\n" + e.getMessage());
		} finally {
			lockManager.unlock(recNo);
		}
	}
	 
 
	/**
	 * Creates the.
	 *
	 * @param data the data
	 * @return the int
	 * @throws DuplicateKeyException the duplicate key exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static synchronized int create(final String [] data) 
			throws DuplicateKeyException, IOException {
		int currentRec = 0;
		final byte [] flagByteArray = new byte[RECORD_FLAG_BYTES];
		try {
			if (isDuplicate(data)) {
				throw new DuplicateKeyException("Create failed, " 
						+ "Records already exists!");
			}
			database.seek(initialOffset);
			while (database.getFilePointer() < database.length()) {
				database.read(flagByteArray);
	  	    		if (getValue(flagByteArray) == VALID) { 
	  	    			read(currentRec);
	  	    			currentRec++;
	  	    		} else {  
	  	    			lockManager.lock(currentRec);
	  	    			break;
	  	    		}	  	    
			}			
			int recordLocation = initialOffset 
					+ (currentRec * FULL_RECORD_SIZE);
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
	 	 
	
	 /**
 	 * Delete.
 	 *
 	 * @param recNo the rec no
 	 * @throws RecordNotFoundException the record not found exception
 	 */
 	static synchronized void delete(final int recNo) 
			 throws RecordNotFoundException {
		 try {
		 if (recNo < 0 || recNo >=  getNoOfRecords()) {
			 throw new RecordNotFoundException("The record you wish to delete:"
					 							+ recNo	+ " was not found");
		 }

			lockManager.lock(recNo);
			int recordLocation = initialOffset + (recNo * FULL_RECORD_SIZE); 
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
	 
	 
	 /**
 	 * Find.
 	 *
 	 * @param criteria the criteria
 	 * @return the int[]
 	 * @throws RecordNotFoundException the record not found exception
 	 */
 	public static int [] find(String [] criteria) 
			 throws RecordNotFoundException {
		 String[] allColumns = 
				 new String[Subcontractor.number_Of_Fields];		 
		 System.arraycopy(criteria, 0, allColumns, 0, criteria.length);
		 criteria = allColumns;		 
		 for (int i = 0; i < allColumns.length; i++) {
			 if (allColumns[i] == null) {
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
	 
	 /**
 	 * Checks if is duplicate.
 	 *
 	 * @param criteria the criteria
 	 * @return true, if is duplicate
 	 */
 	public static boolean isDuplicate(final String [] criteria) {
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
	 
	 
	 /**
 	 * Gets the value.
 	 *
 	 * @param byteArray the byte array
 	 * @return the value
 	 */
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
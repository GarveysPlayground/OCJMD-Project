package suncertify.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;



public class FileAccess {
	
	 private static final String databaseName = "db-2x3.db";
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
	 
	 private static Logger logger = Logger.getLogger("suncertify.db");
	 
	 public static void FileAccess() throws RecordNotFoundException, IOException {
 
		String Location = "C:\\Users\\epagarv\\Google Drive\\Java\\SCJD\\mine\\db\\";
		connectToDB(Location);
		initial_offset = getInitialOffset();
		
		
		 //getAllRecords();
		 //read(13);
		 String[] newRec = new String[6];
		 newRec[0] = "pennys";
		 newRec[1] = "athlone";
		 newRec[2] = "clothes";
		 newRec[3] = "4";
		 newRec[4] = "$20.00";
		 newRec[5] = "";
	}
	 
	 public static void connectToDB(String dbLocation) throws FileNotFoundException {
		 	logger.entering("FileAccess", "connectToDB", dbLocation);
		 	logger.info("Connecting to Database");
			database = new RandomAccessFile(dbLocation + databaseName, "rw");
	 }
	 
	 
	 private static int getInitialOffset() throws IOException{
		 //logger.entering("FileAccess", "getInitialOffset");
		 logger.info("Calculating the files initial offset bytes");
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
  	    if (flag == VALID) {  
          //	System.out.println( "valid record");  
        } else if (flag == INVALID){  
          //System.out.println("deleted record");  
        }

        for (int i = 0; i < recordValues.length; i++) {  
        	byte[] bytes = new byte[FIELD_LENGTHS[i]];  
        	database.read(bytes);  
            recordValues[i] = new String(bytes, ENCODING);             
        }
        return recordValues;
		 
	 }
	 
	 
	 public static int getNoOfRecords() { 
		 int numberOfRecords = 0;
		 try{
			database.seek(initial_offset);
         	/** Get the value of each field in the record  */
         	while (database.getFilePointer() < database.length()) {
	           	String record[] = getSingleRecord();
	           	numberOfRecords++;
	   	 	}
		 }catch(IOException e){
			 logger.severe("Error getting number of records: " + e.getMessage());
		 }
         logger.info("Total Number of records in file: " + numberOfRecords);
         return numberOfRecords;
     } 
	 
	
	 public static String[] read(int recNo) throws RecordNotFoundException{
		 //logger.entering("DataDBAccess", "read", recNo);
		 logger.info("Attempting to read record number: " + recNo);
		 
		 try{
			database.seek(initial_offset + (fullRecordSize*recNo)); //offset + record position
		 	String record[] = getSingleRecord();
		 	logger.exiting("FileAccess", "read");
		 	return record;
		 }
		 catch (Exception e) {
             throw new RecordNotFoundException("Problem encountered reading recNo " +recNo+": "+ e.getMessage());		 
		 }
	 }
	 
	 
	 static void update(int recNo, String[] data) throws RecordNotFoundException{
		 //get record to update

		 if(recNo < 0 || recNo >= getNoOfRecords()){
			 throw new RecordNotFoundException("The record: " + recNo 
					 								+ " was not found");
		 }
		 int recordLocation = initial_offset + (recNo * fullRecordSize);
		 try{
			 database.seek(recordLocation);
			 byte[] record = new byte[fullRecordSize];
			 database.read(record);            

			 if (record[0] == INVALID) {				 
				 logger.severe("Cannot update Record. Record was Deleted.");
			 }
             else if(record[0] == VALID){
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
             } 
         }catch (Exception e){
                 throw new RecordNotFoundException("The record: " + recNo 
                         			+ " was not found, " + e.getMessage());
             }
	}
	 
 
	 public static int create(String [] data) throws DuplicateKeyException, IOException{
		 int numOfRecords = getNoOfRecords();
	 
		 database.seek(initial_offset);
		 byte[] record = new byte[fullRecordSize];
		 database.read(record);
		 int currentRec = 0;
		 while(record[0] == VALID && currentRec < numOfRecords){
			 database.read(record);
			 currentRec++;
		 }
		 if(record[0] == INVALID){
			 database.seek(initial_offset + (currentRec*fullRecordSize));
		 }
	 
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
		return currentRec;
}
	 
	 
	 static void delete(int recNo) throws RecordNotFoundException{

		 if(recNo < 0 || recNo >= getNoOfRecords()){
			 throw new RecordNotFoundException("The record you wish to delete:"
					 							+ recNo	+ " was not found");
		 }
		 int recordLocation = initial_offset + (recNo * fullRecordSize); 
		 try{
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
}
		 
	 }
	 
	 
	 public static int [] find(String [] criteria) throws RecordNotFoundException{
		 int counter = 0;
		 int totalRecords = getNoOfRecords();
		 int[] AllRecords = new int[totalRecords];	
		 try {
			 database.seek(initial_offset);
		 }catch(IOException e){
			 e.printStackTrace();
		 }
			
		 for(int i = 0; i < totalRecords; i++){
			 String [] record = read(i);
			 if(record[0].contains(criteria[0]) && record[1].contains(criteria[1])){
					 AllRecords[counter] = i;
					 counter++;
			 }
		 }
		 int[]  matchingRecords= new int[counter];
		 for(int i = 0; i < counter; i++){
			 matchingRecords[i] = AllRecords[i];
		 }	
		 return matchingRecords;
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
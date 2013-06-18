package suncertify.db;

import java.io.IOException;
import java.io.RandomAccessFile;



public class FileAccess {
	
	 private static final String databaseName = "db-2x3.db";
	 private static RandomAccessFile database = null;
	 
	 
	 //Start of data file
	 private static final int MAGIC_COOKIE_BYTES = 4;
	 private static final int NUMBER_OF_FIELDS_BYTES = 2;
	 
	 //for each field
	 private static final int FIELD_LENGTH_BYTES = 1;
	 private static final int FIELD_NAME_BYTES = 1;  
	 
	 //data section
	 private static final int RECORD_FLAG_BYTES = 1; 
	 private static final int VALID = 00; 
	 private static final int INVALID = 0xFF;
	 private static final String ENCODING = "US-ASCII";
	 
	 /**Set the individual lengths of the field record*/
	 static int[] FIELD_LENGTHS = {Subcontractor.name_Length,
    		 Subcontractor.location_Length,
    		 Subcontractor.specialties_Length,
    		 Subcontractor.size_Length,
    		 Subcontractor.rate_Length,
    		 Subcontractor.owner_Length}; 
	 

	final static int fullRecord = Subcontractor.entry_Length + RECORD_FLAG_BYTES;

	 
	 public static void FileAccess() throws IOException{
 
		 String Location = "C:\\Users\\Garvey\\Desktop\\";
		 connectToDB(Location);
		 getAllRecords();
		 read(27);
	 }
	 
	 private static void connectToDB(String dbLocation) throws IOException{
		 System.out.println("Connecting.....");
		 database = new RandomAccessFile(dbLocation + databaseName, "rw");	
		 System.out.println("Connected!");
		 database.seek(0);
	 }
	 
	 
	 private static int getInitialOffset() throws IOException{
		 database.seek(0);
		 //Read the start of the file as per the Data File Format
		 final byte [] magicCookieByteArray = new byte[MAGIC_COOKIE_BYTES];    
         final byte [] numberOfFieldsByteArray = new byte[NUMBER_OF_FIELDS_BYTES];  
         database.read(magicCookieByteArray);    
         database.read(numberOfFieldsByteArray);  
     
                  
		 /** The bytes that store the length of each field name    */
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
	     //final byte[] initial_offset = new byte[(int) database.getFilePointer()];
	     return initial_offset;
	 }
	 
	 
	 public static void read(int recNo) throws IOException{
		 recNo--; //offsets the zero value
		 final int offset = getInitialOffset();
		 int recordLocation = offset + (fullRecord*recNo);
		 database.seek(recordLocation);
		 String record[] = getSingleRecord();
		 
		 for (int i = 0; i < Subcontractor.number_Of_Fields; i++){
			 System.out.println("-->"+record[i]);
		 }  	   
	 }
	 
	 
	 private static String[] getSingleRecord() throws IOException{
		          
         String[] recordValues = new String[Subcontractor.number_Of_Fields]; 
         final byte [] flagByteArray = new byte[RECORD_FLAG_BYTES];
 	    database.read(flagByteArray);
  	    final int flag = getValue(flagByteArray);
      	if (flag == VALID) {  
          	System.out.println( "valid record");  
        } else{  
          System.out.println("deleted record");  
        }  

        for (int i = 0; i < recordValues.length; i++) {  
        	byte[] bytes = new byte[FIELD_LENGTHS[i]];  
        	database.read(bytes);  
            recordValues[i] = new String(bytes, ENCODING);             
        }
        return recordValues;
		 
	 }
	 
	 
	 private static void getAllRecords() throws IOException{ 
         final int offset = getInitialOffset();		
		 database.seek(offset);
		 int numberOfRecords = 0;
         /** Get the value of each field in the record  */
         while (database.getFilePointer() < database.length()) {
	            String record[] = getSingleRecord();
	            numberOfRecords++;
	            System.out.println("\n-----Record Num:" +numberOfRecords+"-----");
	            for (int i = 0; i < Subcontractor.number_Of_Fields; i++){
	   			 System.out.println("-->"+record[i]);
	   		 	 }
	        }
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
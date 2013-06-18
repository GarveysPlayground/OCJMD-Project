package suncertify.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;



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
	 private static final String ENCODING = "US-ASCII"; ; 
	 
	 
	 final byte[] fullRecord = new byte[Subcontractor.entry_Length];

	 
	 public static void FileAccess(String dbLocation) throws IOException{
		 System.out.println("Connecting.....");
		 database = new RandomAccessFile(dbLocation + databaseName, "rw");	
		 System.out.println("Connected!");
		 

		 database.seek(0);

          
		 		 
		 ////////////////////////////////////////////////////////////////////////
		 final byte [] magicCookieByteArray = new byte[MAGIC_COOKIE_BYTES];    
         final byte [] numberOfFieldsByteArray = new byte[NUMBER_OF_FIELDS_BYTES];  

         database.read(magicCookieByteArray);    
         database.read(numberOfFieldsByteArray);  

         final int magicCookie = getValue(magicCookieByteArray);   
         final int numberOfFields = getValue(numberOfFieldsByteArray); 
		      
         
         
		    /** The bytes that store the length of each field name    */
         final String [] fieldNames = new String[numberOfFields];
         final int [] fieldLengths = new int[numberOfFields];  
		    
		    
		 for (int i = 0; i < numberOfFields; i++) {  
             final byte nameLengthByteArray[] = new byte[FIELD_NAME_BYTES];  
             database.read(nameLengthByteArray);  
             final int nameLength = getValue(nameLengthByteArray);  

             final byte [] fieldNameByteArray = new byte[nameLength];  
             database.read(fieldNameByteArray);  
             fieldNames[i] = new String(fieldNameByteArray, ENCODING);  
             //System.out.println(fieldNames[i]);
             
             final byte [] fieldLength = new byte[FIELD_LENGTH_BYTES];  
             database.read(fieldLength);  
             fieldLengths[i] = getValue(fieldLength);  
           
         } 
		 //////////////////////////////////////////////////////////////////////// 
		 
		 
         int[] FIELD_LENGTHS = {Subcontractor.name_Length,
        		 Subcontractor.location_Length,
        		 Subcontractor.specialties_Length,
        		 Subcontractor.size_Length,
        		 Subcontractor.rate_Length,
        		 Subcontractor.owner_Length}; 
         
         String[] recordValues = new String[6]; // 6 is number of fields  
      
         
         System.out.println("Current DB location is : " +database.getFilePointer());
       
       while (database.getFilePointer() < database.length()) {
    	   final byte [] flagByteArray = new byte[RECORD_FLAG_BYTES];  
    	    database.read(flagByteArray);
      	    final int flag = getValue(flagByteArray);

    	   
            for (int i = 0; i < recordValues.length; i++) {  
            	byte[] bytes = new byte[FIELD_LENGTHS[i]];  
	        	database.read(bytes);  
	            recordValues[i] = new String(bytes, "US-ASCII"); 
	            System.out.println(fieldNames[i] + ": " + recordValues[i]);
	        }
            if (flag == VALID) {  
            	System.out.println( "Status: valid record");  
            } else{  
            System.out.println("Status: deleted record");  
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
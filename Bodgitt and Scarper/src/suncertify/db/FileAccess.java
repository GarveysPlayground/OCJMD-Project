package suncertify.db;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.Statement;






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
 
		 String Location = "C:\\Users\\epagarv\\Desktop\\";
		 connectToDB(Location);
		 //getAllRecords();
		 //read(13);
		 String[] newRec = new String[6];
		 newRec[0] = "Other Realms";
		 newRec[1] = "Cork";
		 newRec[2] = "comics";
		 newRec[3] = "4";
		 newRec[4] = "$80.00";
		 newRec[5] = "485";
		 //persist(13,newRec);

		 update(13,newRec);
		 read(13);
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
	 
	 
	 public static String[] read(int recNo) throws IOException{
		 recNo--; //offsets the zero value
		 final int offset = getInitialOffset();
		 int recordLocation = offset + (fullRecord*recNo);
		 database.seek(recordLocation);
		 String record[] = getSingleRecord();
		 
		 for (int i = 0; i < Subcontractor.number_Of_Fields; i++){
			 //System.out.println("-->"+record[i]);
		 }
		return record;
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
	            System.out.println("\n-----Record Num:" +numberOfRecords+"-----" + database.getFilePointer());
	            for (int i = 0; i < Subcontractor.number_Of_Fields; i++){
	   			 System.out.println("-->"+record[i]);
	   		 	 }
	        }
       } 
	 
	 private static void update(int recNo, String[] data) throws IOException{
		 
		 database.seek(4812);
		 String[] recordDetails = new String[Subcontractor.number_Of_Fields];
		 recordDetails = read(recNo);
		 database.seek(4812);

		
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 byte b = 00;
		 database.write(b);
		 for(int i = 0; i < Subcontractor.number_Of_Fields; i++){
			 //System.out.println(data[i].getBytes().length);
			int padding = FIELD_LENGTHS[i] - data[i].getBytes().length;
			System.out.println("Input: " + data[i].getBytes().length + "Padding: " + padding);
			database.write(data[i].getBytes());
			//database.writeUTF(data[i]);
			while(padding != 0){
				database.write(' ');
				padding --;
			}
			 
			

		 }
		 //database.write(' ');

		 
		 System.out.println("\n\n\nNEW VALUES ");
		 
		 
		 
		 database.seek(2433);
		 recordDetails = read(recNo);
		 for(int i = 0; i < Subcontractor.number_Of_Fields; i++){
			 System.out.println("update " + recordDetails[i]);
			 
		 }
		 
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 private static String emptyRecordString = null;
	 private static void persist(int recNo, String[] data) throws IOException {

	        // Perform as many operations as we can outside of the synchronized
	        // block to improve concurrent operations.

	       

	        
		 	emptyRecordString = new String(new byte[fullRecord]);
	        final StringBuilder out = new StringBuilder(emptyRecordString);

	        /** assists in converting Strings to a byte[] */
	        class RecordFieldWriter {
	            /** current position in byte[] */
	            private int currentPosition = 0;
	            /**
	             * converts a String of specified length to byte[]
	             *
	             * @param data the String to be converted into part of the byte[].
	             * @param length the maximum size of the String
	             */
	            void write(String data, int length) {
	                out.replace(currentPosition,
	                            currentPosition + data.length(),
	                            data);
	                currentPosition += length;
	            }
	        }
	        System.out.println("step 1");
	        RecordFieldWriter writeRecord = new RecordFieldWriter();
	        for(int i = 0; i < Subcontractor.number_Of_Fields; i++){
	        	writeRecord.write(data[i], FIELD_LENGTHS[i]);
	        }
	        System.out.println("step 2");
	        
	        // now that we have everything ready to go, we can go into our
	        // synchronized block & perform our operations as quickly as possible
	        // ensuring that we block other users for as little time as possible.

	        
	           // database.seek(2433);
	           // database.write(out.toString().getBytes());
	           // database.writeUTF("test1");
	            //database.writeChars("this is a test");
	            //database.write(arg0, arg1, arg2)

	        
	    }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 public static byte [] truncateName (String name)
	 {
	     byte [] result = new byte [8];
	     for (int i = 0; i < 8; i++)
	         result [i] = i < name.length () ? (byte)name.charAt (i) : (byte)' ';
	     return result;
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
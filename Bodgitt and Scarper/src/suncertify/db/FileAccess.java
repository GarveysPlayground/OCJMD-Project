package suncertify.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;



public class FileAccess {
	
	 private static final String databaseName = "db-2x3.db";
	 private static RandomAccessFile database = null;
	 final byte[] fullRecord = new byte[Subcontractor.entry_Length];

	 
	 public static void FileAccess(String dbLocation) throws IOException{
		 System.out.println("Connecting.....");
		 database = new RandomAccessFile(dbLocation + databaseName, "rw");	
		 System.out.println("Connected!");
		 

		 database.seek(55);

         
         
         int[] FIELD_LENGTHS = {Subcontractor.name_Length,
        		 Subcontractor.location_Length,
        		 Subcontractor.specialties_Length,
        		 Subcontractor.size_Length,
        		 Subcontractor.rate_Length,
        		 Subcontractor.owner_Length}; 
         
         String[] recordValues = new String[6]; // 6 is number of fields  
         
        for (int i = 0; i < recordValues.length; i++) {  
        byte[] bytes = new byte[FIELD_LENGTHS[i]];  
        	 database.read(bytes);  
             recordValues[i] = new String(bytes, "US-ASCII"); 
            System.out.println("III" + recordValues[i] + "III");

        
        }

	 }
	 	 
}






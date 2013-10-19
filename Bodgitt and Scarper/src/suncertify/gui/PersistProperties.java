package suncertify.gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class PersistProperties {
	
	private static final String DATABASE_LOC = "dbLocation :";
	
	private static final String RMI_PORT = "rmiPort :";
	
	private static final String RMI_HOST = "rmiHost :";
	 
	private static final String FILE_NAME = "suncertify.properties";
	
	private static final String FILE_DIR = System.getProperty("user.dir");
	
	private static File propertiesFile = new File(FILE_NAME, FILE_DIR);

	
    private PersistProperties() {

    }
    
    private static void createFile() throws IOException {

    	PrintWriter writer = new PrintWriter(propertiesFile, "UTF-8");
    	writer.println(DATABASE_LOC + "\n" + RMI_PORT + "\n" + RMI_HOST);
    	writer.close();
    }
    
    private static void readFile() throws IOException {
    	String line;
    	InputStream FileIn =new FileInputStream(propertiesFile);	
    	BufferedReader reader = new BufferedReader(new InputStreamReader(FileIn));
    	while ((line = reader.readLine()) != null) {
    	    // Deal with the line
    	}
    	reader.close();
    }
    
    
    
}

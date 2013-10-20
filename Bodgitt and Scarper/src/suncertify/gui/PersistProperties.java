package suncertify.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class PersistProperties {
	
	private static final String DATABASE_LOC = "dbLocation : ";
	
	private static final String RMI_PORT = "rmiPort : ";
	
	private static final String RMI_HOST = "rmiHost : ";
	private static final String SETUP = DATABASE_LOC + "\n" + 
										 RMI_PORT + "\n" +
										 RMI_HOST;
	 
	private static final String FILE_NAME = "suncertify.properties";
	
	private static final String FILE_DIR = System.getProperty("user.dir");
	
	
	private static File propertiesFile = new File(FILE_DIR,FILE_NAME);

	
	
	public PersistProperties(){
		
		if(!propertiesFile.exists()){
			System.out.println("Not Exists");
			try {
				createFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			System.out.println("Exists");
		}
	}
	
	void writeToFile(String property , String value) throws IOException{
		FileWriter file = new FileWriter(propertiesFile);  
		PrintWriter OutputFile = new PrintWriter(file);
		OutputFile.println("");
		OutputFile.close();
	}
	
	void ReadFromFile(String property , String value) throws IOException{

		String input;
		BufferedReader br = new BufferedReader(new FileReader(propertiesFile));
		while ((input = br.readLine()) != null) {
			System.out.println(input);
		}
		br.close();
	}
	
	void createFile() throws IOException{
		System.out.println("Creating");
		propertiesFile.createNewFile();
		FileWriter file = new FileWriter(propertiesFile);  
		PrintWriter OutputFile = new PrintWriter(file);
		OutputFile.println(SETUP);
		OutputFile.close();
	}
	
	
    
    
}

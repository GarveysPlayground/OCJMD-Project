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

	public enum property{PORT, HOST, FILELOCATION}
	
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
		
		
		
		try {
			writeToFile("host", "MrsSmith");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void writeToFile(String property , String value) throws IOException{
		System.out.println("writing");		
		
			FileWriter file = new FileWriter(propertiesFile);  
			PrintWriter OutputFile = new PrintWriter(file);
			//OutputFile.println(SETUP);
		
		String input;
		BufferedReader br = new BufferedReader(new FileReader(propertiesFile));
		
		while ((input = br.readLine()) != null) {
			System.out.println("--" + input);
			if(property == "dbLocation" && input.contains(DATABASE_LOC)){
				OutputFile.println(DATABASE_LOC + value);	
			}else if(property == "host" && input.contains(RMI_HOST)){
				OutputFile.println(RMI_HOST + value);
			}else if(property == "port" && input.contains(RMI_PORT)){
				OutputFile.println(RMI_PORT+ value);
			}
		}
		br.close();
		
		OutputFile.println("");
		OutputFile.close();
	}
	
	String ReadFromFile(String property) throws IOException{

		String input;
		BufferedReader br = new BufferedReader(new FileReader(propertiesFile));
		
		while ((input = br.readLine()) != null) {
			if(property == "dbLocation" && input.contains(DATABASE_LOC)){
				input = input.replace(DATABASE_LOC, "");
				break;
			}else if(property == "host" && input.contains(RMI_HOST)){
				input = input.replace(RMI_HOST, "");
				break;
			}else if(property == "port" && input.contains(RMI_PORT)){
				input = input.replace(RMI_PORT, "");
				break;
			}
		}
		br.close();
		return input;
	}
	
	void createFile() throws IOException{
		System.out.println("Creating");
		propertiesFile.createNewFile();
		System.out.println("---" + SETUP);
		
		PrintWriter writer = new PrintWriter(propertiesFile);
		writer.println(SETUP);
		writer.close();
		//FileWriter file = new FileWriter(propertiesFile);  
		//PrintWriter OutputFile = new PrintWriter(file);
		//OutputFile.println("cat");
		//OutputFile.close();
		
	
	}
	
	
    
    
}

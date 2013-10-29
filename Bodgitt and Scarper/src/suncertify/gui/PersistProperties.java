package suncertify.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


public class PersistProperties {
	
private static final String FILE_NAME = "suncertify.properties";
	
	private static final String FILE_DIR = System.getProperty("user.dir");
	
	private static File propertiesFile = new File(FILE_DIR, FILE_NAME);
	
	static Properties properties = null;
	
	public PersistProperties() {
		if (!propertiesFile.exists()) {
			properties = new Properties();
			
			properties.setProperty("database", "");
			properties.setProperty("host", "");
			properties.setProperty("port", "");
    		
			try {
				properties.store(new FileOutputStream(propertiesFile), null);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			properties = new Properties();
			try {
				properties.load(new FileInputStream(propertiesFile));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
	
	public void setProperty(String property, String value) {
		properties.setProperty(property, value);
		try {
			properties.store(new FileOutputStream(propertiesFile), null);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public  String getProperty(String property) {
		String value = properties.getProperty(property);
		return value;
	}
    
    
}

/* Project: Bodgitt and Scarper Version 2.3.3
 * @author: Patrick Garvey
 * Last Modified: 30th Oct 2013
 * PersistProperties.java
 */
package suncertify.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;


/**
 * The Class PersistProperties is used to store the configuration properties
 * to suncertify.properties. When available this file gives default values
 * for the application on startup
 */
public class PersistProperties {
	
	/** The Constant FILE_NAME. */
	private static final String FILE_NAME = "suncertify.properties";
	
	/** The Constant FILE_DIR. */
	private static final String FILE_DIR = System.getProperty("user.dir");
	
	/** The properties file. */
	private static File propertiesFile = new File(FILE_DIR, FILE_NAME);
	
	/** The properties. */
	private static Properties properties = null;
	
	/** The logger. */
 	private static Logger logger = Logger.getLogger("suncertify.gui");
	
	/**
	 * Instantiates a new persist properties entering the default values
	 * to expect once run.
	 */
	public PersistProperties() {
		logger.entering("PersistProperties", "PersistProperties");
		if (!propertiesFile.exists()) {
			logger.info("Creating properties file");
			properties = new Properties();
			
			properties.setProperty("database", "");
			properties.setProperty("host", "");
			properties.setProperty("port", "");
    		
			try {
				properties.store(new FileOutputStream(propertiesFile), null);
			} catch (FileNotFoundException e) {
				logger.warning("Issue locating file" + e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				logger.severe("Issue writing to properties files" 
						+ e.getMessage());
				e.printStackTrace();
			}
		} else {
			properties = new Properties();
			try {
				properties.load(new FileInputStream(propertiesFile));
			} catch (IOException e) {
				logger.severe("Issue writing to properties files" 
						+ e.getMessage());
				e.printStackTrace();
			}			
		}
	}
	
	/**
	 * Sets the property in suncertify.properties.
	 *
	 * @param property the property
	 * @param value the value
	 */
	public final void setProperty(final String property, final String value) {
		properties.setProperty(property, value);
		try {
			properties.store(new FileOutputStream(propertiesFile), null);
		} catch (FileNotFoundException e) {
			logger.severe("Issue finding properties file " 
					+ e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.severe("Issue writing to properties files" 
					+ e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Gets the property from suncertify.properties.
	 *
	 * @param property the property
	 * @return the property
	 */
	public final String getProperty(final String property) {
		String value = properties.getProperty(property);
		return value;
	}
    
    
}

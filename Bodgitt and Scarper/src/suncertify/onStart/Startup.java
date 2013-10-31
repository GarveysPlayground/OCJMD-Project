/* Project: Bodgitt and Scarper Version 2.3.3
 * Author: Patrick Garvey
 * Last Modified: 29th Oct 2013
 * Startup.java
 */
package suncertify.onStart;

import java.util.logging.Level;
import java.util.logging.Logger;

import suncertify.gui.DialogBoxViews;

/**
 *  This is the beginning of the application,
 *  This class deals with the arguments passed in
 *  by the end user.
 */
public class Startup {
	
	/** The dialog GUI instance. */
	private static DialogBoxViews dialogs = new DialogBoxViews();
	 
 	/** Logger used for displaying information to the end user.*/
 	private static Logger logger = Logger.getLogger("startUp");
	 
 	/** The connection type as dictated by the end user.*/
 	private static ApplicationMode connectionType = null;
	
	 /**
	 * Sets the application mode that the end user dictated.
	 * @param appMode the new application mode
	 */
	public static void setApplicationMode(final ApplicationMode appMode) {
	     connectionType = appMode;
	 }
	 
	 /**
 	 * Gets the application mode that the end user selected.
 	 *
 	 * @return the application mode
 	 */
 	public static ApplicationMode getApplicationMode() {
	     return connectionType;
	 }


	/**
	 * The main method. Reads in the end users arguments and
	 * starts one of three GUIs depending on the argument.
	 * 
	 * param "server" starts the <code>rmiConnectionWindow()</code> window, 
	 * param "alone" starts the <code>databaseLocationWindow</code> window, 
	 * An empty param calls <code>rmiClient()</code> window,
	 * Any other param returns and error.	
	 * 
	 * @param args the arguments
	 */
	public static void main(final String[] args) {

		if (args.length == 0) {
			logger.info("Starting Client Connection");
			setApplicationMode(ApplicationMode.NETWORK);
			dialogs.rmiClient();			

		} else if (ApplicationMode.SERVER.name().equalsIgnoreCase(args[0])) {
			logger.info("Starting Bodgitt & Scarper server mode.");
			setApplicationMode(ApplicationMode.SERVER);
        	dialogs.rmiConnectionWindow();

		} else if (ApplicationMode.ALONE.name().equalsIgnoreCase(args[0])) {
        	logger.info("Starting standalone mode");
        	setApplicationMode(ApplicationMode.ALONE);
        	dialogs.databaseLocationWindow();
        
		} else {
            logger.log(Level.INFO, "Invalid param supplied to application: " 
                    + args[0]);
        }
	}
}

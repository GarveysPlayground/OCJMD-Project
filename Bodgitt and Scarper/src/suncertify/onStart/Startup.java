package suncertify.onStart;

import java.util.logging.Level;
import java.util.logging.Logger;

import suncertify.gui.DialogBoxViews;

//import suncertify.gui.MainWindowView;



public class Startup {
	private static DialogBoxViews dialogs = new DialogBoxViews();
	 private static Logger logger = Logger.getLogger("startUp");
	 private static ApplicationMode connectionType = null;
	// static MainWindowView startUp = new MainWindowView();
	
	 public static void setApplicationMode(ApplicationMode appMode) {
	     connectionType = appMode;
	 }
	 
	 public static ApplicationMode getApplicationMode() {
	     return connectionType;
	 }


	public static void main(String[] args) {
		System.out.println("humble beginings");
		System.out.println("humble beginings -->" + args.length);
		 launch(args);
	}

	private static void launch(String[] args) {
		if (args.length == 0) {
		//	setApplicationMode(ApplicationMode.NETWORK);
		//	dialogs.rmiClient();

			setApplicationMode(ApplicationMode.ALONE);
        	dialogs.databaseLocationWindow();
			
		} else if (ApplicationMode.SERVER.name().equalsIgnoreCase(args[0])) {
        	setApplicationMode(ApplicationMode.SERVER);
        	dialogs.rmiConnectionWindow();
        	//startUp.DialogSelecter(ApplicationMode.SERVER);
        } else if (ApplicationMode.ALONE.name().equalsIgnoreCase(args[0])) {
        	setApplicationMode(ApplicationMode.ALONE);
        	dialogs.databaseLocationWindow();
        	//startUp.DialogSelecter(ApplicationMode.ALONE);
        } else {
            logger.log(Level.INFO, "Invalid param supplied to application: " 
                    + args[0]);
        }
		
	}


}

package suncertify.onStart;

import java.util.logging.Logger;

import suncertify.gui.DialogBoxViews;
import suncertify.gui.MainWindowView;
import suncertify.gui.PersistProperties;

public class Startup {

	 private Logger logger = Logger.getLogger("startUp");
	 private static String connectionType = null;
	 
	 public Startup(String[] args) {
		// DialogBoxViews connection = new DialogBoxViews();
		// PersistProperties properties = new PersistProperties();
		 if (args.length == 0) {	

			//setConnectionType("remote");   

	        setConnectionType("alone");
			
			//setConnectionType("server");  

	 
	         MainWindowView startUp = new MainWindowView();
			 startUp.DialogSelecter();
	        
			// PersistProperties properties = new PersistProperties();
			//proper.setProperty("port", "1234");
			// proper.setProperty("host", "localhost");
			// properties.
		 
		 
		 }else if ("server".equalsIgnoreCase(args[0])) {
	        //   connection.connectionType("server");
	        }else if ("alone".equalsIgnoreCase(args[0])) {
		     //  connection.connectionType("alone");
		    }
	}
	 
	 
	 public void setConnectionType(String type)
	 {
	     connectionType = type;
	 }
	 
	 public static String getConnectionType()
	 {
	     return connectionType;
	 }


	public static void main(String[] args){
			Startup start = new Startup(args);
	}
}

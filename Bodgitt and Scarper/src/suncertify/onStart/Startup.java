package suncertify.onStart;

import java.util.logging.Logger;

import suncertify.gui.DialogBoxViews;
import suncertify.gui.MainWindowView;

public class Startup {

	 private Logger logger = Logger.getLogger("startUp");
	 private static String connectionType = null;
	 
	 public Startup(String[] args) {
		 DialogBoxViews connection = new DialogBoxViews();
		 if (args.length == 0) {	           
			// setConnectionType("remote");   
			// connection.connectionType("");
	            	           
			// connection.connectionType("alone");
	         setConnectionType("alone");
			
			// setConnectionType("remote");  
			// connection.connectionType("server");
			 MainWindowView gui = new MainWindowView();
			 gui.MainWindowView();	 
	            
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

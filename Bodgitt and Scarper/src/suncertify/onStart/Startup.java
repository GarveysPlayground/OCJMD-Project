package suncertify.onStart;

import java.util.logging.Logger;

import suncertify.gui.DialogBoxViews;

public class Startup {

	 private Logger logger = Logger.getLogger("startUp");
	 
	 public Startup(String[] args) {
		 if (args.length == 0 || "alone".equalsIgnoreCase(args[0])) {
	            DialogBoxViews connection = new DialogBoxViews();
	            connection.connectionType("");
	            //connection.connectionType("alone");
	            connection.connectionType("server");
	        } else if ("server".equalsIgnoreCase(args[0])) {
	           // new ServerWindow();
	        }
	}

	public static void main(String[] args){
			 Startup start = new Startup(args);
	}
}

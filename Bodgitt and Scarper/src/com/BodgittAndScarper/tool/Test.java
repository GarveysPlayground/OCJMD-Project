package com.BodgittAndScarper.tool;

import java.io.FileNotFoundException;
import java.io.IOException;

import suncertify.*;
import suncertify.db.DuplicateKeyException;
import suncertify.db.FileAccess;
import suncertify.db.RecordNotFoundException;
import suncertify.gui.View;

public class Test {
	
	
	
	public static void main(String args[]) throws IOException, RecordNotFoundException, DuplicateKeyException{
		

		
		System.out.println("Hello world");
		
		FileAccess.FileAccess();
		
		View gui = new View();
		gui.makeMainWindow();
		//gui.makeStandaloneWindow();
	}

}

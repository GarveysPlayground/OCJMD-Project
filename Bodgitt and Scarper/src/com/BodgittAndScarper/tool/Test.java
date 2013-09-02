package com.BodgittAndScarper.tool;

import java.io.FileNotFoundException;
import java.io.IOException;

import suncertify.*;
import suncertify.db.Data;
import suncertify.db.DuplicateKeyException;
import suncertify.db.FileAccess;
import suncertify.db.RecordNotFoundException;
import suncertify.gui.View;

public class Test {
	
	
	
	public static void main(String args[]) throws IOException, RecordNotFoundException, DuplicateKeyException{
		

		
		System.out.println("Hello world");
		
		 String[] newRec = new String[6];
		 newRec[0] = "patrick& holmes";
		 newRec[1] = "Atlantis";
		 newRec[2] = "Plumbing";
		 newRec[3] = "4";
		 newRec[4] = "$80.00";
		 newRec[5] = "";
		 
		 Data data = new Data();
		
		
		FileAccess.FileAccess();

		//data.delete(58);
		//data.update(13, newRec);
		
		 
		View gui = new View();
		gui.makeMainWindow();
		//gui.makeStandaloneWindow();
	
		
		String[] search = new String[2];
		search[0] = "Fred";
		search[1] = "";
		 FileAccess.find(search);
		
	}

}

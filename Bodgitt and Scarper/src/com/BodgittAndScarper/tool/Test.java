package com.BodgittAndScarper.tool;

import java.io.FileNotFoundException;
import java.io.IOException;

import suncertify.*;
import suncertify.db.Data;
import suncertify.db.DuplicateKeyException;
import suncertify.db.FileAccess;
import suncertify.db.RecordNotFoundException;
import suncertify.gui.MainWindowView;
import suncertify.gui.View;

public class Test {
	
	
	
	public static void main(String args[]) throws IOException, RecordNotFoundException, DuplicateKeyException{
		

		
		System.out.println("Hello world");
		
		 String[] newRec = new String[6];
		 newRec[0] = "BBB";
		 newRec[1] = "Jericho";
		 newRec[2] = "Plumbing";
		 newRec[3] = "4";
		 newRec[4] = "$80.00";
		 newRec[5] = "";
		 
		 Data data = new Data();
		
		
		FileAccess.FileAccess();

		//data.delete(35);
		//data.update(13, newRec);
		//int newRecNo = data.create(newRec);
		//System.out.println("\n\n\n"+ newRecNo); 
		MainWindowView gui = new MainWindowView();
		gui.MainWindowView();
		//gui.makeStandaloneWindow();
	
		/*
		String[] search = new String[2];
		search[0] = "Bitter";
		search[1] = "Small";
		 int[] AllRecords = FileAccess.find(search);
		 for(int i = 0 ; i < AllRecords.length;i++){
			 System.out.println( "--->" + AllRecords[i]);
		 }
		 */
		
		
	}

}

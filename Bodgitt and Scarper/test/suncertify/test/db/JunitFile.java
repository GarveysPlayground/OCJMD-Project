package suncertify.test.db;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import suncertify.db.Data;
import suncertify.db.DuplicateKeyException;
import suncertify.db.FileAccess;
import suncertify.db.Subcontractor;


public class JunitFile {
	 private static final String DB_PATH = "C:\\Users\\Garvey\\Google Drive\\Java\\SCJD\\mine\\db-2x3.db";
Data data = null;
public void JunitFile() throws DuplicateKeyException{
	try {
		data = new Data(DB_PATH);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
	
	
	
	

	 final String[] dataEntry = new String[6];
		dataEntry[0] = "Garvey";
		dataEntry[1] = "Athlone";
		dataEntry[2] = "Playing, Trouble Making";
		dataEntry[3] = "44";
		dataEntry[4] = "$1.00";
		dataEntry[5] = "12345678";
		
		for(int i = 0; i < 20; i++){
			data.create(dataEntry);
		}
		
}

public void create(){
	
	 final String[] dataEntry = new String[6];
		dataEntry[0] = "Garvey";
		dataEntry[1] = "Athlone";
		dataEntry[2] = "Playing, Trouble Making";
		dataEntry[3] = "44";
		dataEntry[4] = "$1.00";
		dataEntry[5] = "12345678";
		
		for(int i = 0; i < 20; i++){
			
			
		}
	
	
	
}
}

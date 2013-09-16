package suncertify.gui;

import suncertify.db.Data;
import suncertify.db.RecordNotFoundException;


public class TableController {
	Data dbConnect = new Data();
	TableModel TableRecs = new TableModel();	
	
	public TableModel getContractors(String [] criteria){
		TableRecs = new TableModel();
	    try {
	    	String[] record;
			int[] recordNumbers = dbConnect.find(criteria);
			for(int i = 0; i < recordNumbers.length;i++){
			record = dbConnect.read(recordNumbers[i]);
			System.out.println("Record object values" + record);
				TableRecs.addSubcontractorRecord(record);
			}
		} catch (RecordNotFoundException e) {
			
		}
		return TableRecs;
	}
	
	public TableModel getAllContractors(){
		String[] allValues = new String[2];
		allValues[0] =  " ";
		allValues[1] =  " ";		
		return getContractors(allValues);
	}
	
	public void updateContractor(int record, String Customer){
	//String[] data = null;
	System.out.println("I am being called");
	TableRecs.setValueAt(Customer, record, 5);
	
	}

}


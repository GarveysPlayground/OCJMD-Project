package suncertify.gui;

import suncertify.db.Data;
import suncertify.db.RecordNotFoundException;


public class TableController {
	Data dbConnect = new Data();
	
	public TableModel getContractors(String [] criteria){
		TableModel TableRecs = new TableModel();
	    try {
	    	String[] record;
			int[] recordNumbers = dbConnect.find(criteria);
			for(int i = 0; i < recordNumbers.length;i++){
				record = dbConnect.read(recordNumbers[i]);
				TableRecs.addSubcontractorRecord(record);
				//System.out.println("find returning:" + recordNumbers[i]);
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
	
	//dbConnect.update(record, Customer);
	}

}


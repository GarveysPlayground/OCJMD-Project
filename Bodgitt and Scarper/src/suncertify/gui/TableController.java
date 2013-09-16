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
	
	public String[] getSelectedContractor(int row, int column){
		TableRecs = new TableModel();
		String recordConstants[] = new String[6];
		for(int i = 0; i <= 5; i++){
		String record = TableRecs.getSubcontractorRecord(row, i);
		System.out.println(record);
		recordConstants[i] = record;
		}
		return recordConstants;
	}
	
	public int[] getContractorRecNo(String[] criteria) throws RecordNotFoundException{
		int[] recNo = dbConnect.find(criteria);
		return recNo;
	}
	
	public void updateContractor(int record, String Customer){
	//String[] data = null;
	System.out.println("I am being called");
	TableRecs.setValueAt(Customer, record, 5);
	
	}

}


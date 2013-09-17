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
	
	public String[] getSelectedContractor(int rowIndex){
		int columns = TableRecs.getColumnCount();  
	        String[] s = new String[columns];  
	        for(int col = 0; col < columns; col++)  
	        {  
	            Object colValue = TableRecs.getValueAt(rowIndex, col);  
	            //System.out.println(colValue.toString());
	            s[col] = colValue.toString();  
	        }  
	       return s;
	}
		
	public void updateContractor(int row, String Customer) throws RecordNotFoundException{
	TableRecs.setValueAt(Customer, row, 5);

	String[] data = getSelectedContractor(row); 
	System.out.println(data[0]);
	System.out.println("Got record");
	
	
	int[] recNo = dbConnect.find(data);
	System.out.println("REcord to update is : " + recNo[0]);
	if(recNo.length == 1){
		dbConnect.update(recNo[0], data);
	}else{
		
	}
	
	System.out.println("Controller: Update called");
	//TableRecs.getSubcontractorRecord(row, 5);
	
	
	//TableRecs.setValueAt(Customer, record, 5);
	
	
	}

}


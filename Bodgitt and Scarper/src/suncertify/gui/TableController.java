package suncertify.gui;

import java.rmi.RemoteException;

import suncertify.db.Data;
import suncertify.db.RecordNotFoundException;
import suncertify.onStart.Startup;
import suncertify.rmi.ClientRemoteConnect;
import suncertify.rmi.ContractorDBRemote;


public class TableController {
	
	

	
	
	
	Data localConnection = new Data();
	TableModel TableRecs = new TableModel();	
	String appType = Startup.getConnectionType();
	private ContractorDBRemote remoteConnection = null;
	
	
	
	
	
	public TableModel getContractors(String [] criteria){
		TableRecs = new TableModel();
		String[] record;
		int[] recordNumbers;
		try {
			System.out.println("--------------"+appType);
	    	if(appType == "alone"){
	    		
				 recordNumbers = localConnection.find(criteria);
				for(int i = 0; i < recordNumbers.length;i++){
					record = localConnection.read(recordNumbers[i]);
					System.out.println("Record object values" + record);
					TableRecs.addSubcontractorRecord(record);
				//	return TableRecs;
				}
	    	}else if(appType == "remote"){
	    		remoteConnection = 	ClientRemoteConnect.getConnection("localhost", 4566);
	    		System.out.println("-------------------AAAAA----"+criteria.length);
	    		 recordNumbers = remoteConnection.find(criteria);
	    		System.out.println("-------------------BBBBB");
	    		System.out.println(recordNumbers.length);
				for(int i = 0; i < recordNumbers.length;i++){
					System.out.println("-------------------CCCCC");
					record = remoteConnection.read(recordNumbers[i]);
					System.out.println("Record object values" + record);
					TableRecs.addSubcontractorRecord(record);}
	    	}
		} catch (RecordNotFoundException | RemoteException e) {
			
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
	            s[col] = colValue.toString();  
	        }  
	       return s;
	}
		
	public void updateContractor(int row, String Customer) throws RecordNotFoundException{
	TableRecs.setValueAt(Customer, row, 5);

	String[] data = getSelectedContractor(row); 
	System.out.println(data[0]);
	System.out.println("Got record");
	
	
	int[] recNo = localConnection.find(data);
	System.out.println("REcord to update is : " + recNo[0]);
	if(recNo.length == 1){
		localConnection.update(recNo[0], data);
	}else{
		
	}
	
	System.out.println("Controller: Update called");

	
	}

}


package suncertify.gui;

import java.rmi.RemoteException;

import suncertify.db.Data;
import suncertify.db.RecordNotFoundException;
import suncertify.onStart.Startup;
import suncertify.rmi.ClientRemoteConnect;
import suncertify.rmi.ContractorDBRemote;


public class TableController {
		
	TableModel TableRecs = new TableModel();	
	String appType = Startup.getConnectionType();
	private ContractorDBRemote remoteConnection = null;
	private Data localConnection = null;
	
	public TableController(String host, int port){
		if(appType == "alone"){			
			localConnection = new Data();
		
		}else if(appType == "remote"){
			try {
				remoteConnection = 	ClientRemoteConnect.getConnection(host, port);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
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
	    		 recordNumbers = remoteConnection.find(criteria);
				for(int i = 0; i < recordNumbers.length;i++){
					record = remoteConnection.read(recordNumbers[i]);
					TableRecs.addSubcontractorRecord(record);}
	    	}
		} catch (RecordNotFoundException | RemoteException e) {
			System.out.println("Issue populating JTable");
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
	
	public int getRecordNoFromRow(int row) throws RecordNotFoundException{
		int recNo[] = null;
		String[] recordDetails = getSelectedContractor(row);
		if(appType == "alone"){			
			recNo = localConnection.find(recordDetails);
		}else if(appType == "remote"){
			try {
				recNo = remoteConnection.find(recordDetails);
			} catch (RemoteException e) {
				System.err.println("Connection Error : " + e);
			}
		}
		return recNo[0];
	}
		
	public void updateContractor(int row, String Customer) throws RecordNotFoundException{
		TableRecs.setValueAt(Customer, row, 5);
		String[] data = getSelectedContractor(row); 		
		int recNo = getRecordNoFromRow(row);
		
		
		if(appType == "alone"){
			localConnection.update(recNo, data);
		}else if(appType == "remote"){
			try {
				remoteConnection.update(recNo, data);
			} catch (RemoteException e) {
				System.err.println("Connection Error : " + e);
			}
		}	
	}
	
	
	public int getRecNo(int row) throws RecordNotFoundException{
		String[] data = getSelectedContractor(row); 		
		int[] recNo = null;
		if(appType == "alone"){			
			recNo = localConnection.find(data);
		}else if(appType == "remote"){
			try {
				recNo = remoteConnection.find(data);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		System.out.println("About to return rec No " + recNo[0]);
		return recNo[0];
	}
}


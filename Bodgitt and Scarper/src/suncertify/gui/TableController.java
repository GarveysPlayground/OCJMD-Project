package suncertify.gui;

import java.rmi.RemoteException;

import suncertify.db.Data;
import suncertify.db.RecordNotFoundException;
import suncertify.onStart.ApplicationMode;
import suncertify.onStart.Startup;
import suncertify.rmi.ClientRemoteConnect;
import suncertify.rmi.ContractorDBRemote;


public class TableController {
		
	TableModel TableRecs = new TableModel();	
	ApplicationMode appType = Startup.getApplicationMode();
	private ContractorDBRemote remoteConnection = null;
	private Data localConnection = null;
	
	public TableController(String host, int port){
		if(appType == ApplicationMode.ALONE){			
			localConnection = new Data();
		
		}else if(appType == ApplicationMode.NETWORK){
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
	    	if(appType == ApplicationMode.ALONE){	
				 recordNumbers = localConnection.find(criteria);
				for(int i = 0; i < recordNumbers.length;i++){
					record = localConnection.read(recordNumbers[i]);
					TableRecs.addSubcontractorRecord(record);
				}
	    	}else if(appType == ApplicationMode.NETWORK){
	    		 recordNumbers = remoteConnection.find(criteria);
				for(int i = 0; i < recordNumbers.length;i++){
					record = remoteConnection.read(recordNumbers[i]);
					TableRecs.addSubcontractorRecord(record);}
	    	}
		} catch (RecordNotFoundException | RemoteException e) {
			System.err.println("Issue populating JTable");
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
		if(appType == ApplicationMode.ALONE){			
			recNo = localConnection.find(recordDetails);
		}else if(appType == ApplicationMode.NETWORK){
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
		
		if(appType == ApplicationMode.ALONE){
			localConnection.update(recNo, data);
		}else if(appType == ApplicationMode.NETWORK){
			try {
				remoteConnection.update(recNo, data);
			} catch (RemoteException e) {
				System.err.println("Connection Error : " + e);
			}
		}
	}
}


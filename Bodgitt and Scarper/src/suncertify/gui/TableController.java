/* Project: Bodgitt and Scarper Version 2.3.3
 * @author: Patrick Garvey
 * Last Modified: 30th Oct 2013
 * TableController.java
 */
package suncertify.gui;

import java.rmi.RemoteException;
import java.util.logging.Logger;

import suncertify.db.Data;
import suncertify.db.RecordNotFoundException;
import suncertify.onStart.ApplicationMode;
import suncertify.onStart.Startup;
import suncertify.rmi.ClientRemoteConnect;
import suncertify.rmi.ContractorDBRemote;


/**
 * The Class TableController is used has a helper class for handling table
 * requests from the end user. This class interacts with the table model and
 * the Data class to help further distance the view from any data handling.
 */
public class TableController {
		
	/** The Table records . */
	private TableModel tableRecs = new TableModel();	
	
	/** The application type. The client is either connecting locally 
	 * or remotely*/
	private ApplicationMode appType = Startup.getApplicationMode();
	
	/** Create an instance of Data for remote connection use. */
	private ContractorDBRemote remoteConnection = null;
	
	/** Create an instance of Data for local connection use. */
	private Data localConnection = null;
	
	/** The logger. */
 	private static Logger logger = Logger.getLogger("suncertify.gui");
	
	/**
	 * Instantiates a new table controller determining weather a local
	 * or remote connection is to be used.
	 *
	 * @param host the host
	 * @param port the port
	 */
	public TableController(final String host, final int port) {
		if (appType == ApplicationMode.ALONE) {			
			localConnection = new Data();		
		} else if (appType == ApplicationMode.NETWORK) {
			try {
				remoteConnection = 	
						ClientRemoteConnect.getConnection(host, port);
			} catch (RemoteException e) {
				logger.severe("Issue establishing a connection with " + host
						+ " : " + port + " " + e.getMessage());
			}
		}
	}
	
	
	/**
	 * Searches the database for all contractors that match the specified 
	 * String [] Criteria.
	 *
	 * @param criteria The record values to search for
	 * @return the contractors
	 */
	public final TableModel getContractors(final String [] criteria) {
		logger.info("Fetching contractors");
		String[] record;
		int[] recordNumbers;
		try {
	    	if (appType == ApplicationMode.ALONE) {	
				 recordNumbers = localConnection.find(criteria);
				for (int i = 0; i < recordNumbers.length; i++) {
					record = localConnection.read(recordNumbers[i]);
					tableRecs.addSubcontractorRecord(record);
				}
	    	} else if (appType == ApplicationMode.NETWORK) {
	    		 recordNumbers = remoteConnection.find(criteria);
				for (int i = 0; i < recordNumbers.length; i++) {
					record = remoteConnection.read(recordNumbers[i]);
					tableRecs.addSubcontractorRecord(record);
				}
	    	}
		} catch (RecordNotFoundException | RemoteException e) {
			logger.severe("Issue with getting list of contractors, "
					+ "Check connections" + e.getMessage());
		}
		return tableRecs;
	}
	
	/**
	 * Gets the all contractors.
	 *
	 * @return the all contractors
	 */
	public final TableModel getAllContractors() {
		String[] allValues = new String[2];
		allValues[0] =  " ";
		allValues[1] =  " ";		
		return getContractors(allValues);
	}
	
	/**
	 * Gets the selected contractor column values as a String [].
	 *
	 * @param rowIndex the row index
	 * @return the selected contractor
	 */
	public final String[] getSelectedContractor(final int rowIndex) {
		int columns = tableRecs.getColumnCount();  
	        String[] record = new String[columns];  
	        for (int col = 0; col < columns; col++) {
	            Object colValue = tableRecs.getValueAt(rowIndex, col);  
	            record[col] = colValue.toString();  
	        }  
	       return record;
	}
	
	/**
	 * The method <code>getRecordNoFromRow()</code> compares
	 * the current selected record in the table with all records
	 * in the database and returns the record number of the selected row.
	 *
	 * @param row the selected row in the database table
	 * @return the record no of the row from the database
	 * @throws RecordNotFoundException the record not found exception
	 */
	public final int getRecordNoFromRow(final int row) 
			throws RecordNotFoundException {
		String[] recordDetails = getSelectedContractor(row);
		int [] recNo = null;
		if (appType == ApplicationMode.ALONE) {			
			recNo = localConnection.find(recordDetails);
		} else if (appType == ApplicationMode.NETWORK) {
			try {
				recNo = remoteConnection.find(recordDetails);
			} catch (RemoteException e) {
				logger.severe("Issue with getting record number, "
						+ "Check connections " + e.getMessage());
			}
		}
		return recNo[0];
	}
		
	/**
	 * This method updates the Owner field of a record.
	 *
	 * @param row the row
	 * @param customer the customer
	 * @throws RecordNotFoundException the record not found exception
	 */
	public final void updateContractor(final int row, final String customer) 
			throws RecordNotFoundException {
		//owner field is in column 5
		tableRecs.setValueAt(customer, row, 5);
		String[] data = getSelectedContractor(row); 		
		int recNo = getRecordNoFromRow(row);
		
		if (appType == ApplicationMode.ALONE) {
			localConnection.update(recNo, data);
		} else if (appType == ApplicationMode.NETWORK) {
			try {
				remoteConnection.update(recNo, data);
			} catch (RemoteException e) {
				logger.severe("Issue with record, " + recNo
						+ "Check connections " + e.getMessage());
			}
		}
	}
}


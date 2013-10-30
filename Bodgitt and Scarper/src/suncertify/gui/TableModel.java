/* Project: Bodgitt and Scarper Version 2.3.3
 * @author: Patrick Garvey
 * Last Modified: 30th Oct 2013 
 * TableModel.java
 */
package suncertify.gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;


/**
 * The custom table model.
 */
public class TableModel extends AbstractTableModel {


	/** AbstractTableModel implements serializable so here it is.*/
	private static final long serialVersionUID = 1L;
	
	/** The header titles of the table columns. */
	private String [] headerTitles = {"Name", "Location",  "Specialties", 
									"size, ", "rate", "Owner"};
	
	/** Holds all the records to be displayed in the Table. */
	private ArrayList<String[]> records = new ArrayList<String[]>();
	 
	/**
	 * gets the number of columns in the table.
	 * @return Number of columns
	 */
	public int getColumnCount() {
		return this.headerTitles.length;
		
	}

	/**
	 *Gets the number of rows in the table
	 *@returns the number of rows 
	 */
	public int getRowCount() {
		return this.records.size();
	}
	

	/**
	 * Gets the value in a specified cell
	 * @returns a cell objects value
	 */		
	public Object getValueAt(int row, int column) {
		String[] rowValues = this.records.get(row);
        return rowValues[column];
	}
	
	/**
	 * Sets the value in a specified cell
	 * @returns a cell objects value
	 */	
    public void setValueAt(Object obj, int row, int column) {
        Object[] rowValues = this.records.get(row);
        rowValues[column] = obj;
    }
    
    /**
     * Adds the subcontractor record.
     *
     * @param record the record
     */
    public void addSubcontractorRecord(String[] record) {  
    	this.records.add(record);
    }
    
    /**
     * Gets the subcontractor record.
     *
     * @param row the row
     * @param column the column
     * @return the subcontractor record
     */
    public  String getSubcontractorRecord(int row, int column) {  
    	String[] rowValues = this.records.get(row);
        return rowValues[column];
    }
    
    /**
     * Adds the subcontractor record.
     */
    public void addSubcontractorRecord() {
    }
    
    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
     */
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnName(int)
     */
    public String getColumnName(int column) {
        return headerTitles[column];
    }
    
}

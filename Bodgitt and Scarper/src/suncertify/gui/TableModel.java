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
	 *Gets the number of rows in the table.
	 *@return the number of rows 
	 */
	public int getRowCount() {
		return this.records.size();
	}
	

	/**
	 * Gets the value in a specified cell.
	 * @return rowValues[] a cell objects value
	 */		
	public Object getValueAt(int row, int column) {
		String[] rowValues = this.records.get(row);
        return rowValues[column];
	}
	
	/**
	 * Sets the value in a specified cell.
	 * 
	 * @param obj the object value to be placed in the cell
	 */	
    public void setValueAt(Object obj, int row, int column) {
        Object[] rowValues = this.records.get(row);
        rowValues[column] = obj;
        fireTableDataChanged();
    }
    
    /**
     * Adds a subcontractor record to the current records.
     *
     * @param record the record
     */
    public void addSubcontractorRecord(String[] record) {  
    	this.records.add(record);
    }
    
        
    /**
     * Checks to see if a specified cell is editable.
     * 
     * @return false as all cells are non editable
     */
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    /**
     * gets the column headers to be displayed in the table.
     * 
     * @param column The specified column index.
     * @return A String containing the column name.
     */
    public String getColumnName(int column) {
        return headerTitles[column];
    }
    
}

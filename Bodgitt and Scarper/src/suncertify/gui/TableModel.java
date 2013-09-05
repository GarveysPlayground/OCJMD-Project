package suncertify.gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {

	private String [] headerTitles = {"Name", "Location",
									 "Specialties", "size, ", "rate",
									 "Owner"};
	
	private ArrayList<String[]> records = new ArrayList<String[]>();
	
	public int getColumnCount() {
		return this.headerTitles.length;
		
	}

	public int getRowCount() {
		return this.records.size();
	}

	public Object getValueAt(int row, int column) {
		String[] rowValues = this.records.get(row);
        return rowValues[column];
	}
	
    public void setValueAt(Object obj, int row, int column) {
        Object[] rowValues = this.records.get(row);
        rowValues[column] = obj;
    }
    
    public void addSubcontractorRecord(String[] record) {  
    	this.records.add(record);
    }
    
    public void addSubcontractorRecord() {
    }
    
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public String getColumnName(int column) {
        return headerTitles[column];
    }
}

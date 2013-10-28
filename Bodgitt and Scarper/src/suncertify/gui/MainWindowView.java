package suncertify.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import suncertify.db.RecordNotFoundException;

public class MainWindowView implements ActionListener {
	
	//full window frame
	JFrame mainWindowFrame = new JFrame();
	
	//Panel that will display TableModel
	JPanel tablePanel = new JPanel();
	
	//panel in mainWindowFrame that will contain
	//location search, Name search text fields and search button
	JPanel searchPanel = new JPanel();
	
	//panel that will contain un/book buttons
	JPanel bookPanel = new JPanel();
	
	//table that will be display data
	JTable contractorTable;

	JTextField locationSearch;
	
	JTextField nameSearch;
	
	JButton searchButton;
	
	JButton searchAllButton;
	
	JButton bookButton;
	
	JButton unbookButton;
	
	
	private TableModel tableModel = new TableModel();
	
	private TableController controller;
		
	JTable table;
	

	
	public void setupMainWindow(String host, int port){
		mainWindowFrame.setTitle("Bodgitt and Scarper, LLC: Booking System");
		mainWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindowFrame.setSize(800,550);
		mainWindowFrame.setResizable(false);
		
		tablePanel =  makeTablePanel(host, port);
		mainWindowFrame.getContentPane().add(BorderLayout.NORTH, tablePanel);
		bookPanel = makeBookPanel();
		mainWindowFrame.add(bookPanel, BorderLayout.SOUTH);
		searchPanel = makeSearchPanel();
		mainWindowFrame.add(searchPanel, BorderLayout.CENTER);
		mainWindowFrame.setLocationRelativeTo(null);
		mainWindowFrame.setVisible(true);
	}
	
	
	private JPanel makeBookPanel() {
		bookButton = new JButton("Book");
		unbookButton = new JButton("Unbook");
		bookButton.addActionListener(this);
		unbookButton.addActionListener(this);
		bookPanel.add(BorderLayout.EAST, bookButton);
		bookPanel.add(BorderLayout.EAST, unbookButton);
		bookPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		return bookPanel;
	}
	
	private JPanel makeSearchPanel() {
		nameSearch = new JTextField(20);
		JLabel nameLabel = new JLabel("NAME :");
		nameSearch.add(nameLabel);
		searchPanel.add(BorderLayout.CENTER, nameLabel);
		searchPanel.add(BorderLayout.CENTER, nameSearch);
		
		locationSearch = new JTextField(20);
		JLabel locationLabel = new JLabel("LOCATION :");
		nameSearch.add(locationLabel);
		searchPanel.add(BorderLayout.CENTER, locationLabel);
		searchPanel.add(BorderLayout.CENTER, locationSearch);
		searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		searchButton = new JButton("Search");
		searchAllButton = new JButton("Search All");
		searchButton.addActionListener(this);
		searchAllButton.addActionListener(this);
		searchPanel.add(BorderLayout.CENTER, searchButton);
		searchPanel.add(BorderLayout.CENTER, searchAllButton);
		
		return searchPanel;
	}
	
	private JPanel makeTablePanel(String host, int port) {
		controller = new TableController(host, port);
		JPanel tablePanel = new JPanel(new BorderLayout());
		tableModel = this.controller.getAllContractors();
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		tablePanel.add(scrollPane);
		table.getTableHeader().setReorderingAllowed(false);
		return tablePanel;
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bookButton || e.getSource() == unbookButton){	
			int rowNo = table.getSelectedRow();
			String customerID = "";
			if(e.getSource() == bookButton){
				customerID = (String) JOptionPane.showInputDialog(tablePanel,
							 "Please enter the Customer ID", 
							 "Booking SubContractor", 3);
			}
			if((customerID != null && customerID.length() == 8 && isInteger(customerID)) || e.getSource() == unbookButton){						
				try {
					controller.updateContractor(rowNo, customerID);
				} catch (RecordNotFoundException recEx) {
					System.err.println("Issue finding Record on row "
							  +rowNo + " : " + recEx);
				}		
				refreshTable();	
			}else if(customerID == null){
				//logger message about canceling
			}else{
				JOptionPane.showMessageDialog(tablePanel,
				    "Invalid Customer ID :\n8 digit int expected",
				    "Record not booked",
				    JOptionPane.ERROR_MESSAGE);
			}	
		}else if(e.getSource() == searchButton){
			String[] criteria = new String[2];
			criteria[0] = nameSearch.getText();
			criteria[1] = locationSearch.getText();			
			tableModel = controller.getContractors(criteria);
			refreshTable();
		}else if(e.getSource() == searchAllButton){
			String[] criteria = new String[2];
			criteria[0] = "";
			criteria[1] = "";			
			tableModel = controller.getContractors(criteria);
			refreshTable();
		}
		
	}
	
	
	public boolean isInteger( String input ) {
	    try {
	        Integer.parseInt( input );
	        return true;
	    }
	    catch( Exception e ) {
	        return false;
	    }
	}
	
	private void refreshTable() {
		tableModel.fireTableDataChanged();
        this.table.setModel(this.tableModel);       
    }
}

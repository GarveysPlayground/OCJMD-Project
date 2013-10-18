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
import suncertify.onStart.Startup;

public class MainWindowView {
	
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
	
	JButton bookButton;
	
	JButton unbookButton;
	
	String appType = Startup.getConnectionType();
	
	private TableModel tableModel = new TableModel();
	
	private TableController controller;
	
	private DialogBoxViews dialogs = new DialogBoxViews();
	
	
	JTable table;
	
	public MainWindowView(){
		System.out.println("Innitiate");
		if(appType == "alone"){	
			dialogs.databaseLocationWindow();
		}else if(appType == "remote"){
			dialogs.rmiClient();
		}else if(appType == "server"){
			dialogs.rmiConnectionWindow();
		}
	

	}
	public void setupMainWindow(String host, int port){
		mainWindowFrame.setTitle("Bodgitt and Scarper, LLC: Booking System");
		mainWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindowFrame.setSize(800,550);
		mainWindowFrame.setResizable(false);
		
		tablePanel =  makeTablePanel(host, port);
		mainWindowFrame.getContentPane().add(BorderLayout.NORTH, tablePanel);
		System.out.println("Done");
		bookPanel = makeBookPanel();
		mainWindowFrame.add(bookPanel, BorderLayout.SOUTH);
		searchPanel = makeSearchPanel();
		mainWindowFrame.add(searchPanel, BorderLayout.CENTER);
		mainWindowFrame.setLocationRelativeTo(null);
		mainWindowFrame.setVisible(true);
	}
	
	
	private JPanel makeBookPanel() {
		bookButton = new JButton("Book");
		bookButton.addActionListener(new bookContractors());
		bookPanel.add(BorderLayout.EAST, bookButton);
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
		searchButton.addActionListener(new searchContractors());
		searchPanel.add(BorderLayout.CENTER, searchButton);
		
		return searchPanel;
	}
	
	private JPanel makeTablePanel(String host, int port) {
		controller = new TableController(host, port);
		JPanel tablePanel = new JPanel(new BorderLayout());
		//controller.
		tableModel = this.controller.getAllContractors();
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		tablePanel.add(scrollPane);
		return tablePanel;
	}
	
	private class searchContractors implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] criteria = new String[2];
			criteria[0] = nameSearch.getText();
			criteria[1] = locationSearch.getText();			
			tableModel = controller.getContractors(criteria);
			refreshTable();
		}		
	}

	private class bookContractors implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			int rowNo = table.getSelectedRow();
			String customerID = "initial";
			customerID = (String) JOptionPane.showInputDialog(tablePanel,
						 "Please enter the Customer ID", 
						 "Booking SubContractor", 3);		
			if(customerID != null && customerID.length() == 8 && isInteger(customerID)){		
							
				try {
					controller.updateContractor(rowNo, customerID);
				} catch (RecordNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}		
				
				refreshTable();
				
				
			}else{
				JOptionPane.showMessageDialog(tablePanel,
					    "Record not booked due to:\n Incorrect data type or \n user cancellation",
					    "Record not booked",
					    JOptionPane.ERROR_MESSAGE);
				System.out.println("not valiid");
			}
			
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

/* Project: Bodgitt and Scarper Version 2.3.3
 * @author: Patrick Garvey
 * Last Modified: 28th Oct 2013 
 * MainWindowView.java
 */
package suncertify.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import suncertify.db.RecordNotFoundException;

/**
 * The Class <code>MainWindowView</code> displays the applications main GUI.
 * This GUI displays a table of information to the user and allows him/her
 * to search for and modify records. 
 */
public class MainWindowView implements ActionListener {
	
	/** The main window frame that holds the GUI components. */
	private JFrame mainWindowFrame = new JFrame();
	
	/** The table panel that provides space for the Jtable to display. */
	private JPanel tablePanel = new JPanel();
	
	/** The search panel provides table space for search functionality. */
	private JPanel searchPanel = new JPanel();
	
	/** The book panel provides table space for booking functionality. */
	private JPanel bookPanel = new JPanel();

	/** The location search field where the user enters search values. */
	private JTextField locationSearch;
	
	/** The name search field where the user enters search values. */
	private JTextField nameSearch;
	
	/** The search button. */
	private JButton searchButton;
	
	/** The refresh button displays all records. */
	private JButton refreshButton;
	
	/** The book button. */
	private JButton bookButton;
	
	/** The unbook button. */
	private JButton unbookButton;
		
	/** The table model reference. */
	private TableModel tableModel = new TableModel();
	
	/** The controller reference. */
	private TableController controller;
		
	/** The table where the records are displayed. */
	private JTable table;
	
	/**The Logger*/
	private static Logger logger = Logger.getLogger("suncertify.gui");
	

	
	/**
	 * Setup main window by calling on the various function specific methods
	 * found in this class. By having distinct methods focus on a single 
	 * functionality we increase the ease of readability and future 
	 * maintainability.
	 *
	 * @param host : the host holding the database table
	 * @param port : the port to access the database table through
	 * @see #makeBookPanel() adds the booking section to the window
	 * @see #makeSearchPanel() adds the search section to the window
	 * @see #makeTablePanel(String, int) adds the Table
	 */
	public final void setupMainWindow(final String host, final int port) {
		logger.entering("MainWindowView", "setupMainWindow()");
		mainWindowFrame.setTitle("Bodgitt and Scarper, LLC: Booking System");
		mainWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindowFrame.setSize(800, 550);
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
	
	
	/**
	 * Generates the panel for the main window that allows the
	 * end user to <code>book</code> and <code>unbook</code> a record.
	 *
	 * @return the j panel
	 */
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
	
	/**
	 * Generates the panel for the main window that allows the
	 * end user to <code>Search</code> and <code>refresh</code> 
	 * the table. The user may search by name and/or location
	 *
	 * @return the j panel
	 */
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
		refreshButton = new JButton("Refresh");
		searchButton.addActionListener(this);
		refreshButton.addActionListener(this);
		searchPanel.add(BorderLayout.CENTER, searchButton);
		searchPanel.add(BorderLayout.CENTER, refreshButton);
		
		return searchPanel;
	}
	
	/**
	 * Makes table panel and places the table containing the database records
	 * in here. Table values are populated through the controller.
	 *
	 * @param host the host
	 * @param port the port
	 * @return the j panel
	 */
	private JPanel makeTablePanel(final String host, final int port) {
		controller = new TableController(host, port);
		JPanel tablePanel = new JPanel(new BorderLayout());
		tableModel = this.controller.getAllContractors();
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		tablePanel.add(scrollPane);
		table.getTableHeader().setReorderingAllowed(false);
		return tablePanel;
	}
	
	
	/**
	 * Handles the various end user requests based on user actions.
	 * Requests pull data from the table controller.
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bookButton || e.getSource() == unbookButton) {
			logger.info("Booking record");
			int rowNo = table.getSelectedRow();
			String customerID = "";
			if (e.getSource() == bookButton) {
				customerID = (String) JOptionPane.showInputDialog(tablePanel,
							 "Please enter the Customer ID", 
							 "Booking SubContractor", 3);
			}
			if ((customerID != null && customerID.length() == 8 
				  && isInteger(customerID)) || e.getSource() == unbookButton) {
				try {
					controller.updateContractor(rowNo, customerID);
				} catch (RecordNotFoundException recEx) {
					logger.warning("Canceling booking");
					System.err.println("Issue finding Record on row "
							  + rowNo + " : " + recEx);
				}		
				refreshTable();	
			} else if (customerID == null) {
				logger.info("Canceling booking");
			} else {
				logger.warning("Canceling booking");
				JOptionPane.showMessageDialog(tablePanel,
				    "Invalid Customer ID :\n8 digit int expected",
				    "Record not booked",
				    JOptionPane.ERROR_MESSAGE);
			}	
		} else if (e.getSource() == searchButton) {
			logger.info("Searching for record");
			String[] criteria = new String[2];
			criteria[0] = nameSearch.getText();
			criteria[1] = locationSearch.getText();			
			tableModel = controller.getContractors(criteria);
			refreshTable();
		} else if (e.getSource() == refreshButton) {
			logger.info("Getting All records");
			String[] criteria = new String[2];
			criteria[0] = "";
			criteria[1] = "";			
			tableModel = controller.getContractors(criteria);
			refreshTable();
		}
		
	}
	
	
	/**
	 * Checks if is integer.
	 *
	 * @param input the input
	 * @return true, if is integer
	 */
	public boolean isInteger(final String input) {
	    try {
	        Integer.parseInt(input);
	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}
	
	/**
	 * Refresh table.
	 */
	private void refreshTable() {
		tableModel.fireTableDataChanged();
        this.table.setModel(this.tableModel);       
    }
}

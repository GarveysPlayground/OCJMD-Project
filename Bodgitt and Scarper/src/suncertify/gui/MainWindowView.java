package suncertify.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

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
	
	private TableModel tableModel = new TableModel();
	
	private TableController controller = new TableController();
	
	public void MainWindowView(){
		mainWindowFrame.setTitle("Bodgitt and Scarper, LLC: Booking System");
		mainWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindowFrame.setSize(800,600);
		mainWindowFrame.setResizable(false);		
		
		
		tablePanel =  makeTablePanel();
		mainWindowFrame.getContentPane().add(BorderLayout.NORTH, tablePanel);
		System.out.println("Done");
		//mainWindowFrame.add(tablePanel, BorderLayout.SOUTH);
		bookPanel = makeBookPanel();
		mainWindowFrame.add(bookPanel, BorderLayout.SOUTH);
		searchPanel = makeSearchPanel();
		mainWindowFrame.add(searchPanel, BorderLayout.CENTER);
		
		mainWindowFrame.setVisible(true);
		}

	private JPanel makeBookPanel() {
		JButton bookButton = new JButton("Book");
		bookPanel.add(BorderLayout.EAST, bookButton);
		JButton unbookButton = new JButton("Unbook");
		bookPanel.add(BorderLayout.EAST, unbookButton);
		bookPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		return bookPanel;
	}
	
	private JPanel makeSearchPanel() {
		JTextField nameSearch = new JTextField(20);
		JLabel nameLabel = new JLabel("NAME :");
		nameSearch.add(nameLabel);
		searchPanel.add(BorderLayout.CENTER, nameLabel);
		searchPanel.add(BorderLayout.CENTER, nameSearch);
		
		JTextField locationSearch = new JTextField(20);
		JLabel locationLabel = new JLabel("LOCATION :");
		nameSearch.add(locationLabel);
		searchPanel.add(BorderLayout.CENTER, locationLabel);
		searchPanel.add(BorderLayout.CENTER, locationSearch);
		searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		searchButton = new JButton("Search");
		searchPanel.add(BorderLayout.CENTER, searchButton);
		
		return searchPanel;
	}
	
	private JPanel makeTablePanel() {
		/* JPanel tablePanel = new JPanel(new BorderLayout());
		 tableModel = this.controller.getAllContractors();
	     JTable table = new JTable(tableModel);
	     tablePanel.add(new JScrollPane(table));
	    */
		//table.setEnabled(false);
		
		
	
		
		JPanel tablePanel = new JPanel(new BorderLayout());
		tableModel = this.controller.getAllContractors();
		JTable table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		tablePanel.add(scrollPane);
		return tablePanel;
	}
	

}

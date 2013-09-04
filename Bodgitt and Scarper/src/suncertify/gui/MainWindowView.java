package suncertify.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
	
	public void MainWindowView(){
		mainWindowFrame.setTitle("Bodgitt and Scarper, LLC: Booking System");
		mainWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindowFrame.setSize(800,600);
		mainWindowFrame.setResizable(false);		
		mainWindowFrame.setVisible(true);
		
		tablePanel =  makeTablePanel();
		mainWindowFrame.add(tablePanel, BorderLayout.NORTH);
		bookPanel = makeBookPanel();
		mainWindowFrame.add(bookPanel, BorderLayout.SOUTH);
		searchPanel = makeSearchPanel();
		mainWindowFrame.add(searchPanel, BorderLayout.CENTER);
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
		
		return tablePanel;
	}
	

}

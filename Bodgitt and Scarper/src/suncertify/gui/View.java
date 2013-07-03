package suncertify.gui;

import javax.swing.*;


import suncertify.db.FileAccess;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.IOException;


public class View {
	FileAccess data = new FileAccess();
	
	Object [] colNames = {"Rec ID","Name", "Location", "Specialties", "size, ", "rate" , "Owner"};
	
	public static void main(String[] args) throws IOException{
		View gui = new View();
		gui.makeMainWindow();
	}
	
	public void makeMainWindow() throws IOException{
		
		String[] record = new String[data.getAllRecords()];
		Object [] [] rows = new Object[data.getAllRecords() + 1][7]; //6 columns + ID
		for(int i = 1; i < data.getAllRecords() + 1 ;i++){
			record  = data.read(i);
			for(int k = 0; k <= 6; k++){
				if (k == 0){
					rows[i][k] = i;
				}else{
					rows[i][k] = record[k-1].trim();
				}
				
			}
		}
		
		
		
		JFrame frame = new JFrame();
		frame.setTitle("Bodgitt and Scarper, LLC: Booking System");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,600);
		
		
		
		//DB Table
		JTable table = new JTable (rows, colNames);
		//table.setEnabled(false);
		table.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollPane = new JScrollPane(table);
		frame.getContentPane().add(BorderLayout.NORTH, scrollPane);
		
		
			
		// Set up the search pane
;
		JPanel searchPanel = new JPanel();
		JButton searchButton = new JButton("Search");
		
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
		
		searchPanel.add(BorderLayout.CENTER, searchButton);
        
		
		frame.getContentPane().add(BorderLayout.CENTER, searchPanel);

		JPanel bookPanel = new JPanel();
		JButton bookButton = new JButton("Book");
		JButton unbookButton = new JButton("Unbook");
		bookPanel.add(BorderLayout.EAST, bookButton);
		bookPanel.add(BorderLayout.EAST, unbookButton);
		bookPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		frame.getContentPane().add(BorderLayout.SOUTH, bookPanel);        
      
        frame.setVisible(true);
	}
	
	
	public void makeStandaloneWindow() throws IOException{
		
		JFrame frame = new JFrame();
		frame.setTitle("Bodgitt and Scarper, LLC: Database location");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,100);
		
		JPanel databasePanel = new JPanel();
		JButton connectButton = new JButton("Connect");
		
		//JFileChooser dbFile = new JFileChooser();
		JTextField dbFile = new JTextField(20);
		JLabel nameLabel = new JLabel("DATABASE:");
		dbFile.add(nameLabel);
		databasePanel.add(BorderLayout.CENTER, nameLabel);
		databasePanel.add(BorderLayout.CENTER, dbFile);
		databasePanel.add(BorderLayout.CENTER, connectButton);
		
		frame.getContentPane().add(BorderLayout.CENTER, databasePanel);
		
		 frame.setVisible(true);
	}
	
	
}
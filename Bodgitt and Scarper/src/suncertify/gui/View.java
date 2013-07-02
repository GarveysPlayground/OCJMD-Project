package suncertify.gui;

import javax.swing.*;


import suncertify.db.FileAccess;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.IOException;


public class View implements ActionListener{
	JButton exitButton,connectButton;
	FileAccess data = new FileAccess();
	
	
	//int noOfRows = data.getAllRecords();
	
	
	
	
	
	Object [] colNames = {"Rec ID","Name", "Location", "Specialties", "size, ", "rate" , "Owner"};
	
	public static void main(String[] args) throws IOException{
		
		View gui = new View();
		gui.makeWindow();
	}
	
	public void makeWindow() throws IOException{
		
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,800);
		
		
		
		//DB Table
		JTable table = new JTable (rows, colNames);
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
		
		searchPanel.add(BorderLayout.CENTER, searchButton);
        
		
		frame.getContentPane().add(BorderLayout.CENTER, searchPanel);

        
      
        frame.setVisible(true);
	}
	
	
	public void changeIt() {
		exitButton.setText("I’ve been clicked!");
	}
	
	public void actionPerformed(ActionEvent event) {
		exitButton.setText("I’ve been clicked!");
		}
}
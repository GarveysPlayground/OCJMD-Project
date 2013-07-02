package suncertify.gui;

import javax.swing.*;

import suncertify.db.FileAccess;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.IOException;


public class View implements ActionListener{
	JButton exitButton,connectButton;
	FileAccess data = new FileAccess();
	
	
	//int noOfRows = data.getAllRecords();
	
	
	
	
	
	Object [ ] colNames = {"Name", "Location", "Specialties", "size, ", "rate" , "Owner"};
	//Object [] [] rows = { 
	//	  {"Data 3", "Data 4","Data 3", "Data 4","Data 3", "Data 4"},
	//	  {"Data 6", "Data 6","Data 6", "Data 6","Data 6", "Data 6"}
	//	  };
	
	public static void main(String[] args) throws IOException{
		
		View gui = new View();
		gui.makeWindow();
	}
	
	public void makeWindow() throws IOException{
		String[] record = new String[data.getAllRecords()];
		Object [] [] rows = new Object[data.getAllRecords()][6];

		
		for(int i = 1; i < data.getAllRecords() ;i++){
		record  = data.read(i);
			for(int k=0; k<6; k++){
				rows[i][k] = record[k];
			}
		}
		
		
		
		
		//System.out.println(data.getAllRecords());
		
	//	f//or(int i = 0; i < data.getAllRecords() -1 ;i++){
		//	record = data.read(i);
			
		//		for(int k = 1; k < 6; k++){
				//	System.out.println(record[k]);
					//	rows[i][k] += record[k];
					//System.out.println(record[k].toString());
	//			}
	//	}
		
		
		
		
		
		
		JTable table = new JTable (rows, colNames);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setSize(600,600);
		frame.setVisible(true);
		
		
		exitButton = new JButton("Exit");
		connectButton = new JButton("Connect");
		exitButton.addActionListener(this);

		frame.getContentPane().add(BorderLayout.CENTER, table);
		//frame.getContentPane().add(BorderLayout.SOUTH, exitButton);
		//frame.getContentPane().add(BorderLayout.SOUTH, connectButton);
	}
	
	
	public void changeIt() {
		exitButton.setText("I’ve been clicked!");
	}
	
	public void actionPerformed(ActionEvent event) {
		exitButton.setText("I’ve been clicked!");
		}
}
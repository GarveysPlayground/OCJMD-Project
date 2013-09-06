package suncertify.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DialogBoxViews {
	
	public void databaseLocationWindow(){
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
	
	
	public void bookContractorWindow(){
		JFrame frame = new JFrame();
		frame.setTitle("Bodgitt and Scarper, LLC: Book Contractor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,100);
		
		JPanel bookingPanel = new JPanel();
		JButton submitButton = new JButton("Submit");
		
		JTextField ownerIDField = new JTextField(20);
		JLabel label = new JLabel("8 Digit Customer ID:");
		ownerIDField.add(label);
		bookingPanel.add(BorderLayout.CENTER, label);
		bookingPanel.add(BorderLayout.CENTER, ownerIDField);
		bookingPanel.add(BorderLayout.CENTER, submitButton);
		
		frame.getContentPane().add(BorderLayout.CENTER, bookingPanel);
		
		 frame.setVisible(true);
	}

}



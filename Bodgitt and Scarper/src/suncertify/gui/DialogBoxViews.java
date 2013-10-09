package suncertify.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import suncertify.db.FileAccess;



public class DialogBoxViews{
	
	JTextField dbFile;
	JTextField rmiport;
	JFrame frame;
	
	public void connectionType(String connectionType){
		if (connectionType == "alone") {
			databaseLocationWindow();
           
        } else if (connectionType == "server") {
        	rmiConnectionWindow();
        }
	}
	
	public void databaseLocationWindow(){
		frame = new JFrame();
		frame.setTitle("Bodgitt and Scarper, LLC: Database location");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,100);
		
		JPanel databasePanel = new JPanel();
		JButton connectButton = new JButton("Connect");
		connectButton.addActionListener(new selectFile());

		dbFile = new JTextField(20);
		dbFile.setText("C:\\Users\\Garvey\\Google Drive\\Java\\SCJD\\mine\\db");
		JLabel nameLabel = new JLabel("DATABASE:");
		dbFile.add(nameLabel);
		databasePanel.add(BorderLayout.CENTER, nameLabel);
		databasePanel.add(BorderLayout.CENTER, dbFile);
		databasePanel.add(BorderLayout.CENTER, connectButton);
		frame.getContentPane().add(BorderLayout.CENTER, databasePanel);		
		frame.setVisible(true);
	}
	
	public void rmiConnectionWindow(){
		frame = new JFrame();
		frame.setTitle("Bodgitt and Scarper, LLC: Database location, Network Connection");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,100);
		
		dbFile = new JTextField(20);
		dbFile.setText("Localhost");
		JLabel nameLabel = new JLabel("HOST:");
		dbFile.add(nameLabel);
		
		rmiport = new JTextField(20);
		rmiport.setText("8080");
		JLabel portLabel = new JLabel("PORT:");
		rmiport.add(portLabel);
		
		JPanel databasePanel = new JPanel();
		JButton connectButton = new JButton("Connect");
		connectButton.addActionListener(new rmiConnect());
		
		databasePanel.add(BorderLayout.CENTER, nameLabel);
		databasePanel.add(BorderLayout.CENTER, dbFile);
		databasePanel.add(BorderLayout.CENTER, portLabel);
		databasePanel.add(BorderLayout.CENTER, rmiport);
		databasePanel.add( BorderLayout.CENTER,connectButton);
		frame.getContentPane().add(BorderLayout.CENTER, databasePanel);		
		frame.setVisible(true);
	}
	
	private class selectFile implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {	
			FileAccess connect = new FileAccess(); 
			try {
				
				if(dbFile.getText().length() == 0){
					JOptionPane.showMessageDialog(frame,
					    "No Location entered!",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				}else{
				connect.connectToDB(dbFile.getText());
				}
				
				
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	private class rmiConnect implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("POP");	
		}
			
			
	}
	
	
}



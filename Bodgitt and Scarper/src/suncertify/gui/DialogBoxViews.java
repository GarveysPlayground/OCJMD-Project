package suncertify.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import suncertify.db.Data;
import suncertify.db.FileAccess;
import suncertify.onStart.Startup;
import suncertify.rmi.ClientRemoteConnect;
import suncertify.rmi.RMIManager;



public class DialogBoxViews{
	
	JTextField dbFile;
	JTextField rmiPort;
	JFrame frame;
		
	public void databaseLocationWindow(){
		System.out.println("Please");
		frame = new JFrame();
		frame.setTitle("Bodgitt and Scarper, LLC: Database location");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,100);
		
		JPanel databasePanel = new JPanel();
		
		JButton connectButton = new JButton("Connect");
		connectButton.addActionListener(new selectLocalFile());
		
		

		dbFile = new JTextField(20);
		dbFile.setText("C:\\Users\\Garvey\\Google Drive\\Java\\SCJD\\mine\\db\\db-2x3.db");
		JLabel nameLabel = new JLabel("DATABASE:");
		dbFile.add(nameLabel);
		databasePanel.add(BorderLayout.CENTER, nameLabel);
		databasePanel.add(BorderLayout.CENTER, dbFile);
		databasePanel.add(BorderLayout.CENTER, connectButton);
		frame.getContentPane().add(BorderLayout.CENTER, databasePanel);		
		frame.setVisible(true);
		
	}
	
	public void rmiClient(){
		frame = new JFrame();
		frame.setTitle("Bodgitt and Scarper, LLC: Network Connection");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900,600);
		
		dbFile = new JTextField(20);
		dbFile.setText("localhost");
		JLabel nameLabel = new JLabel("Host:");
		dbFile.add(nameLabel);
		
		rmiPort = new JTextField(20);
		rmiPort.setText("4566");
		JLabel portLabel = new JLabel("PORT:");
		rmiPort.add(portLabel);
		
		JPanel databasePanel = new JPanel();
		JButton connectButton = new JButton("Connect");
		connectButton.addActionListener(new clientConnecter());
		
		//BorderLayout.CENTER,
		databasePanel.add(nameLabel,BorderLayout.CENTER);
		databasePanel.add(dbFile,BorderLayout.CENTER);
		databasePanel.add(portLabel,BorderLayout.CENTER);
		databasePanel.add(rmiPort,BorderLayout.CENTER);
		databasePanel.add(connectButton);
		frame.getContentPane().add(databasePanel);		
		frame.setVisible(true);
	}
	
	public void rmiConnectionWindow(){
		frame = new JFrame();
		frame.setTitle("Bodgitt and Scarper, LLC: start Server");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900,600);
		
		dbFile = new JTextField(20);
		dbFile.setText("C:\\Users\\Garvey\\Google Drive\\Java\\SCJD\\mine\\db\\db-2x3.db");
		JLabel nameLabel = new JLabel("Database Location:");
		dbFile.add(nameLabel);
		
		rmiPort = new JTextField(20);
		rmiPort.setText("4566");
		JLabel portLabel = new JLabel("PORT:");
		rmiPort.add(portLabel);
		
		JPanel databasePanel = new JPanel();
		JButton connectButton = new JButton("Connect");
		connectButton.addActionListener(new rmiConnect());
		
		//BorderLayout.CENTER,
		databasePanel.add(nameLabel,BorderLayout.CENTER);
		databasePanel.add(dbFile,BorderLayout.CENTER);
		databasePanel.add(portLabel,BorderLayout.CENTER);
		databasePanel.add(rmiPort,BorderLayout.CENTER);
		databasePanel.add(connectButton);
		frame.getContentPane().add(databasePanel);		
		frame.setVisible(true);
	}
	
	private class selectLocalFile implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {	
			//FileAccess connect = new FileAccess(); 
			Data connect = new Data();
			try {
				
				if(dbFile.getText().length() == 0){
					JOptionPane.showMessageDialog(frame,
					    "No Location entered!",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				}else{
				new Data(dbFile.getText());
					connect = new Data(dbFile.getText());
					MainWindowView gui = new MainWindowView();
					gui.setupMainWindow();
					frame.setVisible(false);
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
	
	
	private class clientConnecter implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {					
				if(dbFile.getText().length() == 0 || rmiPort.getText().length() == 0 ){
					JOptionPane.showMessageDialog(frame,
					    "No Location/port entered!",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				}else{
					int port = Integer.parseInt(rmiPort.getText());
					try {
						ClientRemoteConnect.getConnection(dbFile.getText(), port);
					//	MainWindowView gui = new MainWindowView();
					//	 gui.MainWindowView();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
		}
	}
	
	private class rmiConnect implements ActionListener{
		RMIManager rmiManage = new RMIManager();
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(dbFile.getText().length() == 0 || rmiPort.getText().length() == 0 ){
				JOptionPane.showMessageDialog(frame,
				    "No Location/port entered!",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			}else{
				int port = Integer.parseInt(rmiPort.getText());
				
				try {
					rmiManage.startRegister(dbFile.getText(), port);

				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
			
			
	}
	
	
}



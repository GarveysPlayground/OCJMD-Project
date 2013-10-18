package suncertify.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import suncertify.db.Data;
import suncertify.rmi.ClientRemoteConnect;
import suncertify.rmi.RMIManager;



public class DialogBoxViews{
	
	JTextField dbFile;
	JTextField host;
	JTextField rmiPort;
	JButton connectButton;
	JButton exitButton;
	JButton selectFile;
	JFrame frame;
		
	public void databaseLocationWindow(){
		frame = new JFrame();
		frame.setTitle("Bodgitt and Scarper, LLC: Standalone Mode");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(550,125);
		
		JPanel filePanel = new JPanel();
		JLabel nameLabel = new JLabel("DATABASE: ");
		filePanel.add(nameLabel);
		dbFile = new JTextField(35);
		dbFile.setText("C:\\Users\\Garvey\\Google Drive\\Java\\SCJD\\mine\\db\\db-2x3.db");
		filePanel.add(dbFile);
		selectFile = new JButton("..");
		selectFile.addActionListener(new searchHelper());
		filePanel.add(selectFile);
		
		JPanel confirmationPanel = new JPanel();
		connectButton = new JButton("Connect");
		connectButton.addActionListener(new selectLocalFile());
		exitButton = new JButton("  Exit  ");
		confirmationPanel.add(connectButton);
		confirmationPanel.add(exitButton);

		frame.add(filePanel, BorderLayout.WEST);
		frame.add(confirmationPanel, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		
	}
	
	public void rmiClient(){
		frame = new JFrame();
		frame.setTitle("Bodgitt and Scarper, LLC: Network mode");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,150);
				
		JPanel hostPanel= new JPanel();
		JLabel hostLabel = new JLabel("Host:");
		host = new JTextField(20);
		host.setText("localhost");
		hostPanel.add(hostLabel);
		hostPanel.add(host);
		
		JPanel rmiPanel= new JPanel();
		JLabel portLabel = new JLabel("Port:");
		rmiPort = new JTextField(20);
		rmiPort.setText("4566");
		rmiPanel.add(portLabel);
		rmiPanel.add(rmiPort);
		
		JPanel confirmationPanel= new JPanel();
		connectButton = new JButton("Connect");
		connectButton.addActionListener(new clientConnecter());
		exitButton = new JButton("  Exit  ");
		confirmationPanel.add(connectButton);
		confirmationPanel.add(exitButton);	
		
		frame.getContentPane().add(hostPanel,BorderLayout.NORTH);	
		frame.getContentPane().add(rmiPanel,BorderLayout.CENTER);	
		frame.getContentPane().add(confirmationPanel,BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void rmiConnectionWindow(){
		frame = new JFrame();
		frame.setTitle("Bodgitt and Scarper, LLC: Server mode");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(550,175);
		
		JPanel filePanel = new JPanel();
		JLabel nameLabel = new JLabel("Database: ");
		filePanel.add(nameLabel);
		dbFile = new JTextField(35);
		dbFile.setText("C:\\Users\\Garvey\\Google Drive\\Java\\SCJD\\mine\\db\\db-2x3.db");
		filePanel.add(dbFile);
		selectFile = new JButton("..");
		selectFile.addActionListener(new searchHelper());
		filePanel.add(selectFile);
		
		JPanel rmiPanel= new JPanel();
		JLabel portLabel = new JLabel("Run on Port: ");
		rmiPort = new JTextField(20);
		rmiPort.setText("4566");
		rmiPanel.add(portLabel);
		rmiPanel.add(rmiPort);
		
		JPanel confirmationPanel= new JPanel();
		connectButton = new JButton("Start Server");
		connectButton.addActionListener(new rmiConnect());
		exitButton = new JButton(" Disconnect  ");
		confirmationPanel.add(connectButton);
		confirmationPanel.add(exitButton);	

		frame.add(filePanel, BorderLayout.NORTH);
		frame.getContentPane().add(rmiPanel,BorderLayout.WEST);
		frame.getContentPane().add(confirmationPanel,BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private class selectLocalFile implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {	 
			try {
				
				if(dbFile.getText().length() == 0){
					JOptionPane.showMessageDialog(frame,
					    "No Location entered!",
					    "Inane error",
					    JOptionPane.ERROR_MESSAGE);
				}else{
					new Data(dbFile.getText());
				
					frame.setVisible(false);
					MainWindowView gui = new MainWindowView();
					gui.setupMainWindow(dbFile.getText(), 0);
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
	
	private class searchHelper implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			 JFileChooser fileChooser = new JFileChooser();
		        FileNameExtensionFilter filter = new FileNameExtensionFilter(
		                "Database file", "db");
		        fileChooser.setFileFilter(filter);
		        int returnVal = fileChooser.showOpenDialog(null);
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            dbFile.setText(fileChooser.getSelectedFile().toString());
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
						frame.setVisible(false);
						MainWindowView gui = new MainWindowView();
						gui.setupMainWindow(dbFile.getText(), port);
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
					//frame.setVisible(false);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
			
			
	}
	
	
}



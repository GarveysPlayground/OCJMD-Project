package suncertify.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
import java.net.InetAddress;
import suncertify.db.Data;
import suncertify.rmi.ClientRemoteConnect;
import suncertify.rmi.RMIManager;



public class DialogBoxViews implements ActionListener{
	
	PersistProperties properties = new PersistProperties();
	JTextField dbFile = new JTextField();
	JTextField host = new JTextField();
	JTextField rmiPort = new JTextField();
	JButton connectButton;
	JButton networkButton;
	JButton serverStartButton;
	JButton localConect;
	JButton exitButton;
	JButton selectFile;
	JFrame frame;
	
	public void databaseLocationWindow() {
		frame = new JFrame();
		frame.setTitle("Bodgitt and Scarper, LLC: Standalone Mode");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(550, 125);
		
		JPanel filePanel = new JPanel();
		JLabel nameLabel = new JLabel("DATABASE: ");
		filePanel.add(nameLabel);
		dbFile = new JTextField(35);
		dbFile.setText(properties.getProperty("database"));
		filePanel.add(dbFile);
		selectFile = new JButton("..");
		selectFile.addActionListener(this);
		filePanel.add(selectFile);
		
		JPanel confirmationPanel = new JPanel();
		localConect = new JButton("Connect");
		localConect.addActionListener(this);
		exitButton = new JButton("  Exit  ");
		exitButton.addActionListener(this);
		confirmationPanel.add(localConect);
		confirmationPanel.add(exitButton);

		frame.add(filePanel, BorderLayout.WEST);
		frame.add(confirmationPanel, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);		
	}
		
	public void rmiClient() {
		frame = new JFrame();
		frame.setTitle("Bodgitt and Scarper, LLC: Network mode");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,150);
				
		JPanel hostPanel = new JPanel();
		JLabel hostLabel = new JLabel("Host:");
		host = new JTextField(20);
		host.setText(properties.getProperty("host"));
		hostPanel.add(hostLabel);
		hostPanel.add(host);
		
		JPanel rmiPanel = new JPanel();
		JLabel portLabel = new JLabel("Port:");
		rmiPort = new JTextField(20);
		rmiPort.setText(properties.getProperty("port"));
		rmiPanel.add(portLabel);
		rmiPanel.add(rmiPort);
		
		JPanel confirmationPanel = new JPanel();
		networkButton = new JButton("Connect");
		networkButton.addActionListener(this);
		exitButton = new JButton("  Exit  ");
		confirmationPanel.add(networkButton);
		exitButton.addActionListener(this);
		confirmationPanel.add(exitButton);	
		
		frame.getContentPane().add(hostPanel, BorderLayout.NORTH);
		frame.getContentPane().add(rmiPanel, BorderLayout.CENTER);
		frame.getContentPane().add(confirmationPanel, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void rmiConnectionWindow() {
		frame = new JFrame();
		frame.setTitle("Bodgitt and Scarper, LLC: Server mode");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(550,175);
		
		JPanel filePanel = new JPanel();
		JLabel nameLabel = new JLabel("Database: ");
		filePanel.add(nameLabel);
		dbFile = new JTextField(35);
		dbFile.setText(properties.getProperty("database"));
		filePanel.add(dbFile);
		selectFile = new JButton("..");
		selectFile.addActionListener(this);
		filePanel.add(selectFile);
		
		JPanel rmiPanel = new JPanel();
		JLabel portLabel = new JLabel("Run on Port: ");
		rmiPort = new JTextField(20);
		rmiPort.setText("4566");
		rmiPanel.add(portLabel);
		rmiPanel.add(rmiPort);
		
		JPanel confirmationPanel = new JPanel();
		serverStartButton = new JButton("Start Server");
		serverStartButton.addActionListener(this);
		exitButton = new JButton(" Disconnect  ");
		exitButton.addActionListener(this);
		confirmationPanel.add(serverStartButton);
		confirmationPanel.add(exitButton);	

		frame.add(filePanel, BorderLayout.NORTH);
		frame.getContentPane().add(rmiPanel, BorderLayout.WEST);
		frame.getContentPane().add(confirmationPanel, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public boolean confirmation() {
		MainWindowView gui = new MainWindowView();

		if (dbFile.isShowing()) {
			if (dbFile.getText().length() == 0) {
				JOptionPane.showMessageDialog(
						frame, "No Location entered!", "Inane error", 
						JOptionPane.ERROR_MESSAGE);
				return false;
			} else if (!new File(dbFile.getText()).exists()) {
				JOptionPane.showMessageDialog(
						frame, "Chosen File does not exist!", "Inane error", 
						JOptionPane.ERROR_MESSAGE);
				return false;
			} else if (!dbFile.getText().endsWith("db")) {
				JOptionPane.showMessageDialog(
						frame, "Not a valid db file!", "Inane error", 
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
			properties.setProperty("database", dbFile.getText());
		}
			
		
		if (rmiPort.isShowing()) {
			if (rmiPort.getText().length() == 0) {
				JOptionPane.showMessageDialog(
						frame, "No Port entered!", "Inane error", 
						JOptionPane.ERROR_MESSAGE);
				return false;
			} else if (!gui.isInteger(rmiPort.getText())) {
				JOptionPane.showMessageDialog(
						frame, "Port Value not an int!", "Inane error", 
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
			int range = Integer.parseInt(rmiPort.getText());
			if (range <  1 || range > 65535) {
				JOptionPane.showMessageDialog(
						frame, "Value not in valid port range of 1 - 65535"
						, "Inane error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			properties.setProperty("port", rmiPort.getText());
		}
		
		if (host.isShowing()) {
			if (host.getText().length() == 0) {	
				JOptionPane.showMessageDialog(
						frame, "No Host entered!", "Inane error", 
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
	
			try {
				InetAddress addresses = InetAddress.getByName(host.getText());
				boolean connectable = addresses.isReachable(5);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(
						frame, "Host is unreachable \n Check hostname : \"" 
								+ host.getText() + "\"\n and try again",
								"Inane error", JOptionPane.ERROR_MESSAGE);
				return false;
				//e.printStackTrace();
			}
			properties.setProperty("host", host.getText());	
		}
		return true;
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == selectFile) {
			JFileChooser fileChooser = new JFileChooser();
	        FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        									"Database file", "db");
	        fileChooser.setFileFilter(filter);
	        int returnVal = fileChooser.showOpenDialog(null);
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            dbFile.setText(fileChooser.getSelectedFile().toString());
	        }
	        
		} else if (e.getSource() == localConect) {
			if (confirmation()) {
				try {
					new Data(dbFile.getText());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				frame.setVisible(false);
				MainWindowView gui = new MainWindowView();
				gui.setupMainWindow(dbFile.getText(), 0);
			}
			
		} else if (e.getSource() == networkButton) {
			if (confirmation()) {	
				int port = Integer.parseInt(rmiPort.getText());
				try {
					ClientRemoteConnect.getConnection(host.getText(), port);
					frame.setVisible(false);
					MainWindowView gui = new MainWindowView();
					gui.setupMainWindow(host.getText(), port);
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(
							frame, "Connection Refused!\nIs the server started?"
							, "Inane error", JOptionPane.ERROR_MESSAGE);
					//e1.printStackTrace();
				}
			}
			
		} else if (e.getSource() == serverStartButton) {
			RMIManager rmiManage = new RMIManager();
			if (confirmation()) {
				int port = Integer.parseInt(rmiPort.getText());
				
				try {
					rmiManage.startRegister(dbFile.getText(), port);
					serverStartButton.setText("Server Started");
					serverStartButton.setBackground(Color.GREEN);
					serverStartButton.setEnabled(false);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				
			}
		
		} else if (e.getSource() == exitButton) {
			System.exit(0);
		}
	}	
	
}



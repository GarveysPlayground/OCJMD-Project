/* Project: Bodgitt and Scarper Version 2.3.3
 * @author: Patrick Garvey
 * Last Modified: 28th Oct 2013 
 * DialogBoxViews.java
 */
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



/**
 * The class <>DialogBoxViews<> displays the initial connection configurations 
 * to the end user on application startup. Depending on how the application
 * is run, one of three displays will present itself. 
 * Upon entering valid configurations the configuration values are saved to 
 * a properties file and re-read in on the next application run.
 * Client connections are then displayed the main application window.
 * 
 * @see suncertify.gui.PersistProperties
 * @see suncertify.gui.MainWindowView
 */
public class DialogBoxViews implements ActionListener {
	
	/** The properties for getting default connection values. */
	private PersistProperties properties = new PersistProperties();
	
	/** The textfield for the path to the local database file. */
	private JTextField dbFile = new JTextField();
	
	/**The textfield for the host name in Network mode. */
	private JTextField host = new JTextField();
	
	/** The textfield for the port number in network/server mode. */
	private JTextField rmiPort = new JTextField();
	
	/** The network button to establish a network connection. */
	private JButton networkButton;
	
	/** The server start button. */
	private JButton serverStartButton;
	
	/** The local connect button for establishing a standalone connection. */
	private JButton localConnect;
	
	/** The exit button to quit the application. */
	private JButton exitButton;
	
	/** The select file button to bring up <code>JFileChoser</code>. */
	private JButton selectFile;
	
	/** The frame containing the needed components. */
	private JFrame frame;
	
	/** The helps the user in selecting a file. */
	private JFileChooser fileChooser;
	
	/**
	 * The <code>databaseLocationWindow</code> method is called when the end 
	 * user starts the application in ALONE mode. It presents the user with a 
	 * GUI that queries the user for the  location of the local database file. 
	 */
	public final void databaseLocationWindow() {
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
		localConnect = new JButton("Connect");
		localConnect.addActionListener(this);
		exitButton = new JButton("  Exit  ");
		exitButton.addActionListener(this);
		confirmationPanel.add(localConnect);
		confirmationPanel.add(exitButton);

		frame.add(filePanel, BorderLayout.WEST);
		frame.add(confirmationPanel, BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);		
	}
		
	/**
	 * The <code>rmiClient</code> method is called when the end user starts the
	 * application in NETWORK mode. It presents the user with a GUI that
	 * queries the user for the name of the host s/he wishes to connect with
	 * and the port number to access through. 
	 */
	public final void rmiClient() {
		frame = new JFrame();
		frame.setTitle("Bodgitt and Scarper, LLC: Network mode");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 150);
				
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
	
	/**
	 * The <code>rmiConnectionWindow</code> method is called when the end user
	 * starts the application in SERVER mode. It presents the user with a GUI
	 * that queries the user for location of the database file and the port
	 * in which to grant access to outside users through. 
	 */
	public final void rmiConnectionWindow() {
		frame = new JFrame();
		frame.setTitle("Bodgitt and Scarper, LLC: Server mode");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(550, 175);
		
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
	
	/**
	 * the <code>Confirmation</code> method reads the user input and
	 * determines if the inputed values are valid. If the values are 
	 * valid then they are stored in the property file for later use.
	 *
	 * @return true, if user input is valid
	 */
	public final boolean confirmation() {
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
				/**InetAddress checks if the specified host 
				 * exists on the network*/
				InetAddress addresses = InetAddress.getByName(host.getText());
				addresses.isReachable(5);
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
	
	/**
	 * The <code>actionPerformed</code> is called when the user clicks
	 * on a button. Depending on the button the resulting action differs.
	 * 
	 * @value selectfile : the jFileChooser is called
	 * @value localConnect : the local database file is opened
	 * @value networkButton : a network connection to the databse is opened
	 * @value serverStartButton : the network server is started and waits
	 * 							for a connection
	 * @value exitButton : the application exits 
	 */
	public final void actionPerformed(final ActionEvent e) {
		if (e.getSource() == selectFile) {
			fileChooser = new JFileChooser();
	        FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        									"Database file", "db");
	        fileChooser.setFileFilter(filter);
	        int returnVal = fileChooser.showOpenDialog(null);
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            dbFile.setText(fileChooser.getSelectedFile().toString());
	        }
	        
		} else if (e.getSource() == localConnect) {
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



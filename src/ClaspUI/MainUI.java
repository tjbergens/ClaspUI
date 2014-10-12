/*
MainUI.java
*/

package ClaspUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class MainUI extends JFrame {
	
	// Constants for window width and height
	private final int INIT_WIDTH = 400;
	private final int INIT_HEIGHT = 300;
	private JTextField userField;
	private JPasswordField passField;

	public MainUI() {
		
		// Calls JFrame constructor to set title
		super("Clasp Password Manager");
		
		// Close the program when the window is closed
		// By default, closing the window leaves the program running in the background
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Set look and feel to match the system
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
		}
		
		// Set size and position of the window
		setSize(INIT_WIDTH, INIT_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		
		// Set window layout to a GridBagLayout
		GridBagLayout gridBagLayout = new GridBagLayout();
		this.setLayout(gridBagLayout);
		
		// Add pane for labels and textboxes
		JPanel pane = new JPanel();
		
		// Set size and use a GridLayout
		pane.setMaximumSize(new Dimension(300, 100));
		pane.setLayout(new GridLayout(2, 2, 10, 10));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 5, 0);
		BufferedImage loginImage = null;
		try {
			loginImage = ImageIO.read(new File("ClaspLogin.png"));
		} catch (IOException e) {
			// Do something if the image is not found
		}
		JLabel label = new JLabel(new ImageIcon(loginImage));
		this.add(label, gbc);
		gbc.ipady = 0;
		gbc.insets = new Insets(0, 0, 0, 0);
		
		LoginListener loginListener = new LoginListener();
		
		// Add components to pane, add pane to window
		pane.add(new JLabel("Username"));
		userField = new JTextField(16);
		userField.addActionListener(loginListener);
		pane.add(userField);
		
		pane.add(new JLabel("Password"));
		passField = new JPasswordField(16);
		passField.addActionListener(loginListener);
		pane.add(passField);
		
		gbc.gridy = 1;
		this.add(pane, gbc);
		
		// Add button for login
		JButton loginButton = new JButton("Log in");
		loginButton.addActionListener(loginListener);
		gbc.insets = new Insets(20, 0, 0, 0);
		gbc.gridy = 2;
		this.add(loginButton, gbc);
		
		// Call to show all components
		setVisible(true);
	}
	
	// ActionListener for login
	private class LoginListener implements ActionListener {
	    public void actionPerformed(ActionEvent e) {	    	
	    	JOptionPane.showMessageDialog(null, 
	    		"You have logged in!\nUsername: " + userField.getText() +
	    				"\nPass: " + String.valueOf(passField.getPassword()));
	    }
	}
	
}

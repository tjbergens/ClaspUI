/*
MainUI.java
 */

package ClaspUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

public class MainUI extends JFrame {
	
	// Constants for window width and height
	private final int INIT_WIDTH = 520; 
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
		gbc.insets = new Insets(0, 0, 20, 0);
		JLabel label = new JLabel("Clasp Login");
		label.setFont(UIManager.getFont("Label.font").deriveFont((float) 24.0));
		this.add(label, gbc);
		gbc.ipady = 0;
		gbc.insets = new Insets(0, 0, 0, 0);
		
		// Add components to pane, add pane to window
		userField = new JTextField(16);
		pane.add(new JLabel("Username"));
		pane.add(userField);
		passField = new JPasswordField(16);
		pane.add(new JLabel("Password"));
		pane.add(passField);
		gbc.gridy = 1;
		this.add(pane, gbc);
		
		// Add button for login
		JButton loginButton = new JButton("Log in");
		loginButton.addActionListener(new LoginListener());
		gbc.insets = new Insets(20, 0, 0, 0);
		gbc.gridy = 2;
		this.add(loginButton, gbc);
		
		// Call to show all components
		setVisible(true);
	}
	
	private class LoginListener implements ActionListener {
	    public void actionPerformed(ActionEvent e) {	    	
	    	JOptionPane.showMessageDialog(null, 
	    		"You have logged in!\nUsername: " + userField.getText() +
	    				"\nPass: " + String.valueOf(passField.getPassword()));
	    }
	}
	
}

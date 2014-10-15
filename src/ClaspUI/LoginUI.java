package ClaspUI;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import ClaspBackend.AuthEngine;
import ClaspUI.MainUI.View;

@SuppressWarnings("serial")
public class LoginUI extends JPanel {
	
	private JTextField userField;
	private JPasswordField passField;
	private MainUI parent;

	public LoginUI(MainUI parent) {
		
		// Save parent
		this.parent = parent;
		
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
		
	}
	
	// ActionListener for login
	private class LoginListener implements ActionListener {
		
		@Override
	    public void actionPerformed(ActionEvent e) {	    	

            String userName = userField.getText();
            String masterPassword = String.valueOf(passField.getPassword());

            JOptionPane.showMessageDialog(null,
	    		"You have logged in!\nUsername: " + userName +
	    				"\nPass: " + masterPassword);
	    	parent.changeView(View.PASSWORDS);

            AuthEngine authenticator = new AuthEngine();
            authenticator.makeKey(masterPassword, userName);


	    }
	}
	
}

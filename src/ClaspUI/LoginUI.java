package ClaspUI;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;

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
		BufferedImage plusImage = null;
		BufferedImage forgotImage = null;
		try {
			loginImage = ImageIO.read(new File("ClaspLogin.png"));
			plusImage = ImageIO.read(new File("PlusIcon.png"));
			forgotImage = ImageIO.read(new File("QuestionIcon.png"));
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
		userField.setToolTipText("Enter your username");
		pane.add(userField);
		
		pane.add(new JLabel("Password"));
		passField = new JPasswordField(16);
		passField.addActionListener(loginListener);
		passField.setToolTipText("Enter your password");
		pane.add(passField);
		
		gbc.gridy = 1;
		this.add(pane, gbc);
		
		// Add button for login
		JButton loginButton = new JButton("Log in");
		loginButton.addActionListener(loginListener);
		gbc.insets = new Insets(20, 0, 0, 0);
		gbc.gridy = 2;
		this.add(loginButton, gbc);
		
		// Bottom most buttons for creating an account and resetting password
		JButton createAccountButton = new JButton("Create Account");
		createAccountButton.setIcon(new ImageIcon(plusImage));
		
		JButton forgotPassButton = new JButton("Forgot Password");
		forgotPassButton.setIcon(new ImageIcon(forgotImage));
		
		JPanel bottomPane = new JPanel();
		bottomPane.add(createAccountButton);
		bottomPane.add(forgotPassButton);
		
		gbc.gridy = 3;
		this.add(bottomPane, gbc);
		
	}
	
	// ActionListener for login
	private class LoginListener implements ActionListener {
		
		@Override
	    public void actionPerformed(ActionEvent e) {	    	

            MainUI.userName = userField.getText();
            MainUI.masterPassword = String.valueOf(passField.getPassword());
            
            // Reset component borders
            userField.setBorder(null);
            userField.updateUI();
            passField.setBorder(null);
            passField.updateUI();
            
            // Apply red border if username is empty or whitespace, then return
            if (MainUI.userName.trim().isEmpty()) {
            	userField.setBorder(BorderFactory.createLineBorder(Color.red));
            	return;
            }
            
            // Apply red border if password is empty, then return
            if (MainUI.masterPassword.isEmpty()) {
            	passField.setBorder(BorderFactory.createLineBorder(Color.red));
            	return;
            }
            	
            JOptionPane.showMessageDialog(null,
	    		"You have logged in!\nUsername: " + MainUI.userName +
	    				"\nPass: " + MainUI.masterPassword);
	    	parent.changeView(View.PASSWORDS);

            AuthEngine authenticator = new AuthEngine();
            authenticator.makeKey(MainUI.masterPassword, MainUI.userName);


	    }
	}
	
}

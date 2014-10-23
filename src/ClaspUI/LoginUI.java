package ClaspUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import ClaspBackend.CryptoKit;
import ClaspBackend.SessionManager;
import ClaspBackend.Vault;
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

		JLabel label = new JLabel(new ImageIcon("ClaspLogin.png"));
		this.add(label, gbc);
		gbc.ipady = 0;
		gbc.insets = new Insets(0, 0, 0, 0);
		
		LoginListener loginListener = new LoginListener();
		
		// Add components to pane, add pane to window
		pane.add(new JLabel("Username:"));
		userField = new JTextField(16);
		userField.addActionListener(loginListener);
		userField.setToolTipText("Enter your username");
		pane.add(userField);
		
		pane.add(new JLabel("Password:"));
		passField = new JPasswordField(16);
		passField.addActionListener(loginListener);
		passField.setToolTipText("Enter your password");
		pane.add(passField);
		
		gbc.gridy = 1;
		this.add(pane, gbc);
		
		// Add button for login
		JButton loginButton = new JButton("Log in");
		loginButton.setIcon(new ImageIcon("LoginIcon.png"));
		loginButton.addActionListener(loginListener);
		gbc.insets = new Insets(20, 0, 0, 0);
		gbc.gridy = 2;
		this.add(loginButton, gbc);
		
		// Bottom most buttons for creating an account and resetting password
		JButton createAccountButton = new JButton("Create Account");
		createAccountButton.setIcon(new ImageIcon("PlusIcon.png"));
		createAccountButton.addActionListener(new createAccountListener());
		
		JButton forgotPassButton = new JButton("Forgot Password");
		forgotPassButton.setIcon(new ImageIcon("QuestionIcon.png"));
		forgotPassButton.addActionListener(new resetPasswordListener());
		
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

            SessionManager.setUserName(userField.getText());
            SessionManager.setMasterPassword(String.valueOf(passField.getPassword()));

            // TO DO: Need to handle login failures, etc.
            SessionManager.login();
            
            // Reset component borders
            userField.setBorder(null);
            userField.updateUI();
            passField.setBorder(null);
            passField.updateUI();
            
            // Apply red border if username is empty or whitespace, then return
            if (SessionManager.getUserName().trim().isEmpty()) {
            	userField.setBorder(BorderFactory.createLineBorder(Color.red));
            	return;
            }
            
            // Apply red border if password is empty, then return
            if (SessionManager.getMasterPassword().isEmpty()) {
            	passField.setBorder(BorderFactory.createLineBorder(Color.red));
            	return;
            }
            	
            JOptionPane.showMessageDialog(null,
	    		"You have logged in!\nUsername: " + SessionManager.getUserName() +
	    				"\nPass: " + SessionManager.getMasterPassword());
	    	parent.changeView(View.PASSWORDS);

            String cryptoKey = CryptoKit.getKey(SessionManager.getMasterPassword(), SessionManager.getUserName());
            String passHash = CryptoKit.getHash(cryptoKey, SessionManager.getMasterPassword());
            System.err.println("Key: " + cryptoKey);
            System.err.println("Password Hash: " + passHash);
            
            MainUI.vault = new Vault();
            
            parent.passwordUI.addPasswords(MainUI.vault.getAccounts());
	    }
	}
	
	private class createAccountListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			new CreateAccountDialog(parent);
			
		}
		
	}
	
	private class resetPasswordListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			new ResetPasswordDialog(parent);
			
		}
		
	}
	
}

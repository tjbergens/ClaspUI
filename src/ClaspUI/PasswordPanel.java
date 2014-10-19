package ClaspUI;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class PasswordPanel extends JPanel {
	
	private JPasswordField passwordField;
	private JCheckBox chckbxShowPassword;
	private char echoChar;
	
	public PasswordPanel(String userPass) {
		
		// Set layout
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		// Password field
		passwordField = new JPasswordField();
		passwordField.setColumns(16);
		passwordField.setText(userPass);
		echoChar = passwordField.getEchoChar();
		add(passwordField);

		// Copy button
		JButton btnCopy = new JButton("Copy");
		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringSelection clip = new StringSelection(passwordField.getPassword().toString());
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(clip, clip);
			}
		});
		add(btnCopy);
		
		// Checkbox
		chckbxShowPassword = new JCheckBox("Show Password?");
		chckbxShowPassword.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (chckbxShowPassword.isSelected()) {
					passwordField.setEchoChar('\0');
				} else {
					passwordField.setEchoChar(echoChar);
				}
			}
		});
		add(chckbxShowPassword);
		
		// Constrain password field
		springLayout.putConstraint(SpringLayout.NORTH, passwordField, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, passwordField, 10, SpringLayout.WEST, this);
				
		// Constrain show password checkbox
		springLayout.putConstraint(SpringLayout.WEST, chckbxShowPassword, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, chckbxShowPassword, 10, SpringLayout.SOUTH, passwordField);
		
		// Constrain copy button
		springLayout.putConstraint(SpringLayout.NORTH, btnCopy, 0, SpringLayout.NORTH, passwordField);
		springLayout.putConstraint(SpringLayout.WEST, btnCopy, 10, SpringLayout.EAST, passwordField);

	}
	
	
}

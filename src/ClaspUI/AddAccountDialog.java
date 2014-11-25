//This class represents adding a new subaccount to your main list of subaccounts
package ClaspUI;

import ClaspBackend.Constraints;
import ClaspBackend.Language;
import ClaspBackend.NewAccount;
import ClaspBackend.SessionManager;
import retrofit.RetrofitError;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
class AddAccountDialog extends JDialog {
    // final JFrame
	final JTextField accountField;
	final JTextField userField;
	final JTextField passField;
	
    public AddAccountDialog(final MainUI parent) {

        // Set title, stop parent from allowing interaction
        super(parent, Language.getText("ADD_ACCOUNT_HEADER"), true);

        // Set column widths
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[2];
        gridBagLayout.columnWidths[0] = 150;
        gridBagLayout.columnWidths[1] = 150;
        setLayout(gridBagLayout);

        // Dialog title label
        JLabel titleLabel = new JLabel(Language.getText("ADD_ACCOUNT_HEADER"));
        titleLabel.setFont(titleLabel.getFont().deriveFont(24.0f));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        add(titleLabel, gbc);

        // Account site
        addComponent(new JLabel(Language.getText("ACCOUNT_LOCATION")), 0, 1);
        final JTextField accountName = (JTextField) addComponent(new JTextField(16), 1, 1);
        accountField = accountName;

        // Username components
        addComponent(new JLabel(Language.getText("USERNAME")), 0, 2);
        final JTextField username = (JTextField) addComponent(new JTextField(16), 1, 2);
        userField = username;

        // Password
        addComponent(new JLabel(Language.getText("PASSWORD")), 0, 3);
        final JTextField password = (JTextField) addComponent(new JPasswordField(16), 1, 3);
        passField = password;

        // Submit button
        JButton submitButton = new JButton(Language.getText("SUBMIT"));
        gbc.gridy = 6;
        gbc.insets = new Insets(10, -100, 20, 10);
        add(submitButton, gbc);
        
        
        // Add account listener
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            	if(checkLength()){
	                try {
	                    SessionManager.addAccount(new NewAccount(accountName.getText(), username.getText(), password.getText()));
	                    dispose();
	                } catch (RetrofitError error) {
	                    parent.handleRetroError(error);
	                }
            	} 
            		
            }
        });

        // Cancel button
        JButton cancelButton = new JButton(Language.getText("CANCEL"));
        gbc.gridy = 6;
        gbc.insets = new Insets(10, -250, 20, 10);
        add(cancelButton, gbc);
        cancelButton.addActionListener(new cancelButtonListener());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Add a label or text entry field
    private JComponent addComponent(JComponent jc, int x, int y) {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        int PAD_Y = 10;
        int PAD_X = 20;
        gbc.insets = new Insets(PAD_Y, PAD_X, PAD_Y, PAD_X);
        gbc.fill = GridBagConstraints.BOTH;

        add(jc, gbc);
        return jc;
    }
    //checks validity of user input
    private boolean checkLength() {
    	//makes sure the username is less than 100 characters else spawn error message and return false
    	if(!Constraints.chkLength(accountField.getText())){
    		JOptionPane.showMessageDialog(null, Language.getText("ERROR_ACCOUNT_NAME"), 
    				Language.getText("ERROR_ACCOUNT_NAME_H"), JOptionPane.ERROR_MESSAGE);
    		return false;
    	}
    	//if username is longer than 100 characters spawn error message and return false
    	else if(!Constraints.chkLength(userField.getText())){
    		JOptionPane.showMessageDialog(null, Language.getText("ERROR_USER_NAME"), 
    				Language.getText("ERROR_USER_NAME_H"), JOptionPane.ERROR_MESSAGE);
    		return false;
    	}
    	//if password is longer than 100 characters spawn error message and return false
    	else if(!Constraints.chkLength(passField.getText())){
    		JOptionPane.showMessageDialog(null, Language.getText("ERROR_PASSWORD"), 
    				Language.getText("ERROR_PASSWORD_H"), JOptionPane.ERROR_MESSAGE);
    		return false;
    	}
    	//if it passes all checks then the input is valid and returns true
    	else return true;
    }

    //Decides what to do when cancel button is pressed
    private class cancelButtonListener implements ActionListener {

        @Override
        //disposes of the current window
        public void actionPerformed(ActionEvent e) {
            dispose();

        }

    }

}

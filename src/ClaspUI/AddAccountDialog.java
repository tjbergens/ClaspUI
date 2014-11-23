package ClaspUI;

import ClaspBackend.Constraints;
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
        super(parent, "Add new Password", true);

        // Set column widths
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[2];
        gridBagLayout.columnWidths[0] = 150;
        gridBagLayout.columnWidths[1] = 150;
        setLayout(gridBagLayout);

        // Dialog title label
        JLabel titleLabel = new JLabel("Add New Password");
        titleLabel.setFont(titleLabel.getFont().deriveFont(24.0f));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        add(titleLabel, gbc);

        // Account site
        addComponent(new JLabel("Account Location"), 0, 1);
        final JTextField accountName = (JTextField) addComponent(new JTextField(16), 1, 1);
        accountField = accountName;

        // Username components
        addComponent(new JLabel("Username"), 0, 2);
        final JTextField username = (JTextField) addComponent(new JTextField(16), 1, 2);
        userField = username;

        // Password
        addComponent(new JLabel("Password"), 0, 3);
        final JTextField password = (JTextField) addComponent(new JPasswordField(16), 1, 3);
        passField = password;

        // Submit button
        JButton submitButton = new JButton("Submit");
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
        JButton cancelButton = new JButton("Cancel");
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
    
    private boolean checkLength() {
    	if(!Constraints.chkLength(accountField.getText())){
    		JOptionPane.showMessageDialog(null, "Account Name must be less than 100 characters", "Account Name Error", JOptionPane.ERROR_MESSAGE);
    		return false;
    	}
    	else if(!Constraints.chkLength(userField.getText())){
    		JOptionPane.showMessageDialog(null, "User Name must be less than 100 characters", "User Name Error", JOptionPane.ERROR_MESSAGE);
    		return false;
    	}
    	else if(!Constraints.chkLength(passField.getText())){
    		JOptionPane.showMessageDialog(null, "Password must be less than 100 characters", "Password Error", JOptionPane.ERROR_MESSAGE);
    		return false;
    	}
    	else return true;
    }


    private class cancelButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();

        }

    }

}

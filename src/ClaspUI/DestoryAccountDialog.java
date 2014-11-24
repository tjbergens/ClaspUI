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
class DestroyAccountDialog extends JDialog {
    // final JFrame
	final JTextField username;
	final JTextField password;
	final JTextField password2;
	
    public DestroyAccountDialog(final MainUI parent) {

        // Set title, stop parent from allowing interaction
        super(parent, Language.getText("DELETE_ACCOUNT"), true);

        // Set column widths
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[2];
        gridBagLayout.columnWidths[0] = 150;
        gridBagLayout.columnWidths[1] = 150;
        setLayout(gridBagLayout);

        // Dialog title label
        JLabel titleLabel = new JLabel(Language.getText("DELETE_ACCOUNT"));
        titleLabel.setFont(titleLabel.getFont().deriveFont(24.0f));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        add(titleLabel, gbc);

        // Username
        addComponent(new JLabel(Language.getText("USERNAME")), 0, 1);
        final JTextField accountName = (JTextField) addComponent(new JTextField(16), 1, 1);
        username = accountName;

        // Password
        addComponent(new JLabel(Language.getText("PASSWORD")), 0, 2);
        final JTextField username = (JTextField) addComponent(new JTextField(16), 1, 2);
        password = username;

        // Password Confirmation
        addComponent(new JLabel(Language.getText("REPEAT_PASSWORD")), 0, 3);
        final JTextField password = (JTextField) addComponent(new JPasswordField(16), 1, 3);
        password2 = password;

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
	                    JOptionPane.showConfirmDialog(null, Language.getText("CONFIRM_DELETE"), Language.getText("DELETE_ACCOUNT"), 2);
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
    
    private boolean checkLength() {
    	if(!Constraints.chkLength(username.getText())){
    		JOptionPane.showMessageDialog(null, Language.getText("ERROR_ACCOUNT_NAME"), Language.getText("ERROR_ACCOUNT_NAME_H"), JOptionPane.ERROR_MESSAGE);
    		return false;
    	}
    	else if(!Constraints.chkLength(password.getText())){
    		JOptionPane.showMessageDialog(null, Language.getText("ERROR_USER_NAME"), Language.getText("ERROR_USER_NAME_H"), JOptionPane.ERROR_MESSAGE);
    		return false;
    	}
    	else if(!Constraints.chkLength(password2.getText())){
    		JOptionPane.showMessageDialog(null, Language.getText("ERROR_PASSWORD"), Language.getText("ERROR_PASSWORD_H"), JOptionPane.ERROR_MESSAGE);
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

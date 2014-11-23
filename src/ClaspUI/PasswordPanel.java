package ClaspUI;

import ClaspBackend.Constraints;
import ClaspBackend.SessionManager;
import retrofit.RetrofitError;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@SuppressWarnings("serial")
class PasswordPanel extends JPanel {

    private final MainUI parent;
    private final JPasswordField passwordField;
    private final JCheckBox chckbxShowPassword;
    private final char echoChar;
    private final String id;

    public PasswordPanel(String location, String userName, String userPass, String id, MainUI parent) {

        this.parent = parent;

        setBorder(new TitledBorder(null, location, TitledBorder.LEADING, TitledBorder.TOP, null, null));
        // Save id
        this.id = id;
        // Set layout
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);

        // Password field
        passwordField = new JPasswordField();
        passwordField.setColumns(16);
        passwordField.setText(userPass);
        passwordField.addActionListener(new updateAccountListener());
        echoChar = passwordField.getEchoChar();
        add(passwordField);

        // Copy button
        JButton btnCopy = new JButton("Copy");
        springLayout.putConstraint(SpringLayout.NORTH, btnCopy, -1, SpringLayout.NORTH, passwordField);
        springLayout.putConstraint(SpringLayout.WEST, btnCopy, 6, SpringLayout.EAST, passwordField);
        btnCopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringSelection clip = new StringSelection(String.valueOf(passwordField.getPassword()));
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(clip, clip);
            }
        });
        add(btnCopy);

        // Delete button
        JLabel btnDelete = new JLabel("\u00d7");
        springLayout.putConstraint(SpringLayout.NORTH, btnDelete, -10, SpringLayout.NORTH, this);
                springLayout.putConstraint(SpringLayout.EAST, btnDelete, 0, SpringLayout.EAST, this);
        
        btnDelete.setFont(btnDelete.getFont().deriveFont(18.0f));
        btnDelete.setForeground(Color.red);
        btnDelete.addMouseListener(new deleteAccountListener());
        add(btnDelete);
        

        // Update button
        JButton btnUpdate = new JButton("Save");
        springLayout.putConstraint(SpringLayout.EAST, btnUpdate, 0, SpringLayout.EAST, btnCopy);
        btnUpdate.addActionListener(new updateAccountListener());
        add(btnUpdate);

        JLabel userLabel = new JLabel("Username: " + userName);
        add(userLabel);

        // Checkbox
        chckbxShowPassword = new JCheckBox("Show Password?");
        springLayout.putConstraint(SpringLayout.NORTH, btnUpdate, 0, SpringLayout.NORTH, chckbxShowPassword);
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

        // Constrain username label
        springLayout.putConstraint(SpringLayout.NORTH, userLabel, 10, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, userLabel, 10, SpringLayout.WEST, this);

        // Constrain password field
        springLayout.putConstraint(SpringLayout.NORTH, passwordField, 10, SpringLayout.SOUTH, userLabel);
        springLayout.putConstraint(SpringLayout.WEST, passwordField, 10, SpringLayout.WEST, this);

        // Constrain show password checkbox
        springLayout.putConstraint(SpringLayout.WEST, chckbxShowPassword, 10, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.NORTH, chckbxShowPassword, 10, SpringLayout.SOUTH, passwordField);

    }

    public Dimension getPreferredSize() {
        return new Dimension(224, 112);
    }

    private class updateAccountListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to change this Password?", "Change Password?", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION && checkLength()) {
            
                try {
                    SessionManager.updateAccount(id, new String(passwordField.getPassword()));
                    SessionManager.retrieveAccounts();
                    parent.passwordUI.addPasswords(SessionManager.getAccounts());
                    parent.passwordUI.updateData();
                } catch (RetrofitError error) {
                    parent.handleRetroError(error);
                }
            }

        }

    }
    
    private boolean checkLength() {
    	    if(!Constraints.chkLength(new String(passwordField.getPassword()))){
    		JOptionPane.showMessageDialog(null, "Password must be less than 100 characters", "Password Error", JOptionPane.ERROR_MESSAGE);
    		return false;
    	}
    	else return true;
    }

    // Call function to confirm and delete specified account.
    private class deleteAccountListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to DELETE these credentials?", "DELETE Credentials?", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {

                try {
                    SessionManager.removeAccount(id);
                    SessionManager.retrieveAccounts();
                    parent.passwordUI.addPasswords(SessionManager.getAccounts());
                    parent.passwordUI.updateData();
                } catch (RetrofitError error) {
                    parent.handleRetroError(error);
                }
            }
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

    }

}

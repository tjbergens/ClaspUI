package ClaspUI;

import ClaspBackend.SessionManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class PasswordPanel extends JPanel {

    private MainUI parent;
    private JPasswordField passwordField;
    private JCheckBox chckbxShowPassword;
    private char echoChar;
    private String id;

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
        btnCopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringSelection clip = new StringSelection(String.valueOf(passwordField.getPassword()));
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(clip, clip);
            }
        });
        add(btnCopy);

        // Delete button
        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new deleteAccountListener());
        add(btnDelete);

        // Update button
        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(new updateAccountListener());
        add(btnUpdate);

        JLabel userLabel = new JLabel("Username: " + userName);
        add(userLabel);

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

        // Constrain username label
        springLayout.putConstraint(SpringLayout.NORTH, userLabel, 10, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, userLabel, 10, SpringLayout.WEST, this);

        // Constrain password field
        springLayout.putConstraint(SpringLayout.NORTH, passwordField, 10, SpringLayout.SOUTH, userLabel);
        springLayout.putConstraint(SpringLayout.WEST, passwordField, 10, SpringLayout.WEST, this);

        // Constrain show password checkbox
        springLayout.putConstraint(SpringLayout.WEST, chckbxShowPassword, 10, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.NORTH, chckbxShowPassword, 10, SpringLayout.SOUTH, passwordField);

        // Constrain copy button
        springLayout.putConstraint(SpringLayout.NORTH, btnCopy, 0, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, btnCopy, 10, SpringLayout.EAST, userLabel);

        // Constrain delete button
        springLayout.putConstraint(SpringLayout.SOUTH, btnDelete, 0, SpringLayout.SOUTH, passwordField);
        springLayout.putConstraint(SpringLayout.WEST, btnDelete, 10, SpringLayout.EAST, passwordField);

        // Constrain update button
        springLayout.putConstraint(SpringLayout.SOUTH, btnUpdate, 0, SpringLayout.SOUTH, chckbxShowPassword);
        springLayout.putConstraint(SpringLayout.WEST, btnUpdate, 10, SpringLayout.EAST, chckbxShowPassword);

    }

    public Dimension getPreferredSize() {
        return new Dimension(224, 112);
    }

    public class updateAccountListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to change this Password?", "Change Password?", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                SessionManager.updateAccount(id, new String(passwordField.getPassword()));
                parent.passwordUI.addPasswords(SessionManager.getAccounts());
                parent.passwordUI.updateData();
            }

        }

    }

    // Call function to confirm and delete specified account.
    public class deleteAccountListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to DELETE these credentials?", "DELETE Credentials?", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                SessionManager.removeAccount(id);
                parent.passwordUI.addPasswords(SessionManager.getAccounts());
                parent.passwordUI.updateData();
            }

        }

    }

}

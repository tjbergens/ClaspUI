package ClaspUI;

import ClaspBackend.Language;
import ClaspBackend.SessionManager;
import ClaspUI.MainUI.View;
import retrofit.RetrofitError;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
class LoginUI extends JPanel {

    private final JTextField userField;
    private final JPasswordField passField;
    private final MainUI parent;

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

        JLabel label = new JLabel(new ImageIcon(getClass().getResource("ClaspLogin.png")));
        this.add(label, gbc);
        gbc.ipady = 0;
        gbc.insets = new Insets(0, 0, 0, 0);

        LoginListener loginListener = new LoginListener();

        // Add components to pane, add pane to window
        pane.add(new JLabel(Language.getText("USERNAME") + ":"));
        userField = new JTextField(16);
        userField.addActionListener(loginListener);
        userField.setToolTipText("Enter your username");
        pane.add(userField);

        pane.add(new JLabel(Language.getText("PASSWORD") + ":"));
        passField = new JPasswordField(16);
        passField.addActionListener(loginListener);
        passField.setToolTipText("Enter your password");
        pane.add(passField);

        gbc.gridy = 1;
        this.add(pane, gbc);

        // Add button for login
        JButton loginButton = new JButton(Language.getText("LOGIN"));
        loginButton.setIcon(new ImageIcon(getClass().getResource("LoginIcon.png")));
        loginButton.addActionListener(loginListener);
        gbc.insets = new Insets(20, 0, 0, 0);
        gbc.gridy = 2;
        this.add(loginButton, gbc);
        

        // Bottom most buttons for creating an account and resetting password
        JButton createAccountButton = new JButton(Language.getText("CREATE"));
        createAccountButton.setIcon(new ImageIcon(getClass().getResource("PlusIcon.png")));
        createAccountButton.addActionListener(new createAccountListener());

        JButton deleteAccountButton = new JButton(Language.getText("DELETE"));
        deleteAccountButton.setIcon(new ImageIcon(getClass().getResource("QuestionIcon.png")));
        deleteAccountButton.addActionListener(new deleteAccountListener());

        JPanel bottomPane = new JPanel();
        bottomPane.add(createAccountButton);
        bottomPane.add(deleteAccountButton);

        gbc.gridy = 3;
        this.add(bottomPane, gbc);

    }

    // ActionListener for login
    private class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            SessionManager.setUserName(userField.getText());
            SessionManager.setMasterPassword(String.valueOf(passField.getPassword()));

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

            // TO DO: Need to handle login failures, etc.
            try {
                SessionManager.login();

                JOptionPane.showMessageDialog(null,
                        "You have logged in!\nUsername: " + SessionManager.getUserName());
                parent.changeView(View.PASSWORDS);
                SessionManager.retrieveAccounts();
                parent.passwordUI.addPasswords(SessionManager.getAccounts());
            } catch (RetrofitError error) {
            	JOptionPane.showMessageDialog(null, "Login Failed");
            }

        }
    }


    private class createAccountListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            new CreateAccountDialog(parent);

        }

    }

    
    private class deleteAccountListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            new DestroyAccountDialog(parent);

        }

    }

}

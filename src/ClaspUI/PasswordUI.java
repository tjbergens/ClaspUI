package ClaspUI;

import ClaspBackend.Account;
import ClaspBackend.SessionManager;
import ClaspUI.MainUI.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

//import ClaspUI.LoginUI.createAccountListener;

@SuppressWarnings("serial")
public class PasswordUI extends JPanel {

    private MainUI parent;
    private JButton logoutButton;
    private JLabel welcomeLabel;
    private JPanel contentPane;

    public PasswordUI(MainUI parent) {

        this.parent = parent;

        // Add some text and a button as an example
        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        welcomeLabel = new JLabel();
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(14.0f));

        // Add log out button
        logoutButton = new JButton("Log out");
        logoutButton.setIcon(new ImageIcon("LogoutIcon.png"));
        logoutButton.addActionListener(new LogoutButtonListener());

        // Bottom pane
        JPanel bottomPane = new JPanel();
        bottomPane.setLayout(new GridLayout(1, 3));

        JButton addButton = new JButton("Add Password");
        addButton.setIcon(new ImageIcon("PlusIcon.png"));
        addButton.addActionListener(new addAccountListener());
        bottomPane.add(addButton);

        JButton deleteButton = new JButton("Delete Password");
        deleteButton.addActionListener(new deleteAccountListener());
        bottomPane.add(deleteButton);

        bottomPane.add(logoutButton);

        // Scroll pane for passwords
        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPane.setLayout(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane(contentPane);

        // Add components
        add(welcomeLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPane, BorderLayout.SOUTH);

    }

    // Refresh password list
    public void addPasswords(List<Account> passwords) {

        contentPane.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();

        for (int i = 0; i < passwords.size(); ++i) {
            Account current = passwords.get(i);
            gbc.gridy = i;
            PasswordPanel pp = new PasswordPanel(current.accountName.toString(), current.userName.toString(), current.password.toString(), current.id);
            contentPane.add(pp, gbc);
        }

    }

    private void updateData() {
        welcomeLabel.setText("Welcome " + SessionManager.getUserName() + "!");
    }

    public void paintComponent(Graphics g) {
        updateData();
        super.paintComponent(g);
    }

    private class addAccountListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            new AddAccountDialog(parent);
            System.err.println("about to update password list...");
            addPasswords(SessionManager.getAccounts());
            contentPane.updateUI();
            System.err.println("....done.");

        }

    }

    private class deleteAccountListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            new DeleteAccountDialog(parent);

        }

    }

    private class LogoutButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            parent.changeView(View.LOGIN);
        }

    }

}

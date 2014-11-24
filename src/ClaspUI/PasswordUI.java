package ClaspUI;

import ClaspBackend.Account;
import ClaspBackend.Language;
import ClaspBackend.SessionManager;
import ClaspUI.MainUI.View;
import retrofit.RetrofitError;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

//import ClaspUI.LoginUI.createAccountListener;

@SuppressWarnings("serial")
class PasswordUI extends JPanel {

    private final MainUI parent;
    private final JLabel welcomeLabel;
    private final JPanel contentPane;

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
        JButton logoutButton = new JButton(Language.getText("LOGOUT"));
        logoutButton.setIcon(new ImageIcon("LogoutIcon.png"));
        logoutButton.addActionListener(new LogoutButtonListener());

        // Bottom pane
        JPanel bottomPane = new JPanel();
        bottomPane.setLayout(new GridLayout(1, 3));

        JButton addButton = new JButton(Language.getText("ADD_PASSWORD"));
        addButton.setIcon(new ImageIcon("PlusIcon.png"));
        addButton.addActionListener(new addAccountListener());
        bottomPane.add(addButton);


        bottomPane.add(logoutButton);

        // Scroll pane for passwords
        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPane.setLayout(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane(contentPane);

        // Fix slow scroll
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

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
            PasswordPanel pp = new PasswordPanel(current.accountName, current.userName, current.password, current.id, parent);
            contentPane.add(pp, gbc);
        }

    }

    public void updateData() {
        welcomeLabel.setText(Language.getTextDynamic("WELCOME", SessionManager.getUserName()));
        contentPane.updateUI();
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

            try {
                SessionManager.retrieveAccounts();
                addPasswords(SessionManager.getAccounts());
                contentPane.updateUI();
            } catch (RetrofitError error) {
                parent.handleRetroError(error);
            }
            System.err.println("....done.");

        }

    }

    private class LogoutButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            parent.changeView(View.LOGIN);
        }

    }

}

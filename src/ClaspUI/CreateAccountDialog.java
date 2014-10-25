package ClaspUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class CreateAccountDialog extends JDialog {

    final int PAD_X = 20;
    final int PAD_Y = 10;

    public CreateAccountDialog(JFrame parent) {

        // Set title, stop parent from allowing interaction
        super(parent, "Create an account", true);

        // Set column widths
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[2];
        gridBagLayout.columnWidths[0] = 150;
        gridBagLayout.columnWidths[1] = 150;
        setLayout(gridBagLayout);

        // Dialog title label
        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setFont(titleLabel.getFont().deriveFont(24.0f));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        add(titleLabel, gbc);

        // Username components
        addComponent(new JLabel("Username"), 0, 1);
        addComponent(new JTextField(16), 1, 1);

        // Password
        addComponent(new JLabel("Password"), 0, 2);
        addComponent(new JPasswordField(16), 1, 2);

        // Password retype field
        addComponent(new JLabel("Repeat Password"), 0, 3);
        addComponent(new JPasswordField(16), 1, 3);

        // Security Question
        addComponent(new JLabel("Security Question"), 0, 4);
        addComponent(new JTextField(16), 1, 4);

        // Answer to Security Question
        addComponent(new JLabel("Security Answer"), 0, 5);
        addComponent(new JTextField(16), 1, 5);

        // Submit button
        JButton submitButton = new JButton("Submit");
        gbc.gridy = 6;
        gbc.insets = new Insets(10, 10, 20, 10);
        add(submitButton, gbc);
        submitButton.addActionListener(new submitButtonListener());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Add a label or text entry field
    private void addComponent(JComponent jc, int x, int y) {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.insets = new Insets(PAD_Y, PAD_X, PAD_Y, PAD_X);
        gbc.fill = GridBagConstraints.BOTH;

        add(jc, gbc);

    }

    private class submitButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {


        }

    }

}

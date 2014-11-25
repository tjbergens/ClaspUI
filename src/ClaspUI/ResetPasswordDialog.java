package ClaspUI;

import ClaspBackend.Language;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
class ResetPasswordDialog extends JDialog {

    public ResetPasswordDialog(JFrame parent) {

        // Set title, stop parent from allowing interaction
        super(parent, Language.getText("RESET_PASSWORD"), true);

        // Set column widths
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[2];
        gridBagLayout.columnWidths[0] = 150;
        gridBagLayout.columnWidths[1] = 150;
        setLayout(gridBagLayout);

        // Dialog title label
        JLabel titleLabel = new JLabel(Language.getText("RESET_PASSWORD"));
        titleLabel.setFont(titleLabel.getFont().deriveFont(24.0f));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        add(titleLabel, gbc);

        // Username components
        addComponent(new JLabel(Language.getText("USERNAME")), 0, 1);
        addComponent(new JTextField(16), 1, 1);

        // Submit button
        JButton nextButton = new JButton(Language.getText("NEXT"));
        gbc.gridy = 6;
        gbc.insets = new Insets(10, 10, 20, 10);
        add(nextButton, gbc);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Add a label or text entry field
    private void addComponent(JComponent jc, int x, int y) {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        int PAD_Y = 10;
        int PAD_X = 20;
        gbc.insets = new Insets(PAD_Y, PAD_X, PAD_Y, PAD_X);
        gbc.fill = GridBagConstraints.BOTH;

        add(jc, gbc);

    }

}

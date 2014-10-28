package ClaspUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class DeleteAccountDialog extends JDialog {

    final int PAD_X = 20;
    final int PAD_Y = 10;

    public DeleteAccountDialog(JFrame parent) {

        // Set title, stop parent from allowing interaction
        super(parent, "Delete Password", true);

        // Set column widths
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[2];
        gridBagLayout.columnWidths[0] = 150;
        gridBagLayout.columnWidths[1] = 150;
        setLayout(gridBagLayout);

        // Dialog title label
        JLabel titleLabel = new JLabel("Are you sure you want to delete this password?");
        titleLabel.setFont(titleLabel.getFont().deriveFont(24.0f));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        add(titleLabel, gbc);



        // Submit button
        JButton submitButton = new JButton("Submit");
        gbc.gridy = 6;
        gbc.insets = new Insets(10, 10, 20, 10);
        add(submitButton, gbc);
        submitButton.addActionListener(new submitButtonListener());
        
     // Cancel button
        JButton cancelButton = new JButton("Cancel");
        gbc.gridy = 6;
        gbc.insets = new Insets(10, 10, 20, 10);
        add(cancelButton, gbc);
        cancelButton.addActionListener(new cancelButtonListener());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private class submitButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {


        }

    }
    
    private class cancelButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {


        }

    }

}

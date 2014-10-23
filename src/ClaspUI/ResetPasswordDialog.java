package ClaspUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ResetPasswordDialog extends JDialog {
	
	final int PAD_X = 20;
	final int PAD_Y = 10;

	public ResetPasswordDialog(JFrame parent) {
		
		// Set title, stop parent from allowing interaction
		super(parent, "Reset your password", true);
		
		// Set column widths
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[2];
		gridBagLayout.columnWidths[0] = 150;
		gridBagLayout.columnWidths[1] = 150;
		setLayout(gridBagLayout);

		// Dialog title label
		JLabel titleLabel = new JLabel("Reset Password");
		titleLabel.setFont(titleLabel.getFont().deriveFont(24.0f));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = 2;
		gbc.insets = new Insets(20, 10, 10, 10);
		add(titleLabel, gbc);
		
		// Username components
		addComponent(new JLabel("Username"), 0, 1);
		addComponent(new JTextField(16), 1, 1);
		
		// Submit button
		JButton nextButton = new JButton("Next");
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
		gbc.insets = new Insets(PAD_Y, PAD_X, PAD_Y, PAD_X);
		gbc.fill = GridBagConstraints.BOTH;
		
		add(jc, gbc);
		
	}
	
}

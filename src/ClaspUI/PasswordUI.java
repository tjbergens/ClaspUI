package ClaspUI;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ClaspUI.MainUI.View;

@SuppressWarnings("serial")
public class PasswordUI extends JPanel {
	
	private MainUI parent;
	private JButton backButton;
	private JLabel welcomeLabel;
	
	public PasswordUI(MainUI parent) {
		
		this.parent = parent;
	
		// Add some text and a button as an example
		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(gridBagLayout);
		welcomeLabel = new JLabel();
		backButton = new JButton("Back");
		backButton.addActionListener(new BackButtonListener());
		
		updateData();
		
		// Add components
		gbc.insets = new Insets(20, 20, 20, 20);
		gbc.gridy = 0;
		add(welcomeLabel, gbc);
		
		gbc.gridy = 1;
		PasswordPanel pp = new PasswordPanel("Test Pass");
		add(pp, gbc);
		
		gbc.gridy = 2;
		add(backButton, gbc);
		
		setVisible(true);
	}
	
	private void updateData() {
		welcomeLabel.setText("Welcome " + MainUI.userName + "!");
	}
	
	public void paintComponent(Graphics g) {
		updateData();
		super.paintComponent(g);
	}
	
	
	private class BackButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			parent.changeView(View.LOGIN);
		}
		
	}

}

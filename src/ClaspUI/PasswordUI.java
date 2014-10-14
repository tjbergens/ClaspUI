package ClaspUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

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
		
		welcomeLabel = new JLabel("Welcome username!");
		backButton = new JButton("Back");
		backButton.addActionListener(new BackButtonListener());
		
		// Add components
		gbc.insets = new Insets(20, 20, 20, 20);
		gbc.gridy = 0;
		add(welcomeLabel, gbc);
		gbc.gridy = 1;
		add(backButton, gbc);
		
		setVisible(true);
	}
	
	private class BackButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			parent.changeView(View.LOGIN);
		}
		
	}

}

package ClaspUI;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ClaspUI.MainUI.View;

@SuppressWarnings("serial")
public class PasswordUI extends JPanel {
	
	private MainUI parent;
	private JButton logoutButton;
	private JLabel welcomeLabel;
	
	public PasswordUI(MainUI parent) {
		
		this.parent = parent;
		
		// Add some text and a button as an example
		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(gridBagLayout);
		welcomeLabel = new JLabel();
		logoutButton = new JButton("Log out");
		logoutButton.setIcon(new ImageIcon("LogoutIcon.png"));
		logoutButton.addActionListener(new LogoutButtonListener());
		
		updateData();
		
		// Add components
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridy = 0;
		add(welcomeLabel, gbc);
		
		gbc.gridy = 1;
		PasswordPanel pp = new PasswordPanel("Test Pass");
		JPanel contentPane = new JPanel();
		JScrollPane scrollPane = new JScrollPane(contentPane);
		contentPane.add(pp);
		add(scrollPane, gbc);
		
		gbc.gridy = 2;
		add(logoutButton, gbc);
		
		setVisible(true);
	}
	
	private void updateData() {
		welcomeLabel.setText("Welcome " + MainUI.userName + "!");
	}
	
	public void paintComponent(Graphics g) {
		updateData();
		super.paintComponent(g);
	}
	
	private class LogoutButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			parent.changeView(View.LOGIN);
		}
		
	}

}

package ClaspUI;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import ClaspBackend.Account;
import ClaspUI.MainUI.View;

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
		logoutButton = new JButton("Log out");
		logoutButton.setIcon(new ImageIcon("LogoutIcon.png"));
		logoutButton.addActionListener(new LogoutButtonListener());
		
		updateData();
		
		// Add components
		add(welcomeLabel, BorderLayout.NORTH);
		
		contentPane = new JPanel();
		contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		contentPane.setLayout(new GridBagLayout());
		
		JScrollPane scrollPane = new JScrollPane(contentPane);
		add(scrollPane, BorderLayout.CENTER);
		add(logoutButton, BorderLayout.SOUTH);

	}
	
	public void addPasswords(ArrayList<Account> passwords) {
		
		contentPane.removeAll();
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		for (int i = 0; i < passwords.size(); ++i) {
			Account current = passwords.get(i);
			gbc.gridy = i;
			PasswordPanel pp = new PasswordPanel(current.accountName, current.userName, current.password);
			contentPane.add(pp, gbc);
		}
		
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

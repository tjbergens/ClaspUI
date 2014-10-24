package ClaspUI;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import ClaspBackend.Account;
import ClaspBackend.SessionManager;
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
		
		// Add log out button
		logoutButton = new JButton("Log out");
		logoutButton.setIcon(new ImageIcon("LogoutIcon.png"));
		logoutButton.addActionListener(new LogoutButtonListener());
		
		// Bottom pane
		JPanel bottomPane = new JPanel();
		bottomPane.setLayout(new GridLayout(1, 3));
		
		JButton addButton = new JButton("Add Password");
		addButton.setIcon(new ImageIcon("PlusIcon.png"));
		bottomPane.add(addButton);
		
		JButton deleteButton = new JButton("Delete Password");
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
	
	public void addPasswords(List<Account> passwords) {
				
		contentPane.removeAll();
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		for (int i = 0; i < passwords.size(); ++i) {
			Account current = passwords.get(i);
			gbc.gridy = i;
			PasswordPanel pp = new PasswordPanel(current.accountName.toString(), current.userName.toString(), current.password.toString());
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
	
	private class LogoutButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			parent.changeView(View.LOGIN);
		}
		
	}
	
}

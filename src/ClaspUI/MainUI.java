package ClaspUI;

import java.awt.FlowLayout;

import javax.swing.*;

public class MainUI extends JFrame {
	
	private final int INIT_WIDTH = 520;
	private final int INIT_HEIGHT = 300;

	public MainUI() {
		
		super("Clasp Password Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			
		}
		setSize(INIT_WIDTH, INIT_HEIGHT);
		setLayout(new FlowLayout());
		add(new JLabel("Username"));
		add(new JTextField(16));
		add(new JLabel("Password"));
		add(new JTextField(16));
		JButton jb = new JButton("Log in");
		add(jb);
		setVisible(true);
		
	}
	
}

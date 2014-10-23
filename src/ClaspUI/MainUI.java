/*
MainUI.java
*/

package ClaspUI;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class MainUI extends JFrame {
	
	// Constants for window width and height
	private final int INIT_WIDTH = 400;
	private final int INIT_HEIGHT = 300;


    public static enum View {
		LOGIN, PASSWORDS
	};
	private CardLayout cardLayout;
	private JPanel content;
	PasswordUI passwordUI;

	public MainUI() {
		
		// Calls JFrame constructor to set title
		super("Clasp Password Manager");
		
		// Close the program when the window is closed
		// By default, closing the window leaves the program running in the background
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(INIT_WIDTH, INIT_HEIGHT));

		// Set window icons
		try {
			ArrayList<Image> list = new ArrayList<Image>();
			list.add(ImageIO.read(new File("ClaspIcon16.png")));
			list.add(ImageIO.read(new File("ClaspIcon32.png")));
			list.add(ImageIO.read(new File("ClaspIcon256.png")));
			setIconImages(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Set look and feel to match the system
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// Set size and position of the window
		setSize(INIT_WIDTH, INIT_HEIGHT);
		setLocationRelativeTo(null);
		
		cardLayout = new CardLayout();
		content = new JPanel();
		content.setLayout(cardLayout);
		
		// Add UI views to content panel
		content.add(new LoginUI(this), View.LOGIN.toString());
		passwordUI = new PasswordUI(this);
		content.add(passwordUI, View.PASSWORDS.toString());

		this.add(content);
		// Call to show all components
		setVisible(true);
	}
	
	public void changeView(View view) {
		cardLayout.show(content, view.toString());
	}

}

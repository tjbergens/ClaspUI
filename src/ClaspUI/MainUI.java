/*
MainUI.java
*/

package ClaspUI;

import ClaspBackend.Language;
import retrofit.RetrofitError;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

@SuppressWarnings("serial")
public class MainUI extends JFrame {

    final PasswordUI passwordUI;
    private final CardLayout cardLayout;
    private final JPanel content;

    public MainUI() {

        // Calls JFrame constructor to set title
        super(Language.getText("CLASP_MANAGER"));
        
        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        // File menu
        JMenu fileMenu = new JMenu(Language.getText("FILE"));
        
        // Exit option
        JMenuItem fileExitItem = new JMenuItem(Language.getText("EXIT"));
        
        // Close window when exit is chosen
        fileExitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
        });
        
        // Show exit icon
        fileExitItem.setIcon(new ImageIcon("LogoutIcon.png"));
        
        // Add exit to file menu, and file menu to the menu bar
        fileMenu.add(fileExitItem);
        menuBar.add(fileMenu);
        
        // Language option
        JMenu langMenu = new JMenu(Language.getText("LANGUAGE"));
        
        // Get resource bundle representing available languages
        final ResourceBundle resourceBundle = ResourceBundle.getBundle("lang.Languages");
        
        // Get list of languages from resource bundle
        String[] langs = resourceBundle.getString("order").split(",");
        
        // Add a button group for the language radio buttons
        ButtonGroup langGroup = new ButtonGroup();
        
        // For each available language
        for (final String s : langs) {
        	
        	// Add item to language menu with text from resource bundle
        	JRadioButtonMenuItem temp = new JRadioButtonMenuItem(resourceBundle.getString(s));
        	langGroup.add(temp);
        	langMenu.add(temp);
        	
        	// If the language is selected, change language
        	temp.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		Language.setLanguage(s);
            	}
            });
        	
        	// Also show that the language is selected in the menu
        	if (Locale.getDefault().getLanguage().equals(s)) {
        		temp.setSelected(true);
        	}
        }

        // Add language menu to menu bar
        menuBar.add(langMenu);
        
        // Help menu
        JMenu helpMenu = new JMenu(Language.getText("HELP"));
        
        // About option
        JMenuItem aboutItem = new JMenuItem(Language.getText("ABOUT"));

        // When about is selected, show a dialog box with the team members
        aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, 
						Language.getText("ALPACA_TEAM") + "\n\n" +
						"Nigel Carter    Cindy Harn\n" +
						"Mathew Church    Thomas Bergens\n" +
						"Michelle Edwards    William Pearigen", 
						Language.getText("ABOUT"), JOptionPane.PLAIN_MESSAGE,
						new ImageIcon("ClaspIcon32.png")
						);
			}
        });
        
        // Set about item icon
        aboutItem.setIcon(new ImageIcon("QuestionIcon.png"));
        
        // Add about item to help menu, and help menu to menu bar
        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);
        
        // Close the program when the window is closed
        // By default, closing the window leaves the program running in the background
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int INIT_HEIGHT = 300;
        int INIT_WIDTH = 400;
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set size and position of the window
        setSize(INIT_WIDTH, INIT_HEIGHT);
        setLocationRelativeTo(null);

        // Card layout to manage multiple screens
        cardLayout = new CardLayout();
        content = new JPanel();
        content.setLayout(cardLayout);

        // Add UI views to content panel
        content.add(new LoginUI(this), View.LOGIN.toString());
        passwordUI = new PasswordUI(this);
        content.add(passwordUI, View.PASSWORDS.toString());

        // Add JPaen with content to JFrame
        this.add(content);
        
        // Call to show all components
        setVisible(true);
    }

    // Handle any Network or Request errors and display to the user.
    public void handleRetroError(RetrofitError e) {

        if (e.getKind().equals(RetrofitError.Kind.NETWORK)) {

        	// Network error
            JOptionPane.showMessageDialog(null, Language.getText("ERROR_CONNECTION"), 
            		Language.getText("ERROR_CONNECTION_H"), JOptionPane.ERROR_MESSAGE);

        } else if (e.getKind().equals(RetrofitError.Kind.HTTP)) {

        	// Generic error
            JOptionPane.showMessageDialog(null, e.getMessage(), Language.getText("ERROR"), JOptionPane.ERROR_MESSAGE);
        }
    }

    // Change to another view
    public void changeView(View view) {
        cardLayout.show(content, view.toString());
    }

    // View enumeration to represent each screen
    public static enum View {
        LOGIN, PASSWORDS
    }

}

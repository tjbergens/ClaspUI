/*
MainUI.java
*/

package ClaspUI;

import retrofit.RetrofitError;

import javax.imageio.ImageIO;
import javax.swing.*;

import ClaspBackend.Language;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

@SuppressWarnings("serial")
public class MainUI extends JFrame {

    final PasswordUI passwordUI;
    private final CardLayout cardLayout;
    private final JPanel content;

    public MainUI() {

        // Calls JFrame constructor to set title
        super("Clasp Password Manager");
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu fileMenu = new JMenu(Language.getText("FILE"));
        JMenuItem fileExitItem = new JMenuItem(Language.getText("EXIT"));
        fileExitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
        });
        fileExitItem.setIcon(new ImageIcon("LogoutIcon.png"));
        fileMenu.add(fileExitItem);
        menuBar.add(fileMenu);
        
        
        JMenu langMenu = new JMenu(Language.getText("LANGUAGE"));
        ButtonGroup langGroup = new ButtonGroup();
        JRadioButtonMenuItem defaultLangItem = new JRadioButtonMenuItem("Default");
        JRadioButtonMenuItem englishLangItem = new JRadioButtonMenuItem("English");
        JRadioButtonMenuItem spanishLangItem = new JRadioButtonMenuItem("Spanish");
        if (Locale.getDefault() == Language.defaultLocale)
        	defaultLangItem.setSelected(true);
        else 
        	if (Locale.getDefault().getLanguage().equals("en"))
        	englishLangItem.setSelected(true);
        else if (Locale.getDefault().getLanguage().equals("es"))
        	spanishLangItem.setSelected(true);
        
        langMenu.add(defaultLangItem);
        langMenu.add(englishLangItem);
        langMenu.add(spanishLangItem);
        langGroup.add(defaultLangItem);
        langGroup.add(englishLangItem);
        langGroup.add(spanishLangItem);
        menuBar.add(fileMenu);
        menuBar.add(langMenu);
        defaultLangItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Language.setLanguage("default");
        	}
        });
        englishLangItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Language.setLanguage("en");
        	}
        });
        spanishLangItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Language.setLanguage("es");
        	}
        });
        
        
        JMenu aboutMenu = new JMenu(Language.getText("HELP"));
        JMenuItem aboutItem = new JMenuItem(Language.getText("ABOUT"));

        aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, 
						"Created by the Alpaca Team:\n\n" +
						"Nigel Carter    Cindy Harn\n" +
						"Mathew Church    Thomas Bergens\n" +
						"Michelle Edwards    William Pearigen", 
						"About", JOptionPane.PLAIN_MESSAGE,
						new ImageIcon("ClaspIcon32.png")
						);
			}
        });
        aboutItem.setIcon(new ImageIcon("QuestionIcon.png"));
        aboutMenu.add(aboutItem);
        menuBar.add(aboutMenu);
        
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

    // Handle any Network or Request errors and display to the user.
    public void handleRetroError(RetrofitError e) {

        if (e.getKind().equals(RetrofitError.Kind.NETWORK)) {

            JOptionPane.showMessageDialog(null, "A connection error has occurred. Please check your connection and try again.", "Connection Error!", JOptionPane.ERROR_MESSAGE);

        } else if (e.getKind().equals(RetrofitError.Kind.HTTP)) {


            JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void changeView(View view) {
        cardLayout.show(content, view.toString());
    }

    public static enum View {
        LOGIN, PASSWORDS
    }

}

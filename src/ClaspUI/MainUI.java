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
        final ResourceBundle resourceBundle = ResourceBundle.getBundle("lang.Languages");
       
        ButtonGroup langGroup = new ButtonGroup();
        //ArrayList<JRadioButtonMenuItem> langItems = new ArrayList<JRadioButtonMenuItem>();
        String[] langs = resourceBundle.getString("order").split(",");
        for (final String s : langs) {
        	JRadioButtonMenuItem temp = new JRadioButtonMenuItem(resourceBundle.getString(s));
        	//langItems.add(temp);
        	langGroup.add(temp);
        	langMenu.add(temp);
        	temp.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		Language.setLanguage(s);
            	}
            });
        	if (Locale.getDefault().getLanguage().equals(s)) {
        		temp.setSelected(true);
        	}
        }

        menuBar.add(fileMenu);
        menuBar.add(langMenu);
        
        JMenu aboutMenu = new JMenu(Language.getText("HELP"));
        JMenuItem aboutItem = new JMenuItem(Language.getText("ABOUT"));

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

            JOptionPane.showMessageDialog(null, Language.getText("ERROR_CONNECTION"), 
            		Language.getText("ERROR_CONNECTION_H"), JOptionPane.ERROR_MESSAGE);

        } else if (e.getKind().equals(RetrofitError.Kind.HTTP)) {


            JOptionPane.showMessageDialog(null, e.getMessage(), Language.getText("ERROR"), JOptionPane.ERROR_MESSAGE);
        }
    }

    public void changeView(View view) {
        cardLayout.show(content, view.toString());
    }

    public static enum View {
        LOGIN, PASSWORDS
    }

}

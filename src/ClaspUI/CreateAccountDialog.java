package ClaspUI;

import ClaspBackend.Constraints;
import ClaspBackend.ErrorChecking;
import ClaspBackend.Language;
import ClaspBackend.SessionManager;
import retrofit.RetrofitError;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
class CreateAccountDialog extends JDialog {

    private final JTextField userField;
    //private final JTextField emailField;
    private final JPasswordField passField;
    private final JPasswordField repeatField;
    private final MainUI parent;

    public CreateAccountDialog(final MainUI parent) {

        // Set title, stop parent from allowing interaction
        super(parent, Language.getText("CREATE_HEADER"), true);
        this.parent = parent;
        
        // Set column widths
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[2];
        gridBagLayout.columnWidths[0] = 150;
        gridBagLayout.columnWidths[1] = 150;
        setLayout(gridBagLayout);

        // Dialog title label
        JLabel titleLabel = new JLabel(Language.getText("CREATE"));
        titleLabel.setFont(titleLabel.getFont().deriveFont(24.0f));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 3;
        gbc.insets = new Insets(20, 10, 10, 10);
        add(titleLabel, gbc);

        // Username components
        userField = new JTextField(16);
        addComponent(new JLabel(Language.getText("USERNAME")), 0, 1);
        addComponent(userField, 1, 1);

        // Email components
        //emailField = new JTextField(16);
        //addComponent(new JLabel("Email"), 0, 2);
        //addComponent(emailField, 1, 2);

        // Password
        passField = new JPasswordField(16);
        addComponent(new JLabel(Language.getText("PASSWORD")), 0, 3);
        addComponent(passField, 1, 3);

        // Password retype field
        repeatField = new JPasswordField(16);
        addComponent(new JLabel(Language.getText("REPEAT_PASSWORD")), 0, 4);
        addComponent(repeatField, 1, 4);

        // Security Question
        //addComponent(new JLabel("Security Question"), 0, 5);
        //addComponent(new JTextField(16), 1, 5);

        // Answer to Security Question
        //addComponent(new JLabel("Security Answer"), 0, 6);
        //addComponent(new JTextField(16), 1, 6);

        // Submit button
        JButton submitButton = new JButton(Language.getText("SUBMIT"));
        gbc.gridy = 7;
        gbc.insets = new Insets(10, 10, 20, 10);
        add(submitButton, gbc);
        submitButton.addActionListener(new submitButtonListener());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Add a label or text entry field
    private void addComponent(JComponent jc, int x, int y) {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        int PAD_Y = 10;
        int PAD_X = 20;
        gbc.insets = new Insets(PAD_Y, PAD_X, PAD_Y, PAD_X);
        gbc.fill = GridBagConstraints.BOTH;

        add(jc, gbc);

    }
    
  //checks validity of user argument
    private boolean checkConstraints(){
    	//makes sure the username entered is valid if it is not it pops error message
    	if(!Constraints.userName(userField.getText()) || userField.getText().isEmpty()){
    		JOptionPane.showMessageDialog(null, Language.getText("ERROR_USER_NAME"),
                    Language.getText("ERROR_USER_NAME_H"), JOptionPane.ERROR_MESSAGE);
    		//not true so return false
	    	return false;
    	}
    	//else if(!Constraints.email(emailField.getText())){
    	//	JOptionPane.showMessageDialog(null, "Email must be in format Username@Domain", "Email Error", JOptionPane.ERROR_MESSAGE);
        //	return false;
    	//}
    	//check if password is valid, if not spawn error message
    	else if((!Constraints.password(new String(passField.getPassword()))) || (new String(passField.getPassword()).isEmpty())){
    		JOptionPane.showMessageDialog(null, Language.getText("ERROR_PASSWORD"), 
    				Language.getText("ERROR_PASSWORD_H"), JOptionPane.ERROR_MESSAGE);
    		//return false
        	return false;	
    	}
    	//check that passwords match if not spawn error
    	else if(!(new String(passField.getPassword()).equals(new String(repeatField.getPassword()))) || (new String(passField.getPassword()).isEmpty())){
    		JOptionPane.showMessageDialog(null, Language.getText("ERROR_PASSWORD_NOMATCH"), 
    				Language.getText("ERROR_PASSWORD_H"), JOptionPane.ERROR_MESSAGE);
    		//return false
        	return false;
    	}
    	//otherwise all fields are valid return true
    	else return true;
    }
    //decided what to do when submit button is clicked by the user
    private class submitButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
        	//if user fields are valid
        	if(checkConstraints()){
        		
        		//try to connect to the server
	            try {
	                SessionManager.createAccount(userField.getText(), "", new String(passField.getPassword()));
	                dispose();
	                //if server communication fails display corresponding error message.
	            } catch (RetrofitError error) {
	            	
	            	boolean known = ErrorChecking.handleRetrofit(error);
	            	if (!known) {
	            		JOptionPane.showMessageDialog(null, Language.getText("ERROR_CREATE_FAILED"));
	            	}
	            }
            }
        }

    }

}

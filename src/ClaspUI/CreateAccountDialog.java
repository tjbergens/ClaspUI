package ClaspUI;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class CreateAccountDialog extends JDialog {

	public CreateAccountDialog() {
		super((JFrame)null, "Create an account");
		
		add(new JButton("Sample Button"));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
}

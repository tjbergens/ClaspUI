package ClaspBackend;

import retrofit.RetrofitError;

import javax.swing.*;

// Exception handler for all web service requests
public final class ErrorChecking {

	private ErrorChecking() {
		
	}


	// Handler for our API call exception
	public static boolean handleRetrofit(RetrofitError e) {
		switch (e.getKind()) {
    	
    		case NETWORK:
    			JOptionPane.showMessageDialog(null, Language.getText("ERROR_NETWORK"));
    			break;
    		default:
    			return false;
		}
		return true;
	}
	
}

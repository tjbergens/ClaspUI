package ClaspBackend;

import javax.swing.JOptionPane;

import retrofit.RetrofitError;

public final class ErrorChecking {

	private ErrorChecking() {
		
	}
	
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

package ClaspBackend;

import java.util.Locale;
import java.util.ResourceBundle;

public final class Language {
	
	private static ResourceBundle resourceBundle = 
			ResourceBundle.getBundle("lang.System");
	
	public static String getText(String id) {
		
		return resourceBundle.getString(id);
				
	}
	
}

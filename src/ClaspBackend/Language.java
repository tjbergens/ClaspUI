package ClaspBackend;

import ClaspUI.MainUI;

import java.util.Locale;
import java.util.ResourceBundle;

public final class Language {
	
	private static ResourceBundle resourceBundle = 
			ResourceBundle.getBundle("lang.System");
	public static Locale defaultLocale = Locale.getDefault();
	
	public static String getText(String id) {
		
		return resourceBundle.getString(id);
				
	}
	
	public static String getTextDynamic(String id, String... args) {
		
		return String.format(getText(id), (Object[])args);
		
	}
	
	public static void setLanguage(String id) {
		Locale oldLocale = Locale.getDefault();
		if (id.equals("default"))
			Locale.setDefault(defaultLocale);
		else
			Locale.setDefault(Locale.forLanguageTag(id));
		if (Locale.getDefault().getLanguage().equals(oldLocale.getLanguage()))
			return;
		resourceBundle = ResourceBundle.getBundle("lang.System");
		Main.mainUI.dispose();
		Main.mainUI = new MainUI();
	}
	
}

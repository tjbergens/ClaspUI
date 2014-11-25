package ClaspBackend;

import ClaspUI.MainUI;

import java.util.Locale;
import java.util.ResourceBundle;

public final class Language {
	
	// Retrieve default resource bundle
	private static ResourceBundle resourceBundle = 
			ResourceBundle.getBundle("lang.System");
	
	// Set locale to default
	public static Locale defaultLocale = Locale.getDefault();
	
	// Get UI text for current language from id
	public static String getText(String id) {
		
		return resourceBundle.getString(id);
				
	}
	
	// Get UI text for current language from id, 
	// Allows additional strings to be inserted in middle of text
	public static String getTextDynamic(String id, String... args) {
		
		return String.format(getText(id), (Object[])args);
		
	}
	
	// Set language to id (where id is a two character prefix representing the language, or "default")
	public static void setLanguage(String id) {
		
		// Save old locale
		Locale oldLocale = Locale.getDefault();
		
		// Update new locale
		Locale.setDefault(Locale.forLanguageTag(id));
		
		// If selecting the current language, don't try to update;
		if (Locale.getDefault().getLanguage().equals(oldLocale.getLanguage()))
			return;
		
		// Update resource bundle and reopen UI
		resourceBundle = ResourceBundle.getBundle("lang.System");
		Main.mainUI.dispose();
		Main.mainUI = new MainUI();
	}
	
}

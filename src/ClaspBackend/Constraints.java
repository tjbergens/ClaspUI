package ClaspBackend;

public final class Constraints {
	private static String string[] = new String[2];

	private Constraints(){
		
	}
	//takes in a potential username
	public static boolean userName(String userName){
		//splits the email at the @ symbol
		string = userName.split("@");
		//make sure both sides contain something
		if(string[0].isEmpty() || string[1].isEmpty() || string[0] == null || string[1] == null){
			return false;
		}
		else
			return true;
	}
	
	//takes in a potential password
	public static boolean password(String password){
		String pattern= "^[a-zA-Z0-9]*$";
		//check if the password is alphanumeric
		if(password.matches(pattern)){
			return true;
		}
		else
			return false;
	}
}

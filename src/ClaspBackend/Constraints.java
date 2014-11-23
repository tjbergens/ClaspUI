package ClaspBackend;

final class Constraints {

    private static final int MAX_CHAR_LENGTH = 100;

    private Constraints() {

    }
    //checks username
    public static boolean userName(String userName){
    	String pattern = "^[a-zA-Z0-9]{1,100}$";
        //check if the password is alphanumeric
        return userName.matches(pattern); 
    }
    //takes in a potential email
    public static boolean email(String email) {
        //splits the email at the @ symbol
        String[] string = email.split("@");
        //make sure both sides contain something
        return !(string.length != 2 || string[0].isEmpty() || string[1].isEmpty());
    }

    //takes in a potential password
    public static boolean password(String password) {
        String pattern = "^[a-zA-Z0-9]{10,100}$";
        //check if the password is alphanumeric
        return password.matches(pattern);
    }

    //takes in a string and determines length
    public static boolean chkLength(String check) {

        //return true if string is less than 100 characters
//return false if it is greater than 100 characters
        return check.length() <= MAX_CHAR_LENGTH;
    }
}

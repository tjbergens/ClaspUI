package ClaspBackend;

public final class Constraints {
    private static String string[] = null;

    private Constraints() {

    }

    //takes in a potential username
    public static boolean userName(String userName) {
        //splits the email at the @ symbol
        string = userName.split("@");
        //make sure both sides contain something
        if (string.length != 2 || string[0].isEmpty() || string[1].isEmpty()) {
            return false;
        } else
            return true;
    }

    //takes in a potential password
    public static boolean password(String password) {
        String pattern = "^[a-zA-Z0-9]{12,100}$";
        //check if the password is alphanumeric
        if (password.matches(pattern)) {
            return true;
        } else
            return false;
    }

    //takes in a string and determines length
    public static boolean chkLength(String check) {

        if (check.length() <= 100)
            //return true if string is less than 100 characters
            return true;
        else
            //return false if it is greater than 100 characters
            return false;
    }
}

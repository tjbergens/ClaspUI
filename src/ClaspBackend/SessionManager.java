package ClaspBackend;

public class SessionManager {

    private static String authToken;
    private static Vault vault;
    private static String userName;
    private static String masterPassword;

    //TO DO
    public static int login(){

        authToken =  SessionManager.getAuthToken();
        // HTTP SUCCESS INT
        return 200;
    }

    // TO DO
    public static String getAuthToken() {

        // DO SOMETHING

        return SessionManager.authToken;
    }

    //TO DO
    public static int sendVault() {

        //DO SOMETHING
        SessionManager.vault = null;
        //HTTP SUCCESS INT
        return 200;
    }

    //TO DO
    public static Vault retrieveVault() {

        SessionManager.vault = null;
        return SessionManager.vault;
    }

    public static void setMasterPassword(String masterPassword){

        SessionManager.masterPassword = masterPassword;
    }

    public static void setUserName(String userName){

        SessionManager.userName = userName;
    }

    public static String getMasterPassword(){
        return SessionManager.masterPassword;
    }

    public static String getUserName(){

        return SessionManager.userName;
    }
}

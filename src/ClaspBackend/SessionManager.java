package ClaspBackend;

import java.util.ArrayList;

public class SessionManager {

    private static String authToken;
    private static Vault vault = new Vault();
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

    // Called by the UI when saving the passwords is required.
    public static int savePasswords(ArrayList<Account> accounts){

        SessionManager.vault.saveAccounts(accounts);
        SessionManager.sendVault();

        // HTTP SUCCESS
        return 200;
    }

    // Called by the UI when retrieving the credentials to display.
    public static ArrayList<Account> retrievePasswords() {

        SessionManager.vault = CryptoKit.decryptVault(SessionManager.vault);
        ArrayList<Account> accounts = SessionManager.vault.getAccounts();
        return accounts;
    }
    //TO DO
    private static int sendVault() {

        //DO SOMETHING
        SessionManager.vault = CryptoKit.encryptVault(SessionManager.vault);
        //HTTP SUCCESS INT
        return 200;
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

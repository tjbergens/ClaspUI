package ClaspBackend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.crypto.SecretKey;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SessionManager {

    private static String authToken;
    private static String userName;
    private static String masterPassword;
    private static SecretKey cryptoKey;
    private static SecretKey passHash;

    private static List<Account> accounts = new ArrayList<Account>();
    public static FileReader reader;
    public static FileWriter writer;

    //TO DO
    public static int login(){

        authToken =  SessionManager.getAuthToken();
        //accounts = getAccounts();

        cryptoKey = CryptoKit.getKey(SessionManager.getMasterPassword(), SessionManager.getUserName());
        passHash = CryptoKit.getHash(cryptoKey.toString(), SessionManager.getMasterPassword());
        System.err.println("Key: " + cryptoKey);
        System.err.println("Password Hash: " + passHash);

        // Testing
        Account test = new Account("Google", "Googler", "password123");
        accounts.add(test);
        System.err.println(accounts.get(0).toString());
        saveAccounts(accounts);

        // HTTP SUCCESS INT
        return 200;
    }

    // TO DO
    public static String getAuthToken() {

        // DO SOMETHING

        return SessionManager.authToken;
    }

    // Called by the UI when saving the passwords is required.
    public static int saveAccounts(List<Account> newAccounts) {

        try {
            writer = new FileWriter(userName + ".json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        accounts = newAccounts;
        List<Account> encryptedAccounts = CryptoKit.encryptAccounts(accounts, cryptoKey);
        new Gson().toJson(encryptedAccounts, new TypeToken<List<Account>>(){}.getType() ,writer);

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //HTTP Success
        return 200;

    }

    // Called by the UI when retrieving the credentials to display.
    public static List<Account> getAccounts() {

        Gson gson = new GsonBuilder().create();
        try {
            reader = new FileReader(SessionManager.userName + ".json");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        List <Account> encryptedAccounts = gson.fromJson(reader, new TypeToken<List<Account>>(){}.getType());

        accounts = CryptoKit.decryptAccounts(encryptedAccounts, cryptoKey);
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accounts;

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

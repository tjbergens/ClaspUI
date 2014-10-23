package ClaspBackend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SessionManager {

    private static String authToken;
    private static String userName;
    private static String masterPassword;

    private static ArrayList<Account> accounts;
    public static Gson gson;
    public static FileReader reader;
    public static FileWriter writer;

    //TO DO
    public static int login(){

        authToken =  SessionManager.getAuthToken();
        accounts = getAccounts();

        // HTTP SUCCESS INT
        return 200;
    }

    // TO DO
    public static String getAuthToken() {

        // DO SOMETHING

        return SessionManager.authToken;
    }

    // Called by the UI when saving the passwords is required.
    public static int saveAccounts(ArrayList<Account> newAccounts) {

        try {
            writer = new FileWriter(userName + ".json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        accounts = newAccounts;
        ArrayList<Account> encryptedAccounts = CryptoKit.encryptAccounts(accounts);
        gson.toJson(encryptedAccounts, writer);

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //HTTP Success
        return 200;

    }

    // Called by the UI when retrieving the credentials to display.
    public static ArrayList<Account> getAccounts() {

        gson = new GsonBuilder().create();
        try {
            reader = new FileReader(SessionManager.userName + ".json");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        ArrayList <Account> encryptedAccounts = gson.fromJson(reader, new TypeToken<ArrayList<Account>>(){}.getType());

        accounts = CryptoKit.decryptAccounts(encryptedAccounts);

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

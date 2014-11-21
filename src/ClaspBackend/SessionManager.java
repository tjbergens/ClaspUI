package ClaspBackend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.*;

import javax.crypto.SecretKey;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SessionManager {

    public static FileReader reader;
    public static FileWriter writer;
    private static String authToken;
    private static String userName;
    private static String masterPassword;
    private static SecretKey cryptoKey;
    private static SecretKey passHash;
    private static List<Account> accounts = new ArrayList<Account>();


    RequestInterceptor requestInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {
            request.addHeader("User-Agent", "Clasp-App");
        }
    };
    public static RestAdapter restAdapter = new RestAdapter.Builder()
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setEndpoint("http://alpacapass.com:8001")
            .build();
    public static AlpacaService service = restAdapter.create(AlpacaService.class);

    public interface AlpacaService {

        // Get Accounts Interface Method
        @GET("/accounts")
        List<Account> listAccounts(@Header("Authorization: Token ") String token);

        // Delete Account Interface Method
        @DELETE("/accounts/{id}")
        void deleteAccount(@Header("Authorization: Token ") String token, @Path("id") String id);

        // Add Account Interface Method
        @POST("/accounts/")
        void addAccount(@Header("Authorization: Token ") String token);

        // Update Account Interface Method
        @PUT("/accounts/{id}")
        void updateAccount(@Header("Authorization: Token ") String token, @Path("id") String id);

        // Get Auth Token Interface Method
        @POST("/api-token-auth/")
        String getAuthToken(@Field("username") String username, @Field("password") String password);

        // Register Account Interface Method
        @POST("/users/register")
        void registerAccount(@Field("username") String username, @Field("email") String email, @Field("password") String password);

    }

    //TO DO
    public static int login() {

        authToken = SessionManager.getAuthToken();

        // Disabled for now until we get adding accounts working in the UI.
        //accounts = getAccounts();

        cryptoKey = CryptoKit.getKey(SessionManager.getMasterPassword(), SessionManager.getUserName());
        passHash = CryptoKit.getHash(cryptoKey.toString(), SessionManager.getMasterPassword());
        System.err.println("Key: " + cryptoKey);
        System.err.println("Password Hash: " + passHash);
        SessionManager.getAccounts();


        // Testing. Go on, add more accounts or whatever to see how it looks in the JSON file and in the UI.
        //Account test = new Account(new String("Google"), new String("Googler"), new String("password123"));
        //accounts.add(test);
        //System.err.println(accounts.get(0).toString());
        //saveAccounts(accounts);

        // HTTP SUCCESS INT
        return 200;
    }

    public static void createAccount() {

        cryptoKey = CryptoKit.getKey(SessionManager.getMasterPassword(), SessionManager.getUserName());
        passHash = CryptoKit.getHash(cryptoKey.toString(), SessionManager.getMasterPassword());
        service.registerAccount(userName, "", passHash.toString());

    }

    // TO DO
    public static String getAuthToken() {

        // Call auth token request.
        System.err.println(passHash.toString());
        authToken = service.getAuthToken(userName, passHash.toString());
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
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson includeNullsGson = gsonBuilder.serializeNulls().create();
        includeNullsGson.toJson(encryptedAccounts, new TypeToken<ArrayList<Account>>() {
        }.getType(), writer);

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

//        try {
//            reader = new FileReader(SessionManager.userName + ".json");
//        } catch (FileNotFoundException e1) {
//            e1.printStackTrace();
//        }
//
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        Gson includeNullsGson = gsonBuilder.serializeNulls().create();
//        List<Account> encryptedAccounts = includeNullsGson.fromJson(reader, new TypeToken<ArrayList<Account>>() {
//        }.getType());

        List<Account> encryptedAccounts = service.listAccounts(authToken);

        accounts = CryptoKit.decryptAccounts(encryptedAccounts, cryptoKey);
//        try {
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return accounts;

    }

    public static String getMasterPassword() {
        return SessionManager.masterPassword;
    }

    public static void setMasterPassword(String masterPassword) {

        SessionManager.masterPassword = masterPassword;
    }

    public static String getUserName() {

        return SessionManager.userName;
    }

    public static void setUserName(String userName) {

        SessionManager.userName = userName;
    }

}

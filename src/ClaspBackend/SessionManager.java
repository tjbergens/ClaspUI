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
    private static String passHash;
    private static List<Account> accounts = new ArrayList<Account>();

    // Intercepts all requests to append user agent string globally
    public static RequestInterceptor requestInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {
            request.addHeader("User-Agent", "Clasp-App");
        }
    };

    // Build REST service adapter with full logging
    public static RestAdapter restAdapter = new RestAdapter.Builder()
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setEndpoint("http://alpacapass.com:8001")
            .setRequestInterceptor(requestInterceptor)
            .build();

    // Create interface instance for REST services
    public static AlpacaService service = restAdapter.create(AlpacaService.class);

    public interface AlpacaService {

        // Get Accounts Interface Method
        @GET("/accounts/?format=json")
        List<Account> listAccounts(@Header("Authorization") String token);

        // Delete Account Interface Method
        @DELETE("/accounts/{id}")
        void deleteAccount(@Header("Authorization") String token, @Path("id") String id);

        // Add Account Interface Method
        @POST("/accounts/?format=json")
        Account addAccount(@Header("Authorization") String token, @Body NewAccount account);

        // Update Account Interface Method
        @PUT("/accounts/{id}/?format=json")
        Account updateAccount(@Header("Authorization") String token, @Path("id") String id, @Body Account account);

        // Get Auth Token Interface Method
        @POST("/api-token-auth/")
        @Multipart
        AuthToken getAuthToken(@Part("username") String username, @Part("password") String password);

        // Register Account Interface Method
        @POST("/users/register/?format=json")
        String createAccount(@Body RegistrationAccount account);

    }

    //TO DO
    public static int login() {


        cryptoKey = CryptoKit.getKey(SessionManager.getMasterPassword(), SessionManager.getUserName());
        passHash = CryptoKit.getHash(cryptoKey.toString(), SessionManager.getMasterPassword());
        SessionManager.getAuthToken();

        // Debug
        System.err.println("Auth Token: " + authToken);

        // Disabled for now until we get adding accounts working in the UI.
        //accounts = getAccounts();

        // Debug
        System.err.println("Key: " + cryptoKey);
        System.err.println("Password Hash: " + passHash);

        // Testing. Go on, add more accounts or whatever to see how it looks in the JSON file and in the UI.
        //Account test = new Account(new String("Google"), new String("Googler"), new String("password123"));
        //accounts.add(test);
        //System.err.println(accounts.get(0).toString());
        //saveAccounts(accounts);

        // HTTP SUCCESS INT
        return 200;
    }

    public static void createAccount(String username, String email, String password) {

        cryptoKey = CryptoKit.getKey(password, username);
        password = CryptoKit.getHash(cryptoKey.toString(), password);



        // Send it off to attempt registration.
        service.createAccount(new RegistrationAccount(username, email, password));
    }

    public static void updateAccount(String id, String newPass) {

        for(Account account : accounts) {
            if(account.getId().equals(id)) {
                account.password = newPass;
                service.updateAccount("Token " + authToken, id, CryptoKit.encryptAccount(account, cryptoKey));
            }
        }

    }

    // TO DO
    public static void getAuthToken() {

        // DO SOMETHING
        AuthAccount account = new AuthAccount(userName, passHash);
        AuthToken token = service.getAuthToken(userName, passHash);
        SessionManager.authToken = token.token;
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

    public static int addAccount(NewAccount account) {

        account = CryptoKit.encryptNewAccount(account, cryptoKey);
        service.addAccount("Token " + authToken, account);
        // No errors
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
        List<Account> encryptedAccounts = service.listAccounts("Token " + authToken);
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

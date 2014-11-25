package ClaspBackend;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.http.*;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.List;

public class SessionManager {

    // Our RESTful service endpoint.
    private static final String API_ENDPOINT = "http://http://alpacapass.com/alpacapass";

    // Intercepts all requests to append user agent string globally
    private static final RequestInterceptor requestInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {
            request.addHeader("User-Agent", "Clasp-App");
        }
    };
    // Build REST service adapter with full logging
    private static final RestAdapter restAdapter = new RestAdapter.Builder()
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setEndpoint(API_ENDPOINT)
            .setRequestInterceptor(requestInterceptor)
            .build();
    // Create interface instance for REST services
    private static final AlpacaService service = restAdapter.create(AlpacaService.class);
    private static String authToken;
    private static String userName;
    private static String masterPassword;
    private static SecretKey cryptoKey;
    private static String passHash;
    private static List<Account> accounts = new ArrayList<Account>();

    //TO DO
    public static int login() {


        cryptoKey = CryptoKit.getKey(SessionManager.getMasterPassword(), SessionManager.getUserName());
        passHash = CryptoKit.getHash(cryptoKey.toString(), SessionManager.getMasterPassword());

        return SessionManager.getAuthToken();
    }

    // Register a user's account.
    public static int createAccount(String username, String email, String password) {

        // Make our password key and password hash.
        cryptoKey = CryptoKit.getKey(password, username);
        password = CryptoKit.getHash(cryptoKey.toString(), password);


        // Send it off to attempt registration.
        service.createAccount(new RegistrationAccount(username, email, password));
        return 200;


    }

    // Update a user's account password.
    public static void updateAccount(String id, String newPass) {

        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                account.password = newPass;

                service.updateAccount("Token " + authToken, id, CryptoKit.encryptAccount(account, cryptoKey));


            }
        }

    }

    // Remove a specified set of credentials from the user's account list.
    public static void removeAccount(String id) {

        for (Account account : accounts) {
            if (account.getId().equals(id)) {

                service.deleteAccount("Token " + authToken, id);


            }
        }
    }

    // Form our token object which will hold a User's authentication token for later use in API calls.
    private static int getAuthToken() {


        AuthToken token = service.getAuthToken(userName, passHash);
        // Make API call to retrieve user's token.
        SessionManager.authToken = token.token;


        return 200;
    }

    // Encrypt and make the API call to add an account to a User's credential list.
    public static int addAccount(NewAccount account) {

        account = CryptoKit.encryptNewAccount(account, cryptoKey);

        service.addAccount("Token " + authToken, account);


        // No errors
        return 200;
    }

    // Called by the UI when retrieving the credentials to display.
    public static List<Account> getAccounts() {

        return accounts;

    }

    public static int retrieveAccounts() {


        List<Account> encryptedAccounts = service.listAccounts("Token " + authToken);
        accounts = CryptoKit.decryptAccounts(encryptedAccounts, cryptoKey);

        return 200;
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

    // Handle any Network or Request exceptions that are thrown.
    private static int handleErrors(RetrofitError e) {

        if (e.getKind().equals(RetrofitError.Kind.HTTP)) {

            return e.getResponse().getStatus();
        } else if (e.getKind().equals(RetrofitError.Kind.NETWORK)) {

            return 0;

        }

        // Must be an internal error. Crash the program. This should NEVER happen.
        else {
            throw e;
        }
    }

    public interface AlpacaService {

        // Get Accounts Interface Method
        @GET("/accounts/?format=json")
        List<Account> listAccounts(@Header("Authorization") String token);

        // Delete Account Interface Method
        @DELETE("/accounts/{id}/?format=json")
        Account deleteAccount(@Header("Authorization") String token, @Path("id") String id);

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
}

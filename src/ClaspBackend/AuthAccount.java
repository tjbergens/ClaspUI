package ClaspBackend;

//  Class for our User's account used in authenticating and retrieving the authentication token to login and make api calls.
public class AuthAccount {

    String username;
    String password;

    AuthAccount(String accountName, String hashedPass) {

        username = accountName;
        password = hashedPass;
    }
}

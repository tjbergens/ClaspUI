package ClaspBackend;

//  Class for our User's account used in authenticating and retrieving the authentication token to login and make api calls.
class AuthAccount {

    private final String username;
    private final String password;

    AuthAccount(String accountName, String hashedPass) {

        username = accountName;
        password = hashedPass;
    }
}

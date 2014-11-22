package ClaspBackend;

public class AuthAccount {

    String username;
    String password;

    AuthAccount(String accountName, String hashedPass) {

        username = accountName;
        password = hashedPass;
    }
}

package ClaspBackend;

// Account Registration class that is used to serialize an account registration request to the api
public class RegistrationAccount {

    public String username;
    public String email; //says unused but used in constructor
    public String password; //says unused but used in constructor


    public RegistrationAccount(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}

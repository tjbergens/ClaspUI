package ClaspBackend;

// Account Registration class that is used to serialize an account registration request to the api
class RegistrationAccount {
    String username;
    String email;
    String password;
    //Constructor that initializes a new Account upon creation
    public RegistrationAccount(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}

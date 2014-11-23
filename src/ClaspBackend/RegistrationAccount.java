package ClaspBackend;

// Account Registration class that is used to serialize an account registration request to the api
class RegistrationAccount {
    String username;
    String email;
    String password;

    public RegistrationAccount(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}

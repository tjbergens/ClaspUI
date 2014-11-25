package ClaspBackend;

// New Account that will be sent to web service.
public class NewAccount {

    //attributes of a password account
    public String accountName;
    public String userName; 
    public String password; 

    //Constructor instantiates all attributes upon creation
    public NewAccount(String accountName, String userName, String password) {
        this.accountName = accountName;
        this.userName = userName;
        this.password = password;
    }
}

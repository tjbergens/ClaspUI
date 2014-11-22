package ClaspBackend;

// Account that will be sent to web service.
public class NewAccount {

    //I guessed that these are all the attributes of a subaccount
    public String accountName;
    public String userName; //says unused but used in constructor
    public String password; //says unused but used in constructor


    public NewAccount(String accountName, String userName, String password) {
        this.accountName = accountName;
        this.userName = userName;
        this.password = password;
    }
}

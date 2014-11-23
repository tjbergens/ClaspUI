package ClaspBackend;


// Main class for holding a User's single account.
public class Account {
    //I guessed that these are all the attributes of a subaccount
    public String id;
    public String accountName;
    public String userName; //says unused but used in constructor
    public String password; //says unused but used in constructor


    public Account(String accountName, String userName, String password) {
        this.accountName = accountName;
        this.userName = userName;
        this.password = password;
    }

    public String getId() {
        return this.id;
    }

    //created equals function for the arraylist.remove method.
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Account)) return false;
        Account o = (Account) obj;
        return o.id.equals(this.id);
    }
}

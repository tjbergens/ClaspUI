package ClaspBackend;


// Main class for holding a User's single account.
public class Account {
    // Account attrs
    public String id;
    public String accountName;
    public String userName;
    public String password;

    // Account constructor
    public Account(String accountName, String userName, String password) {
        this.accountName = accountName;
        this.userName = userName;
        this.password = password;
    }

    // Gets an account object's id for use in API calls.
    public String getId() {
        return this.id;
    }

    // created equals function for the arraylist.remove method.The account id is the key within the database and determines uniqueness
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Account)) return false;
        Account o = (Account) obj;
        return o.id.equals(this.id);
    }
}

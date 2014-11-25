package ClaspBackend;


// Main class for holding a User's single account.
public class Account {
    //Attributes that are used in a password
    public String id;
    public String accountName;
    public String userName; 
    public String password; 

    //Constructor for an password account
    public Account(String accountName, String userName, String password) {
        this.accountName = accountName;
        this.userName = userName;
        this.password = password;
    }
    //returns the id of current account
    public String getId() {
        return this.id;
    }

    //created equals function for the arraylist.remove method so that it can handle removing objects.
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Account)) return false;
        Account o = (Account) obj;
        return o.id.equals(this.id);
    }
}

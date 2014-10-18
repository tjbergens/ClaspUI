package ClaspBackend;

public class Account {
	//I guessed that these are all the attributes of a subaccount
	private String accountName;
	private String userName;
	private String password;
	
	//Constructor sets all attributes
	 public Account(String accountName, String userName, String password){
		this.accountName = accountName;
		this.userName = userName;
		this.password = password;
	}
	 
	 //created equals function for the arraylist.remove method.
	 public boolean equals(Object obj) {
		 if(obj == null) return false;
		 if(obj == this) return true;
		 if(!(obj instanceof Account)) return false;
		 Account o = (Account) obj;
		 return o.accountName == this.accountName;
	 }
}

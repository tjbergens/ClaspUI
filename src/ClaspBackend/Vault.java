package ClaspBackend;

import java.io.FileReader;
import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.simple.parser.JSONParser;

/*I have not provided an output yet as i am not sure how the organization is supposed to go
 * I have not included the encryption or decryption in here either.
 * 
 */


public class Vault {
	//Create master username and MasterPassword not sure how to do authentication
	private String masterUserName;
	private String masterPassword;
	//Creates a list of all accounts associate with the users vault
	private ArrayList<Account> accounts = new ArrayList<Account>();
	
	//Constructor for the Vault class in case of new User
	public Vault(String masterUserName, String masterPassword){
		this.masterPassword = masterPassword;
		this.masterUserName = masterUserName;
	}
	
	//Not sure if all the JSON is correct I am getting a weird notification from eclipse on line 29
	//Create new parser
	JSONParser parser = new JSONParser();
	//Get the contents of JSON file assuming its being stored as an object
	JSONObject mainJSON = (JSONObject) parser.parse(new FileReader("path_to_json_file"));
	//Create JSONArray to store all Accounts in JSON file
	JSONArray jsonArr = new JSONArray();  //Syntax error here? I don't see it
	//Get the JSONArray from the JSON file, assuming this is how the JSON file information is being stored
	jsonArr = mainJSON.getJSONArray("Account");
	//for each entry in the array add an account to current Users Vault
	for(int i = 0; i < jsonArr.length(); i++){
		JSONObject subAccount = jsonArr.getJSONObject(i);
		addAccount(subAccount.getString("accountName"), subAccount.getString("userName"), subAccount.getString("password"));
	}
	
	
	//If user wants to add a new account that is not already listed in the JSON file (not sure if this needs to be handled in UI)
	public void addAccount(String accountName, String userName, String password) { //Syntax errors again, still not seeing it
		accounts.add(new Account(accountName, userName, password));
	}
	
	//Remove an account from the JSON file
	public void removeAccount(Account obj) {
		accounts.remove(obj);
	}
	
	//Returns the list of accounts to Main so that they may be displayed
	public ArrayList<Account> getAccount(){
		return accounts;
	}
	
}

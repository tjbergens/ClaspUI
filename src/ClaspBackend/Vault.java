package ClaspBackend;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/*I have not provided an output yet as i am not sure how the organization is supposed to go
 * I have not included the encryption or decryption in here either.
 * 
 */

// Static as only one user will be logged in using this application. Vault does not need to be an instance.
public class Vault {

	//Creates a list of all accounts associate with the users vault
	private ArrayList<Account> accounts;
    private Gson gson;
    private FileReader reader;
    private FileWriter writer;  

    public Vault() {

        accounts = new ArrayList<Account>();
        gson = new GsonBuilder().create();
        try {
			reader = new FileReader("input.json");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
        try {
            writer = new FileWriter("output.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //this.accounts = getAccounts();
    }


    //If user wants to add a new account that is not already listed in the JSON file (not sure if this needs to be handled in UI)
	public void addAccount(String accountName, String userName, String password, String description){ 

        accounts.add(new Account(accountName, userName, password, description));
	}

    // Might not need if we handle ArrayList of accounts in UI and then save.
    public void updateAccount(Account oldAccount, Account newAccount){

        accounts.remove(oldAccount);
        accounts.add(newAccount);
    }

	//Remove an account from the JSON file
	public void removeAccount(Account obj) {

        accounts.remove(obj);
	}
	
	//Returns the list of accounts to Main so that they may be displayed
	public ArrayList<Account> getAccounts(){//Syntax error here? I don't see it
		accounts = gson.fromJson(reader, new TypeToken<ArrayList<Account>>(){}.getType());

        return accounts;
	}

    public void saveAccounts(ArrayList<Account> accounts) {

        gson.toJson(accounts, writer);
    }
    
    public void closeOutput() {
    	try {
    		writer.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
	
}

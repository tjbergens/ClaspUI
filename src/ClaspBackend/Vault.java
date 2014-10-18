package ClaspBackend;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

/*I have not provided an output yet as i am not sure how the organization is supposed to go
 * I have not included the encryption or decryption in here either.
 * 
 */

// Static as only one user will be logged in using this application. Vault does not need to be an instance.
public class Vault {

	//Creates a list of all accounts associate with the users vault
	private ArrayList<Account> accounts;
    private Gson gson;
    private File file;
    private FileWriter writer;

    public Vault() {

        accounts = new ArrayList<Account>();
        gson = new GsonBuilder().create();
        file = new File("test.json");
        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //If user wants to add a new account that is not already listed in the JSON file (not sure if this needs to be handled in UI)
	public void addAccount(String accountName, String userName, String password){ 

        accounts.add(new Account(accountName, userName, password));
	}

    public void updateAccount(Account oldAccount, Account newAccount){

        accounts.remove(oldAccount);
        accounts.add(newAccount);
        this.saveAccounts();
    }

	//Remove an account from the JSON file
	public void removeAccount(Account obj) {//Syntax error here? I don't see it

        accounts.remove(obj);
        this.saveAccounts();
	}
	
	//Returns the list of accounts to Main so that they may be displayed
	public ArrayList<Account> getAccounts(){//Syntax error here? I don't see it

        return accounts;
	}

    public void saveAccounts() {

        gson.toJson(accounts, writer);
    }
	
}

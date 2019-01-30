package BankingApp;
import java.util.Random;
import java.util.HashMap;
import User.*;
import Product.*;
import java.io.*;

/**
 * Bank App adds a user to the hashmap, and stores the hashmap. 
 * Controls the current user and generates an account num. 
 * @author Jordan Dobrescu, Jennifer Hunchak, Vishaal Bakshi, Nicholas Adam
 * @version FinalSubmission
 */
public class BankApp implements Serializable {
	private static final long serialVersionUID = 6273309535719285052L;
	private User currentUser = null;
	// Creates the HashMap that stores all the users 
	private HashMap<String, User> users = new HashMap<String, User>();
    
    /**
     * This constructor sets up a dummy user for demonstration purposes,
     * and initializes the input/output streams for file reading/writing.
     */
    @SuppressWarnings("unchecked")
	public BankApp() {
    	// Dummy user with all required registration info
		User user1 = new User();
		user1.setAccountNum("12345678");
		user1.setPassword("John4$");
		user1.setFirstName("John");
		user1.setLastName("Smith");
		user1.setDateOfBirth("1996-04-17");
		user1.setProvince("AB");
		user1.setCity("Calgary");
		user1.setAddress("197 woodford drive");
		user1.setSinNumber("123456789");
		user1.addProduct(new ChequingAccount(10000.00));
		user1.addProduct(new SavingsAccount(500000.00));
		users.put(user1.getAccountNum(), user1);
		
		// Try/catch blocks to set up file IO
		try {
			FileInputStream filein = new FileInputStream("Data.ser");
			// Reads file input as an Object
			ObjectInputStream in = new ObjectInputStream(filein);
			// Type casts the object being read as a HashMap for the purposes of the app
			users = (HashMap<String, User>) in.readObject();
			in.close();
			filein.close();
		}
		catch (FileNotFoundException e){
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		// Class exception for type casting
		catch (ClassNotFoundException cnfe) {
			System.out.println(" User class not found.");
			cnfe.printStackTrace();
		}
    }
    
	/**
	 * Method that writes the HashMap to a file
	 */
	public void writeToFile() {
		try {
			FileOutputStream fileOut = new FileOutputStream("Data.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut); //writeobject
			out.writeObject(users);
			out.flush();
			out.close();
			fileOut.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			}
		catch(IOException i) {
			System.out.println("ERROR: Account information could not be saved.");
		}
		System.out.println("Done writing to file");
	}
	
	/**
	 * Adds the user to the HashMap.
	 * @param accountNum stores the users account number as the key in the HashMap
	 * @param user is of type User and is stored in the HashMap 
	 */
    public void addUser(String accountNum, User user) {
    	users.put(accountNum, user);
    }
	
	/**
	 * Checks that the accountNum and password passed in are the ones that match the hashmap.
	 * @param accountNum is the users account number
	 * @param password is the password the user has entered
	 * @return correctAccountInfo tells whether the account information entered is correct
	 */
    public boolean authenticateUser(String accountNum, String password){
    	boolean correctAccountInfo = false;
    	// Checks the key contains the accountNum entered by the user
    	if (users.containsKey(accountNum)){
    		currentUser = users.get(accountNum);
    		// Checks that the password entered is correct
    		if (currentUser.correctPassword(password)) {
    			correctAccountInfo = true;
    		} 	
    	} return correctAccountInfo;
    }

	/**
	 * Generates the user a random number for the account number, makes sure it is not already used.
	 * @return accountNum returns the account num of type string  
	 */
	public String genAccountNum() {
		String accountNum = null;
		while (accountNum == null) {
			int userAccountNum = new Random().nextInt((90000000) + 10000000);
			String accountNumTemp = Integer.toString(userAccountNum);
			// Sets the accountNum
			if (!users.containsKey(accountNum)) {
				accountNum = accountNumTemp;
			}
		}
		return accountNum;
	}

	/**
	* Checks that the string passed in is a digit 
	* @param amount is a String used in a transaction 
	* @return returns a boolean of whether the amount is a digit or not 
	*/
	// http://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java
	public boolean isDigit(String amount) {
		try {  
    		double transactionAmount = Double.parseDouble(amount);
    		if(transactionAmount < 0){
    			return false;
    		}  
  		}  
  		catch(NumberFormatException nfe) {  
    		return false;  
    	}  
  		return true;
  	}
	
	/**
	* A getter method that returns the current user 
	* @return the current User
	*/
  	public User getCurrentUser() {
  		return this.currentUser;
  	} 
}

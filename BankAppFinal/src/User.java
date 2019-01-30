package User;
import java.text.*;
import java.util.ArrayList;
import Product.*;
import java.io.*;

/**
 * User class that collects and holds all user information required to
 * sign up for an account with the Bank.
 * Class assumes that the user lives in Canada.
 * @author Jordan Dobrescu, Jennifer Hunchak, Vishaal Bakshi, Nick Adam
 * @version FinalSubmission
 */
public class User implements Serializable {
	private static final long serialVersionUID = 1262225571362397926L;
	private String firstName, lastName, password, dateOfBirth, province, city, address, accountNum;
	private int sinNumber;
	private  ArrayList<Product> productList;
	
	/**
	 * Constructor for the user that creates the array list of Products
	 * that a user has signed up for and has access to.
	 */
	public User() {
		productList = new ArrayList<Product>();
	}
	
	/**
	 * Mutator method that adds a Product to this user's list
	 * of products that they have access to.
	 * @param account Product to add to the product list
	 */
	public void addProduct(Product account) {
        productList.add(account);
    }
	
    /**
     * Accessor method that retrieves all products from a user's product
     * list and presents it in a 2D list to be displayed on the GUI.
     * @return accounts Returns the 2D list of accounts to display it
     */
    public String[][] getAccounts() {
    	// Gets size of product list
    	int size = this.productList.size();
    	String[][] accounts = new String[size][];
    	for (int i = 0; i < size; i++) {
    		Product product = productList.get(i);
			String productType = "";
			if (product instanceof ChequingAccount) {
				productType = "Chequing";
			} else {
				productType = "Savings";
			}
    		String[] account = new String[] {productType, Double.toString(product.getBalance())};
    		// Puts the type and balance in at index i 
    		accounts[i] = account;
    	}
    	return accounts;
    }
    
    /**
     * Accesor method that returns product from user's product list
     * for access in the GUI.
     * PRIVACY LEAK
     * @param userAccountName
     * @return 
     */
	public Product getProduct(String userAccountName) {
        for (int i = 0; i < this.productList.size(); i++) {
        	Product product = this.productList.get(i);
            if (product instanceof ChequingAccount && userAccountName.equals("Chequing")) {
                return this.productList.get(i);
            }
            else if (product instanceof SavingsAccount && userAccountName.equals("Savings")) {
            	return this.productList.get(i);
            }
        }return null;
	}
	
	/**
	 * Accessor method that returns the user's first name
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Mutator method that sets the user's first name
	 * Error checks to ensure name is all letters
	 * @param firstName User input first name to be checked
	 * @return validFirstName Returns error message if first name in wrong format
	 */
	public String setFirstName(String firstName) {
		String validFirstName = "ERROR: name must be all letters";
		if(firstName.matches("[A-Za-z]+")) {
		    this.firstName = firstName;
		    validFirstName = "";
		}
		return validFirstName;
	}
	
	/**
	 * Accessor method that returns the user's last name
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Mutator method that sets the user's last name
	 * Error checks to ensure name is all letters
	 * @param lastName User input last name to be checked
	 * @return validLastName Returns error message if last name in wrong format
	 */
	public String setLastName(String lastName) {
		String validLastName = "ERROR: name must be all letters";
		if(lastName.matches("[A-Za-z]+")) {
		   this.lastName = lastName;
		   validLastName = "";
		}
		return validLastName;	
	}
	
	/**
	 * Method to check if password input by the user matches their actual password
	 * @param attempt User input to check
	 * @return Returns false is passwords don't match
	 */
	public boolean correctPassword(String attempt) {
		return this.password.equals(attempt);
	}
	
	/**
	 * Accessor method that returns the user's date of birth
	 * @return dateOfBirth
	 */
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	
	/**
	 * Mutator method that sets the user's date of birth
	 * Calls the dobISValid method to check the format of the input
	 * @param dateOfBirth User input dob to be checked/set
	 * @return validDOB Returns an error message is dob entered in wrong format
	 */
	public String setDateOfBirth(String dateOfBirth) {
		String validDOB = "ERROR: Not a valid date of birth. Must be YYYY-MM-DD.";
		if(dobIsValid(dateOfBirth)){
		    this.dateOfBirth = dateOfBirth;
		    validDOB = "";
		}
		return validDOB;
	}	
	
	/**
	 * Mutator method that changes the users password
	 * Error checks that password is at least length 1
	 * @param password User input password to be set
	 * @return validPasswordCheck Returns an error message if wrong format
	 */
	public String setPassword(String password) {
		boolean validPass = (password.length() > 0);
		String validPasswordCheck = "ERROR: password must meet requirements";
		if(validPass){
			this.password = password;
			validPasswordCheck = "";
		}
		return validPasswordCheck;
	}
	
	/**
	 * Method that confirms if the user has put in the same password twice
	 * @param password First password input by user
	 * @param confirmPassword Second password input by the user to confirm the first
	 * @return Returns an error message if the passwords do not match
	 */
	public String confirmPassword(String password, String confirmPassword){
		String errorConfirmPass = "ERROR: Your passwords do not match.";
		if (password.equals(confirmPassword)) {
			errorConfirmPass = "";
		}
		return errorConfirmPass;
	}
	
	/**
	 * Accessor method that returns the user's province
	 * @return province
	 */
	public String getProvince() {
		return province;
	}
	
	/**
	 * Mutator method that sets the user's province.
	 * Pre-condition: Must be Canadian province
	 * Error checks for proper format AB, ON, etc.
	 * @param province User input province to be checked/set
	 * @return validProvince Returns error message if province in wrong format
	 */
	public String setProvince(String province) {
		String validProvince = "ERROR: Not a valid province";
		ArrayList<String> provinces = new ArrayList<String>();
		provinces.add("NL");
		provinces.add("PE");
		provinces.add("NS");
		provinces.add("NB");
		provinces.add("QC");
		provinces.add("ON");
		provinces.add("MB");
		provinces.add("SK");
		provinces.add("AB");
		provinces.add("BC");
		provinces.add("YT");
		provinces.add("NT");
		provinces.add("NU");
		province = province.toUpperCase();
		if (provinces.contains(province)) {
			this.province = province;
			validProvince = "";
		}
		return validProvince;
	}
	
	/**
	 * Accessor method that returns user's city
	 * @return city
	 */
	public String getCity() {
		return city;
	}
	
	/**
	 * Mutator method for setting the user's city
	 * Error checks to ensure city is formatted with all letters
	 * @param city User input city to be checked/set
	 * @return validCity Returns error message if city in wrong format
	 */
	public String setCity(String city) {
		String validCity = "ERROR: city must be all letters";
		if(city.matches("[A-Za-z]+")) {
		    this.city = city;
		    validCity = "";
		}
		return validCity;
	}
	
	/**
	 * Accessor method that returns the user's address
	 * @return address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Mutator method for setting the user's street address.
	 * Error checks to ensure in proper format of number and street name
	 * @param address User input address to check/set
	 * @return validAddress Returns an error message if the input in wrong format
	 */
	public String setAddress(String address) {
		String validAddress = "ERROR: Not a valid address";
		if (address.matches("\\d{1,5} [A-Za-z0-9 ]*")) {
			this.address = address;
			validAddress = "";
		}
		return validAddress;
	}
	
	/**
	 * Accessor method that returns the user's SIN number
	 * @return sinNumber
	 */
	public int getSinNumber() {
		return sinNumber;
	}
	
	/**
	 * Mutator method that sets the user's SIN number.
	 * Error checks to ensure SIN is proper 9-digit format.
	 * @param sinNumber
	 * @return validSin Return boolean true if SIN number is valid
	 */
	public String setSinNumber(String sinNumber) {
		String validSin = "ERROR: Not a valid sin. Must be 9 digits.";
		if(sinNumber.matches("\\d{9}")){
		    this.sinNumber = Integer.parseInt(sinNumber);
		    validSin = "";
		}return validSin;
	}
	
	/**
	 * Mutator method the sets the user's account number
	 * @param accountNum
	 */
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	
	/**
	 * Accessor method returns the user's account number
	 * @return accountNum 
	 */
	public String getAccountNum() {
		return accountNum;
	}
	
	/**
	 * Error checking helper method to ensure date of birth entered in format yyy/mm/dd
	 * @param dob Date of birth entered by the user
	 * @return Boolean returned true is dob is valid
	 */
	private boolean dobIsValid(String dob) {
		boolean dobValid = false;
		// http://stackoverflow.com/questions/2149680/regex-date-format-validation-on-java
	    if (dob == null || !dob.matches("\\d{4}-[01]\\d-[0-3]\\d"))
	        dobValid = false;
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    df.setLenient(false);
	    try {
	        df.parse(dob);
	        dobValid = true;
	    } catch (ParseException ex) {
	        dobValid = false;
	    }
	    return dobValid;
	}
}
package Product;
import java.io.*;

/**
 * Chequing Account class, part of the Product package
 * Subclass of Product, represents a chequing account that 
 * users may sign up for
 * This code was inspired by code provided by Nathaly Verwaal during tutorials.
 * @author Jennifer Hunchak, Jordan Dobresu, Vishaal Bakshi, Nick Adam
 * @version FinalSubmission
 */
public class ChequingAccount extends Product implements Serializable {
	private static final long serialVersionUID = 4260935584248005692L;
	private double minBalance = 500.00;
	
	/**
	 * Default constructor for CheqingAccount
	 * Calls default constructor in super class
	 */
	public ChequingAccount() {
		super();
	}
	
	/**
	 * Creates new chequing account, takes start balance as a parameter
	 * Calls the constructor from the super class that takes same parameter
	 * @param startBalance Initial balance for the chequing account
	 */
	public ChequingAccount(double startBalance) {
		super(startBalance);
    }
	
	/**
	 * Creates a new chequing account that is a copy of another
	 * @param aChequingAccount Chequing account being copied
	 */
    public ChequingAccount(ChequingAccount aChequingAccount) {
        super(aChequingAccount);
    }
    
    /**
     * Mutator method overrides the deposit method in the super class.
     * Option to add a transaction to the total number of transactions
     * is commented out as GUI is not yet equipped to handle it.
     * Calls super class to handle the deposit.
     * @param amount Amount to be deposited
     * @return Returns empty string as super class will handle error checking.
     */
	public String deposit(double amount) {
        super.deposit(amount);
        return "";
    }
    
	/**
	 * Mutator method overrides the withdraw method in the super class.
     * Option to add a transaction to the total number of transactions
     * is commented out as GUI is not yet equipped to handle it.
     * Error checks to ensure withdraw will not go below minimum balance.
     * Calls super class to handle withdraw.
     * @param amount Amount to be withdrawn
     * @return Returns error message if funds insufficient
	 */
    public String withdraw(double amount) {
    	String errorMessage = "";
    	if ((super.getBalance() - amount) < minBalance) {
    		errorMessage =  "Error: Must stay above the minimum Balance.";
    	} else {
    		super.withdraw(amount);
    		errorMessage =  "";
    	}
    	return errorMessage;
    }
    
	/**
	 * Mutator method that transfers amount passed in, to the account passed in.
	 * Error checks to make sure the amount won't bring this account lower than
	 * it's minimum balance.
	 * Calls the super class the handle the transfer.
	 * @param amount Amount to be transferred between products
	 * @param toAccount Product to receive the transfer
	 * @return Returns error message if funds insufficient
	 */
	public String transfer(double amount, Product toAccount) {
		String errorMessage = "";
		if ((this.getBalance() - amount) < this.minBalance) {
			errorMessage = "Error: Must stay above the minimum balance.";
		} else {
			super.transfer(amount, toAccount);
		}
		return errorMessage;
	} 
			
}
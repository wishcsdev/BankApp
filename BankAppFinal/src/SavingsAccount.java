package Product;
import java.io.*;

/**
 * Savings Account class, part of the Product package
 * Subclass of Product, represents a savings account that
 * users may sign up for
 * This code was inspired by code provided by Nathaly Verwaal during tutorials.
 * @author Jennifer Hunchak, Jordan Dobresu, Vishaal Bakshi, Nick Adam
 * @version FinalSubmission
 */
public class SavingsAccount extends Product implements Serializable {
	private static final long serialVersionUID = 2958645857433454519L;
	private double minBalance = 100.00;

	/**
	 * Default constructor for SavingsAccount with no parameters
	 * Calls default constructor in super class
	 */
	public SavingsAccount() {
		super();
	}
	
	/**
	 * Creates new savings account, takes start balance as a parameter
	 * Calls the constructor from the super class that takes same parameter
	 * @param startBalance Initial balance for the savings account
	 */
	public SavingsAccount(double startBalance) {
		super(startBalance);
	}
	
	/**
	 * Mutator method overrides the withdraw method in the super class.
	 * Error checks to ensure the withdraw will not go below minimum balance.
	 * Calls super class to handle the withdraw
	 * @param amount Amount to be withdrawn
	 * @return Returns error message if funds insufficent
	 */
	public String withdraw(double amount) {
		String errorMessage = "";
		if ((this.getBalance() - amount) < this.minBalance) {
			errorMessage = "Error: Must stay above the minimum balance.";
		} else {
			super.withdraw(amount);
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
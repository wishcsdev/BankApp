package Product;
import java.io.*;

/**
 * Product class, part of the Product package
 * Super class for all products a user may sign up for
 * @author Jennifer Hunchak, Jordan Dobrescu, Vishaal Bakshi, Nick Adam
 * @version FinalSubmission
 *
 */
public class Product implements Serializable {
	private static final long serialVersionUID = -1231761613877032749L;
	private double balance = 0.0;
    String errorMessage = "";
    
    /**
     * Default constructor for Product
     * Creates new Product, sets balance to the default
     */
    public Product() {
        this(0.0);
    }
    
    /**
     * Creates new Product, takes a start balance as a parameter
     * @param startBalance Initial balance for the product
     */
    public Product(double startBalance) {
        balance = startBalance;
    }
    
    /**
     * Creates new Product that is a copy of another
     * @param accountToCopy Product that is being copied
     */
    public Product(Product accountToCopy) {
        if (accountToCopy != null) {
            balance = accountToCopy.balance;
        }
    }
    
    /**
     * Accessor method, returns the balance of the product
     * @return balance The balance of the product
     */
    public double getBalance() {
        return balance;
    }
    
    /**
     * Mutator method checks that amount passed to it is greater
     * than the current balance, and that amount is not negative.
     * If it is greater, subtracts amount
     * from the balance and returns the new amount.
     * @param amount Amount to be withdrawn from product
     * @return Returns message to display depending on result of withdraw
     */
    public String withdraw(double amount) {
        if (amount <= balance  && amount > 0) {
            balance -= amount;
            errorMessage =  "";
        } else if (amount <= 0) {
            errorMessage =  "Error: You can only withdraw positive amounts.";
        } else {
            errorMessage =  "Error: You do not have sufficient funds.";
        }
        return errorMessage;
    }
    
    /**
     * Mutator method checks that amount passed to it is not negative.
     * If amount greater than zero, amount is added to balance.
     * @param amount Amount to be deposited into product.
     * @return Returns message depending on result of error check
     */
    public String deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            errorMessage =  "";
        } else {
        	errorMessage =  "Error: You can only deposit positive amounts.";
        }
        return errorMessage;
    }
    
    /**
     * Mutator method that transfers amount passed in to the product
     * passed in. Error checks to make sure the amount is greater than
     * zero and that this product has sufficient funds.
     * @param amount Amount to be transferred between products
     * @param toAccount Product to receive the transfer
     * @return Returns message based on positive amount, sufficient funds
     */
    public String transfer(double amount, Product toAccount) {
        if (amount > 0 && balance >= amount) {
            withdraw(amount);
            toAccount.deposit(amount);
            errorMessage =  "";
        } else if (amount <= 0) {
            errorMessage =  "Error: you can only transfer positive amounts.";
        } else {
            errorMessage =  "Error: you do not have sufficient funds.";
        }
    	return errorMessage;
    }
}
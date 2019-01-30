package GUI;
import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import User.*;
import Main.BankAppGUI;
import Product.*;
import java.io.*;

/**
 * This class represents the deposit card for the banking app GUI
 * @author Jordan Dobrescu, Jennifer Hunchak, Vishaal Bakshi, Nicholas Adam
 * @version FinalSubmission
 */
public class DepositGUI extends JPanel implements Serializable {
	// Initializes instance variables
	private static final long serialVersionUID = -5492769162372278538L;
	private JTextField depText;
	private JTable productTable;
	private JPanel depositPanel;
	private BankAppGUI mainClass;
	private Timer timer = new Timer();
	private JLabel errorText;
	public boolean init = true;
	
	/**
	 * Constructor that takes the main GUI class as a parameter,
	 * which is done to accomplish action listening for the buttons
	 * @param mainClass
	 */
	public DepositGUI(BankAppGUI mainClass) {
		this.mainClass = mainClass;
	}

	/**
	 * Initializes setup of card.
	 * Will only successfuly initialize if the user selects a product first
	 * Creates action buttons that pass the listening back to the main BankAppGUI class.
	 * @return Returns true is an account has been selected by the user before trying to make a transfer
	 */
	public boolean init() {
		this.productTable = DisplayAccountGUI.getProductTable();
		if (productTable != null) {
			// When nothing is highlighted 
			if (productTable.getSelectedRow() == -1) {
				return false;
			}
		}
		else {
			return false;
		}
		if (init) {
			// Sets layout for the card
			depositPanel = new JPanel(new GridLayout(4,2));
			// Prompts for deposit amount
			JLabel depAmount = new JLabel("Enter the amount to deposit: ");
			depositPanel.add(depAmount);
			depText = new JTextField(10);
			depositPanel.add(depText);
			// Creates and displays submit deposit button
			JButton submitDepButton = new JButton("Submit Deposit");
			depositPanel.add(submitDepButton);
			submitDepButton.addActionListener(mainClass);
			// Creates and displays cancel deposit button
			JButton cancelDeposit = new JButton("Cancel Deposit");
			depositPanel.add(cancelDeposit);
			cancelDeposit.addActionListener(mainClass);
			errorText = new JLabel("");
			depositPanel.add(errorText);
			errorText.setForeground(Color.red);
			add(depositPanel);
			init = false;
		}
		else {
			depText.setText("");
		}
		return true;
	}
	// http://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java
	/**
	 * Makes sure that the amount entered by the user is a valid amount and returns a boolean 
	 * @return boolean
	 */
	public boolean validDep(){
		String depositAmount = depText.getText();
		  try  
		  {  
		    double valid = Double.parseDouble(depositAmount); 
		    return valid >= 0;
		  }  
		  catch(NumberFormatException nfe)  
		  {  
		    return false;  
		  }  
	}
	
	public boolean submitDeposit() {
		
		if (!validDep()) {
			displayError("Error: Please enter a valid amount to deposit");
			return false;
		}
		productTable = DisplayAccountGUI.getProductTable();
		User user = mainClass.getUser();
		int selectedRow = productTable.getSelectedRow();
		// http://stackoverflow.com/questions/12275011/java-getting-jtable-value-per-row
		Object dep = productTable.getModel().getValueAt(selectedRow, 0);
		// Gets the account 
		Product aProduct = user.getProduct(String.valueOf(dep));
		// Gets the amount to deposit 
		double depositAmount = Double.parseDouble(depText.getText()); 
		String depositError = aProduct.deposit(depositAmount);
		// If deposit is valid 
		if (depositError == "") {
			return true;
		}
		// Displays error 
		else {
			displayError(depositError);
			return false;
		}		
	}

	/**
	 * Creates and error message that shows on the card for the amount specified by the time
	 * @param error
	 */
	public void displayError(String error) {
		if (!error.equals("")) {
			errorText.setText(error);
			// Displays an error message for 3 seconds 
			//http://stackoverflow.com/questions/4044726/how-to-set-a-timer-in-java
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					errorText.setText("");
				}
			}, 3*1000);
		}	
	}
	
	
}

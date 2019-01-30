package GUI;
import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import BankingApp.*;
import User.*;
import Main.BankAppGUI;
import Product.*;
import java.io.*;

/**
 * This class represents the transfer card for the banking app GUI
 * @author Jordan Dobrescu, Jennifer Hunchak, Vishaal Bakshi, Nicholas Adam
 * @version FinalSubmission
 */
public class TransferGUI extends JPanel implements Serializable {
	// Initializes instance variables
	private static final long serialVersionUID = 7225364412115433575L;
	private JTextField transAccountText;
	private JTextField transText;
	private JTable productTable;
	private Timer timer = new Timer();
	private JLabel errorText;
	private BankAppGUI mainClass;
	boolean init = true;
	
	/**
	 * Constructor that takes the main GUI class as a parameter,
	 * which is done to accomplish action listening for the buttons
	 * @param mainClass
	 */
	public TransferGUI(BankAppGUI mainClass) {
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
			transfer();
			init = false;
		}
		else {
			transText.setText("");
		}
		return true;
	}
	
	public boolean correctAccount(){
		User user = mainClass.getUser();
		String transferAccount = transAccountText.getText();
		Product toAccount = user.getProduct(transferAccount);
		return toAccount != null;
	}
	
	public void transfer() {
		// Sets layout for the card 
		JPanel transferPanel = new JPanel(new GridLayout(4,2));
		// Label and text field to choose account to transfer to
		JLabel transAccount = new JLabel("Enter the account to transfer to: ");
		transferPanel.add(transAccount);
		transAccountText = new JTextField(10);
		transferPanel.add(transAccountText);
		// Label and text field to specify the amount to transfer
		JLabel transAmount = new JLabel("Enter the amount to transfer: ");
		transferPanel.add(transAmount);
		transText = new JTextField(10);
		transferPanel.add(transText);
		// Submit transfer button
		JButton submitTransButton = new JButton("Submit Transfer");
		transferPanel.add(submitTransButton);
		submitTransButton.addActionListener(mainClass);
		// Cancel transfer button
		JButton cancelTransfer = new JButton("Cancel Transfer");
		transferPanel.add(cancelTransfer);
		cancelTransfer.addActionListener(mainClass);
		errorText = new JLabel("");
		transferPanel.add(errorText);
		errorText.setForeground(Color.red);
		add(transferPanel);
	}
	
	/**
	 * Calls transfer from Product or displays an error message if account doesn't exist
	 * or numerical digit is not entered.
	 */
	public boolean submitTransfer() {
		if (!validTrans()) {
			displayError("Error: Please enter a valid amount to transfer");
			return false;
		}
		User user = mainClass.getUser();
		BankApp bank = mainClass.getBank();
		int selectedRow = productTable.getSelectedRow();
		// http://stackoverflow.com/questions/12275011/java-getting-jtable-value-per-row
		Object trans = productTable.getModel().getValueAt(selectedRow, 0);
		// Gets the account 
		Product cProduct = user.getProduct(String.valueOf(trans));
		String transferAccount = transAccountText.getText();
		// Gets the account to transferTo
		Product toAccount = user.getProduct(transferAccount);
		String transferError = "Error: could not find account.";
		// Checks the account exists
		if (toAccount != null) {
			// Checks it is a valid amount 
			if (bank.isDigit(transText.getText())) {
				double transferAmount = Double.parseDouble(transText.getText()); 
				// Calls transfer in product 
				transferError = cProduct.transfer(transferAmount,toAccount);
			}
			else {
				transferError = "Error: please enter a digit.";
			}
		}
		if (transferError == "") {
			return true;
		}
		else {
			displayError(transferError);
			/*
			JLabel transferErrorField = new JLabel(transferError);
			currentPane.add(transferErrorField);
			super.revalidate();
			//http://stackoverflow.com/questions/4044726/how-to-set-a-timer-in-java
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					currentPane.remove(transferErrorField);
				}
			}, 3*1000);*/
			return false;
		}
	}
	
	// http://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java
	/**
	 * Makes sure that the amount entered by the user is a valid amount and returns a boolean 
	 * @return boolean
	 */
			public boolean validTrans(){
				String transferAmount = transText.getText();
				  try  
				  {  
					double valid = Double.parseDouble(transferAmount); 
					return valid >= 0; 
				  }  
				  catch(NumberFormatException nfe)  
				  {  
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

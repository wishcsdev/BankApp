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
 * This class represents the withdraw card for the banking app GUI
 * @author Jordan Dobrescu, Jennifer Hunchak, Vishaal Bakshi, Nicholas Adam
 * @version FinalSubmission
 */
public class WithdrawGUI extends JPanel implements Serializable {
	// Initializes instance variables
	private static final long serialVersionUID = 1288680866323064454L;
	private JTextField witText;
	private JTable productTable;
	private BankAppGUI mainClass;
	private Timer timer = new Timer();
	private JLabel errorText;
	public boolean init = true;
	
	/**
	 * Constructor that takes the main GUI class as a parameter,
	 * which is done to accomplish action listening for the buttons
	 * @param mainClass
	 */
	public WithdrawGUI(BankAppGUI mainClass) {
		this.mainClass = mainClass;
		
	}
	
	/**
	 * Initializes setup of card.
	 * Will only successfuly initialize if the user selects a product first
	 * Creates action buttons that pass the listening back to the main BankAppGUI class.
	 * @return Returns true is an account has been selected by the user before trying to make a transfer
	 */
	public boolean init(){
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
			JPanel withdrawPanel = new JPanel(new GridLayout(4,2));
			// Prompts for withdraw amount
			JLabel witAmount = new JLabel("Enter the amount to withdraw: ");
			withdrawPanel.add(witAmount);
			witText = new JTextField(10);
			withdrawPanel.add(witText);
			// Creates and displays submit withdraw button
			JButton submitWitButton = new JButton("Submit Withdraw");
			withdrawPanel.add(submitWitButton);
			submitWitButton.addActionListener(mainClass);
			// Creates and displays cancel withdraw button
			JButton cancelWithdraw = new JButton("Cancel Withdraw");
			withdrawPanel.add(cancelWithdraw);
			cancelWithdraw.addActionListener(mainClass);
			errorText = new JLabel("");
			withdrawPanel.add(errorText);
			errorText.setForeground(Color.red);
			add(withdrawPanel);
			init = false;
		}
		else {
			witText.setText("");
		}
		return true;
	}
	
	/**
	 * Method to actually submit the withdrawal from the user's account.
	 */
	public boolean submitWithdraw() {
		if (!validWit()) {
			displayError("Error: Please enter a valid amount to withdraw");
			return false;
		}
		productTable = DisplayAccountGUI.getProductTable();
		User user = mainClass.getUser();
		// Gets the account selected 
		int selectedRow = productTable.getSelectedRow();
		// http://stackoverflow.com/questions/12275011/java-getting-jtable-value-per-row
		Object wit = productTable.getModel().getValueAt(selectedRow, 0);
		// Gets the account 
		Product bProduct = user.getProduct(String.valueOf(wit));
		double withdrawAmount = Double.parseDouble(witText.getText());
		String withdrawError = bProduct.withdraw(withdrawAmount);
		// If withdraw is valid 
		if (withdrawError == "") {
			return true;
		}
		// Displays an error message 
		else {
			displayError(withdrawError);
			return false;
		}
	}
	
	// http://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java
	/**
	 * Makes sure that the amount entered by the user is a valid amount and returns a boolean 
	 * @return boolean
	 */
		public boolean validWit(){
			String withdrawAmount = witText.getText();
			  try  
			  {  
				double valid = Double.parseDouble(withdrawAmount); 
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

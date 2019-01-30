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
 * This class represents card to add an account for the banking app GUI
 * @author Jordan Dobrescu, Jennifer Hunchak, Vishaal Bakshi, Nicholas Adam
 * @version FinalSubmission
 */
public class AddAccountGUI extends JPanel implements Serializable {
	// Initializes instance variables
	private static final long serialVersionUID = -3421688532345206942L;
	private JPanel topPanel;
	private JComboBox<String> selectAccountType;
	private Timer timer = new Timer();
	private BankAppGUI mainClass;
	public boolean init = true;
	
	/**
	 * Constructor that takes the main GUI class as a parameter,
	 * which is done to accomplish action listening for the buttons
	 * @param mainClass
	 */
	public AddAccountGUI(BankAppGUI mainClass) {
		this.mainClass = mainClass;
	}
	
	/**
	 * Initializes setup of card.
	 * Displays dropdown menu to the user for them to choose what type of
	 * account they want to add, chequing or savings.
	 */
	public void init(){
		if (init) {
			// Sets up layout for the card
			String[] productTypes = {"Chequing", "Savings"};
			JPanel addAccountPanel = new JPanel(new GridLayout(10,1));
			topPanel = new JPanel(new FlowLayout());
			// Creates drop down menu 
			// https://docs.oracle.com/javase/tutorial/uiswing/components/combobox.html
			selectAccountType = new JComboBox<String>(productTypes);
			topPanel.add(selectAccountType);
			// Create account button 
			JButton newAccountButton = new JButton("Create Account");
			topPanel.add(newAccountButton);
			newAccountButton.addActionListener(mainClass);
			// Cancel button 
			JButton returnMenu = new JButton("Cancel");
			topPanel.add(returnMenu);
			returnMenu.addActionListener(mainClass);
			// Sets panel 
			addAccountPanel.add(topPanel);
			add(addAccountPanel);
			init = false;
		}
		else {
		}
	}
	
	/**
	 * Method that creates a new account for the user iff they do not already have an account of that type.
	 * @return Returns true to main GUI when adding account complete
	 */
	public boolean createAccount() {
		User user = mainClass.getUser();
		//http://stackoverflow.com/questions/4962416/preferred-way-of-getting-the-selected-item-of-a-jcombobox
		// Gets item selected in drop down menu 
		String accountType = String.valueOf(selectAccountType.getSelectedItem());
		if (accountType.equals("Chequing")) {
			// If the user doesnt already have a chequing account
			if (user.getProduct("Chequing") == null){
				user.addProduct(new ChequingAccount());
				return true;

			} else {
				// User already has an account so displays an error message 
				//http://stackoverflow.com/questions/4044726/how-to-set-a-timer-in-java
				JTextField errorAccountC = new JTextField(50);
				errorAccountC.setText("ERROR: You already have an account of this type.");
				errorAccountC.setForeground(Color.red);
				// Displays the error until timer is up 
				topPanel.add(errorAccountC);
				super.revalidate();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						topPanel.remove(errorAccountC);
					}
				// Displays error message for 3 seconds
				}, 3*1000);
			}
		} else if (accountType.equals("Savings")) {
			// If the user doesnt already have a savings account 
			if (user.getProduct("Savings") == null){
				user.addProduct(new SavingsAccount());
				return true;
			} else {
				// User already has a savings account 
				JTextField errorAccountS = new JTextField(50);
				errorAccountS.setText("ERROR: You already have an account of this type.");
				errorAccountS.setForeground(Color.red);
				// Displays the error message until timer is up 
				topPanel.add(errorAccountS);
				super.revalidate();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						topPanel.remove(errorAccountS);
					}
				// Displays error message for 3 seconds 
				}, 3*1000);
			}
		}
		return false;
	}

}

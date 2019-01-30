package GUI;
import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import BankingApp.*;
import Main.BankAppGUI;
import java.io.*;

/**
 * This class represents the log in card for the banking app GUI.
 * @author Jordan Dobrescu, Jennifer Hunchak, Vishaal Bakshi, Nicholas Adam
 * @version FinalSubmission
 *
 */
public class LogInGUI extends JPanel implements Serializable {
	// Initializes instance variables
	private static final long serialVersionUID = -6298334959532479632L;
	private JLabel accountNumPrompt;
	private JLabel password;
	private JButton signInButton;
	private JButton registerButton;
	private JTextField accountNumText;
	private JTextField passwordText;
	private JTextField incorrectAccountText;
	private JLabel welcome;
	private Timer timer = new Timer();
	private BankAppGUI mainClass = new BankAppGUI();
	public boolean init = true;
	
	/**
	 * Constructor that takes the main GUI class as a parameter,
	 * which is done to accomplish action listening for the buttons
	 * @param mainClass
	 */
	public LogInGUI(BankAppGUI mainClass) {
		this.mainClass = mainClass;
	}
	
	/**
	 * Initializes setup of card.
	 * Displays option to sign in, requiring correct account number and password
	 * from the user. Also displays option to register as a new user.
	 * Creates action buttons that pass the listening back to the main BankAppGUI class.
	 */
	public void init() {
		if (init) {
			// Sets layout for the card
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			welcome = new JLabel("Welcome. Please sign in, or register as a new user.");
			add(welcome);
			JPanel accountNumPanel = new JPanel(new FlowLayout());
			// Label and text field for user account number
			accountNumPrompt = new JLabel("Account number: ");
			accountNumPanel.add(accountNumPrompt);
			accountNumText = new JTextField(8);
			accountNumPanel.add(accountNumText);
			add(accountNumPanel);
			// Label and text field for user password
			JPanel passwordPanel = new JPanel(new FlowLayout());
			password = new JLabel("Password: ");
			passwordPanel.add(password);
			passwordText = new JTextField(12);
			passwordPanel.add(passwordText);
			add(passwordPanel);
			// Adds sign in and register buttons for user 
			JPanel buttonsPanel = new JPanel(new FlowLayout());
			signInButton = new JButton("Sign in");
			buttonsPanel.add(signInButton);
			signInButton.addActionListener(mainClass);
			registerButton = new JButton("Register");
			buttonsPanel.add(registerButton);
			registerButton.addActionListener(mainClass);
			add(buttonsPanel);
			init = false;
		}
		else {
			accountNumText.setText("");
			passwordText.setText("");
		}
	}
	
	/**
	 * Method to handle signing in using user input.
	 * Checks the hashmap for the user account, and if the account exists, checks
	 * that the correct password associated with the account has been added.
	 */
	public void signIn() {
		boolean correctAccount = false;
		String accountNumString = accountNumText.getText();
		String passwordString = passwordText.getText();
		BankApp bank = mainClass.getBank();
		correctAccount = bank.authenticateUser(accountNumString, passwordString);
		if (correctAccount == false) {
			incorrectAccountText = new JTextField();
			incorrectAccountText.setText("Invalid username or password. Please try again.");
			incorrectAccountText.setForeground(Color.red);
			add(incorrectAccountText);
			super.revalidate();
			//http://stackoverflow.com/questions/4044726/how-to-set-a-timer-in-java
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					remove(incorrectAccountText);
				}
			// Displays the message for 4 seconds 	
			}, 4*1000);
		// If login is successful then it sets the currentUser and takes them to menu options	
		} else {
			mainClass.setUser(bank.getCurrentUser());
		}
	}

}

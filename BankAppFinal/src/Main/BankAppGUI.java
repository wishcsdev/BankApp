package Main;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import GUI.*;
import BankingApp.*;
import User.*;
import java.io.*;

/**
 * Main GUI class for the banking app.
 * Initializes all cards for the bank app GUI, using CardLayout to
 * provide the interface to the user.
 * @author Jordan Dobrescu, Jennifer Hunchak, Vishaal Bakshi, Nicholas Adam
 * @version FinalSubmission
 *
 */
public class BankAppGUI extends JApplet implements ActionListener, Serializable {
	// Initializes instance variables
	private static final long serialVersionUID = 2437116948491570059L;
	public static final int DEFAULT_WINDOW_WIDTH = 1200;
    public static final int DEFAULT_WINDOW_HEIGHT = 700;
    public static String LOGIN_SCREEN = "logInScreen";
    public static String REGISTER_SCREEN = "registerScreen";
    public static String REGISTER_COMPLETE_SCREEN = "registerCompleteScreen";
    public static String ACCOUNT_SCREEN = "accountScreen";
    public static String ADD_ACCOUNT_SCREEN = "addAccountScreen";
    public static String WITHDRAW_SCREEN = "withdrawScreen";
    public static String DEPOSIT_SCREEN = "depositScreen";
    public static String TRANSFER_SCREEN = "transferScreen";
	private User user = null;
	private BankApp bank = new BankApp();
	private Timer timer2=new Timer();
	JPanel bankCards;
	CardLayout bankCardsLayout = null;
	LogInGUI logInScreen;
	RegisterGUI registerScreen;
	RegisterCompleteGUI registerCompleteScreen;
	DisplayAccountGUI accountScreen;
	AddAccountGUI addAccountScreen;
	WithdrawGUI withdrawScreen;
	DepositGUI depositScreen;
	TransferGUI transferScreen;
    public boolean init = true;
    
    /**
     * Initializes when applet is started. Creates all cards used for the GUI
     * and adds them to the main card in the CardLayout. Sets dimensions and 
     * layout specifications for the main panel.
     */
	public void init() {
		if (init) {
			// Sets layout for the card
			Container contentPane = getContentPane();
			setSize (DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
			contentPane.setLayout(new FlowLayout());
			contentPane.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
			// Creates a timer for the app that saves/writes to file 
			timer2.schedule(new TimerTask() {
				@Override
				public void run() {
					bank.writeToFile();        
				}
			}, 10000, 2000);
			// Creates all GUI cards for the app and adds them to the CardLayout
			logInScreen = new LogInGUI(this);
			registerScreen = new RegisterGUI(this);
			registerCompleteScreen = new RegisterCompleteGUI(this);
			accountScreen = new DisplayAccountGUI(this);
			addAccountScreen = new AddAccountGUI(this);
			withdrawScreen = new WithdrawGUI(this);
			depositScreen = new DepositGUI(this);
			transferScreen = new TransferGUI(this);
			bankCardsLayout = new CardLayout();
			bankCards = new JPanel(bankCardsLayout);
			Dimension d = new Dimension(DEFAULT_WINDOW_WIDTH,DEFAULT_WINDOW_HEIGHT);
			bankCards.add(logInScreen, LOGIN_SCREEN);
			bankCards.add(registerScreen, REGISTER_SCREEN);
			bankCards.add(registerCompleteScreen, REGISTER_COMPLETE_SCREEN);
			bankCards.add(accountScreen, ACCOUNT_SCREEN);
			bankCards.add(addAccountScreen, ADD_ACCOUNT_SCREEN);
			bankCards.add(withdrawScreen, WITHDRAW_SCREEN);
			bankCards.add(depositScreen, DEPOSIT_SCREEN);
			bankCards.add(transferScreen, TRANSFER_SCREEN);
			bankCardsLayout.show(bankCards, LOGIN_SCREEN);
			// Initializes the cards, displays the log in card to the user
			logInScreen.init();
			contentPane.add(bankCards);
			setVisible(true);
			contentPane.setVisible(true);
			bankCards.setVisible(true);
			bankCards.revalidate();
			super.repaint();
			super.revalidate();
			init = false;
		}
	}
	
	/**
	 * Handles action performing for every button on all GUI cards.
	 * Depending on button pressed by the user, the appropriate card
	 * is brought to the front to interact with.
	 */
	public void actionPerformed(ActionEvent e) {
		bankCardsLayout = (CardLayout) bankCards.getLayout();
		String selected = "";
		String error = "";
		// Sign in button, LogInGUI card
		if (e.getActionCommand().equals("Sign in")) {
			logInScreen.signIn();
			if (user != null) {
				selected = ACCOUNT_SCREEN;
				accountScreen.init();
			}
		// Register button, LogInGUI card
		} else if (e.getActionCommand().equals("Register")) {
			selected = REGISTER_SCREEN;
			registerScreen.init();
		// Deposit button, DisplayAccountGUI card
		} else if (e.getActionCommand().equals("Deposit")) {
			if (depositScreen.init()) {
				selected = DEPOSIT_SCREEN;
			}
			else {
				error = "Error: Please select an account to make a transaction";
			}
		// Withdraw button, DisplayAccountGUI card
		} else if (e.getActionCommand().equals("Withdraw")) {
			if (withdrawScreen.init()) {
				selected = WITHDRAW_SCREEN;
			}
			else {
				error = "Error: Please select an account to make a transaction";
			}
		// Transfer button, DisplayAccountGUI card
		} else if (e.getActionCommand().equals("Transfer")) {
			if (transferScreen.init()) {
				selected = TRANSFER_SCREEN;
			}
			else {
				error = "Error: Please select an account to make a transaction";
			}
		// Submit deposit button, DepositGUI card
		} else if (e.getActionCommand().equals("Submit Deposit")) {
			if (depositScreen.submitDeposit()) {
				selected = ACCOUNT_SCREEN;
				accountScreen.init();
			}
			
		// Submit withdraw button, WithdrawGUI card
		} else if (e.getActionCommand().equals("Submit Withdraw")) {
			if(withdrawScreen.submitWithdraw()) {
				selected = ACCOUNT_SCREEN;
				accountScreen.init();
			}
			
		// Submit transfer button, TransferGUI card
		} else if (e.getActionCommand().equals("Submit Transfer")) {
			if(transferScreen.submitTransfer()) {
				selected = ACCOUNT_SCREEN;
				accountScreen.init();
			}
		// Submit button, RegisterGUI card
		} else if (e.getActionCommand().equals("Submit")) {
			registerScreen.createUser();
			selected = REGISTER_COMPLETE_SCREEN;
			registerCompleteScreen.init();
		// OK button, RegisterCompleteGUI card
		} else if (e.getActionCommand().equals("OK")) {
			logInScreen.init();
			selected = LOGIN_SCREEN;
		// Add an account button, DisplayAccountGUI card
		} else if (e.getActionCommand().equals("Add Account")) {
			addAccountScreen.init();
			selected = ADD_ACCOUNT_SCREEN;
		// Create account button, AddAccountGUI card
		} else if (e.getActionCommand().equals("Create Account")) {
			if (addAccountScreen.createAccount()) {
				selected = ACCOUNT_SCREEN;
				accountScreen.init();
			}
		// Cancel adding an account button, AddAccountGUI card
		} else if (e.getActionCommand().equals("Cancel")) {
			accountScreen.init();
			selected = ACCOUNT_SCREEN;
		// Cancel withdraw button, WithdrawGUI card
		} else if (e.getActionCommand().equals("Cancel Withdraw")) {
			accountScreen.init();
			selected = ACCOUNT_SCREEN;
		// Cancel deposit button, DepositGUI card
		} else if (e.getActionCommand().equals("Cancel Deposit")) {
			accountScreen.init();
			selected = ACCOUNT_SCREEN;
		// Cancel transfer button,TransferGUI card
		} else if (e.getActionCommand().equals("Cancel Transfer")) {
			accountScreen.init();
			selected = ACCOUNT_SCREEN;
		// Cancel withdraw button, WithdrawGUI card
		} else if (e.getActionCommand().equals("Cancel Register")) {
			logInScreen.init();
			selected = LOGIN_SCREEN;
		// Logout button, DisplayAccountGUI card
		} else if (e.getActionCommand().equals("Logout")){
			user = null;
			logInScreen.init();
			selected = LOGIN_SCREEN;
		}
		// Shows the specified selected card after listening to action buttons
		if (!selected.equals("")) {
			bankCardsLayout.show(bankCards, selected);
		}
		// For account transactions, displays error message if user does not select an account
		accountScreen.displayError(error);
	}
	
	/**
	 * Accessor method to get current running bank app
	 * @return bank 
	 */
	public BankApp getBank() {
		return bank;
	}
	
	/**
	 * Accessor method that gets user currently logged in
	 * @return user
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * After successful login, mutator method to set the user to current/logged in
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
}


	
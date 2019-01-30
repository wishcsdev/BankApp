package GUI;
import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import Main.BankAppGUI;
import User.*;
import java.io.*;

/**
 * This class represents the card that display's the user's account for the banking app GUI
 * @author Jordan Dobrescu, Jennifer Hunchak, Vishaal Bakshi, Nicholas Adam
 * @version FinalSubmission
 */
public class DisplayAccountGUI extends JPanel implements Serializable {
	// Initializes instance variables
	private static final long serialVersionUID = -1033187754781864824L;
	private JLabel welcome;
	private JButton addAccountButton;
	private JButton depositButton;
	private JButton withdrawButton;
	private JButton transferButton;
	private JButton logoutButton;
	private JScrollPane scrollTable;
	private static JTable productTable;
	private BankAppGUI mainClass;
	private Timer timer = new Timer();
	private JLabel errorText;
	public boolean init = true;
	
	/**
	 * Constructor that takes the main GUI class as a parameter,
	 * which is done to accomplish action listening for the buttons
	 * @param mainClass
	 */
	public DisplayAccountGUI(BankAppGUI mainClass) {
		this.mainClass = mainClass;
	}
	
	/**
	 * Initializes setup of card.
	 * Displays the user's products to the screen.
	 * Option to submit, withdraw, transfer, add account, or log out.
	 */
	public void init() {
		if (init) {
			// Sets up layout for the card
			JPanel welcomePanel = new JPanel();
			// http://stackoverflow.com/questions/9212155/java-boxlayout-panels-alignment
			welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
			welcome = new JLabel("Welcome! Please choose an option from the choices below.");
			welcome.setVisible(true);
			welcome.repaint();
			super.repaint();
			welcomePanel.add(welcome);
			// Creates the buttons for the user make transactions in their accounts or add a new account
			JPanel buttonPanel = new JPanel(new FlowLayout());
			addAccountButton = new JButton("Add Account");
			buttonPanel.add(addAccountButton);
			addAccountButton.addActionListener(mainClass);
			depositButton = new JButton("Deposit");
			buttonPanel.add(depositButton);
			depositButton.addActionListener(mainClass);
			withdrawButton = new JButton("Withdraw");
			buttonPanel.add(withdrawButton);
			withdrawButton.addActionListener(mainClass);
			transferButton = new JButton("Transfer");
			buttonPanel.add(transferButton);
			transferButton.addActionListener(mainClass);
			welcomePanel.add(buttonPanel);
			errorText = new JLabel("");
			welcomePanel.add(errorText);
			errorText.setForeground(Color.red);

			// Adds a table with their accounts 
			// http://www.java2s.com/Tutorial/Java/0240__Swing/CreatingaJTable.htm
			scrollTable = new JScrollPane(productTable);
			viewAccounts(scrollTable);
			welcomePanel.add(scrollTable);
			
			// Logout button 
			logoutButton = new JButton("Logout");
			welcomePanel.add(logoutButton);
			logoutButton.addActionListener(mainClass);
			add(welcomePanel);
			init = false;
		} else {
			viewAccounts(scrollTable);
		}
	}
	/**
	 * This method gets the user's product information and displays it on
	 * the card in the form of a table.
	 * @param insertTable Takes JScrollPane as parameter to make the table
	 */
	public void viewAccounts(JScrollPane insertTable) {
		// Creates a 2D list that gets list of users accounts
		// Stores the users account type, and balance
		User user = mainClass.getUser();
		if (user != null) {
			String[][] userData = user.getAccounts();
			// List stores the headings 
			String[] colNames = new String[] {"Account Type", "Balance"};
			// http://www.java2s.com/Tutorial/Java/0240__Swing/CreatingaJTable.htm
			// Displays the users information 
			productTable = new JTable(userData, colNames);
			// http://www.java2s.com/Code/JavaAPI/javax.swing/JScrollPanesetViewportViewComponentview.htm
			insertTable.setViewportView(productTable);
		}
	}
	
	/**
	 * Accessor method to access the product table 
	 * @return productTable
	 */
	public static JTable getProductTable() {
		return productTable;
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

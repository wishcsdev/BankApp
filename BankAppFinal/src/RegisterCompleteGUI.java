package GUI;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import Main.BankAppGUI;
import java.io.*;

/**
 * This class represents the card that comfirms complete registration for the banking app GUI.
 * @author Jordan Dobrescu, Jennifer Hunchak, Vishaal Bakshi, Nicholas Adam
 * @version FinalSubmission
 */
public class RegisterCompleteGUI extends JPanel implements Serializable {
	// Initializes instance variables
	private static final long serialVersionUID = 2720147161337269179L;
	private JButton okAccountNum;
	private BankAppGUI mainClass;
	
	/**
	 * Constructor that takes the main GUI class as a parameter,
	 * which is done to accomplish action listening for the buttons
	 * @param mainClass
	 */
	public RegisterCompleteGUI(BankAppGUI mainClass) {
		this.mainClass = mainClass;
	}
	
	/**
	 * Initializes setup of the card.
	 * Displays the user's account number to the screen as confirmation that
	 * the account has been created.
	 * Creates action button for user to return to the log in screen.
	 */
	public void init() {
		// Sets the layout for the card
		JPanel accountNumPanel = new JPanel(new FlowLayout());
		JLabel userAccountNum = new JLabel("Your account number is: " + RegisterGUI.getANum());
		accountNumPanel.add(userAccountNum);
		// Creates button for user confirmation (gives them time to note their account number)
		okAccountNum = new JButton("OK");
		accountNumPanel.add(okAccountNum);
		okAccountNum.addActionListener(mainClass);
		add(userAccountNum);
		add(okAccountNum);
	}
}

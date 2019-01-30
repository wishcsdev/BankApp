package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import Main.BankAppGUI;
import User.*;
import java.io.*;

/**
 * This class represents the register card for the banking app GUI.
 * @author Jordan Dobrescu, Jennifer Hunchak, Vishaal Bakshi, Nicholas Adam
 * @version FinalSubmission
 */
public class RegisterGUI extends JPanel implements Serializable {
	// Initializes instance variables
	private static final long serialVersionUID = 3735038577802301265L;
	private static String aNum;
	private JLabel firstName;
	private JLabel firstNameError;
	private JLabel lastName;
	private JLabel lastNameError;
	private JLabel birthday;
	private JLabel birthdayError;
	private JLabel password;
	private JLabel passwordError;
	private JLabel passwordConfirm;
	private JLabel passwordConfirmError;
	private JLabel province;
	private JLabel provinceError;
	private JLabel city;
	private JLabel cityError;
	private JLabel address;
	private JLabel addressError;
	private JLabel sin;
	private JLabel sinError;
	private JTextField inputFirstName;
	private JTextField inputLastName;
	private JTextField inputBirthday;
	private JTextField inputPassword;
	private JTextField inputPasswordConfirm;
	private JTextField inputProvince;
	private JTextField inputCity;
	private JTextField inputAddress;
	private JTextField inputSin;
	private JButton submitButton;
	private BankAppGUI mainClass;
	private ArrayList<JTextField> registerFields = new ArrayList<>();
	private User user = null;
	public boolean init = true;
	
	/**
	 * Constructor that takes the main GUI class as a parameter,
	 * which is done to accomplish action listening for the buttons
	 * @param mainClass
	 */
	public RegisterGUI(BankAppGUI mainClass) {
		this.mainClass = mainClass;
	}
	
	/**
	 * Initializes setup of the card.
	 * Displays fields for all user input required for registration.
	 * Focus listener is set up for each text field so that user cannot submit
	 * until all fields meet registration requirements; error message displayed
	 * in red to the user.
	 * Creates action buttons that pass the listening back to the main BankAppGUI class.
	 */
	public void init() {	
		if (init) {
			// Sets layout for the card
			setLayout(new GridLayout(10,3));
			// First name field; must be String
			User newUser = new User();
			firstName = new JLabel("First name: ");
			add(firstName);
			inputFirstName = new JTextField(10);
			inputFirstName.addFocusListener(new FocusListener() {
			      public void focusGained(FocusEvent e) {
			      }
			      public void focusLost(FocusEvent e) {
					String errorFirstName = newUser.setFirstName(inputFirstName.getText());
					firstNameError.setText(errorFirstName);
					// http://stackoverflow.com/questions/2966334/how-do-i-set-the-colour-of-a-label-coloured-text-in-java
					firstNameError.setForeground(Color.red);
			      }
			    });
			registerFields.add(inputFirstName);
			add(inputFirstName);
			firstNameError = new JLabel("");
			add(firstNameError);
			// Last name field; must be string
			lastName = new JLabel("Last name: ");
			add(lastName);
			inputLastName = new JTextField(10);
			inputLastName.addFocusListener(new FocusListener() {
				public void focusGained(FocusEvent e) {
				}
				public void focusLost(FocusEvent e) {
					String errorLastName = newUser.setLastName(inputLastName.getText());
					lastNameError.setText(errorLastName);
					lastNameError.setForeground(Color.red);
				}
			});
			registerFields.add(inputLastName);
			add(inputLastName);
			lastNameError = new JLabel("");
			add(lastNameError);
			// Birthday field; must be in date format as displayed
			birthday = new JLabel("Enter your birthday (YYYY-MM-DD): ");
			add(birthday);
			inputBirthday = new JTextField(10);
			inputBirthday.addFocusListener(new FocusListener() {
				public void focusGained(FocusEvent e) {
				}
				public void focusLost(FocusEvent e) {
					String errorDOB = newUser.setDateOfBirth(inputBirthday.getText());
					birthdayError.setText(errorDOB);
					birthdayError.setForeground(Color.red);
				}
			});
			registerFields.add(inputBirthday);
			add(inputBirthday);
			birthdayError = new JLabel("");
			add(birthdayError);
			// Password field
			password = new JLabel("Enter your password: ");
			add(password);
			inputPassword = new JTextField(10);
			inputPassword.addFocusListener(new FocusListener() {
				public void focusGained(FocusEvent e) {
				}
				public void focusLost(FocusEvent e) {
					String errorPass = newUser.setPassword(inputPassword.getText());
					passwordError.setText(errorPass);
					passwordError.setForeground(Color.red);
				}
			});	
			registerFields.add(inputPassword);
			add(inputPassword);
			passwordError = new JLabel("");
			add(passwordError);
			// Confirm password field; must match first password input
			passwordConfirm = new JLabel("Re-enter your password: ");
			add(passwordConfirm);
			inputPasswordConfirm = new JTextField(10);
			inputPasswordConfirm.addFocusListener(new FocusListener() {
				public void focusGained(FocusEvent e) {
				}
				public void focusLost(FocusEvent e) {
					String passConfirmError = newUser.confirmPassword(inputPassword.getText(), inputPasswordConfirm.getText());
					passwordError.setText(passConfirmError);
					passwordError.setForeground(Color.red);
				}
			});
			registerFields.add(inputPasswordConfirm);
			add(inputPasswordConfirm);
			passwordConfirmError = new JLabel("");
			add(passwordConfirmError);
			// Province field; must be a Canadian province in format "XX"
			province = new JLabel("Enter your province: ");
			add(province);
			inputProvince = new JTextField(10);
			inputProvince.addFocusListener(new FocusListener() {
				public void focusGained(FocusEvent e) {
				}
				public void focusLost(FocusEvent e) {
					String errorProvince = newUser.setProvince(inputProvince.getText());
					provinceError.setText(errorProvince);
					provinceError.setForeground(Color.red);
				}	
			});
			registerFields.add(inputProvince);
			add(inputProvince);
			provinceError = new JLabel("");
			add(provinceError);
			// City field
			city = new JLabel("Enter your city: ");
			add(city);
			inputCity = new JTextField(10);
			inputCity.addFocusListener(new FocusListener() {
				public void focusGained(FocusEvent e) {
				}
				public void focusLost(FocusEvent e) {
					String errorCity = newUser.setCity(inputCity.getText());
					cityError.setText(errorCity);
					cityError.setForeground(Color.red);
				}	
			});
			registerFields.add(inputCity);
			add(inputCity);
			cityError = new JLabel("");
			add(cityError);
			// Address field; must be number followed by alphabetic street name
			address = new JLabel("Enter your address: ");
			add(address);
			inputAddress = new JTextField(10);
			inputAddress.addFocusListener(new FocusListener() {
				public void focusGained(FocusEvent e) {
				}
				public void focusLost(FocusEvent e) {
					String errorAddress = newUser.setAddress(inputAddress.getText());
					addressError.setText(errorAddress);
					addressError.setForeground(Color.red);
				}	
			});
			registerFields.add(inputAddress);
			add(inputAddress);
			addressError = new JLabel("");
			add(addressError);
			// Sin field; must be 9 digit number
			sin = new JLabel("Enter your SIN (XXXXXXXXX): ");
			add(sin);
			inputSin = new JTextField(10);
			inputSin.addFocusListener(new FocusListener() {
				public void focusGained(FocusEvent e) {
				}
				public void focusLost(FocusEvent e) {
					String errorSin = newUser.setSinNumber(inputSin.getText());
					sinError.setText(errorSin);
					sinError.setForeground(Color.red);
				}	
			});
			registerFields.add(inputSin);
			add(inputSin);
			sinError = new JLabel("");
			add(sinError);
			// Creates submit button
			submitButton = new JButton("Submit");
			add(submitButton);
			submitButton.addActionListener(mainClass);
			// Creates cancel register button 
			JButton cancelRegisterButton = new JButton("Cancel Register");
			add(cancelRegisterButton);
			cancelRegisterButton.addActionListener(mainClass);
			init = false;
			submitButton.setEnabled(false);
			
			for (JTextField tf : registerFields) {
    			tf.getDocument().addDocumentListener(listener);
			}
			super.repaint();
			super.revalidate();
		}
		else {
			inputFirstName.setText("");
			inputLastName.setText("");
			inputBirthday.setText("");
			inputPassword.setText("");
			inputPasswordConfirm.setText("");
			inputProvince.setText("");
			inputCity.setText("");
			inputAddress.setText("");
			inputSin.setText("");
		}
	}
	
	/**
	 * Document listener checks to see that the user input for registration is valid.
	 * Only when this text is all valid is the user able to submit their registration.
	 */
	DocumentListener listener = new DocumentListener() {
	    @Override
	    public void removeUpdate(DocumentEvent e) { changedUpdate(e); }
	    @Override
	    public void insertUpdate(DocumentEvent e) { changedUpdate(e); }
	    @Override
	    public void changedUpdate(DocumentEvent e) {
	        /*boolean canEnable = true;
	        for (JTextField tf : registerFields) {
	            if (tf.getText().isEmpty() || validRegistration() == null) {
	                canEnable = false;
	            }
	        }
	        submitButton.setEnabled(canEnable);*/
	        submitButton.setEnabled(validRegistration() != null);
	    }
	};
	
	/**
	 * Method validates the input from the user to check it meets registration
	 * requirements. Returns a new user with variables set if all requirements met.
	 * @return newUser The new user with variables set
	 * @return null Returns null if user input is invalid
	 */
	public User validRegistration() {
		boolean isValidUser = true;
		User newUser = new User();
		// Passes the users input into setName methods for validation
		String errorFirstName = newUser.setFirstName(inputFirstName.getText());
		isValidUser = isValidUser && (errorFirstName == "");
		String errorLastName = newUser.setLastName(inputLastName.getText()); 
		isValidUser = isValidUser && (errorLastName == "");
		// Passes user input into setBirthday method for validation
		String errorDOB = newUser.setDateOfBirth(inputBirthday.getText()); 
		isValidUser = isValidUser && (errorDOB == "");
		// Passes user input into setPassword method for validation
		String errorPass = newUser.setPassword(inputPassword.getText());
		isValidUser = isValidUser && (errorPass == "");
		// Passes password and confirm password into confirmPassword method for validation
		String errorConfPass = newUser.confirmPassword(inputPassword.getText(),inputPasswordConfirm.getText()); 
		isValidUser = isValidUser && (errorConfPass == "");
		// Passes user input into setProvince method  for validation
		String errorProvince = newUser.setProvince(inputProvince.getText());
		isValidUser = isValidUser && (errorProvince == "");
		// Passes user input into setCity for validation
		String errorCity = newUser.setCity(inputCity.getText());
		isValidUser = isValidUser && (errorCity == "");
		// Passes user input into setAddress for validation
		String errorAddress = newUser.setAddress(inputAddress.getText()); 
		isValidUser = isValidUser && (errorAddress == "");
		// Passes user input into setSin for validation
		String errorSin = newUser.setSinNumber(inputSin.getText());
		isValidUser = isValidUser && (errorSin == "");
		// If all user input is valid, returns new user with input set, otherwise
		// returns nothing.
		if (isValidUser) {
			return newUser;
		} else {
			return null;
		}
	}	
	
	/**
	 * This method uses the validRegistration method to check that all user input
	 * is valid, then it will create a new user object and add it to the hashmap of the bank app.
	 */
	public void createUser(){
		user = validRegistration();
		// If fields are valid the it generates and displays an account number
		// and adds the user to the hash map
		if (user != null) {
			aNum = mainClass.getBank().genAccountNum();
			mainClass.getBank().addUser(aNum, user);
		}
	}
	
	/**
	 * Returns the new user's account numbers as a string
	 * @return aNum
	 */
	public static String getANum() {
		return aNum;
	}
	
}

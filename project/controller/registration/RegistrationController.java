package controller.registration;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.UserDetails;
import ultilities.EmailHelper;
import ultilities.InputValidation;
import ultilities.PinGenerator;
import view.ConfirmationView;
import view.RegistrationView;

/**
 * RegistrationController handles input from the registration screen, including user data and parsing.
 * */
public class RegistrationController extends Controller implements EventHandler<ActionEvent>
{
	private static final String EMPTY = "Need to be filled";
	private static final String WRONG_SYNTAX = 
		"Can have only maximum length of 15 of normal alphabet, number and following special character: ,.;:?_-$";
	private static final String INVALID_EMAIL = "Please enter a valid email";
	private static final String NOT_SAME_PASSWORD = "Not the same password";

	private RegistrationView view;
	
	/**
	 * Constructs a controller for the login UI.
	 * @param view The view associated to this controller
	 */
	public RegistrationController(RegistrationView view)
	{
		this.view = view;
	}
	
	@Override
	public void handle(ActionEvent event)
	{
		String user = view.getUsernameField().getText();
		String email = view.getEmailField().getText();
		String pw = view.getPasswordField().getText();
		String pwRe = view.getConfirmPasswordField().getText();

		//create form to send to database
		UserDetails newUser = new UserDetails(-1, user, pw, email);
		
		boolean qualified = true;
		
		//reset alert Text
		view.userCheck.setText("");
		view.passCheck.setText("");
		view.retypePassCheck.setText("");

		//check if user name is allowed
		if (!InputValidation.inputValidation(user))
		{
			view.userCheck.setText(WRONG_SYNTAX);
			qualified = false;
		}

		//check if user name is empty
		if (user.isEmpty())
		{
			view.userCheck.setText(EMPTY);
			qualified = false;
		}

		//check if email is allowed
		if (!InputValidation.emailValidation(email))
		{
			view.emailCheck.setText(INVALID_EMAIL);
			qualified = false;
		}

		//check if email is empty
		if (email.isEmpty())
		{
			view.emailCheck.setText(EMPTY);
			qualified = false;
		}

		//check if password is allowed
		if (!InputValidation.inputValidation(pw))
		{
			view.passCheck.setText(WRONG_SYNTAX);
			qualified = false;
		}

		//check if password is empty
		if (pw.isEmpty())
		{
			view.passCheck.setText(EMPTY);
			qualified = false;
		}
	
		//check if password retype is empty
		if (pwRe.isEmpty())
		{
			view.retypePassCheck.setText(EMPTY);
			qualified = false;
		}
						
		//check if password and password retype is the same
		if (!pw.equals(pwRe))
		{
			view.retypePassCheck.setText(NOT_SAME_PASSWORD);
			qualified = false;
		}

		if (qualified) 
		{
			//check user name in database
			if (getModel().checkUsername(newUser)!=null)
			{
				view.internetCheck.setText(getModel().checkUsername(newUser));
				qualified = false;
			}
		}
		
		if (qualified) 
		{
			String pin = PinGenerator.generatePin();
			//register user to database
			String alert = getModel().registerNewUser(newUser, pin);
			if (alert == null)
			{
				//send email with pin number
				EmailHelper.sendAuthenticationEmail(email, pin);
				//all text field is qualified, go back to login
				switchView(new ConfirmationView(newUser));
			} 
			else 
			{
				view.internetCheck.setText(alert);
			}
		}
		event.consume();
	}
}

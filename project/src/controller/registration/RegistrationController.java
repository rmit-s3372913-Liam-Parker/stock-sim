package controller.registration;

import java.util.Random;
import java.util.regex.Matcher;
import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.UserDetails;
import view.RegistrationConfirmationView;
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
	RegistrationView view;
	TextField user;
	TextField email;
	TextField pw;
	TextField pwRe;
	
	/**
	 * Constructs a controller for the login UI.
	 * @param user The TextField used to input username.
	 * @param pw The TextField used to input password.
	 * @param pwRe The TextField used to retype password.
	 */
	public RegistrationController(RegistrationView view, TextField userField, TextField emailField,
			PasswordField pwField, PasswordField pwFieldRetype)
	{
		this.view = view;
		if (userField!=null)
			this.user = userField;
		if (emailField!=null)
			this.email = emailField;
		if (pwField!=null)
			this.pw = pwField;
		if (pwFieldRetype!=null)
			this.pwRe = pwFieldRetype;
	}
	
	@Override
	public void handle(ActionEvent event)
	{
		//create form to send to database
		UserDetails newUser = new UserDetails(user.getText(), pw.getText(), email.getText());
		
		boolean qualified = true;
		String inputPattern = "^[0-9a-zA-Z,.;:?_-dollar]{0,15}$";
		String emailPattern = "^[0-9a-zA-Z][0-9a-zA-Z._]*@[0-9a-zA-Z]+([.][a-zA-Z]+)+$";
		inputPattern.replace("dollar", Matcher.quoteReplacement("$"));
		
		//reset alert Text
		view.userCheck.setText("");
		view.passCheck.setText("");
		view.retypePassCheck.setText("");

		//check if user name is allowed
		if (!user.getText().matches(inputPattern)){
			view.userCheck.setText(WRONG_SYNTAX);
			qualified = false;
		}

		//check if user name is empty
		if (user.getText().equals("")){
			view.userCheck.setText(EMPTY);
			qualified = false;
		}

		//check if email is allowed
		if (!email.getText().matches(emailPattern)){
			view.emailCheck.setText(INVALID_EMAIL);
			qualified = false;
		}

		//check if email is empty
		if (email.getText().equals("")){
			view.emailCheck.setText(EMPTY);
			qualified = false;
		}

		//check if password is allowed
		if (!pw.getText().matches(inputPattern)){
			view.passCheck.setText(WRONG_SYNTAX);
			qualified = false;
		}

		//check if password is empty
		if (pw.getText().equals("")){
			view.passCheck.setText(EMPTY);
			qualified = false;
		}
	
		//check if password retype is empty
		if (pwRe.getText().equals("")){
			view.retypePassCheck.setText(EMPTY);
			qualified = false;
		}
						
		//check if password and password retype is the same
		if (!pw.getText().equals(pwRe.getText())){
			view.retypePassCheck.setText(NOT_SAME_PASSWORD);
			qualified = false;
		}
		
		//check username in database
		if (getModel().checkUsername(newUser)!=null){
			view.internetCheck.setText(getModel().checkUsername(newUser));
			qualified = false;
		}
		
		if (qualified) {
			String pin = generatePin();
			//register user to database
			if (getModel().registerNewUser(newUser, pin)==null){
				//send email with pin number
				sendMail(pin);
				
				//all text field is qualified, go back to login
				switchView(new RegistrationConfirmationView());
			}
		}
		event.consume();
	}
	
	private String generatePin(){
		//generate pin of 4 number
		String pin = "";
		Random random = new Random();
		for (int i=0; i<4; i++){
			pin+=random.nextInt(10);
		}
		return pin;
	}
	
	private void sendMail(String pin){
		
	}
}

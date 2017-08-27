package controller.registration;

import java.util.Random;
import java.util.regex.Matcher;
import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.UserDetails;
import view.LoginView;
import view.RegistrationView;

/**
 * RegistrationController handles input from the registration screen, including user data and parsing.
 * */
public class RegistrationController extends Controller implements EventHandler<ActionEvent>
{
	private static final String EMPTY = "Need to be filled";
	private static final String WRONG_SYNTAX = 
		"Can have only maximum length of 15 of normal alphabet, number and following special character: ,.;:?_-$";
	private static final String NOT_SAME_PASSWORD = "Not the same password";
	RegistrationView view;
	TextField user;
	TextField pw;
	TextField pwRe;
	
	/**
	 * Constructs a controller for the login UI.
	 * @param user The TextField used to input username.
	 * @param pw The TextField used to input password.
	 * @param pwRe The TextField used to retype password.
	 */
	public RegistrationController(RegistrationView view, TextField userField,
			PasswordField pwField, PasswordField pwFieldRetype)
	{
		this.view = view;
		if (userField!=null)
			this.user = userField;
		if (pwField!=null)
			this.pw = pwField;
		if (pwFieldRetype!=null)
			this.pwRe = pwFieldRetype;
	}
	
	@Override
	public void handle(ActionEvent event)
	{
		//create form to send to database
		UserDetails newUser = new UserDetails(user.getText(), pw.getText());
		
		boolean qualified = true;
		String pattern = "^[0-9a-zA-Z,.;:?_-dollar]{0,15}$";
		pattern.replace("dollar", Matcher.quoteReplacement("$"));
		
		//reset alert Text
		view.userCheck.setText("");
		view.passCheck.setText("");
		view.retypePassCheck.setText("");

		//check if user name is allowed
		if (!user.getText().matches(pattern)){
			view.userCheck.setText(WRONG_SYNTAX);
			qualified = false;
		}
		
		//check if user name is empty
		if (user.getText().equals("")){
			view.userCheck.setText(EMPTY);
			qualified = false;
		}

		//check if password is allowed
		if (!pw.getText().matches(pattern)){
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
		
		//check username
		if (getModel().checkUsername(newUser)!=null){
			view.internetCheck.setText(getModel().checkUsername(newUser));
			qualified = false;
		}
		
		if (qualified) {
			//generate pin of 4 number
			String pin = "";
			Random random = new Random();
			for (int i=0; i<4; i++){
				pin.concat(String.valueOf(random.nextInt(10)));
			}
			
			//register user to database
			if (getModel().registerNewUser(newUser, pin)==null){
				//send email with pin number

				//all text field is qualified, go back to login
				switchView(new LoginView());
			}
		}
		event.consume();
	}
}

package controller.registration;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import view.RegistrationView;

/**
 * RegistrationController handles input from the registration screen, including user data and parsing.
 * */
public class RegistrationController implements EventHandler<ActionEvent>
{
	TextField user;
	TextField pw;
	TextField pwRe;
	RegistrationView view;
	
	/**
	 * Constructs a controller for the login UI.
	 * @param user The TextField used to input username.
	 * @param pw The TextField used to input password.
	 * @param pwRe The TextField used to retype password.
	 */
	public RegistrationController(RegistrationView registrationView, TextField userField, PasswordField pwField, PasswordField pwFieldRetype)
	{
		this.view = registrationView;
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
		if (pw==pwRe && pw!=null && pwRe!=null){
			
		} else {
			view.sample.setText("lol");
			
			if (!user.getText().equals("lol"))
			view.sample.setText("Test");
		}
	}
}

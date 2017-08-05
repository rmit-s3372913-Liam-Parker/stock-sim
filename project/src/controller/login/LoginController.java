package controller.login;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;

/**
 * LoginController handles login and input validation.
 * */
public class LoginController implements EventHandler<ActionEvent>
{
	TextField user;
	TextField pw;
	
	/**
	 * Constructs a controller for the login UI.
	 * @param user The TextField used to input username.
	 * @param pw The TextField used to input password.
	 */
	public LoginController(TextField user, TextField pw)
	{
		this.user = user;
		this.pw = pw;
	}
	
	@Override
	public void handle(ActionEvent event) 
	{
		String userString = user.getText();
		String pwString = pw.getText();
		
		System.out.println("TEST OUTPUT: Username: " + userString + " Password: " + pwString);
		
		event.consume();
	}

}

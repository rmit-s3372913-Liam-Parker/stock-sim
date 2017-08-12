package controller.login;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.UserDetails;
import view.DashboardView;
import view.StockApplication;

/**
 * LoginController handles login and input validation.
 * */
public class LoginController extends Controller implements EventHandler<ActionEvent>
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
		Label lblStatus = null;
		
		//TODO: Input validation
		
		// check username if empty
		if(userString.isEmpty()) {
			lblStatus.setText("Please enter a username");
		}
		
		// check password if null
		if(pwString.isEmpty()) {
			lblStatus.setText("Please enter the password");
		}
		
		// check both username & password
		if(userString.isEmpty() && pwString.isEmpty()) {
			lblStatus.setText("Please enter a username and a password");
		}
		
		// connect to database and get match
		//UserDetails user = new UserDetails(userString, pwString);
		//getModel().beginSession(user);
		System.out.println("TEST OUTPUT: Username: " + userString + " Password: " + pwString);
		
		switchView(new DashboardView());
		event.consume();
	}

}

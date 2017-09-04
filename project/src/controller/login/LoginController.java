package controller.login;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import model.UserDetails;
import view.ConfirmationView;
import view.LoginView;
import view.StockApplication;

/**
 * LoginController handles login and input validation.
 * */
public class LoginController extends Controller implements EventHandler<ActionEvent>
{
	LoginView view;
	TextField user;
	TextField pw;
	CheckBox re;
	
	/**
	 * Constructs a controller for the login UI.
	 * @param user The TextField used to input username.
	 * @param pw The TextField used to input password.
	 */
	public LoginController(LoginView view, TextField user, TextField pw, CheckBox re)
	{
		this.view = view;
		this.user = user;
		this.pw = pw;
		this.re = re;
	}
	
	@Override
	public void handle(ActionEvent event) 
	{
		String userString = user.getText();
		String pwString = pw.getText();
		
		view.alert.setText(null);
		
		//TODO: Input validation
		
		// check username if empty
		if(userString.isEmpty()) {
			view.alert.setText("Please enter a username");
		}
		
		// check password if null
		if(pwString.isEmpty()) {
			newline();
			view.alert.setText(view.alert.getText()+"Please enter the password");
		}
		
		
		// connect to database and get match
		UserDetails user = new UserDetails(userString, pwString);
		if (getModel().beginSession(user)==null){
			System.out.println("TEST OUTPUT: Username: " + userString + " Password: " + pwString);
			
			//switchView(new DashboardView());
			switchView(new ConfirmationView());
		}
		event.consume();
	}

	private void newline(){
		if (view.alert.getText()!="")
			view.alert.setText(view.alert.getText()+"\r\n");
	}
}

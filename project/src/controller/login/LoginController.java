package controller.login;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import model.UserDetails;
import view.ConfirmationView;
import view.DashboardView;
import view.LoginView;

/**
 * LoginController handles login and input validation.
 * */
public class LoginController extends Controller implements EventHandler<ActionEvent>
{
	private static final String NOT_CONFIRM = "Email is not confirmed";
			
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
		boolean qualified = true;
		
		view.alert.setText(null);
		
		//TODO: Input validation
		
		// check username if empty
		if(userString.isEmpty()) {
			view.alert.setText("Please enter a username");
			qualified = false;
		}
		
		// check password if null
		if(pwString.isEmpty()) {
			newline();
			view.alert.setText(view.alert.getText()+"Please enter the password");
			qualified = false;
		}
		
		if (qualified){
			// connect to database and get match
			UserDetails user = new UserDetails(userString, pwString);
			String loginAlert = getModel().login(user);
			if (loginAlert==null){
				String confirmAlert = getModel().confirmedUser(user);
				if (confirmAlert==null)
					switchView(new DashboardView());
				else
					if (confirmAlert.equals(NOT_CONFIRM))
						switchView(new ConfirmationView(user));
				newline();
				view.alert.setText(view.alert.getText()+confirmAlert);
			}
			newline();
			view.alert.setText(view.alert.getText()+loginAlert);
		}
		event.consume();
	}

	private void newline(){
		if (view.alert.getText()!="")
			view.alert.setText(view.alert.getText()+"\r\n");
	}
}

package controller.login;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import view.DashboardView;
import view.StockApplication;

/**
 * LoginController handles login and input validation.
 * */
public class LoginController extends Controller
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
		
		//TODO: Input validation
		
		switchView(new DashboardView());
		event.consume();
	}

}

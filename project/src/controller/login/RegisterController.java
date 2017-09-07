package controller.login;

import controller.Controller;
import javafx.event.ActionEvent;
import view.RegistrationView;

/**
 * RegisterController handles registration button and view switch.
 * */
public class RegisterController extends Controller
{
	@Override
	public void handle(ActionEvent event) 
	{
		switchView(new RegistrationView());
		
		event.consume();
	}
}

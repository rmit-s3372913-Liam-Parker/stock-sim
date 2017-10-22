package controller.cancel;

import controller.Controller;
import javafx.event.ActionEvent;
import view.LoginView;

public class ReturnToLoginButtonController  extends Controller
{
	//return the user's view to login
	@Override
	public void handle(ActionEvent event) 
	{
		switchView(new LoginView());
		
		event.consume();
	}
}

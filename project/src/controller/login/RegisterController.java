package controller.login;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.DashboardView;
import view.RegistrationView;
import view.StockApplication;

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

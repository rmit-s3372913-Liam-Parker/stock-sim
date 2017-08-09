package controller.login;

import controller.Controller;
import javafx.event.ActionEvent;
<<<<<<< HEAD
import javafx.event.EventHandler;
import javafx.scene.Scene;
import view.LoginView;
import view.RegistrationView;
=======
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.DashboardView;
import view.RegistrationView;
import view.StockApplication;
>>>>>>> 8259e98c3862cde9156eedea566937cda54dddc9

/**
 * RegisterController handles registration button and view switch.
 * */
public class RegisterController extends Controller
{
	private static final int WINDOW_WIDTH = 1920/2;
	private static final int WINDOW_HEIGHT = 1080/2;
	
	@Override
	public void handle(ActionEvent event) 
	{
<<<<<<< HEAD
		RegistrationView registrationView = new RegistrationView();
		Scene scene = new Scene(registrationView, WINDOW_WIDTH, WINDOW_HEIGHT);
//        stage.setScene(scene);
=======
		switchView(new RegistrationView());
		
>>>>>>> 8259e98c3862cde9156eedea566937cda54dddc9
		event.consume();
	}
}

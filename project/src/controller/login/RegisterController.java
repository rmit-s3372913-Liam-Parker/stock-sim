package controller.login;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import view.LoginView;
import view.RegistrationView;

/**
 * RegisterController handles registration button and view switch.
 * */
public class RegisterController implements EventHandler<ActionEvent> 
{
	private static final int WINDOW_WIDTH = 1920/2;
	private static final int WINDOW_HEIGHT = 1080/2;
	
	@Override
	public void handle(ActionEvent event) 
	{
		RegistrationView registrationView = new RegistrationView();
		Scene scene = new Scene(registrationView, WINDOW_WIDTH, WINDOW_HEIGHT);
//        stage.setScene(scene);
		event.consume();
	}
}

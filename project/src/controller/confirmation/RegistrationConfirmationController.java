package controller.confirmation;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import view.LoginView;

public class RegistrationConfirmationController extends Controller implements EventHandler<ActionEvent>{
		public RegistrationConfirmationController(){
		}
		
		@Override
		public void handle(ActionEvent event) {
			//go back to login
			switchView(new LoginView());
			event.consume();
		}
}

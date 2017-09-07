package controller.confirmation;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;

public class ConfirmationController extends Controller implements EventHandler<ActionEvent>{
	private String pin;
	
	public ConfirmationController(TextField pinField){
		//store pin
		this.pin = pinField.getText();
	}
	
	@Override
	public void handle(ActionEvent event) {
		//compare pin
		
		//activate user account
		//getModel().confirmNewUser(user);
		
	}

}

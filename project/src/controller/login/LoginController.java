package controller.login;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/*
 * LoginController handles interaction with the login screen. Including input and parsing.
 * */
public class LoginController implements EventHandler<ActionEvent>
{
	/* Handles interaction */
	@Override
	public void handle(ActionEvent event) 
	{
		System.out.println("Login pressed!");
		event.consume();
	}

}

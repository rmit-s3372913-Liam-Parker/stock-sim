package view;

import controller.confirmation.ConfirmationController;
import controller.login.LoginController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;

public class RegistrationConfirmationView extends GridPane {
	private static final String TITLE = "Confirmation";
	private static final String EXPLAINATION = "Please enter PIN number sent to your email for confirmation";
	private static final String PIN_LABEL = "PIN:";
	private static final String CONFIRM_BUTTON = "Confirm";
	
	/**
	 *  Constructs a LoginView object with default positioning.
	 **/
	public RegistrationConfirmationView()
	{
		setAlignment(Pos.CENTER);
		setHgap(5);
		setVgap(5);
		populate();
	}
	
	/**
	 * Builds the UI for this pane. UI elements and
	 * any call-backs for functionality should be registered
	 * here.   
	 * */
	public void populate()
	{
		// Setup title
		Text title = new Text(TITLE);
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		add(title, 0, 0, 2, 1);

		// Setup user-name field
		Label pin = new Label(PIN_LABEL);
		add(pin, 0, 1);
		TextField pinField = new TextField();
		add(pinField, 1, 1);
		
		// Explaination
		Text explaination = new Text(EXPLAINATION);
		explaination.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
		add(explaination, 0, 0, 2, 1);

		// Set up buttons
		Button confirmButton = new Button(CONFIRM_BUTTON);
		confirmButton.setOnAction(new ConfirmationController(pinField));
		add(confirmButton, 1, 3);
	}
}

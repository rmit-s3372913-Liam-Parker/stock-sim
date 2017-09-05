package view;

import controller.confirmation.CancelButtonController;
import controller.confirmation.ConfirmationController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ConfirmationView extends GridPane {
	private static final String TITLE = "Confirmation";
	private static final String EXPLAINATION = "Please enter PIN number sent to your email for confirmation";
	private static final String PIN_LABEL = "PIN:";
	private static final String CONFIRM_BUTTON = "Confirm";
	private static final String CANCEL_BUTTON = "Cancel";
	
	/**
	 *  Constructs a ConfirmationView object with default positioning.
	 **/
	public ConfirmationView()
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
		add(explaination, 0, 2, 2, 1);

		// Set up buttons
		Button confirmButton = new Button(CONFIRM_BUTTON);
		confirmButton.setOnAction(new ConfirmationController(pinField));
		Button cancelButton = new Button(CANCEL_BUTTON);
		cancelButton.setOnAction(new CancelButtonController());
		add(cancelButton, 1, 3);
		HBox buttons = new HBox(2.5);
		buttons.getChildren().addAll(confirmButton, cancelButton);
		add(buttons, 1, 5);
	}
}

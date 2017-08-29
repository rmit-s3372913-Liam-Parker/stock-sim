package view;

import controller.confirmation.RegistrationConfirmationController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class RegistrationConfirmationView extends GridPane {
	private static final String CONFIRMATION_TITLE = "Registration Complete";
	private static final String DESCRIPTION_LABEL = "A confirmation mail is sent to your email";
	private static final String CONFIRM_BUTTON = "Confirm";
	
	public RegistrationConfirmationView()
	{
		setAlignment(Pos.CENTER);
		setHgap(5);
		setVgap(5);
		populate();
	}
	
	public void populate()
	{
		// Setup title
		Text confirmTitle = new Text(CONFIRMATION_TITLE);
		confirmTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		add(confirmTitle, 0, 0, 2, 1);

		// Setup description
		Label username = new Label(DESCRIPTION_LABEL);
		add(username, 0, 1);
		
		// Set up buttons
		Button confirmButton = new Button(CONFIRM_BUTTON);
		confirmButton.setOnAction(new RegistrationConfirmationController());
		add(confirmButton, 1, 2);
	}
}

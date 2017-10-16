package view;

import controller.cancel.ReturnToLoginButtonController;
import controller.registration.RegistrationController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * RegistrationView contains any UI code relevant to the registration screen.
 * */
public class RegistrationView extends BorderPane
{	
	private static final String REGISTRATION_TITLE = "ASX Registration";
	private static final String USERNAME_LABEL = "Username:";
	private static final String EMAIL_LABEL = "Email:";
	private static final String PASSWORD_LABEL = "Password:";
	private static final String RETYPE_PASSWORD_LABEL = "Retype Password:";
	
	private static final String REGISTER_BUTTON = "Register";
	private static final String CANCEL_BUTTON = "Cancel";

	public Text userCheck = new Text();
	public Text emailCheck = new Text();
	public Text passCheck = new Text();
	public Text retypePassCheck = new Text();
	public Text internetCheck = new Text();
	
	public RegistrationView()
	{
		setCenter(addGridPane());
	}

	private GridPane addGridPane(){
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);

		// Setup title
		Text registrationTitle = new Text(REGISTRATION_TITLE);
		registrationTitle.setFont(StockApplication.APP_HEADING_FONT);
		gridPane.add(registrationTitle, 0, 0, 2, 1);

		// Setup user-name field
		Label username = new Label(USERNAME_LABEL);
		gridPane.add(username, 0, 1);
		TextField usernameField = new TextField();
		gridPane.add(usernameField, 1, 1);

		//Creating an alert Text 
		gridPane.add(userCheck, 2, 1);
		
		// Setup email field
		Label email = new Label(EMAIL_LABEL);
		gridPane.add(email, 0, 2);
		TextField emailField = new TextField();
		gridPane.add(emailField, 1, 2);

		//Creating an alert Text 
		gridPane.add(emailCheck, 2, 2);

		// Setup password field
		Label password = new Label(PASSWORD_LABEL);
		gridPane.add(password, 0, 3);
		PasswordField passwordField = new PasswordField();
		gridPane.add(passwordField, 1, 3);

		//Creating an alert Text 
		gridPane.add(passCheck, 2, 3);
		
		// Setup password retype field
		Label retypePassword = new Label(RETYPE_PASSWORD_LABEL);
		gridPane.add(retypePassword, 0, 4);
		PasswordField retypePasswordField = new PasswordField();
		gridPane.add(retypePasswordField, 1, 4);

		//Creating an alert Text 
		gridPane.add(retypePassCheck, 2, 4);
		
		//Creating an alert Text 
		gridPane.add(internetCheck, 1, 5);
		
		// Set up buttons
		Button registerButton = new Button(REGISTER_BUTTON);
		registerButton.setOnAction(new RegistrationController(this, usernameField, emailField, passwordField, retypePasswordField));
		gridPane.add(registerButton, 1, 3);
		Button cancelButton = new Button(CANCEL_BUTTON);
		cancelButton.setOnAction(new ReturnToLoginButtonController());
		gridPane.add(cancelButton, 1, 4);
		HBox buttons = new HBox(2.5);
		buttons.getChildren().addAll(registerButton, cancelButton);
		gridPane.add(buttons, 1, 6);
		return gridPane;
	}
}

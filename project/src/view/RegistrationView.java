package view;

import controller.registration.RegistrationController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * RegistrationView contains any UI code relevant to the registration screen.
 * */
public class RegistrationView extends BorderPane
{	
	private static final String REGISTRATION_TITLE = "ASX Registration";
	private static final String USERNAME_LABEL = "Username:";
	private static final String PASSWORD_LABEL = "Password:";
	private static final String RETYPE_PASSWORD_LABEL = "Retype Password:";
	
	private static final String REGISTER_BUTTON = "Register";

	public Text userCheck = new Text();
	public Text passCheck = new Text();
	public Text retypePassCheck = new Text();
	public RegistrationView()
	{
		populate();
	}
	
	/**
	 * Builds the UI for this pane. UI elements and
	 * any call-backs for functionality should be registered
	 * here.   
	 * */
	public void populate()
	{
		setCenter(addGridPane());
	}
	
	private GridPane addGridPane(){
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		
		// Setup title
		Text registrationTitle = new Text(REGISTRATION_TITLE);
		registrationTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		gridPane.add(registrationTitle, 0, 0, 2, 1);
		
		// Setup user-name field
		Label username = new Label(USERNAME_LABEL);
		gridPane.add(username, 0, 1);
		TextField usernameField = new TextField();
		gridPane.add(usernameField, 1, 1);

		//Creating an alert Text 
		gridPane.add(userCheck, 2, 1);

		// Setup password field
		Label password = new Label(PASSWORD_LABEL);
		gridPane.add(password, 0, 2);
		PasswordField passwordField = new PasswordField();
		gridPane.add(passwordField, 1, 2);

		//Creating an alert Text 
		gridPane.add(passCheck, 2, 2);
		
		// Setup password retype field
		Label retypePassword = new Label(RETYPE_PASSWORD_LABEL);
		gridPane.add(retypePassword, 0, 3);
		PasswordField retypePasswordField = new PasswordField();
		gridPane.add(retypePasswordField, 1, 3);

		//Creating an alert Text 
		gridPane.add(retypePassCheck, 2, 3);
		
		// Set up buttons
		Button registerButton = new Button(REGISTER_BUTTON);
		registerButton.setOnAction(new RegistrationController(this, usernameField, passwordField, retypePasswordField));
		return gridPane;
	}
}

package view;

import controller.registration.RegistrationController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * RegistrationView contains any UI code relevant to the registration screen.
 * */
public class RegistrationView extends GridPane
{
	private static final String REGISTRATION_TITLE = "Register";
	private static final String USERNAME_LABEL = "Username:";
	private static final String PASSWORD_LABEL = "Password:";
	private static final String RETYPE_PASSWORD_LABEL = "Retype Password:";
	
	private static final String REGISTER_BUTTON = "Register";
	
	public Text sample;//to b removed
	
	/**
	 *  Constructs a LoginView object with default positioning.
	 **/
	public RegistrationView()
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
		Text loginTitle = new Text(REGISTRATION_TITLE);
		loginTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		add(loginTitle, 0, 0, 2, 1);

		// Setup user-name field
		Label username = new Label(USERNAME_LABEL);
		add(username, 0, 1);
		TextField usernameField = new TextField();
		usernameField.setOnAction(new RegistrationController(this, usernameField, null, null));
		add(usernameField, 1, 1);

		//Creating a Text object 
		sample = new Text(); 
		add(sample, 2, 1);

		// Setup password field
		Label password = new Label(PASSWORD_LABEL);
		add(password, 0, 2);
		PasswordField passwordField = new PasswordField();
		add(passwordField, 1, 2);
		
		// Setup password retype field
		Label retypePassword = new Label(RETYPE_PASSWORD_LABEL);
		add(retypePassword, 0, 3);
		PasswordField retypePasswordField = new PasswordField();
		add(retypePasswordField, 1, 3);
		
		// Set up buttons
		Button registerButton = new Button(REGISTER_BUTTON);
		registerButton.setOnAction(new RegistrationController(this, usernameField, passwordField, retypePasswordField));
		add(registerButton, 1, 3);
		HBox buttons = new HBox(2.5);
		buttons.getChildren().addAll(registerButton);
		add(buttons, 1, 5);
	}
}
package view;

import controller.login.LoginController;
import controller.login.RegisterController;
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

/*
 * LoginView contains any UI code relevant to the login screen. This class also
 * constructs and registers any relevant
 * */
public class LoginView extends GridPane 
{
	private static final String LOGIN_TITLE = "ASX Simulator";
	private static final String USERNAME_LABEL = "Username:";
	private static final String PASSWORD_LABEL = "Password:";
	private static final String REMEMBER_LABEL = "Remember:";
	
	private static final String LOGIN_BUTTON = "Login";
	private static final String REGISTER_BUTTON = "Register";
	
	/* Constructs a LoginView object centered in the pane. */
	public LoginView()
	{
		setAlignment(Pos.CENTER);
		setHgap(5);
		setVgap(5);
		populate();
	}
	
	/* Builds the UI for this pane. UI elements and
	 * any call-backs for functionality should be registered
	 * here.   
	 * */
	public void populate()
	{
		// Setup title
		Text loginTitle = new Text(LOGIN_TITLE);
		loginTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		add(loginTitle, 0, 0, 2, 1);

		// Setup user-name field
		Label username = new Label(USERNAME_LABEL);
		add(username, 0, 1);
		TextField usernameField = new TextField();
		add(usernameField, 1, 1);
		
		// Setup password field and remember toggle
		Label password = new Label(PASSWORD_LABEL);
		add(password, 0, 2);
		PasswordField passwordField = new PasswordField();
		add(passwordField, 1, 2);
		Label rPassword = new Label(REMEMBER_LABEL);
		add(rPassword, 2, 2);
		CheckBox rPasswordCheckbox = new CheckBox();
		add(rPasswordCheckbox, 3, 2);
		
		// Set up buttons
		Button loginButton = new Button(LOGIN_BUTTON);
		loginButton.setOnAction(new LoginController(usernameField, passwordField));
		Button registerButton = new Button(REGISTER_BUTTON);
		registerButton.setOnAction(new RegisterController());
		add(registerButton, 1, 3);
		HBox buttons = new HBox(2.5);
		buttons.getChildren().addAll(loginButton, registerButton);
		add(buttons, 1, 5);
	}
	
	
}

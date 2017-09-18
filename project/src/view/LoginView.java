package view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import controller.login.ForgotPasswordController;
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

/**
 * LoginView contains any UI code relevant to the login screen. This class also
 * constructs and registers any relevant
 * */
public class LoginView extends GridPane 
{
	private static final String LOGIN_TITLE = "ASX Simulator";
	private static final String USERNAME_LABEL = "Username:";
	private static final String PASSWORD_LABEL = "Password:";
	private static final String REMEMBER_LABEL = "Remember:";
	private static final String FORGOT_PASSWORD_LABEL = "Forgot Password";
	
	private static final String LOGIN_BUTTON = "Login";
	private static final String REGISTER_BUTTON = "Register";
	private static final String FORGOT_PASSWORD_BUTTON = "Forgot Password";
	

	private TextField usernameField = new TextField();
	private PasswordField passwordField = new PasswordField();
	private TextField forgotPasswordField = new PasswordField();
	private CheckBox rPasswordCheckbox = new CheckBox();
	public Text alert = new Text();
	
	/**
	 *  Constructs a LoginView object with default positioning.
	 **/
	public LoginView()
	{
		setAlignment(Pos.CENTER);
		setHgap(5);
		setVgap(5);
		populate();
		checkRemember();
	}
	
	/**
	 * Builds the UI for this pane. UI elements and
	 * any call-backs for functionality should be registered
	 * here.   
	 * */
	private void populate()
	{
		// Setup title
		Text loginTitle = new Text(LOGIN_TITLE);
		loginTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		add(loginTitle, 0, 0, 2, 1);

		// Setup user-name field
		Label username = new Label(USERNAME_LABEL);
		add(username, 0, 1);
		add(usernameField, 1, 1);
		
		// Setup password field and remember toggle
		Label password = new Label(PASSWORD_LABEL);
		add(password, 0, 2);
		add(passwordField, 1, 2);
		
		Label rPassword = new Label(REMEMBER_LABEL);
		add(rPassword, 2, 2);
		add(rPasswordCheckbox, 3, 2);
		
		//Creating an alert Text 
		add(alert, 0, 4);
		
		// Set up buttons
		Button loginButton = new Button(LOGIN_BUTTON);
		loginButton.setOnAction(new LoginController(this, usernameField, passwordField, rPasswordCheckbox));
		Button registerButton = new Button(REGISTER_BUTTON);
		registerButton.setOnAction(new RegisterController());
		Button forgotPasswordButton = new Button(FORGOT_PASSWORD_BUTTON);
		forgotPasswordButton.setOnAction(new ForgotPasswordController());
		add(registerButton, 1, 3);
		HBox buttons = new HBox(2.5);
		buttons.getChildren().addAll(loginButton, registerButton,forgotPasswordButton);
		add(buttons, 1, 5);
	}
	
	private void checkRemember(){
		try {
			FileReader reader = new FileReader("./dataStorage/lastLogin.txt");
			BufferedReader bufferedReader = new BufferedReader(reader);
 
            String line;
 
            if ((line = bufferedReader.readLine()) != null) {
                usernameField.setText(line);
            }

            if ((line = bufferedReader.readLine()) != null) {
                passwordField.setText(line);
                rPasswordCheckbox.setSelected(true);
            }
            
            reader.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

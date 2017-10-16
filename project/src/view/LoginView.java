package view;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.Random;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

import controller.login.ForgotPasswordController;
import controller.login.LoginController;
import controller.login.RegisterController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Pair;


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
	
	private static final String LOGIN_BUTTON = "Login";
	private static final String REGISTER_BUTTON = "Register";
	private static final String FORGOT_PASSWORD_BUTTON = "Forgot Password";

	private TextField usernameField = new TextField();
	private PasswordField passwordField = new PasswordField();
	private CheckBox passwordCheckbox = new CheckBox();

	public Text alert = new Text();
	
	/**
	 *  Constructs a LoginView object with default positioning.
	 **/
	public LoginView()
	{
		// Basic position setup
		setAlignment(Pos.CENTER);
		setHgap(5);
		setVgap(5);

		// Setup title
		Text loginTitle = new Text(LOGIN_TITLE);
		loginTitle.setFont(StockApplication.APP_HEADING_FONT);
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
		add(passwordCheckbox, 3, 2);

		//Creating an alert Text
		add(alert, 0, 4);

		// Set up buttons
		Button loginButton = new Button(LOGIN_BUTTON);
		loginButton.setOnAction(new LoginController(this));
		Button registerButton = new Button(REGISTER_BUTTON);
		registerButton.setOnAction(new RegisterController());
		Button forgotPasswordButton = new Button(FORGOT_PASSWORD_BUTTON);
		forgotPasswordButton.setOnAction(new ForgotPasswordController(this));

		add(registerButton, 1, 3);
		HBox buttons = new HBox(2.5);
		buttons.getChildren().addAll(loginButton, registerButton,forgotPasswordButton);
		add(buttons, 1, 5);
	}

	public TextField getUserNameText()
	{
		return usernameField;
	}

	public TextField getPasswordText()
	{
		return passwordField;
	}

	public CheckBox getPasswordCheckbox()
	{
		return passwordCheckbox;
	}
}

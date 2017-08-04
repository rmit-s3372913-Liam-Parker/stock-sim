package view;

import controller.login.LoginController;
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
		Text loginTitle = new Text("ASX Simulator");
		loginTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		add(loginTitle, 0, 0, 2, 1);

		// Setup user-name field
		Label username = new Label("User Name:");
		add(username, 0, 1);
		TextField usernameField = new TextField();
		add(usernameField, 1, 1);
		
		// Setup password field and remember toggle
		Label password = new Label("Password:");
		add(password, 0, 2);
		PasswordField passwordField = new PasswordField();
		add(passwordField, 1, 2);
		Label rPassword = new Label("Remember:");
		add(rPassword, 2, 2);
		CheckBox rPasswordCheckbox = new CheckBox();
		add(rPasswordCheckbox, 3, 2);
		
		// Set up buttons
		Button loginButton = new Button("Login");
		loginButton.setOnAction(new LoginController());
		Button registerButton = new Button("Register");
		add(registerButton, 1, 3);
		HBox buttons = new HBox(2.5);
		buttons.getChildren().addAll(loginButton, registerButton);
		add(buttons, 1, 5);
	}
	
	
}

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
		//forgotPasswordButton.setOnAction(new ForgotPasswordController());
		forgotPasswordButton.setOnAction((event)->{
			try {
				verifyRecoveryEmail();
			} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
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
	
	private void verifyRecoveryEmail() throws UnsupportedEncodingException, NoSuchAlgorithmException 
	{
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Password Recovery");
		dialog.setHeaderText("Please Enter Username & Email");

		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Submit", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField username = new TextField();
		username.setPromptText("Username");
		TextField emailField = new TextField();
		emailField.setPromptText("Email");

		grid.add(new Label("Username:"), 0, 0);
		grid.add(username, 1, 0);
		grid.add(new Label("Email:"), 0, 1);
		grid.add(emailField, 1, 1);

		// Enable/Disable login button depending on whether a username was entered.
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		username.textProperty().addListener((observable, oldValue, newValue) -> {
		    loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		Platform.runLater(() -> username.requestFocus());

		// Convert the result to a username-password-pair when the login button is clicked.
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == loginButtonType) {
		        return new Pair<>(username.getText(), emailField.getText());
		    }
		    return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();
		String userName;
		String email;
		if(result.isPresent()) {
			userName = result.get().getKey();
			email = result.get().getValue();
			System.out.println(userName + " : " + email);
			String userEmail = view.StockApplication.getModel().getUserEmailByUsername(userName);
			if(email.equals(userEmail))
			{

				StringBuilder pin = new StringBuilder();
				Random random = new Random();
				for (int i=0; i<5; i++){
					pin.append(random.nextInt(10));
				}
				
				String subject = "ASX Simulator Account Verification";
				Email from = new Email("asxsimulator@gmail.com");
			    Email to = new Email(email);
			    Content content = new Content("text/plain", "Please enter the following pin into your currently active application to verify. \n\n "
			    							  + pin.toString() + "\n\n"
			    							  + " If you fail to enter this pin before you close your application a new pin will need to be generated by the application."
			    							  + " Best enter your pin now before you forget!");
			    
			    Mail mail = new Mail(from, subject, to, content);

			    SendGrid sg = new SendGrid(System.getenv("SENDGRID_API"));
			    Request request = new Request();
			    try 
			    {
			    	request.setMethod(Method.POST);
			    	request.setEndpoint("mail/send");
			     	request.setBody(mail.build());
			     	Response response = sg.api(request);
			     	System.out.println(response.getStatusCode());
			     	System.out.println(response.getBody());
			     	System.out.println(response.getHeaders());
			    } 
			    catch (IOException ex) { ex.printStackTrace(); }
			    
			    String resp = view.StockApplication.getModel().updateUserPinByUsername(userName, pin.toString());
			    if(resp.equals("DONE")) {
			    	TextInputDialog dialogPin = new TextInputDialog("");
			    	dialogPin.setTitle("Pin");
			    	dialogPin.setHeaderText("Enter Pin Sent on Email");
			    	dialogPin.setContentText("Pin: ");

			    	// Traditional way to get the response value.
			    	Optional<String> resultPin = dialogPin.showAndWait();
			    	String userPin;
			    	if (resultPin.isPresent()){
			    		userPin = resultPin.get();
			    	    String savedPin = view.StockApplication.getModel().getUserPinByUsername(userName);
			    	    if(userPin.equals(savedPin)) {

			    	    	boolean matched = false;
			    	    	while(!matched) {
				    	    	Dialog<Pair<String, String>> dialogPassword = new Dialog<>();
				    	    	dialogPassword.setTitle("Password Recovery");
				    	    	dialogPassword.setHeaderText("Please Enter New Password & Confirm");

				    			// Set the button types.
				    			ButtonType loginButtonType2 = new ButtonType("Submit", ButtonData.OK_DONE);
				    			dialogPassword.getDialogPane().getButtonTypes().addAll(loginButtonType2, ButtonType.CANCEL);

				    			// Create the username and password labels and fields.
				    			GridPane grid2 = new GridPane();
				    			grid2.setHgap(10);
				    			grid2.setVgap(10);
				    			grid2.setPadding(new Insets(20, 150, 10, 10));

				    			PasswordField password = new PasswordField();
				    			PasswordField confirmPassword = new PasswordField();

				    			grid2.add(new Label("New Password:"), 0, 0);
				    			grid2.add(password, 1, 0);
				    			grid2.add(new Label("Confirm Password:"), 0, 1);
				    			grid2.add(confirmPassword, 1, 1);

				    			// Enable/Disable login button depending on whether a username was entered.
				    			Node loginButton2 = dialogPassword.getDialogPane().lookupButton(loginButtonType2);
				    			loginButton2.setDisable(true);

				    			// Do some validation (using the Java 8 lambda syntax).
				    			password.textProperty().addListener((observable, oldValue, newValue) -> {
				    			    loginButton2.setDisable(newValue.trim().isEmpty());
				    			});

				    			dialogPassword.getDialogPane().setContent(grid2);

				    			// Request focus on the username field by default.
				    			Platform.runLater(() -> password.requestFocus());

				    			// Convert the result to a username-password-pair when the login button is clicked.
				    			dialogPassword.setResultConverter(dialogButton -> {
				    			    if (dialogButton == loginButtonType2) {
				    			        return new Pair<>(password.getText(), confirmPassword.getText());
				    			    }
				    			    return null;
				    			});

				    			Optional<Pair<String, String>> result2 = dialogPassword.showAndWait();
				    			
				    			if(result2.isPresent()) {
				    				if(result2.get().getKey().equals(result2.get().getValue())) {
				    					String status = view.StockApplication.getModel().updateUserPasswordByUsername(userName,result2.get().getValue());
				    					if(status.equals("DONE")) {
				    						matched=true;
				    						Alert alert = new Alert(AlertType.INFORMATION);
				    						alert.setTitle("Information Dialog");
				    						alert.setHeaderText("Look, Password Updated");
				    						alert.setContentText("Your password is updated. You may Login!");

				    						alert.showAndWait();
				    					}
				    				}else {
				    					Alert alert = new Alert(AlertType.ERROR);
				    					alert.setTitle("Error Dialog");
				    					alert.setHeaderText("Look, Password didn't Match");
				    					alert.setContentText("Ooops, Password didn't Match");

				    					alert.showAndWait();
				    				}
				    			}
			    	    	}
			    	    }else {
			    	    	Alert alert = new Alert(AlertType.ERROR);
	    					alert.setTitle("Error Dialog");
	    					alert.setHeaderText("Look, Incorrect Pin");
	    					alert.setContentText("Pin You Entered Doesn't match pin sent by Email");

	    					alert.showAndWait();
			    	    }
			    	}

			    	// The Java 8 way to get the response value (with lambda expression).
			    	result.ifPresent(name -> System.out.println("Your name: " + name));
			    }
			}else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Look, Incorrect Email !!");
				alert.setContentText("Email You Entered doesn't match email saved");

				alert.showAndWait();
			}
		}
	}
}

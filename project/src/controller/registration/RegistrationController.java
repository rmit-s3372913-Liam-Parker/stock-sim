package controller.registration;

import java.io.IOException;
import java.util.Random;
import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.UserDetails;
import ultilities.InputValidation;
import view.ConfirmationView;
import view.RegistrationView;
import com.sendgrid.*;

/**
 * RegistrationController handles input from the registration screen, including user data and parsing.
 * */
public class RegistrationController extends Controller implements EventHandler<ActionEvent>
{
	private static final String EMPTY = "Need to be filled";
	private static final String WRONG_SYNTAX = 
		"Can have only maximum length of 15 of normal alphabet, number and following special character: ,.;:?_-$";
	private static final String INVALID_EMAIL = "Please enter a valid email";
	private static final String NOT_SAME_PASSWORD = "Not the same password";
	RegistrationView view;
	TextField user;
	TextField email;
	TextField pw;
	TextField pwRe;
	
	/**
	 * Constructs a controller for the login UI.
	 * @param user The TextField used to input username.
	 * @param pw The TextField used to input password.
	 * @param pwRe The TextField used to retype password.
	 */
	public RegistrationController(RegistrationView view, TextField userField, TextField emailField,
			PasswordField pwField, PasswordField pwFieldRetype)
	{
		this.view = view;
		if (userField!=null)
			this.user = userField;
		if (emailField!=null)
			this.email = emailField;
		if (pwField!=null)
			this.pw = pwField;
		if (pwFieldRetype!=null)
			this.pwRe = pwFieldRetype;
	}
	
	@Override
	public void handle(ActionEvent event)
	{
		//create form to send to database
		UserDetails newUser = new UserDetails(user.getText(), pw.getText(), email.getText());
		
		boolean qualified = true;
		
		//reset alert Text
		view.userCheck.setText("");
		view.passCheck.setText("");
		view.retypePassCheck.setText("");

		//check if user name is allowed
		if (!InputValidation.inputValidation(user.getText()))
		{
			view.userCheck.setText(WRONG_SYNTAX);
			qualified = false;
		}

		//check if user name is empty
		if (user.getText().equals(""))
		{
			view.userCheck.setText(EMPTY);
			qualified = false;
		}

		//check if email is allowed
		if (!InputValidation.emailValidation(email.getText()))
		{
			view.emailCheck.setText(INVALID_EMAIL);
			qualified = false;
		}

		//check if email is empty
		if (email.getText().equals(""))
		{
			view.emailCheck.setText(EMPTY);
			qualified = false;
		}

		//check if password is allowed
		if (!InputValidation.inputValidation(pw.getText()))
		{
			view.passCheck.setText(WRONG_SYNTAX);
			qualified = false;
		}

		//check if password is empty
		if (pw.getText().equals(""))
		{
			view.passCheck.setText(EMPTY);
			qualified = false;
		}
	
		//check if password retype is empty
		if (pwRe.getText().equals(""))
		{
			view.retypePassCheck.setText(EMPTY);
			qualified = false;
		}
						
		//check if password and password retype is the same
		if (!pw.getText().equals(pwRe.getText()))
		{
			view.retypePassCheck.setText(NOT_SAME_PASSWORD);
			qualified = false;
		}

		if (qualified) 
		{
			//check user name in database
			if (getModel().checkUsername(newUser)!=null)
			{
				view.internetCheck.setText(getModel().checkUsername(newUser));
				qualified = false;
			}
		}
		
		if (qualified) 
		{
			String pin = generatePin();
			//register user to database
			String alert = getModel().registerNewUser(newUser, pin);
			if (alert == null)
			{
				//send email with pin number
				sendMail(email.getText(), pin);
				
				//all text field is qualified, go back to login
				switchView(new ConfirmationView(newUser));
			} else {
				view.internetCheck.setText(alert);
			}
		}
		event.consume();
	}
	
	private String generatePin()
	{
		//generate pin of 4 number
		String pin = "";
		Random random = new Random();
		for (int i=0; i<5; i++){
			pin+=random.nextInt(10);
		}
		return pin;
	}
	
	/**
	 * Sends an email containing registration confirmation data.
	 * 
	 * Reference: 
	 * 
	 * 			Sendgrid integration tutorial 
	 * 			https://app.sendgrid.com/guide/integrate/langs/java
	 * 
	 * 			Run the following shell command in your local environment otherwise email won't work.
	 * 			 
	 * 				echo "export SENDGRID_API_KEY='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'" > sendgrid.env
	 * 
	 * @param email The email for the pin to be sent to.
	 * @param pin The pin to send.
	 */
	private void sendMail(String email, String pin)
	{
		String subject = "ASX Simulator Account Verification";
		Email from = new Email("asxsimulator@gmail.com");
	    Email to = new Email(email);
	    Content content = new Content("text/plain", "Please enter the following pin into your currently active application to verify. \n\n "
	    							  + pin + "\n\n" 
	    							  + " If you fail to enter this pin before you close your application a new pin will need to be generated by the application."
	    							  + " Best enter your pin now before you forget!");
	    
	    Mail mail = new Mail(from, subject, to, content);

	    SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
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
	}
}

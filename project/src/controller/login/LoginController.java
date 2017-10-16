package controller.login;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import model.UserDetails;
import ultilities.InputValidation;
import view.ConfirmationView;
import view.DashboardView;
import view.LoginView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * LoginController handles login and input validation.
 * */
public class LoginController extends Controller implements EventHandler<ActionEvent>
{
	private static final String WRONG_SYNTAX = "Can have only maximum length of 15 of normal alphabet, number and following special character: ,.;:?_-$";
	private static final String REMEMBER_DETAILS_PATH = "./dataStorage/lastLogin.txt";
	private LoginView view;
	
	/**
	 * Constructs a controller for the login UI.
	 * @param view The view associated to this controller.
	 */
	public LoginController(LoginView view)
	{
		this.view = view;
		checkRemember();
	}
	
	@Override
	public void handle(ActionEvent event) 
	{
		String userString = view.getUserNameText().getText();
		String pwString = view.getPasswordText().getText();
		boolean qualified = true;
		
		view.alert.setText(null);
		
		//Input validation
		// check username if empty
		if (userString.isEmpty())
		{
			view.alert.setText("Please enter a username");
			qualified = false;
		}
		
		// check password if empty
		if (pwString.isEmpty())
		{
			newline();
			view.alert.setText(view.alert.getText()+"Please enter the password");
			qualified = false;
		}

		//check if password is allowed
		if (!InputValidation.inputValidation(pwString) || !InputValidation.inputValidation(userString))
		{
			newline();
			view.alert.setText(view.alert.getText() + WRONG_SYNTAX);
			qualified = false;
		}
		
		if (qualified)
		{
			// connect to database and get match
			UserDetails user = new UserDetails(userString, pwString);
			String loginAlert = getModel().login(user);
			if (loginAlert == null)
			{
				String confirmAlert = getModel().confirmedUser(user);
				if (view.getPasswordCheckbox().isSelected())
					user.setRemember(true);
				if (confirmAlert == null)
				{
					getModel().beginSession(user);
					switchView(new DashboardView());
				}
				else
					if (confirmAlert.equals("Email is not confirmed"))
						switchView(new ConfirmationView(user));
				newline();
				view.alert.setText(view.alert.getText() + confirmAlert);
			}
			newline();
			view.alert.setText(view.alert.getText() + loginAlert);
		}
		event.consume();
	}

	private void newline(){
		if (view.alert.getText().equals(""))
			view.alert.setText(view.alert.getText()+"\r\n");
	}

	// Retrieves saved information if required.
	private void checkRemember()
	{
		try(FileReader reader = new FileReader(REMEMBER_DETAILS_PATH))
		{
			BufferedReader bufferedReader = new BufferedReader(reader);

			String line;

			if ((line = bufferedReader.readLine()) != null)
			{
				view.getUserNameText().setText(line);
			}

			if ((line = bufferedReader.readLine()) != null)
			{
				view.getPasswordText().setText(line);
				view.getPasswordCheckbox().setSelected(true);
			}
		}
		catch (IOException e)
		{
			displayExceptionModal(e, "Something went wrong when trying to read saved login information.");

			// Reset the input fields
			view.getUserNameText().setText("");
			view.getPasswordText().setText("");
			view.getPasswordCheckbox().setSelected(false);
		}
	}
}

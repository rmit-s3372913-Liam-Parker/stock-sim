package controller.confirmation;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import model.UserDetails;
import ultilities.InputValidation;
import view.ConfirmationView;
import view.DashboardView;

public class ConfirmationController extends Controller implements EventHandler<ActionEvent>
{
	private UserDetails user;
	private TextField pinField;
	private ConfirmationView view;
	
	public ConfirmationController(UserDetails user, TextField pinField, ConfirmationView view)
	{
		this.user = user;
		this.pinField = pinField;
		this.view = view;
	}
	
	/* validate entered pin then show appropriate warning upon incorrect pin
	 * or change user's view to dashboard if correct pin is entered
	 */
	@Override
	public void handle(ActionEvent event) 
	{
		//validate pin field format
		if (InputValidation.pinValidation((pinField.getText()))){
			//compare pin and activate user account
			String alert = getModel().confirmNewUser(user, pinField.getText());
			
			if (alert==null)
			{
				//store user's information
				getModel().beginSession(user);
				//change user's view to dashboard
				switchView(new DashboardView());
			}
			else
			{
				view.warning.setText(alert);
			}
		} 
		else
		{
			view.warning.setText("Incorrect pin");
		}
		event.consume();
	}

}

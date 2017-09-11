package controller.confirmation;

import controller.Controller;
import javafx.event.ActionEvent;
import view.LoginView;

public class CancelButtonController  extends Controller
{
	@Override
	public void handle(ActionEvent event) 
	{
		switchView(new LoginView());
		
		event.consume();
	}

}

package controller.dashboard;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import view.LoginView;

/**
 * Responsible for handling interaction with the tool-bar on the main dash-board.
 */
public class ToolbarController extends Controller
{
	private static final String CONFIRMATION_MSG = "Are you sure you want to log out?";
	
	Text title = new Text(CONFIRMATION_MSG);
	
	@Override
	public void handle(ActionEvent event) 
	{
		if(this.displayQuestionModal(CONFIRMATION_MSG))
		{
			this.getModel().endSession();
			this.switchView(new LoginView());
		}
	}

}

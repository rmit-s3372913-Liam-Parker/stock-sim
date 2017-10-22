package controller.dashboard;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ultilities.InputValidation;

public class SendMessageController extends Controller
{
	private static String INVALID_INPUT = "Message include bad characters";
	
	public SendMessageController (Stage dialog, Text alert, String receiverUsername, String message)
	{
		String error;
		
		//check if message is ok
		if (!InputValidation.inputValidation(message))
		{
			alert.setText(INVALID_INPUT);
			return;
		}
		
		//store the transaction on cloud database
		if ((error = getModel().sendMessage(receiverUsername, message)) == null)
			dialog.hide();
		else
			alert.setText(error);
	}

	@Override
	public void handle(ActionEvent event) {		
	}
}

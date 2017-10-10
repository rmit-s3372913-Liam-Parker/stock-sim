package controller.dashboard;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SendFriendRequestController extends Controller {

	public SendFriendRequestController(Stage dialog, Text alert, String receiverUsername)
	{
		String error;
		
		//store the transaction on cloud database
		if ((error = getModel().sendFriendRequest(receiverUsername)) == null)
			dialog.hide();
		else
			alert.setText(error);
	}

	@Override
	public void handle(ActionEvent event) {
	}

}

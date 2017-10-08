package controller.dashboard;

import java.util.Date;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.MoneyTransaction;
import model.TransactionType;

public class SendMessageController extends Controller
{
	final private int NOT_USED = 0;
	final private String NOT_ENOUGH = "Not enough winning";
	
	public SendMessageController (Stage dialog, Text alert, String receiverUsername, String message)
	{
		int transactionId = NOT_USED;

		//will be generated by database
		Date time = null;
		
		String error;
		
		//check if message is ok
		
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
